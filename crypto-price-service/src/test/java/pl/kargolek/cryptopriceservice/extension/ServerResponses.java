package pl.kargolek.cryptopriceservice.extension;

import lombok.Getter;

/**
 * @author Karol Kuta-Orlowicz
 */
@Getter
public class ServerResponses {

    private final String bodyResError400 = """
            {
                "status": {
                    "timestamp": "2023-01-21T21:35:59.813Z",
                    "error_code": 400,
                    "error_message": "\\"id\\" should only include comma-separated numeric CoinMarketCap cryptocurrency ids",
                    "elapsed": 0,
                    "credit_count": 0
                }
            }
            """;

    private final String bodyRes200MapSymbolMatic = """
            {
                "status": {
                    "timestamp": "2023-01-21T19:44:52.202Z",
                    "error_code": 0,
                    "error_message": null,
                    "elapsed": 17,
                    "credit_count": 1,
                    "notice": null
                },
                "data": [
                    {
                        "id": 3890,
                        "name": "Polygon",
                        "symbol": "MATIC",
                        "slug": "polygon",
                        "rank": 11,
                        "displayTV": 1,
                        "manualSetTV": 0,
                        "tvCoinSymbol": "",
                        "is_active": 1,
                        "first_historical_data": "2019-04-28T20:04:10.000Z",
                        "last_historical_data": "2023-01-21T19:39:00.000Z",
                        "platform": null
                    }
                ]
            }
            """;

    private final String bodyRes200MapSymbolXen = """
            {
                "status": {
                    "timestamp": "2023-01-26T20:40:57.238Z",
                    "error_code": 0,
                    "error_message": null,
                    "elapsed": 15,
                    "credit_count": 1,
                    "notice": null
                },
                "data": [
                    {
                        "id": 22118,
                        "name": "XEN Crypto",
                        "symbol": "XEN",
                        "slug": "xen-crypto",
                        "rank": 2689,
                        "displayTV": 1,
                        "manualSetTV": 0,
                        "tvCoinSymbol": "",
                        "is_active": 1,
                        "first_historical_data": "2022-10-09T18:41:00.000Z",
                        "last_historical_data": "2023-01-26T20:36:00.000Z",
                        "platform": {
                            "id": 1027,
                            "name": "Ethereum",
                            "symbol": "ETH",
                            "slug": "ethereum",
                            "token_address": "0x06450dEe7FD2Fb8E39061434BAbCFC05599a6Fb8"
                        }
                    },
                    {
                        "id": 1470,
                        "name": "Xenixcoin",
                        "symbol": "XEN",
                        "slug": "xenixcoin",
                        "rank": null,
                        "displayTV": 1,
                        "manualSetTV": 0,
                        "tvCoinSymbol": "",
                        "is_active": 0,
                        "first_historical_data": "2016-12-01T19:32:30.000Z",
                        "last_historical_data": "2017-10-03T01:14:18.000Z",
                        "platform": null
                    },
                    {
                        "id": 5528,
                        "name": "Xenon",
                        "symbol": "XEN",
                        "slug": "xenon-xen",
                        "rank": null,
                        "displayTV": 1,
                        "manualSetTV": 0,
                        "tvCoinSymbol": "",
                        "is_active": 0,
                        "first_historical_data": "2020-05-03T14:19:15.000Z",
                        "last_historical_data": "2021-06-14T09:59:09.000Z",
                        "platform": {
                            "id": 1027,
                            "name": "Ethereum",
                            "symbol": "ETH",
                            "slug": "ethereum",
                            "token_address": "0x5fbdb42bb048c685c990a37f2c87fe087c586655"
                        }
                    }
                ]
            }
            """;

    private final String bodyRes200MapSymbolBTC = """
            {
                "status": {
                    "timestamp": "2023-01-20T00:06:31.909Z",
                    "error_code": 0,
                    "error_message": null,
                    "elapsed": 16,
                    "credit_count": 1,
                    "notice": null
                },
                "data": [
                    {
                        "id": 1,
                        "name": "Bitcoin",
                        "symbol": "BTC",
                        "slug": "bitcoin",
                        "rank": 1,
                        "displayTV": 1,
                        "manualSetTV": 0,
                        "tvCoinSymbol": "",
                        "is_active": 1,
                        "first_historical_data": "2013-04-28T18:47:21.000Z",
                        "last_historical_data": "2023-01-19T23:59:00.000Z",
                        "platform": null
                    }
                ]
            }
            """;

