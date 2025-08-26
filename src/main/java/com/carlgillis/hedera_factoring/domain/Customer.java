package com.carlgillis.hedera_factoring.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "customers")
@Getter
@Setter
//@Data                  // Lombok: getters, setters, toString, equals/hashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // Optional: Hedera account ID for the customer (string)
    private String accountId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();
}
