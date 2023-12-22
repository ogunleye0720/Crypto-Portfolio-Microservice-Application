@crypto_price_service_api_test_negative
@crypto_price_service_api_test
Feature: CryptoPriceService API Test - negative

  Background: The system has db data default
    Given crypto price service db data deleted
    And adding token data to db
      | id | name      | symbol | coinMarketId | platform | tokenAddress                               | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      | 1  | Ethereum  | ETH    | 1027         | ethereum | 0x2170Ed0880ac9A755fd29B2688956BD959F933F8 | 1       | 2000.12345   | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
      | 2  | Polygon   | MATIC  | 3890         |          |                                            | 2       | 1.12345      | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
      | 3  | Avalanche | AVAX   | 5805         |          |                                            | 3       | 20.12345     | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |

  @severity=critical
  Scenario: As user GET and query resource by name /cryptocurrency?name={token_name_not_available}
    Given valid API endpoint
    When send GET request to path "/cryptocurrency?name=bnb"
    Then receive status code 200
    And receive data is json format
    And receive empty body

  @severity=critical
  Scenario: As user when GET resource /cryptocurrency/contract-address/{address_not_available}
    Given valid API endpoint
    When send GET request to path "/cryptocurrency/contract-address/123"
    Then receive status code 404
    And receive data is json format
    And receive valid json data
      | status  | NOT_FOUND                                                    |
      | message | Provided contract address is not exist, contractAddress: 123 |

  @severity=critical
  Scenario: As user when POST json data that already exist to the resource /cryptocurrency
    Given valid API endpoint
    When send POST json data to path "/cryptocurrency"
      | name   | Ethereum |
      | symbol | ETH      |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                           |
      | message | Duplicate entry '1027' for key 'cryptocurrency.UniqueCoinMarketCapId' |

  @severity=critical
  Scenario: As user when POST json data to the resource /cryptocurrency without property name
    Given valid API endpoint
    When send POST json data to path "/cryptocurrency"
      | symbol | BTC |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                   |
      | message | Method argument are not valid |
      | errors  | [Name cannot be an empty]     |

  @severity=critical
  Scenario: As user when POST json data to the resource /cryptocurrency without property symbol
    Given valid API endpoint
    When send POST json data to path "/cryptocurrency"
      | name | Bitcoin |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                   |
      | message | Method argument are not valid |
      | errors  | [Symbol cannot be an empty]   |

  @severity=critical
  Scenario: As user when POST json data to the resource /cryptocurrency property name not match with symbol
    Given valid API endpoint
    When send POST json data to path "/cryptocurrency"
      | name   | Ethereum |
      | symbol | BTC      |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                |
      | message | Received coin market cap crypto name, no match with provided name:Ethereum |

  @severity=critical
  Scenario: As user when POST json data to the resource /cryptocurrency property name too short
    Given valid API endpoint
    When send POST json data to path "/cryptocurrency"
      | name   | E   |
      | symbol | BTC |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                         |
      | message | Method argument are not valid       |
      | errors  | [Name length exceeds range [2,100]] |

  @severity=critical
  Scenario: As user when POST json data to the resource /cryptocurrency property name too long
    Given valid API endpoint
    When add payload property "name" as random alphanumeric 101
    And send POST json data to path "/cryptocurrency"
      | symbol | BTC |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                         |
      | message | Method argument are not valid       |
      | errors  | [Name length exceeds range [2,100]] |

  @severity=critical
  Scenario: As user when POST json data to the resource /cryptocurrency property symbol too short
    Given valid API endpoint
    When send POST json data to path "/cryptocurrency"
      | name   | Bitcoin |
      | symbol | B       |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                          |
      | message | Method argument are not valid        |
      | errors  | [Symbol length exceeds range [2,20]] |

  @severity=critical
  Scenario: As user when POST json data to the resource /cryptocurrency property symbol too long
    Given valid API endpoint
    When add payload property "symbol" as random alphanumeric 21
    And send POST json data to path "/cryptocurrency"
      | name | Bitcoin |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                          |
      | message | Method argument are not valid        |
      | errors  | [Symbol length exceeds range [2,20]] |

  @severity=critical
  Scenario: As user when POST json data to the resource /cryptocurrency property symbol not allowed char
    Given valid API endpoint
    When send POST json data to path "/cryptocurrency"
      | name   | Bitcoin |
      | symbol | BTC!    |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                                                        |
      | message | Server status code: 400, message: "symbol" should only include comma-separated alphanumeric cryptocurrency symbols |

  @severity=critical
  Scenario: As user when POST json data to the resource /cryptocurrency with symbol unknown
    Given valid API endpoint
    When send POST json data to path "/cryptocurrency"
      | name   | SomeUnknownCrypto |
      | symbol | SomeUnknownCrypto |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                                                       |
      | message | Server status code: 400, message: Invalid value for "symbol": "SOMEUNKNOWNCRYPTO" |

  @severity=critical
  Scenario: As user when POST json data to the resource /cryptocurrency with unknown properties
    Given valid API endpoint
    When send POST json data to path "/cryptocurrency"
      | unknown1 | unknown1 |
      | unknown2 | unknown2 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                   |
      | message | Method argument are not valid |

  @severity=critical
  Scenario: As user when POST json data to the resource /cryptocurrency too short platform property
    Given valid API endpoint
    When send POST json data to path "/cryptocurrency"
      | name         | Bitcoin              |
      | symbol       | BTC                  |
      | platform     | p                    |
      | tokenAddress | tokenAddressExample2 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                             |
      | message | Method argument are not valid           |
      | errors  | [Platform length exceeds range [2,100]] |

  @severity=critical
  Scenario: As user when POST json data to the resource /cryptocurrency too long platform property
    Given valid API endpoint
    When add payload property "platform" as random alphanumeric 101
    And send POST json data to path "/cryptocurrency"
      | name         | Bitcoin              |
      | symbol       | BTC                  |
      | tokenAddress | tokenAddressExample2 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                             |
      | message | Method argument are not valid           |
      | errors  | [Platform length exceeds range [2,100]] |

  @severity=critical
  Scenario: As user when POST json data to the resource /cryptocurrency too short tokenAddress property
    Given valid API endpoint
    When send POST json data to path "/cryptocurrency"
      | name         | Bitcoin  |
      | symbol       | BTC      |
      | platform     | platform |
      | tokenAddress | t        |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                  |
      | message | Method argument are not valid                |
      | errors  | [Token address length exceeds range [2,255]] |

  @severity=critical
  Scenario: As user when PUT json data to the resource /cryptocurrency/{id_not_available}
    Given valid API endpoint
    When send PUT json data to path "/cryptocurrency/123456789"
      | name         | stEthereum           |
      | symbol       | stETH                |
      | platform     | platformExample2     |
      | tokenAddress | tokenAddressExample2 |
    Then receive status code 404
    And receive data is json format
    And receive valid json data
      | status  | NOT_FOUND                                        |
      | message | Unable to find cryptocurrency with id: 123456789 |

  @severity=critical
  Scenario: As user when PUT json data to the resource /cryptocurrency/{id_not_available} without property name
    Given valid API endpoint
    When send PUT json data to path "/cryptocurrency/1"
      | symbol       | BTC           |
      | platform     | platform2     |
      | tokenAddress | tokenAddress2 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                   |
      | message | Method argument are not valid |
      | errors  | [Name cannot be an empty]     |

  @severity=critical
  Scenario: As user when PUT json data to the resource /cryptocurrency/{id_available} without property symbol
    Given valid API endpoint
    When send PUT json data to path "/cryptocurrency/1"
      | name         | Bitcoin       |
      | platform     | platform2     |
      | tokenAddress | tokenAddress2 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                   |
      | message | Method argument are not valid |
      | errors  | [Symbol cannot be an empty]   |

  @severity=critical
  Scenario: As user when PUT json data to the resource /cryptocurrency/{id_available} property name too short
    Given valid API endpoint
    When send PUT json data to path "/cryptocurrency/1"
      | name         | E             |
      | symbol       | ETH           |
      | platform     | platform2     |
      | tokenAddress | tokenAddress2 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                         |
      | message | Method argument are not valid       |
      | errors  | [Name length exceeds range [2,100]] |

  @severity=critical
  Scenario: As user when PUT json data to the resource /cryptocurrency/{id_available} property name too long
    Given valid API endpoint
    When add payload property "name" as random alphanumeric 101
    And send PUT json data to path "/cryptocurrency/1"
      | symbol       | ETH           |
      | platform     | platform2     |
      | tokenAddress | tokenAddress2 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                         |
      | message | Method argument are not valid       |
      | errors  | [Name length exceeds range [2,100]] |

  @severity=critical
  Scenario: As user when PUT json data to the resource /cryptocurrency/{id_available} property symbol too short
    Given valid API endpoint
    When send PUT json data to path "/cryptocurrency/1"
      | name   | Ethereum |
      | symbol | E        |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                          |
      | message | Method argument are not valid        |
      | errors  | [Symbol length exceeds range [2,20]] |

  @severity=critical
  Scenario: As user when PUT json data to the resource /cryptocurrency/{id_available} property symbol too long
    Given valid API endpoint
    When add payload property "symbol" as random alphanumeric 21
    And send PUT json data to path "/cryptocurrency/1"
      | name         | Ethereum      |
      | platform     | platform2     |
      | tokenAddress | tokenAddress2 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                          |
      | message | Method argument are not valid        |
      | errors  | [Symbol length exceeds range [2,20]] |

  @severity=critical
  Scenario: As user when PUT json data to the resource /cryptocurrency/{id_available} too short platform property
    Given valid API endpoint
    When send PUT json data to path "/cryptocurrency/1"
      | name         | Bitcoin              |
      | symbol       | BTC                  |
      | platform     | p                    |
      | tokenAddress | tokenAddressExample2 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                             |
      | message | Method argument are not valid           |
      | errors  | [Platform length exceeds range [2,100]] |

  @severity=critical
  Scenario: As user when PUT json data to the resource /cryptocurrency/{id_available} too long platform property
    Given valid API endpoint
    When add payload property "platform" as random alphanumeric 101
    And send PUT json data to path "/cryptocurrency/1"
      | name         | Bitcoin              |
      | symbol       | BTC                  |
      | tokenAddress | tokenAddressExample2 |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                             |
      | message | Method argument are not valid           |
      | errors  | [Platform length exceeds range [2,100]] |

  @severity=critical
  Scenario: As user when PUT json data to the resource /cryptocurrency/{id_available} too short tokenAddress property
    Given valid API endpoint
    When send PUT json data to path "/cryptocurrency/1"
      | name         | Bitcoin  |
      | symbol       | BTC      |
      | platform     | platform |
      | tokenAddress | t        |
    Then receive status code 400
    And receive data is json format
    And receive valid json data
      | status  | BAD_REQUEST                                  |
      | message | Method argument are not valid                |
      | errors  | [Token address length exceeds range [2,255]] |

  @severity=critical
  Scenario: As user when DELETE resource /cryptocurrency/{id_not_available}
    Given valid API endpoint
    When send DELETE request to path "/cryptocurrency/123456789"
    Then receive status code 404
    And receive data is json format
    And receive valid json data
      | status  | NOT_FOUND                                                                                     |
      | message | No class pl.kargolek.cryptopriceservice.model.Cryptocurrency entity with id 123456789 exists! |