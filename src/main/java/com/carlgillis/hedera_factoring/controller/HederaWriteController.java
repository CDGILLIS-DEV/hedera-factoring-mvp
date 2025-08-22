package com.carlgillis.hedera_factoring.controller;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.TransactionReceipt;
import com.hedera.hashgraph.sdk.TransactionResponse;
import com.hedera.hashgraph.sdk.TransferTransaction;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HederaWriteController {

    private final Client client;
    private final AccountId operatorId;

    // Constructor: must be inside the class and have the same name as the class
    public HederaWriteController(Client client) {
        this.client = client;
        AccountId id = client.getOperatorAccountId();
        if (id == null) {
            throw new IllegalStateException("Hedera operator is not configured on the Client.");
        }
        this.operatorId = id;
    }

    // Sends 1 tinybar from operator to itself to confirm signing works
    @PostMapping("/hedera/ping")
    public Map<String, Object> ping() throws Exception {
        Hbar amount = Hbar.fromTinybars(1);

        TransferTransaction tx = new TransferTransaction()
                .addHbarTransfer(operatorId, amount.negated()) // debit
                .addHbarTransfer(operatorId, amount);          // credit

        TransactionResponse resp = tx.execute(client);
        TransactionReceipt receipt = resp.getReceipt(client);

        return Map.of(
                "transactionId", resp.transactionId.toString(),
                "status", receipt.status.toString(),
                "transferredTinybars", amount.toTinybars()
        );
    }
}
