package com.gb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class GroceryBookingApplication {

	// TODO : add user creation with roles 						>> DONE
	// TODO : add Spring Security with Roles 					>> DONE

	// TODO : write unit tests									>> DONE

	// TODO: Integration Tests [Optional]
	// TODO: Swagger API [Optional]  							<<TRY>>

	// TODO : add H2 database for unit tests

	// TODO : create docker compose file						>> DONE
	// TODO : dockerize the application							>> DONE

	// TODO : Add Loggin with logback file : store in file		>> DONE
	// TODO : take port dynamically or take default port   		>> DONE

	// TODO : add documentation 								>> DONE
	// TODO : add transactional annotations						>> DONE

	public static void main(String[] args) {
		SpringApplication.run(GroceryBookingApplication.class, args);
	}

}
