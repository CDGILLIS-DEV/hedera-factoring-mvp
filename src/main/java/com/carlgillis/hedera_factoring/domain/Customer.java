package com.carlgillis.hedera_factoring.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "customers")
@Getter
@Setter
@Data                  // Lombok: getters, setters, toString, equals/hashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@Builder
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String email;

    // Optional: Hedera account ID for the customer (string)
    private String accountId;

    @Builder.Default
    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt = Instant.now();
}
