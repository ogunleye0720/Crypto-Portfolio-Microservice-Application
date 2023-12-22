import { TokenPrice } from "./token-price";

export interface TokenCrypto{
    name: string,
    symbol: string,
    price: TokenPrice,
}