package msacqrses;

import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.contextsupport.spring.AnnotationDriven;
import org.axonframework.domain.EventMessage;
import org.axonframework.eventhandling.ClassNamePrefixClusterSelector;
import org.axonframework.eventhandling.Cluster;
import org.axonframework.eventhandling.ClusterSelector;
import org.axonframework.eventhandling.ClusteringEventBus;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventBusTerminal;
import org.axonframework.eventhandling.SimpleCluster;
import org.axonframework.eventhandling.replay.DiscardingIncomingMessageHandler;
import org.axonframework.eventhandling.replay.IncomingMessageHandler;
import org.axonframework.eventhandling.replay.ReplayingCluster;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.jdbc.JdbcEventStore;
import org.axonframework.eventstore.jpa.JpaEventStore;
import org.axonframework.eventstore.management.EventStoreManagement;
import org.axonframework.repository.Repository;
import org.axonframework.unitofwork.NoTransactionManager;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import msacqrses.model.Cart;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring Configuration class.
 */
@Configuration
@AnnotationDriven
public class AppConfiguration {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username("sa")
                .password("")
                .url("jdbc:h2:mem:axonappdb")
                .driverClassName("org.h2.Driver")
                .build();
    }

    /**
     * An event sourcing implementation needs a place to store events. i.e. The event Store.
     * In our use case we will be storing our events in database, so we configure
     * the JdbcEventStore as our EventStore implementation
     *
     * It should be noted that Axon allows storing the events
     * in other persistent mechanism...jdbc, jpa, filesystem etc
     *
     * @return the {@link EventStore}
     */
   /* @Bean
    public EventStore jdbcEventStore() {
        return new JdbcEventStore(dataSource());
    }*/

    @Bean
    EntityManagerProvider entityManagerProvider() {
        return new ContainerManagedEntityManagerProvider();
    }
    
    @PersistenceContext(unitName="events")
    private EntityManager em;
    
    @Bean
    public EventStore jpaEventStore() {
        return new JpaEventStore(entityManagerProvider());
    }
    
    @Bean
    public SimpleCommandBus commandBus() {
        SimpleCommandBus simpleCommandBus = new SimpleCommandBus();
        return simpleCommandBus;
    }

    /**
     *  A cluster which can be used to "cluster" together event handlers. This implementation is based on
     * {@link SimpleCluster} and it would be used to cluster event handlers that would listen to events thrown
     * normally within the application.
     *
     * @return an instance of {@link SimpleCluster}
     */
    @Bean
    public Cluster normalCluster() {
        SimpleCluster simpleCluster = new SimpleCluster("simpleCluster");
        return simpleCluster;
    }

/*    *//**
     *  A cluster which can be used to "cluster" together event handlers. This implementation is based on
     * {@link SimpleCluster} and it would be used to cluster event handlers that would listen to replayed events.
     *
     * As can be seen, the bean is just a simple implementation of {@link SimpleCluster} there is nothing about
     * it that says it would be able to handle replayed events. The bean definition #replayCluster is what makes
     * this bean able to handle replayed events.
     *
     * @return an instance of {@link SimpleCluster}
     *//*
    @Bean
    public Cluster replay() {
        SimpleCluster simpleCluster = new SimpleCluster("replayCluster");
        return simpleCluster;
    }

    *//**
     * Takes the #replay() cluster and wraps it with a Replaying Cluser, turning the event handlers that are registered
     * to be able to pick up events when events are replayed.
     *
     * @return an instance of {@link ReplayingCluster}
     *//*
    @Bean
    public ReplayingCluster replayCluster() {
        IncomingMessageHandler incomingMessageHandler = new DiscardingIncomingMessageHandler();
        EventStoreManagement eventStore = (EventStoreManagement) jdbcEventStore();
        return new ReplayingCluster(replay(), eventStore, new NoTransactionManager(),0,incomingMessageHandler);
    }
*/
    /**
     * This configuration registers event handlers with the two defined clusters
     *
     * @return an instance of {@link ClusterSelector}
     */
    @Bean
    public ClusterSelector clusterSelector() {
        Map<String, Cluster> clusterMap = new HashMap<>();
        clusterMap.put("msacqrses.eventhandler", normalCluster());
        //clusterMap.put("msacqrses.replay", replayCluster());
        return new ClassNamePrefixClusterSelector(clusterMap);
    }


    /**
     * This replaces the simple event bus that was initially used. The clustering event bus is needed to be able
     * to route events to event handlers in the clusters. It is configured with a {@link EventBusTerminal} defined
     * by #terminal(). The EventBusTerminal contains the configuration rules which determines which cluster gets an
     * incoming event
     *
     * @return a {@link ClusteringEventBus} implementation of {@link EventBus}
     */
    @Bean
    public EventBus clusteringEventBus() {
        ClusteringEventBus clusteringEventBus = new ClusteringEventBus(clusterSelector(), terminal());
        return clusteringEventBus;
    }

    /**
     * An {@link EventBusTerminal} which publishes application domain events onto the normal cluster
     *
     * @return an instance of {@link EventBusTerminal}
     */
    @Bean
    public EventBusTerminal terminal() {
        return new EventBusTerminal() {
            @Override
            public void publish(EventMessage... events) {
                normalCluster().publish(events);
            }
            @Override
            public void onClusterCreated(Cluster cluster) {

            }
        };
    }

    @Bean
    public DefaultCommandGateway commandGateway() {
        return new DefaultCommandGateway(commandBus());
    }

    /**
     * Our aggregate root is now created from stream of events and not from a representation in a persistent mechanism,
     * thus we need a repository that can handle the retrieving of our aggregate root from the stream of events.
     *
     * We configure the EventSourcingRepository which does exactly this. We supply it with the event store
     * @return a {@link EventSourcingRepository} implementation of {@link Repository}
     */
    @Bean
    public Repository<Cart> eventSourcingRepository() {
        EventSourcingRepository eventSourcingRepository = new EventSourcingRepository(Cart.class, jpaEventStore());
        eventSourcingRepository.setEventBus(clusteringEventBus());
        return eventSourcingRepository;
    }
}
