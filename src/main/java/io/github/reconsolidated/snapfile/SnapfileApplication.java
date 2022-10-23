package io.github.reconsolidated.snapfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class SnapfileApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnapfileApplication.class, args);
	}

}
