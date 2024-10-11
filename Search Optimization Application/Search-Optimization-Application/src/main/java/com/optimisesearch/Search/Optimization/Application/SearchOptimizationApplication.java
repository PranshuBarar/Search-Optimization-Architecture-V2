package com.optimisesearch.Search.Optimization.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.searchoptimization.reposervice.repo")
@EntityScan("com.searchoptimization.reposervice.entities")
@SpringBootApplication(scanBasePackages = {"com.searchoptimization.authservice", "com.searchoptimization.reposervice", "com.optimisesearch", "com.searchoptimization", "com.searchoptimization.resourceservice"})
public class SearchOptimizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchOptimizationApplication.class, args);
	}
}
