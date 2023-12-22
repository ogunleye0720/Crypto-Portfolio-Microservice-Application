# Wallet Service API Documentation

### Features

A service has been built to check wallet balances for multiple blockchains and tokens.

| Method | URI | Description | Request Body | Response Body |
| ------ | --- | ----------- | ------------ | ------------- |
| GET    | `/api/v1/wallet/matic/balance` | Get polygon wallet balance | Query: `wallets` (required: true, type: string) | JSON: UserWallet |
| GET    | `/api/v1/wallet/eth/balance` | Get ethereum wallet balance | Query: `wallets` (required: true, type: string) | JSON: UserWallet |
| GET    | `/api/v1/wallet/avax/balance` | Get avalanche wallet balance | Query: `wallets` (required: true, type: string) | JSON: UserWallet |

### Swagger
You can view the endpoint and scheme at the link below:
https://app.swaggerhub.com/apis-docs/KAROLORLOWICZ/wallet-service_api_documentation/1.0.0

### How to use

*Preconditions*
1. Create an account on the https://coinmarketcap.com/ and add API key "COIN_MARKET_CAP_API_KEY" to system env variable
2. Create an account on the https://etherscan.io/ and add API key "ETHERSCAN_API_KEY" to system env variable
3. Create an account on the https://snowtrace.io/ and add API key "AVALANCHE_API_KEY" to system env variable
4. Create an account on the https://polygonscan.com/ and add API key "POLYGON_API_KEY" to system env variable
5. Make sure you have Docker installed on your environment

*Main Steps*
1. Clone repo
2. Navigate to path: */crypto-portfolio-microservices
3. Run cmd script for build and compose docker containers
```shell
start build.bat
```
4. Navigate to swagger documentation
* http://localhost:8082/swagger-ui/index.html#/

The app will start with preloaded data for Bitcoin, Ethereum, Matic, Avax tokens.