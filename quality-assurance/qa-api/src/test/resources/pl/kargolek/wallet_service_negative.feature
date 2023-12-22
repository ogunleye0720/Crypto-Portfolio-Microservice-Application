@wallet_service_api_test_negative
@wallet_service_api_test
Feature: WalletService API Test - negative

  Background: The system has db data default
    Given crypto price service db data deleted
    And adding token data to db
      | id | name      | symbol | coinMarketId | platform | tokenAddress                               | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      | 1  | Ethereum  | ETH    | 1027         | ethereum | 0x2170Ed0880ac9A755fd29B2688956BD959F933F8 | 1       | 2000.12345   | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
      | 2  | Polygon   | MATIC  | 3890         |          |                                            | 2       | 1.12345      | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
      | 3  | Avalanche | AVAX   | 5805         |          |                                            | 3       | 20.12345     | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/eth/balance with invalid wallet
    Given valid API endpoint
    When send GET request to path "/wallet/eth/balance" with query params
      | query   | value                                      |
      | wallets | 0xZB6eD29A95753C3Ad948348e3e7b1A251080Ffb9 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                           |
      | message | Address is invalid for crypto ETH and address 0xZB6eD29A95753C3Ad948348e3e7b1A251080Ffb9, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/eth/balance with invalid too short wallet address
    Given valid API endpoint
    When send GET request to path "/wallet/eth/balance" with query params
      | query   | value   |
      | wallets | 0x01234 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                        |
      | message | Address is invalid for crypto ETH and address 0x01234, message: address is invalid |


  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/eth/balance with invalid too long wallet address
    Given valid API endpoint
    When send GET request to path "/wallet/eth/balance" with query params
      | query   | value                                          |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba5744390000 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                               |
      | message | Address is invalid for crypto ETH and address 0x8d81156294aBC787e5cBcc16774799Aeba5744390000, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/eth/balance with one wallet address query param is invalid
    Given valid API endpoint
    When send GET request to path "/wallet/eth/balance" with query params
      | query   | value                                                                                     |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439,0x8d81156294aBC787e5cBcc16774799Aeba5744390000 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                               |
      | message | Address is invalid for crypto ETH and address 0x8d81156294aBC787e5cBcc16774799Aeba5744390000, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/eth/balance with one wallet address query param is invalid
    Given valid API endpoint
    When send GET request to path "/wallet/eth/balance" with query params
      | query   | value                                          |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439     |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba5744390000 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                               |
      | message | Address is invalid for crypto ETH and address 0x8d81156294aBC787e5cBcc16774799Aeba5744390000, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/eth/balance with two wallet address query param is invalid
    Given valid API endpoint
    When send GET request to path "/wallet/eth/balance" with query params
      | query   | value                                                                                                                                    |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439,0x8d81156294aBC787e5cBcc16774799Aeba5744390000,0x8d81156294aBC787e5cBcc16774799Aeba5744391111 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                               |
      | message | Address is invalid for crypto ETH and address 0x8d81156294aBC787e5cBcc16774799Aeba5744390000, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/eth/balance with invalid query param syntax
    Given valid API endpoint
    When send GET request to path "/wallet/eth/balance" with query params
      | query   | value                                                                                 |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439.0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                                                                      |
      | message | Address is invalid for crypto ETH and address 0x8d81156294aBC787e5cBcc16774799Aeba574439.0x8d81156294aBC787e5cBcc16774799Aeba574439, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/eth/balance with empty prices for ETH
    Given valid API endpoint
    And crypto price service db data deleted
    When send GET request to path "/wallet/eth/balance" with query params
      | query   | value                                      |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 500
    And receive data is json format
    And receive valid json data
      | status  | INTERNAL_SERVER_ERROR                    |
      | message | Unable to get price for crypto: Ethereum |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/eth/balance with current price for ETH is null
    Given valid API endpoint
    And crypto price service db data deleted
    And adding token data to db
      | id | name      | symbol | coinMarketId | platform | tokenAddress                               | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      | 1  | Ethereum  | ETH    | 1027         | ethereum | 0x2170Ed0880ac9A755fd29B2688956BD959F933F8 | 1       |              | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
      | 2  | Polygon   | MATIC  | 3890         |          |                                            | 2       | 1.12345      | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
      | 3  | Avalanche | AVAX   | 5805         |          |                                            | 3       | 20.12345     | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
    When send GET request to path "/wallet/eth/balance" with query params
      | query   | value                                      |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 500
    And receive data is json format
    And receive valid json data
      | status  | INTERNAL_SERVER_ERROR          |
      | message | Unable to get price for crypto |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/matic/balance with invalid wallet
    Given valid API endpoint
    When send GET request to path "/wallet/matic/balance" with query params
      | query   | value                                      |
      | wallets | 0xZB6eD29A95753C3Ad948348e3e7b1A251080Ffb9 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                           |
      | message | Address is invalid for crypto ETH and address 0xZB6eD29A95753C3Ad948348e3e7b1A251080Ffb9, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/matic/balance with invalid too short wallet address
    Given valid API endpoint
    When send GET request to path "/wallet/matic/balance" with query params
      | query   | value   |
      | wallets | 0x01234 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                        |
      | message | Address is invalid for crypto ETH and address 0x01234, message: address is invalid |


  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/matic/balance with invalid too long wallet address
    Given valid API endpoint
    When send GET request to path "/wallet/matic/balance" with query params
      | query   | value                                          |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba5744390000 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                               |
      | message | Address is invalid for crypto ETH and address 0x8d81156294aBC787e5cBcc16774799Aeba5744390000, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/matic/balance with one wallet address query param is invalid
    Given valid API endpoint
    When send GET request to path "/wallet/matic/balance" with query params
      | query   | value                                                                                     |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439,0x8d81156294aBC787e5cBcc16774799Aeba5744390000 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                               |
      | message | Address is invalid for crypto ETH and address 0x8d81156294aBC787e5cBcc16774799Aeba5744390000, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/matic/balance with one wallet address query param is invalid
    Given valid API endpoint
    When send GET request to path "/wallet/matic/balance" with query params
      | query   | value                                          |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439     |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba5744390000 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                               |
      | message | Address is invalid for crypto ETH and address 0x8d81156294aBC787e5cBcc16774799Aeba5744390000, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/matic/balance with two wallet address query param is invalid
    Given valid API endpoint
    When send GET request to path "/wallet/matic/balance" with query params
      | query   | value                                                                                                                                    |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439,0x8d81156294aBC787e5cBcc16774799Aeba5744390000,0x8d81156294aBC787e5cBcc16774799Aeba5744391111 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                               |
      | message | Address is invalid for crypto ETH and address 0x8d81156294aBC787e5cBcc16774799Aeba5744390000, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/matic/balance with invalid query param syntax
    Given valid API endpoint
    When send GET request to path "/wallet/matic/balance" with query params
      | query   | value                                                                                 |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439.0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                                                                      |
      | message | Address is invalid for crypto ETH and address 0x8d81156294aBC787e5cBcc16774799Aeba574439.0x8d81156294aBC787e5cBcc16774799Aeba574439, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/matic/balance with empty prices for MATIC
    Given valid API endpoint
    And crypto price service db data deleted
    When send GET request to path "/wallet/matic/balance" with query params
      | query   | value                                      |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 500
    And receive data is json format
    And receive valid json data
      | status  | INTERNAL_SERVER_ERROR                   |
      | message | Unable to get price for crypto: Polygon |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/matic/balance with current price for MATIC is null
    Given valid API endpoint
    And crypto price service db data deleted
    And adding token data to db
      | id | name      | symbol | coinMarketId | platform | tokenAddress                               | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      | 1  | Ethereum  | ETH    | 1027         | ethereum | 0x2170Ed0880ac9A755fd29B2688956BD959F933F8 | 1       | 2000.12345   | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
      | 2  | Polygon   | MATIC  | 3890         |          |                                            | 2       |              | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
      | 3  | Avalanche | AVAX   | 5805         |          |                                            | 3       | 20.12345     | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
    When send GET request to path "/wallet/matic/balance" with query params
      | query   | value                                      |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 500
    And receive data is json format
    And receive valid json data
      | status  | INTERNAL_SERVER_ERROR          |
      | message | Unable to get price for crypto |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/avax/balance with invalid wallet
    Given valid API endpoint
    When send GET request to path "/wallet/avax/balance" with query params
      | query   | value                                      |
      | wallets | 0xZB6eD29A95753C3Ad948348e3e7b1A251080Ffb9 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                           |
      | message | Address is invalid for crypto ETH and address 0xZB6eD29A95753C3Ad948348e3e7b1A251080Ffb9, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/avax/balance with invalid too short wallet address
    Given valid API endpoint
    When send GET request to path "/wallet/avax/balance" with query params
      | query   | value   |
      | wallets | 0x01234 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                        |
      | message | Address is invalid for crypto ETH and address 0x01234, message: address is invalid |


  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/avax/balance with invalid too long wallet address
    Given valid API endpoint
    When send GET request to path "/wallet/avax/balance" with query params
      | query   | value                                          |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba5744390000 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                               |
      | message | Address is invalid for crypto ETH and address 0x8d81156294aBC787e5cBcc16774799Aeba5744390000, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/avax/balance with one wallet address query param is invalid
    Given valid API endpoint
    When send GET request to path "/wallet/avax/balance" with query params
      | query   | value                                                                                     |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439,0x8d81156294aBC787e5cBcc16774799Aeba5744390000 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                               |
      | message | Address is invalid for crypto ETH and address 0x8d81156294aBC787e5cBcc16774799Aeba5744390000, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/avax/balance with one wallet address query param is invalid
    Given valid API endpoint
    When send GET request to path "/wallet/avax/balance" with query params
      | query   | value                                          |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439     |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba5744390000 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                               |
      | message | Address is invalid for crypto ETH and address 0x8d81156294aBC787e5cBcc16774799Aeba5744390000, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/avax/balance with two wallet address query param is invalid
    Given valid API endpoint
    When send GET request to path "/wallet/avax/balance" with query params
      | query   | value                                                                                                                                    |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439,0x8d81156294aBC787e5cBcc16774799Aeba5744390000,0x8d81156294aBC787e5cBcc16774799Aeba5744391111 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                               |
      | message | Address is invalid for crypto ETH and address 0x8d81156294aBC787e5cBcc16774799Aeba5744390000, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/avax/balance with invalid query param syntax
    Given valid API endpoint
    When send GET request to path "/wallet/avax/balance" with query params
      | query   | value                                                                                 |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439.0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                                                                      |
      | message | Address is invalid for crypto ETH and address 0x8d81156294aBC787e5cBcc16774799Aeba574439.0x8d81156294aBC787e5cBcc16774799Aeba574439, message: address is invalid |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/avax/balance with empty prices for AVAX
    Given valid API endpoint
    And crypto price service db data deleted
    When send GET request to path "/wallet/avax/balance" with query params
      | query   | value                                      |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 500
    And receive data is json format
    And receive valid json data
      | status  | INTERNAL_SERVER_ERROR                     |
      | message | Unable to get price for crypto: Avalanche |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/avax/balance with current price for AVAX is null
    Given valid API endpoint
    And crypto price service db data deleted
    And adding token data to db
      | id | name      | symbol | coinMarketId | platform | tokenAddress                               | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      | 1  | Ethereum  | ETH    | 1027         | ethereum | 0x2170Ed0880ac9A755fd29B2688956BD959F933F8 | 1       | 2000.12345   | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
      | 2  | Polygon   | MATIC  | 3890         |          |                                            | 2       | 1.12345      | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
      | 3  | Avalanche | AVAX   | 5805         |          |                                            | 3       |              | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
    When send GET request to path "/wallet/avax/balance" with query params
      | query   | value                                      |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 500
    And receive data is json format
    And receive valid json data
      | status  | INTERNAL_SERVER_ERROR          |
      | message | Unable to get price for crypto |