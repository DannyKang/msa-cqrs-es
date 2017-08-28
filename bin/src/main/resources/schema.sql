CREATE TABLE domainevententry
(
    aggregateidentifier VARCHAR(255) NOT NULL,
    sequencenumber BIGINT NOT NULL,
    type VARCHAR(255) NOT NULL,
    eventidentifier VARCHAR(255) NOT NULL,
    metadata BYTEA,
    payload BYTEA,
    payloadrevision VARCHAR(255),
    payloadtype VARCHAR(255) NOT NULL,
    timestamp VARCHAR(255) NOT NULL,
    CONSTRAINT newdomainevententry_pkey PRIMARY KEY (aggregateidentifier, sequencenumber, type)
);


CREATE TABLE snapshotevententry
(
    aggregateidentifier VARCHAR(255) NOT NULL,
    sequencenumber BIGINT NOT NULL,
    type VARCHAR(255) NOT NULL,
    eventidentifier VARCHAR(255) NOT NULL,
    payloadrevision VARCHAR(255),
    payloadtype VARCHAR(255) NOT NULL,
    timestamp VARCHAR(255) NOT NULL,
    metadata BYTEA,
    payload BYTEA NOT NULL,
    CONSTRAINT snapshotevententry_pkey1 PRIMARY KEY (aggregateidentifier, sequencenumber, type)
);