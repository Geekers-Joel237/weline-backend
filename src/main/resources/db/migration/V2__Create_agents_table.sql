CREATE TABLE agents (
                        id VARCHAR(255) PRIMARY KEY,
                        created_at TIMESTAMP,
                        updated_at TIMESTAMP,
                        deleted_at TIMESTAMP,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL,
                        service_id VARCHAR(255) NOT NULL REFERENCES services(id),
                        role VARCHAR(255) NOT NULL
);