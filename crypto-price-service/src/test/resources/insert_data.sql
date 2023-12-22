SET time_zone = 'UTC';
INSERT INTO
    cryptocurrency (name, symbol, coin_market_id, platform, token_address, last_update)
VALUES
    ('Bitcoin', 'BTC', 1, 'platformBTC', 'tokenAddressBTC', NOW());
SET @crypto_id = (SELECT id FROM cryptocurrency ORDER BY id DESC LIMIT 1);
INSERT INTO
    price (cryptocurrency_id, price_current, percent_change_1h, percent_change_24h, percent_change_7d, percent_change_30d, percent_change_60d, percent_change_90d, last_update)
VALUES
    (@crypto_id, 20000.50, 0.5, -1.0, 1.5, -2.0, 2.5, -3.0, NOW());

INSERT INTO
    cryptocurrency (name, symbol, coin_market_id, platform, token_address, last_update)
VALUES
    ('Ethereum', 'ETH', 1027, 'platformETH', 'tokenAddressETH', NOW());
SET @cry = (SELECT id FROM cryptocurrency ORDER BY id DESC LIMIT 1);
INSERT INTO
    price (cryptocurrency_id, price_current, percent_change_1h, percent_change_24h, percent_change_7d, percent_change_30d, percent_change_60d, percent_change_90d, last_update)
VALUES
    (@cry, 1800.50, 4.5, -5.0, 5.5, -6.0, 6.5, -7.0, NOW());