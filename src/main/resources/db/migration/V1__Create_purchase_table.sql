CREATE TABLE public.purchase
(
    id             SERIAL
        CONSTRAINT purchase_pk
            PRIMARY KEY,
    description    VARCHAR(50)    NOT NULL,
    effective_date DATE           NOT NULL,
    amount         NUMERIC(14, 2) NOT NULL
);