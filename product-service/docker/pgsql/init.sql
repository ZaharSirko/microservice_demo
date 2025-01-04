DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_database WHERE datname = 'product_service'
    ) THEN
        CREATE DATABASE product_service;
END IF;
END
$$;
