package pl.kargolek.walletservice.testutils.fixture;

/**
 * @author Karol Kuta-Orlowicz
 */
public class ResponseEtherscanData {

    public static final String ETHERSCAN_MULTI_WALLET_RES_200_AS_EXPECTED = """
            {
                "status": "1",
                "message": "OK",
                "result": [
                    {
                        "account": "0x8111111111111111111111111111111111111111",
                        "balance": "10000000000000000000"
                    },
                    {
                        "account": "0x8222222222222222222222222222222222222222",
                        "balance": "20000000000000000000"
                    }
                ]
            }
            """;
    public static final String ETHERSCAN_MULTI_WALLET_RES_200_RESULT_EMPTY_ARRAY = """
            {
                "status": "1",
                "message": "OK",
                "result": []
            }
            """;

    public static final String ETHERSCAN_MULTI_WALLET_RES_200_RESULT_AS_STRING = """
            {
                "status": "1",
                "message": "OK",
                "result": "example string"
            }
            """;

    public static final String ETHERSCAN_MULTI_WALLET_RES_200_RESULT_IS_NULL = """
            {
                "status": "1",
                "message": "OK"
            }
            """;

    public static final String ETHERSCAN_RES_200_MAX_LIMIT_REACHED = """
            {
                "status": "0",
                "message": "NOTOK",
                "result": "Max rate limit reached, please use API Key for higher rate limit"
            }
            """;

    public static final String ETHERSCAN_20_MULTI_WALLET_RES_200_AS_EXPECTED = """
            {
                "status": "1",
                "message": "OK",
                "result": [
                    {
                        "account": "0x8111111111111111111111111111111111111111",
                        "balance": "10000000000000000000"
                    },
                    {
                        "account": "0x8222222222222222222222222222222222222222",
                        "balance": "20000000000000000000"
                    },
                    {
                        "account": "0x8111111111111111111111111111111111111111",
                        "balance": "10000000000000000000"
                    },
                    {
                        "account": "0x8222222222222222222222222222222222222222",
                        "balance": "20000000000000000000"
                    },
                    {
                        "account": "0x8111111111111111111111111111111111111111",
                        "balance": "10000000000000000000"
                    },
                    {
                        "account": "0x8222222222222222222222222222222222222222",
                        "balance": "20000000000000000000"
                    },
                    {
                        "account": "0x8111111111111111111111111111111111111111",
                        "balance": "10000000000000000000"
                    },
                    {
                        "account": "0x8222222222222222222222222222222222222222",
                        "balance": "20000000000000000000"
                    },
                    {
                        "account": "0x8111111111111111111111111111111111111111",
                        "balance": "10000000000000000000"
                    },
                    {
                        "account": "0x8222222222222222222222222222222222222222",
                        "balance": "20000000000000000000"
                    },
                    {
                        "account": "0x8111111111111111111111111111111111111111",
                        "balance": "10000000000000000000"
                    },
                    {
                        "account": "0x8222222222222222222222222222222222222222",
                        "balance": "20000000000000000000"
                    },
                    {
                        "account": "0x8111111111111111111111111111111111111111",
                        "balance": "10000000000000000000"
                    },
                    {
                        "account": "0x8222222222222222222222222222222222222222",
                        "balance": "20000000000000000000"
                    },
                    {
                        "account": "0x8111111111111111111111111111111111111111",
                        "balance": "10000000000000000000"
                    },
                    {
                        "account": "0x8222222222222222222222222222222222222222",
                        "balance": "20000000000000000000"
                    },
                    {
                        "account": "0x8111111111111111111111111111111111111111",
                        "balance": "10000000000000000000"
                    },
                    {
                        "account": "0x8222222222222222222222222222222222222222",
                        "balance": "20000000000000000000"
                    },
                    {
                        "account": "0x8111111111111111111111111111111111111111",
                        "balance": "10000000000000000000"
                    },
                    {
                        "account": "0x8222222222222222222222222222222222222222",
                        "balance": "20000000000000000000"
                    }
                ]
            }
            """;


}
