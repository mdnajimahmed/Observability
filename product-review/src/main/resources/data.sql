-- Clear existing data (optional)
TRUNCATE TABLE reviews RESTART IDENTITY CASCADE;

-- Insert sample reviews
INSERT INTO reviews (product_id, user_id, rating, comment, created_at, updated_at) VALUES
(1, 101, 5, 'Excellent phone with great camera!', '2023-01-15 10:30:00', '2023-01-15 10:30:00'),
(1, 102, 4, 'Very good but battery could be better', '2023-01-20 14:15:00', '2023-01-20 14:15:00'),
(2, 103, 5, 'Amazing sound quality!', '2023-02-05 09:45:00', '2023-02-05 09:45:00'),
(3, 104, 3, 'Decent features but screen is too small', '2023-02-10 16:20:00', '2023-02-10 16:20:00'),
(3, 105, 4, 'Great fitness tracking capabilities', '2023-02-12 11:10:00', '2023-02-12 11:10:00'),
(4, 106, 2, 'Disappointed with the performance', '2023-03-01 08:30:00', '2023-03-01 08:30:00'),
(5, 107, 5, 'Absolutely worth every penny!', '2023-03-05 19:45:00', '2023-03-05 19:45:00');

-- Reset sequence to avoid conflicts
SELECT setval('reviews_id_seq', (SELECT MAX(id) FROM reviews));