package com.carlgillis.hedera_factoring.controller;

import com.carlgillis.hedera_factoring.config.HederaProperties;
import com.hedera.hashgraph.sdk.AccountBalance;
import com.hedera.hashgraph.sdk.AccountBalanceQuery;
import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HederaController {
    private final Client client;
    private final AccountId operatorId;

    public HederaController(Client client, HederaProperties props) {
        this.client = client;
        this.operatorId = AccountId.fromString(props.getOperatorId());
    }

    @GetMapping("/hedera/balance")
    public Map<String, Object> balance() throws Exception {
        AccountBalance bal = new AccountBalanceQuery()
                .setAccountId(operatorId)
                .execute(client);

        return Map.of(
                "accountId", operatorId.toString(),
                "hbars", bal.hbars.toString(),
                "tokens", bal.tokens.toString()
        );
    }
}
