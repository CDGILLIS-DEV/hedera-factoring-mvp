package com.carlgillis.hedera_factoring.config;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PrivateKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
@Configuration
public class HederaClientConfig {
    @Bean
    public Client hederaClient(HederaProperties props) {
        String network = props.getNetwork() != null ? props.getNetwork() : "testnet";

        Client client = Client.forName(network);
        client.setOperator(
                AccountId.fromString(props.getOperatorId()),
                PrivateKey.fromString(props.getOperatorKey())
        );
        client.setDefaultMaxTransactionFee(new Hbar(2));
        client.setDefaultMaxQueryPayment(new Hbar(1));
        client.setRequestTimeout(Duration.ofSeconds(20));

        return client;
    }
}
