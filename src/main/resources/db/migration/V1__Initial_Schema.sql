CREATE TABLE customer_otps
(
    id          VARCHAR(255) NOT NULL,
    deleted     BOOLEAN      NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    deleted_at  TIMESTAMP WITHOUT TIME ZONE,
    customer_id VARCHAR(255) NOT NULL,
    otp_code    VARCHAR(255) NOT NULL,
    expired_in  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_customer_otps PRIMARY KEY (id)
);

CREATE TABLE customers
(
    id           VARCHAR(255) NOT NULL,
    deleted      BOOLEAN      NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    deleted_at   TIMESTAMP WITHOUT TIME ZONE,
    phone_number VARCHAR(255) NOT NULL,
    status       VARCHAR(255) NOT NULL,
    CONSTRAINT pk_customers PRIMARY KEY (id)
);

CREATE TABLE partners
(
    id         VARCHAR(255) NOT NULL,
    deleted    BOOLEAN      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    name       VARCHAR(255) NOT NULL,
    CONSTRAINT pk_partners PRIMARY KEY (id)
);

CREATE TABLE queues
(
    id                 VARCHAR(255) NOT NULL,
    deleted            BOOLEAN      NOT NULL,
    created_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    deleted_at         TIMESTAMP WITHOUT TIME ZONE,
    service_id         VARCHAR(255) NOT NULL,
    last_ticket_number INTEGER      NOT NULL,
    CONSTRAINT pk_queues PRIMARY KEY (id)
);

CREATE TABLE service_points
(
    id         VARCHAR(255) NOT NULL,
    deleted    BOOLEAN      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    name       VARCHAR(255) NOT NULL,
    location   VARCHAR(255) NOT NULL,
    partner_id VARCHAR(255),
    CONSTRAINT pk_service_points PRIMARY KEY (id)
);

CREATE TABLE services
(
    id               VARCHAR(255) NOT NULL,
    deleted          BOOLEAN      NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    deleted_at       TIMESTAMP WITHOUT TIME ZONE,
    queue_id         VARCHAR(255) NOT NULL,
    name             VARCHAR(255) NOT NULL,
    description      VARCHAR(255) NOT NULL,
    code             VARCHAR(255) NOT NULL,
    service_point_id VARCHAR(255),
    CONSTRAINT pk_services PRIMARY KEY (id)
);

CREATE TABLE tickets
(
    id          VARCHAR(255) NOT NULL,
    deleted     BOOLEAN      NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    deleted_at  TIMESTAMP WITHOUT TIME ZONE,
    customer_id VARCHAR(255) NOT NULL,
    number      VARCHAR(255) NOT NULL,
    status      VARCHAR(255) NOT NULL,
    queue_id    VARCHAR(255),
    CONSTRAINT pk_tickets PRIMARY KEY (id)
);

ALTER TABLE customers
    ADD CONSTRAINT uc_customers_phonenumber UNIQUE (phone_number);

CREATE INDEX idx_2d0c59eb0ccaa38897459163a ON queues (service_id);

CREATE INDEX idx_8005ffbe55030369e074c7e1a ON tickets (customer_id);

CREATE INDEX idx_93c0956a7b5c862fcfb2ac4e8 ON services (queue_id);

CREATE UNIQUE INDEX idx_customer_id ON customer_otps (customer_id);

CREATE UNIQUE INDEX idx_fa2c32f4b9a6390628a0d149c ON tickets (number);

ALTER TABLE services
    ADD CONSTRAINT FK_SERVICES_ON_SERVICE_POINT FOREIGN KEY (service_point_id) REFERENCES service_points (id);

ALTER TABLE service_points
    ADD CONSTRAINT FK_SERVICE_POINTS_ON_PARTNER FOREIGN KEY (partner_id) REFERENCES partners (id);

ALTER TABLE tickets
    ADD CONSTRAINT FK_TICKETS_ON_QUEUE FOREIGN KEY (queue_id) REFERENCES queues (id);