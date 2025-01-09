DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_database WHERE datname = 'order_service'
    ) THEN
        CREATE DATABASE product_service;
END IF;
END
$$;
