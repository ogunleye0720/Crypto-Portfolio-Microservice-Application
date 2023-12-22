# Crypto Price Service API Documentation

### Feature

A service has been created to store, manipulate, and retrieve cryptocurrency prices from http://coinmarketcap.com

| Method | URI | Description | Request Body                                  | Response Body |
|---|---|---|-----------------------------------------------|---|
| GET | `/api/v1/cryptocurrency/{id}` | Get a cryptocurrency by ID | None                                          | CryptocurrencyDTO |
| GET | `/api/v1/cryptocurrency/contract-address/{contractAddress}` | Get a cryptocurrency by smart contract address | None                                          | CryptocurrencyDTO |
| GET | `/api/v1/cryptocurrency` | Get all cryptocurrencies | Query: `names` (required: true, type: string) | List[CryptocurrencyDTO] |
| POST | `/api/v1/cryptocurrency` | Register a new cryptocurrency | CryptocurrencyPostDTO                         | CryptocurrencyDTO |
| DELETE | `/api/v1/cryptocurrency/{id}` | Delete a cryptocurrency by ID | None                                          | None |
| PUT | `/api/v1/cryptocurrency/{id}` | Update a cryptocurrency | CryptocurrencyPostDTO                         | CryptocurrencyDTO |

### Swagger
You can view the endpoint and scheme at the link below:
https://app.swaggerhub.com/apis-docs/KAROLORLOWICZ/crypto-price_service_api_documentation/1.0.0

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
* http://localhost:8081/swagger-ui/index.html#/

The app will start with preloaded data for Bitcoin, Ethereum, Matic, Avax tokens.