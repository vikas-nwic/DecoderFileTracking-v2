package com.nwic.gt.decoder.file.tracking.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DecoderFileTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DecoderFileTrackingApplication.class, args);
	}
}