    private final String bodyRes200MapSymbolETH = """
            {
                "status": {
                    "timestamp": "2023-01-26T23:06:35.889Z",
                    "error_code": 0,
                    "error_message": null,
                    "elapsed": 14,
                    "credit_count": 1,
                    "notice": null
                },
                "data": [
                    {
                        "id": 1027,
                        "name": "Ethereum",
                        "symbol": "ETH",
                        "slug": "ethereum",
                        "rank": 2,
                        "displayTV": 1,
                        "manualSetTV": 0,
                        "tvCoinSymbol": "",
                        "is_active": 1,
                        "first_historical_data": "2015-08-07T14:49:30.000Z",
                        "last_historical_data": "2023-01-26T22:59:00.000Z",
                        "platform": null
                    }
                ]
            }
            """;

    private final String bodyRes400MapSymbolUnknown = """
            {
                "status": {
                    "timestamp": "2023-01-26T23:09:15.752Z",
                    "error_code": 400,
                    "error_message": "Invalid value for \\"symbol\\": \\"UNKNOWN\\"",
                    "elapsed": 0,
                    "credit_count": 0,
                    "notice": null
                }
            }
            """;

    private final String bodyRes400MapSymbolEmptyNoAllowed = """
            {
                "status": {
                    "timestamp": "2023-01-26T23:11:04.391Z",
                    "error_code": 400,
                    "error_message": "\\"symbol\\" is not allowed to be empty",
                    "elapsed": 0,
                    "credit_count": 0
                }
            }
            """;

    private final String bodyRes200LatestPriceBTC = """
            {
                "data": {
                    "1": {
                        "id": 1,
                        "name": "Bitcoin",
                        "symbol": "BTC",
                        "slug": "bitcoin",
                        "is_active": 1,
                        "is_fiat": 0,
                        "circulating_supply": 17199862,
                        "total_supply": 17199862,
                        "max_supply": 21000000,
                        "date_added": "2013-04-28T00:00:00.000Z",
                        "num_market_pairs": 331,
                        "cmc_rank": 1,
                        "last_updated": "2018-08-09T21:56:28.000Z",
                        "tags": [
                            "mineable"
                        ],
                        "platform": null,
                        "self_reported_circulating_supply": null,
                        "self_reported_market_cap": null,
                        "quote": {
                            "USD": {
                                "price": 25000.12345,
                                "volume_24h": 4314444687.5194,
                                "volume_change_24h": -0.152774,
                                "percent_change_1h": 1.54321,
                                "percent_change_24h": -2.54321,
                                "percent_change_7d": 3.54321,
                                "percent_change_30d": -4.54321,
                                "percent_change_60d": 5.54321,
                                "percent_change_90d": -6.54321,
                                "market_cap": 852164659250.2758,
                                "market_cap_dominance": 51,
                                "fully_diluted_market_cap": 952835089431.14,
                                "last_updated": "2018-08-09T21:56:28.000Z"
                            }
                        }
                    }
                },
                "status": {
                    "timestamp": "2023-01-03T19:55:35.426Z",
                    "error_code": 0,
                    "error_message": "",
                    "elapsed": 10,
                    "credit_count": 1
                }
            }
            """;

