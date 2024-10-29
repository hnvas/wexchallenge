CREATE TABLE IF NOT EXISTS exchange_rate (
    id SERIAL PRIMARY KEY,
    country VARCHAR(50) NOT NULL,
    currency VARCHAR(50) NOT NULL,
    record_date DATE NOT NULL,
    rate_value NUMERIC NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(country, currency, record_date)
);