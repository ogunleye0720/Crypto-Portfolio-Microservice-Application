@crypto_price_service_api_test_positive
@crypto_price_service_api_test
Feature: CryptoPriceService API Test - positive

  Background: The system has db data default
    Given crypto price service db data deleted
    And adding token data to db
      | id | name      | symbol | coinMarketId | platform | tokenAddress                               | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      | 1  | Ethereum  | ETH    | 1027         | ethereum | 0x2170Ed0880ac9A755fd29B2688956BD959F933F8 | 1       | 2000.12345   | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
      | 2  | Polygon   | MATIC  | 3890         |          |                                            | 2       | 1.12345      | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
      | 3  | Avalanche | AVAX   | 5805         |          |                                            | 3       | 20.12345     | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |

  @severity=critical
  Scenario: As user GET resource /cryptocurrency
    Given valid API endpoint
    When send GET request to path "/cryptocurrency"
    Then receive status code 200
    And receive data is json format
    And receive valid array json data for crypto-price
      | id | name      | symbol | coinMarketId | platform | tokenAddress                               | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      | 1  | Ethereum  | ETH    | 1027         | ethereum | 0x2170Ed0880ac9A755fd29B2688956BD959F933F8 | 1       | 2000.12345   | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
      | 2  | Polygon   | MATIC  | 3890         |          |                                            | 2       | 1.12345      | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
      | 3  | Avalanche | AVAX   | 5805         |          |                                            | 3       | 20.12345     | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |

  @severity=critical
  Scenario: As user GET and query resource by name /cryptocurrency?name={token_name_available}
    Given valid API endpoint
    When send GET request to path "/cryptocurrency?name=Ethereum"
    Then receive status code 200
    And receive data is json format
    And receive valid array json data for crypto-price
      | id | name     | symbol | coinMarketId | platform | tokenAddress                               | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      | 1  | Ethereum | ETH    | 1027         | ethereum | 0x2170Ed0880ac9A755fd29B2688956BD959F933F8 | 1       | 2000.12345   | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |

  @severity=critical
  Scenario: As user GET and query resource by name /cryptocurrency?name={token_name_available},{token_name_available}
    Given valid API endpoint
    When send GET request to path "/cryptocurrency?name=Ethereum,Polygon"
    Then receive status code 200
    And receive data is json format
    And receive valid array json data for crypto-price
      | id | name     | symbol | coinMarketId | platform | tokenAddress                               | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      | 1  | Ethereum | ETH    | 1027         | ethereum | 0x2170Ed0880ac9A755fd29B2688956BD959F933F8 | 1       | 2000.12345   | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
      | 2  | Polygon  | MATIC  | 3890         |          |                                            | 2       | 1.12345      | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |

  @severity=critical
  Scenario: As user GET and query resource by name /cryptocurrency?name={token_name_available},{token_name_not_available}
    Given valid API endpoint
    When send GET request to path "/cryptocurrency?name=Ethereum,Bitcoin"
    Then receive status code 200
    And receive data is json format
    And receive valid array json data for crypto-price
      | id | name     | symbol | coinMarketId | platform | tokenAddress                               | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      | 1  | Ethereum | ETH    | 1027         | ethereum | 0x2170Ed0880ac9A755fd29B2688956BD959F933F8 | 1       | 2000.12345   | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |

  @severity=critical
  Scenario: As user GET and query resource by name /cryptocurrency/{id_available_1}
    Given valid API endpoint
    When send GET request to path "/cryptocurrency/1"
    Then receive status code 200
    And receive data is json format
    And receive valid json data for crypto-price
      | id | name     | symbol | coinMarketId | platform | tokenAddress                               | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      | 1  | Ethereum | ETH    | 1027         | ethereum | 0x2170Ed0880ac9A755fd29B2688956BD959F933F8 | 1       | 2000.12345   | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |

  @severity=critical
  Scenario: As user GET and query resource by name /cryptocurrency/{id_available_2}
    Given valid API endpoint
    When send GET request to path "/cryptocurrency/2"
    Then receive status code 200
    And receive data is json format
    And receive valid json data for crypto-price
      | id | name    | symbol | coinMarketId | platform | tokenAddress | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      | 2  | Polygon | MATIC  | 3890         |          |              | 2       | 1.12345      | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |

  @severity=critical
  Scenario: As user when GET resource /cryptocurrency/contract-address/{address_available}
    Given valid API endpoint
    When send GET request to path "/cryptocurrency/contract-address/0x2170Ed0880ac9A755fd29B2688956BD959F933F8"
    Then receive status code 200
    And receive data is json format
    And receive valid json data for crypto-price
      | id | name     | symbol | coinMarketId | platform | tokenAddress                               | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      | 1  | Ethereum | ETH    | 1027         | ethereum | 0x2170Ed0880ac9A755fd29B2688956BD959F933F8 | 1       | 2000.12345   | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |

  @severity=critical
  Scenario: As user when POST json data to the resource /cryptocurrency
    Given valid API endpoint
    When send POST json data to path "/cryptocurrency"
      | name         | Bitcoin             |
      | symbol       | BTC                 |
      | platform     | platformExample     |
      | tokenAddress | tokenAddressExample |
    Then receive status code 201
    And receive data is json format
    And receive valid json data for crypto-price
      | id | name    | symbol | coinMarketId | platform        | tokenAddress        | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      |    | Bitcoin | BTC    | 1            | platformExample | tokenAddressExample |         |              |                 |                  |                 |                  |                  |                  |

  @severity=critical
  Scenario: As user when POST json data to the resource /cryptocurrency
    Given valid API endpoint
    When send POST json data to path "/cryptocurrency"
      | name   | Bitcoin |
      | symbol | BTC     |
    Then receive status code 201
    And receive data is json format
    And receive valid json data for crypto-price
      | id | name    | symbol | coinMarketId | platform | tokenAddress | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      |    | Bitcoin | BTC    | 1            |          |              |         |              |                 |                  |                 |                  |                  |                  |

  @severity=critical
  Scenario: As user when PUT json data to the resource /cryptocurrency/{id}
    Given valid API endpoint
    When send PUT json data to path "/cryptocurrency/1"
      | name         | stEthereum           |
      | symbol       | stETH                |
      | platform     | platformExample2     |
      | tokenAddress | tokenAddressExample2 |
    Then receive status code 200
    And receive data is json format
    And receive valid json data for crypto-price
      | id | name       | symbol | coinMarketId | platform         | tokenAddress         | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      | 1  | stEthereum | stETH  | 1027         | platformExample2 | tokenAddressExample2 | 1       | 2000.12345   | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |

  @severity=critical
  Scenario: As user when PUT json data to the resource /cryptocurrency/{id}
    Given valid API endpoint
    When send PUT json data to path "/cryptocurrency/1"
      | name   | stEthereum |
      | symbol | stETH      |
    Then receive status code 200
    And receive data is json format
    And receive valid json data for crypto-price
      | id | name       | symbol | coinMarketId | platform | tokenAddress | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      | 1  | stEthereum | stETH  | 1027         |          |              | 1       | 2000.12345   | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |


  @severity=normal
  Scenario: As user when DELETE resource /cryptocurrency/{id}
    Given valid API endpoint
    When send DELETE request to path "/cryptocurrency/1"
    Then receive status code 200