    private final String bodyRes200LatestPriceBTCETH = """
            {
                "status": {
                    "timestamp": "2023-01-16T09:23:08.500Z",
                    "error_code": 0,
                    "error_message": null,
                    "elapsed": 40,
                    "credit_count": 1,
                    "notice": null
                },
                "data": {
                    "1": {
                        "id": 1,
                        "name": "Bitcoin",
                        "symbol": "BTC",
                        "slug": "bitcoin",
                        "num_market_pairs": 9931,
                        "date_added": "2013-04-28T00:00:00.000Z",
                        "max_supply": 21000000,
                        "circulating_supply": 19263793,
                        "total_supply": 19263793,
                        "is_active": 1,
                        "platform": null,
                        "cmc_rank": 1,
                        "is_fiat": 0,
                        "self_reported_circulating_supply": null,
                        "self_reported_market_cap": null,
                        "tvl_ratio": null,
                        "last_updated": "2023-01-16T09:21:00.000Z",
                        "quote": {
                            "USD": {
                                "price": 25000.12345,
                                "volume_24h": 4314444687.5194,
                                "volume_change_24h": -0.152774,
                                "percent_change_1h": 1.54321,
                                "percent_change_24h": -2.54321,
                                "percent_change_7d": 3.54321,
                                "percent_change_30d": -4.54321,
                                "percent_change_60d": 5.54321,
                                "percent_change_90d": -6.54321,
                                "market_cap": 400915038365.6269,
                                "market_cap_dominance": 40.9945,
                                "fully_diluted_market_cap": 437048706123.36,
                                "tvl": null,
                                "last_updated": "2023-01-16T09:21:00.000Z"
                            }
                        }
                    },
                    "1027": {
                                "id": 1027,
                                "name": "Ethereum",
                                "symbol": "ETH",
                                "slug": "ethereum",
                                "num_market_pairs": 6360,
                                "date_added": "2015-08-07T00:00:00.000Z",
                                "max_supply": null,
                                "circulating_supply": 122373866.2178,
                                "total_supply": 122373866.2178,
                                "is_active": 1,
                                "platform": null,
                                "cmc_rank": 2,
                                "is_fiat": 0,
                                "self_reported_circulating_supply": null,
                                "self_reported_market_cap": null,
                                "tvl_ratio": null,
                                "last_updated": "2023-01-16T09:35:00.000Z",
                                "quote": {
                                    "USD": {
                                        "price": 2543.3040228407024,
                                        "volume_24h": 7685876434.055611,
                                        "volume_change_24h": -6.5169,
                                        "percent_change_1h": 7.54321,
                                        "percent_change_24h": -8.54321,
                                        "percent_change_7d": 9.54321,
                                        "percent_change_30d": -10.54321,
                                        "percent_change_60d": 11.54321,
                                        "percent_change_90d": -12.54321,
                                        "market_cap": 188860080024.50067,
                                        "market_cap_dominance": 19.2937,
                                        "fully_diluted_market_cap": 188860080024.5,
                                        "tvl": null,
                                        "last_updated": "2023-01-16T09:35:00.000Z"
                                    }
                                }
                            }
                }
            }
            """;

    private final String bodyRes200LatestPriceNullCryptoData = """
            {
                "status": {
                    "timestamp": "2023-01-16T09:23:08.500Z",
                    "error_code": 0,
                    "error_message": null,
                    "elapsed": 40,
                    "credit_count": 1,
                    "notice": null
                }
            }
            """;

    private final String bodyRes500ErrorServerNotAvailable = """
            {
                "status": {
                    "timestamp": "2018-06-02T22:51:28.209Z",
                    "error_code": 500,
                    "error_message": "An internal server error occurred",
                    "elapsed": 10,
                    "credit_count": 0
                }
            }
            """;

    private final String bodyRes200LatestPriceCryptoDataEmpty = """
            {
                "status": {
                    "timestamp": "2023-01-22T19:04:55.091Z",
                    "error_code": 0,
                    "error_message": null,
                    "elapsed": 97,
                    "credit_count": 1,
                    "notice": null
                },
                "data": {
                    "1": {
                    }
                }
            }
            """;

    private final String bodyRes200LatestPriceDataPriceNull = """
            {
                "status": {
                    "timestamp": "2023-01-16T09:23:08.500Z",
                    "error_code": 0,
                    "error_message": null,
                    "elapsed": 40,
                    "credit_count": 1,
                    "notice": null
                },
                "data": {
                    "1": {
                        "id": 1,
                        "name": "Bitcoin",
                        "symbol": "BTC",
                        "slug": "bitcoin",
                        "num_market_pairs": 9931,
                        "date_added": "2013-04-28T00:00:00.000Z",
                        "max_supply": 21000000,
                        "circulating_supply": 19263793,
                        "total_supply": 19263793,
                        "is_active": 1,
                        "platform": null,
                        "cmc_rank": 1,
                        "is_fiat": 0,
                        "self_reported_circulating_supply": null,
                        "self_reported_market_cap": null,
                        "tvl_ratio": null,
                        "last_updated": "2023-01-16T09:21:00.000Z"
                    }
                }
            }
            """;

    private final String bodyRes400LatestPrice = """
            {
                "status": {
                    "timestamp": "2018-06-02T22:51:28.209Z",
                    "error_code": 400,
                    "error_message": "Invalid value for \\"id\\"",
                    "elapsed": 10,
                    "credit_count": 0
                }
            }
            """;

    private final String bodyRes401Unauthorized = """
            {
                "status": {
                    "timestamp": "2018-06-02T22:51:28.209Z",
                    "error_code": 1002,
                    "error_message": "API key missing.",
                    "elapsed": 10,
                    "credit_count": 0
                }
            }
            """;


}
