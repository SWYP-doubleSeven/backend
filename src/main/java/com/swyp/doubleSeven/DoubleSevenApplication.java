package com.swyp.doubleSeven;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.swyp.doubleSeven.domain.*.dao")
public class DoubleSevenApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoubleSevenApplication.class, args);
	}

}
