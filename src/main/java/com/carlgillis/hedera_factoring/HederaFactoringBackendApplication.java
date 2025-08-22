package com.carlgillis.hedera_factoring;

import com.carlgillis.hedera_factoring.domain.Customer;
import com.carlgillis.hedera_factoring.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HederaFactoringBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HederaFactoringBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner demo(CustomerRepository repo) {
		return  args -> {
			// Save a record
			repo.save(new Customer(null, "Test Company"));

			// Read all records
			repo.findAll().forEach(System.out::println);
		};
	}
}


