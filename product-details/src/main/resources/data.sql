begin;
CREATE EXTENSION IF NOT EXISTS hstore;
-- Create the product table
DROP TABLE if exists products;
CREATE TABLE products (
    id SERIAL PRIMARY KEY,                        -- B-Tree (default, used in foreign keys)
    name VARCHAR(255) NOT NULL,                    -- B-Tree (for sorting, searching)
    description TEXT,                              -- Full-Text Index candidate (for searching large text)
    category VARCHAR(100),                         -- B-Tree (for sorting, searching)
    price NUMERIC(10, 2),                          -- B-Tree (for range queries)
    created_at TIMESTAMPTZ DEFAULT NOW(),          -- BRIN (for large datasets with time-series data)
    updated_at TIMESTAMPTZ DEFAULT NOW(),          -- BRIN (for large datasets with time-series data)
    is_active BOOLEAN DEFAULT TRUE,                -- Partial Index candidate
    tags TEXT[],                                   -- GIN Index (for array searches)
    metadata HSTORE,                               -- GIN Index (for key-value searches)
    image_url TEXT,                                -- GIN Index (for flexible querying)
    location POINT,                                -- GiST/SP-GiST (for spatial queries)
    weight NUMERIC(5, 2),                          -- B-Tree (for sorting and searching)
    dimensions JSONB                              -- GIN Index (for flexible JSON querying)
);

--
-- CREATE OR REPLACE FUNCTION generate_products(num_products bigint)
-- RETURNS bigint
-- LANGUAGE 'plpgsql'
-- COST 100
-- VOLATILE PARALLEL UNSAFE
-- AS $$
-- DECLARE
--     total_rows BIGINT := 0;
-- BEGIN
--
--     INSERT INTO products (name, description, category, price, created_at, updated_at, is_active, tags, metadata, image_url, location, weight, dimensions)
--     SELECT
--         'Product ' || ii,
--         'This is a random description for product ' || ii || '. It has various attributes and properties that make it unique.',
--         (ARRAY['electronics', 'furniture', 'clothing', 'food'])[FLOOR(RANDOM() * 4 + 1)::INT],
--         ROUND((RANDOM() * 500 + 10)::numeric, 2),
--         NOW() - INTERVAL '1 year' * RANDOM(),
--         NOW() - INTERVAL '1 year' * RANDOM(),
--         RANDOM() < 0.95,
--         ARRAY[
--             (ARRAY['new', 'sale', 'trending', 'discounted'])[FLOOR(RANDOM() * 4 + 1)::INT]
--         ],
--         hstore(
--             ARRAY['color', 'material', 'size'],
--             ARRAY[
--                 (ARRAY['red', 'blue', 'green', 'black'])[FLOOR(RANDOM() * 4 + 1)::INT],
--                 (ARRAY['wood', 'metal', 'plastic'])[FLOOR(RANDOM() * 3 + 1)::INT],
--                 (ARRAY['small', 'medium', 'large'])[FLOOR(RANDOM() * 3 + 1)::INT]
--             ]
--         ),
--         'http://example.com/product-image-' || ii || '.jpg',
--         POINT((RANDOM() * 180 - 90), (RANDOM() * 360 - 180)),
--         ROUND((RANDOM() * 10 + 0.5)::numeric, 2),
--         jsonb_build_object(
--             'height', ROUND((RANDOM() * 50 + 10)::numeric, 2),
--             'width', ROUND((RANDOM() * 50 + 10)::numeric, 2),
--             'depth', ROUND((RANDOM() * 50 + 10)::numeric, 2)
--         )
--     FROM generate_series(1, num_products) AS ii;
--
--     RETURN num_products;
-- END;
-- $$;

CREATE OR REPLACE FUNCTION generate_products(num_products bigint)
RETURNS bigint
LANGUAGE 'plpgsql'
COST 100
VOLATILE PARALLEL UNSAFE
AS '
DECLARE
    total_rows BIGINT := 0;
BEGIN
    INSERT INTO products (name, description, category, price, created_at, updated_at, is_active, tags, metadata, image_url, location, weight, dimensions)
    SELECT
        ''Product '' || ii,
        ''This is a random description for product '' || ii || ''. It has various attributes and properties that make it unique.'',
        (ARRAY[''electronics'', ''furniture'', ''clothing'', ''food''])[FLOOR(RANDOM() * 4 + 1)::INT],
        ROUND((RANDOM() * 500 + 10)::numeric, 2),
        NOW() - INTERVAL ''1 year'' * RANDOM(),
        NOW() - INTERVAL ''1 year'' * RANDOM(),
        RANDOM() < 0.95,
        ARRAY[
            (ARRAY[''new'', ''sale'', ''trending'', ''discounted''])[FLOOR(RANDOM() * 4 + 1)::INT]
        ],
        hstore(
            ARRAY[''color'', ''material'', ''size''],
            ARRAY[
                (ARRAY[''red'', ''blue'', ''green'', ''black''])[FLOOR(RANDOM() * 4 + 1)::INT],
                (ARRAY[''wood'', ''metal'', ''plastic''])[FLOOR(RANDOM() * 3 + 1)::INT],
                (ARRAY[''small'', ''medium'', ''large''])[FLOOR(RANDOM() * 3 + 1)::INT]
            ]
        ),
        ''http://example.com/product-image-'' || ii || ''.jpg'',
        POINT((RANDOM() * 180 - 90), (RANDOM() * 360 - 180)),
        ROUND((RANDOM() * 10 + 0.5)::numeric, 2),
        jsonb_build_object(
            ''height'', ROUND((RANDOM() * 50 + 10)::numeric, 2),
            ''width'', ROUND((RANDOM() * 50 + 10)::numeric, 2),
            ''depth'', ROUND((RANDOM() * 50 + 10)::numeric, 2)
        )
    FROM generate_series(1, num_products) AS ii;

    RETURN num_products;
END;
';

SELECT generate_products(200);  -- Adjust the number as needed
-- select * from products;
commit;