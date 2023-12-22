@wallet_service_api_test_positive
@wallet_service_api_test
Feature: WalletService API Test - positive

  Background: The system has db data default
    Given crypto price service db data deleted
    And adding token data to db
      | id | name      | symbol | coinMarketId | platform | tokenAddress                               | priceId | priceCurrent | percentChange1h | percentChange24h | percentChange7d | percentChange30d | percentChange60d | percentChange90d |
      | 1  | Ethereum  | ETH    | 1027         | ethereum | 0x2170Ed0880ac9A755fd29B2688956BD959F933F8 | 1       | 2000.12345   | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
      | 2  | Polygon   | MATIC  | 3890         |          |                                            | 2       | 1.12345      | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |
      | 3  | Avalanche | AVAX   | 5805         |          |                                            | 3       | 20.12345     | 5.5             | -5.5             | 10.5            | -10.5            | 15.5             | -15.5            |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/eth/balance for single wallet
    Given valid API endpoint
    When send GET request to path "/wallet/eth/balance" with query params
      | query   | value                                      |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name     | symbol |
      | Ethereum | ETH    |
    And receive wallets json data totals quantity
      | totalQuantity        | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 0.235197014191261014 | 470.42       | 444.55         | 496.29          | 421.03         |
    And receive wallets json data balances
      | walletAddress                              | quantity             | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                 |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.235197014191261014 | 470.42  | 444.55    | 496.29     | 421.03    | 519.81     | 397.50     | 543.34     | https://goerli.etherscan.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/eth/balance for multiple wallets
    Given valid API endpoint
    When send GET request to path "/wallet/eth/balance" with query params
      | query   | value                                      |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name     | symbol |
      | Ethereum | ETH    |
    And receive wallets json data totals quantity
      | totalQuantity        | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 0.470394028382522028 | 940.84       | 889.10         | 992.58          | 842.06         |
    And receive wallets json data balances
      | walletAddress                              | quantity             | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                 |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.235197014191261014 | 470.42  | 444.55    | 496.29     | 421.03    | 519.81     | 397.50     | 543.34     | https://goerli.etherscan.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.235197014191261014 | 470.42  | 444.55    | 496.29     | 421.03    | 519.81     | 397.50     | 543.34     | https://goerli.etherscan.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/eth/balance for multiple wallets in single query param
    Given valid API endpoint
    When send GET request to path "/wallet/eth/balance" with query params
      | query   | value                                                                                 |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439,0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name     | symbol |
      | Ethereum | ETH    |
    And receive wallets json data totals quantity
      | totalQuantity        | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 0.470394028382522028 | 940.84       | 889.10         | 992.58          | 842.06         |
    And receive wallets json data balances
      | walletAddress                              | quantity             | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                 |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.235197014191261014 | 470.42  | 444.55    | 496.29     | 421.03    | 519.81     | 397.50     | 543.34     | https://goerli.etherscan.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.235197014191261014 | 470.42  | 444.55    | 496.29     | 421.03    | 519.81     | 397.50     | 543.34     | https://goerli.etherscan.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/eth/balance for empty wallet
    Given valid API endpoint
    When send GET request to path "/wallet/eth/balance" with query params
      | query   | value                                      |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name     | symbol |
      | Ethereum | ETH    |
    And receive wallets json data totals quantity
      | totalQuantity | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 0             | 0.00         | 0.00           | 0.00            | 0.00           |
    And receive wallets json data balances
      | walletAddress                              | quantity | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://goerli.etherscan.io/address/0x0E510578889ce76db52686625E2E12D35D0b092e |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/eth/balance for empty wallet  and not empty wallet
    Given valid API endpoint
    When send GET request to path "/wallet/eth/balance" with query params
      | query   | value                                      |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name     | symbol |
      | Ethereum | ETH    |
    And receive wallets json data totals quantity
      | totalQuantity        | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 0.235197014191261014 | 470.42       | 444.55         | 496.29          | 421.03         |
    And receive wallets json data balances
      | walletAddress                              | quantity             | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0                    | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://goerli.etherscan.io/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.235197014191261014 | 470.42  | 444.55    | 496.29     | 421.03    | 519.81     | 397.50     | 543.34     | https://goerli.etherscan.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/eth/balance for 22 wallet addresses
    Given valid API endpoint
    When send GET request to path "/wallet/eth/balance" with query params
      | query   | value                                      |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name     | symbol |
      | Ethereum | ETH    |
    And receive wallets json data totals quantity
      | totalQuantity        | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 2.587167156103871154 | 5174.62      | 4890.05        | 5459.19         | 4631.33        |
    And receive wallets json data balances
      | walletAddress                              | quantity             | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0                    | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://goerli.etherscan.io/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.235197014191261014 | 470.42  | 444.55    | 496.29     | 421.03    | 519.81     | 397.50     | 543.34     | https://goerli.etherscan.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0                    | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://goerli.etherscan.io/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.235197014191261014 | 470.42  | 444.55    | 496.29     | 421.03    | 519.81     | 397.50     | 543.34     | https://goerli.etherscan.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0                    | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://goerli.etherscan.io/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.235197014191261014 | 470.42  | 444.55    | 496.29     | 421.03    | 519.81     | 397.50     | 543.34     | https://goerli.etherscan.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0                    | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://goerli.etherscan.io/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.235197014191261014 | 470.42  | 444.55    | 496.29     | 421.03    | 519.81     | 397.50     | 543.34     | https://goerli.etherscan.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0                    | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://goerli.etherscan.io/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.235197014191261014 | 470.42  | 444.55    | 496.29     | 421.03    | 519.81     | 397.50     | 543.34     | https://goerli.etherscan.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0                    | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://goerli.etherscan.io/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.235197014191261014 | 470.42  | 444.55    | 496.29     | 421.03    | 519.81     | 397.50     | 543.34     | https://goerli.etherscan.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0                    | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://goerli.etherscan.io/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.235197014191261014 | 470.42  | 444.55    | 496.29     | 421.03    | 519.81     | 397.50     | 543.34     | https://goerli.etherscan.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0                    | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://goerli.etherscan.io/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.235197014191261014 | 470.42  | 444.55    | 496.29     | 421.03    | 519.81     | 397.50     | 543.34     | https://goerli.etherscan.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0                    | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://goerli.etherscan.io/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.235197014191261014 | 470.42  | 444.55    | 496.29     | 421.03    | 519.81     | 397.50     | 543.34     | https://goerli.etherscan.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0                    | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://goerli.etherscan.io/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.235197014191261014 | 470.42  | 444.55    | 496.29     | 421.03    | 519.81     | 397.50     | 543.34     | https://goerli.etherscan.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0                    | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://goerli.etherscan.io/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.235197014191261014 | 470.42  | 444.55    | 496.29     | 421.03    | 519.81     | 397.50     | 543.34     | https://goerli.etherscan.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/matic/balance for single wallet
    Given valid API endpoint
    When send GET request to path "/wallet/matic/balance" with query params
      | query   | value                                      |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name    | symbol |
      | Polygon | MATIC  |
    And receive wallets json data totals quantity
      | totalQuantity | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 0.4           | 0.45         | 0.43           | 0.47            | 0.40           |
    And receive wallets json data balances
      | walletAddress                              | quantity | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                    |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/matic/balance for multiple wallets
    Given valid API endpoint
    When send GET request to path "/wallet/matic/balance" with query params
      | query   | value                                      |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name    | symbol |
      | Polygon | MATIC  |
    And receive wallets json data totals quantity
      | totalQuantity | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 0.8           | 0.90         | 0.86           | 0.94            | 0.80           |
    And receive wallets json data balances
      | walletAddress                              | quantity | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                    |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/matic/balance for multiple wallets in single query param
    Given valid API endpoint
    When send GET request to path "/wallet/matic/balance" with query params
      | query   | value                                                                                 |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439,0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name    | symbol |
      | Polygon | MATIC  |
    And receive wallets json data totals quantity
      | totalQuantity | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 0.8           | 0.90         | 0.86           | 0.94            | 0.80           |
    And receive wallets json data balances
      | walletAddress                              | quantity | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                    |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/matic/balance for empty wallet
    Given valid API endpoint
    When send GET request to path "/wallet/matic/balance" with query params
      | query   | value                                      |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name    | symbol |
      | Polygon | MATIC  |
    And receive wallets json data totals quantity
      | totalQuantity | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 0             | 0.00         | 0.00           | 0.00            | 0.00           |
    And receive wallets json data balances
      | walletAddress                              | quantity | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                    |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/matic/balance for empty wallet and not empty wallet
    Given valid API endpoint
    When send GET request to path "/wallet/matic/balance" with query params
      | query   | value                                      |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name    | symbol |
      | Polygon | MATIC  |
    And receive wallets json data totals quantity
      | totalQuantity | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 0.4           | 0.45         | 0.43           | 0.47            | 0.40           |
    And receive wallets json data balances
      | walletAddress                              | quantity | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                    |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/matic/balance for 44 wallet addresses
    Given valid API endpoint
    When send GET request to path "/wallet/matic/balance" with query params
      | query   | value                                      |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name    | symbol |
      | Polygon | MATIC  |
    And receive wallets json data totals quantity
      | totalQuantity | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 8.8           | 9.90         | 9.46           | 10.34           | 8.80           |
    And receive wallets json data balances
      | walletAddress                              | quantity | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                    |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://mumbai.polygonscan.com/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 0.4      | 0.45    | 0.43      | 0.47       | 0.40      | 0.50       | 0.38       | 0.52       | https://mumbai.polygonscan.com/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |

    ###############

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/avax/balance for single wallet
    Given valid API endpoint
    When send GET request to path "/wallet/avax/balance" with query params
      | query   | value                                      |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name      | symbol |
      | Avalanche | AVAX   |
    And receive wallets json data totals quantity
      | totalQuantity | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 1.5           | 30.19        | 28.53          | 31.85           | 27.02          |
    And receive wallets json data balances
      | walletAddress                              | quantity | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                  |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 1.5      | 30.19   | 28.53     | 31.85      | 27.02     | 33.36      | 25.51      | 34.87      | https://testnet.snowtrace.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/avax/balance for multiple wallets
    Given valid API endpoint
    When send GET request to path "/wallet/avax/balance" with query params
      | query   | value                                      |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name      | symbol |
      | Avalanche | AVAX   |
    And receive wallets json data totals quantity
      | totalQuantity | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 1.5          | 30.19        | 28.53          | 31.85           | 27.02          |
    And receive wallets json data balances
      | walletAddress                              | quantity | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                  |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 1.5      | 30.19   | 28.53     | 31.85      | 27.02     | 33.36      | 25.51      | 34.87      | https://testnet.snowtrace.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/avax/balance for multiple wallets in single query param
    Given valid API endpoint
    When send GET request to path "/wallet/avax/balance" with query params
      | query   | value                                                                                 |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439,0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name      | symbol |
      | Avalanche | AVAX   |
    And receive wallets json data totals quantity
      | totalQuantity | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 1.5          | 30.19        | 28.53          | 31.85           | 27.02          |
    And receive wallets json data balances
      | walletAddress                              | quantity | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                  |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 1.5      | 30.19   | 28.53     | 31.85      | 27.02     | 33.36      | 25.51      | 34.87      | https://testnet.snowtrace.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/avax/balance for empty wallet
    Given valid API endpoint
    When send GET request to path "/wallet/avax/balance" with query params
      | query   | value                                      |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name      | symbol |
      | Avalanche | AVAX   |
    And receive wallets json data totals quantity
      | totalQuantity | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 0             | 0.00         | 0.00           | 0.00            | 0.00           |
    And receive wallets json data balances
      | walletAddress                              | quantity | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                  |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://testnet.snowtrace.io/address/0x0E510578889ce76db52686625E2E12D35D0b092e |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/avax/balance for empty wallet and not empty wallet
    Given valid API endpoint
    When send GET request to path "/wallet/avax/balance" with query params
      | query   | value                                      |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name      | symbol |
      | Avalanche | AVAX   |
    And receive wallets json data totals quantity
      | totalQuantity | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 1.5           | 30.19        | 28.53          | 31.85           | 27.02          |
    And receive wallets json data balances
      | walletAddress                              | quantity | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                  |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://testnet.snowtrace.io/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 1.5      | 30.19   | 28.53     | 31.85      | 27.02     | 33.36      | 25.51      | 34.87      | https://testnet.snowtrace.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |

  @severity=critical
  Scenario: As user GET resource /api/v1/wallet/avax/balance for 22 wallet addresses
    Given valid API endpoint
    When send GET request to path "/wallet/avax/balance" with query params
      | query   | value                                      |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | wallets | 0x0E510578889ce76db52686625E2E12D35D0b092e |
      | wallets | 0x8d81156294aBC787e5cBcc16774799Aeba574439 |
    Then receive status code 200
    And receive data is json format
    And receive wallet json data name and symbol
      | name      | symbol |
      | Avalanche | AVAX   |
    And receive wallets json data totals quantity
      | totalQuantity | totalBalance | totalBalance1h | totalBalance24h | totalBalance7d |
      | 3.0           | 60.38        | 57.06          | 63.70           | 54.04          |
    And receive wallets json data balances
      | walletAddress                              | quantity | balance | balance1h | balance24h | balance7d | balance30d | balance60d | balance90d | walletExplorer                                                                  |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://testnet.snowtrace.io/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 1.5      | 30.19   | 28.53     | 31.85      | 27.02     | 33.36      | 25.51      | 34.87      | https://testnet.snowtrace.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |
      | 0x0E510578889ce76db52686625E2E12D35D0b092e | 0        | 0.00    | 0.00      | 0.00       | 0.00      | 0.00       | 0.00       | 0.00       | https://testnet.snowtrace.io/address/0x0E510578889ce76db52686625E2E12D35D0b092e |
      | 0x8d81156294aBC787e5cBcc16774799Aeba574439 | 1.5      | 30.19   | 28.53     | 31.85      | 27.02     | 33.36      | 25.51      | 34.87      | https://testnet.snowtrace.io/address/0x8d81156294aBC787e5cBcc16774799Aeba574439 |