CREATE TABLE public.exchange_rate
(
    country     VARCHAR(50) NOT NULL,
    currency    VARCHAR(50) NOT NULL,
    record_date DATE        NOT NULL,
    rate_value  NUMERIC     NOT NULL,
    CONSTRAINT exchange_rate_pk
        PRIMARY KEY (country, currency, record_date)
);