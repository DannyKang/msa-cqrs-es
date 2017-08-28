package msacqrses.controller;

import org.axonframework.repository.Repository;
import org.axonframework.unitofwork.DefaultUnitOfWork;
import org.axonframework.unitofwork.UnitOfWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.FileSystemUtils;

import msacqrses.model.Cart;

import java.nio.file.Paths;
import javax.annotation.PostConstruct;

@Component
public class Datastore {

    @Autowired
    @Qualifier("transactionManager")
    protected PlatformTransactionManager txManager;

    @Autowired
    private Repository repository;

    @Autowired
    private javax.sql.DataSource dataSource;

    @PostConstruct
    private void init(){
        // init the event store

        // delete previous events on startup
        //FileSystemUtils.deleteRecursively(Paths.get("./events").toFile());

        TransactionTemplate transactionTmp = new TransactionTemplate(txManager);
        transactionTmp.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                UnitOfWork uow = DefaultUnitOfWork.startAndGet();
                repository.add(new Cart("cart1"));
                repository.add(new Cart("cart2"));
                uow.commit();
            }
        });

        // init the tables for query/view
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("create table cartview (cartid VARCHAR , items NUMBER )");
        jdbcTemplate.update("insert into cartview (cartid, items) values (?, ?)", new Object[]{"cart1", 0});
        jdbcTemplate.update("insert into cartview (cartid, items) values (?, ?)", new Object[]{"cart2", 0});
    }

}
