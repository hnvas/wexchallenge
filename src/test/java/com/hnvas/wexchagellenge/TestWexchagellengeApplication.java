package com.hnvas.wexchagellenge;

import org.springframework.boot.SpringApplication;

public class TestWexchagellengeApplication {

	public static void main(String[] args) {
		SpringApplication.from(WexchagellengeApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
