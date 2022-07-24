package com.visademo.cartExtra;

import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static com.visademo.cartExtra.util.IOUtil.*;

@SpringBootApplication
public class CartExtraApplication {
	private static final Logger logger = LoggerFactory.getLogger(CartExtraApplication.class);
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CartExtraApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", 8081));
		DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);
		List<String> opsVal = appArgs.getOptionValues("dataDir");
		System.out.println(opsVal);
		if (opsVal != null) {
			logger.info("" + (String) opsVal.get(0));
			createDir((String) opsVal.get(0));
		} else {
			logger.warn("No data directory was provided");
			System.exit(1);
		}
		app.run(args);
	}
}