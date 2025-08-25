# Hedera Factoring Backend (MVP)

Spring Boot + Postgres + Hedera Java SDK

## Postgres
- Java 21
- Docker
- Gradle
- Hedera Testnet account (operator ID & private key)

## Run Postgres
docker compose up -0d

## App Config (env vars)
- HEDERA_NETWORK=TESTNET
- HEDERA_OPERATOR_ID=0.0.xxxx
- HEDERA_OPERATOR_KEY=302E02...

Set these in your IDE Run configuration or export in your shell before running

## Run 
./gradlew bootRun

## Endpoints
- GET /health -> {"status":"UP"}
- GET /hedera/balance -> { accountId, hbars, tokens }
- POST /hedera/ping -> sends 1 tinybar from operator to itself; retutns transactionId and status

## Troubleshooting
- If /hedera/balance errors with gRPC transport: add dependency `io.grpc:grpc-netty-shaded:1.64.0`
- If UnknownHostException for testnet.mirrornode.hedera.com:
  - macOS: `sudo dscacheutil -flushcache; sudo killall -HUP mDNSResponder`