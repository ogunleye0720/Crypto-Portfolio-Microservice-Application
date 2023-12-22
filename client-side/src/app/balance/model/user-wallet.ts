import { TotalBalance } from "./total-balance";
import { UserBalance } from "./user-balance"

export interface UserWallet {
    name: string;
    symbol: string;
    total: TotalBalance;
    balance: UserBalance[];
}
