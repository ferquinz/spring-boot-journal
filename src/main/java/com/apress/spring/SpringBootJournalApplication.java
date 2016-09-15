package com.apress.spring;

import com.apress.spring.domain.Journal;
import com.apress.spring.repository.JournalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.liquibase.LiquibaseServiceLocatorApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.List;

@SpringBootApplication
public class SpringBootJournalApplication {

	private static Logger log = LoggerFactory.getLogger(SpringBootJournalApplication.class);

	@Value("${myapp.server-ip}")
	String serverIp;



	@Bean
	InitializingBean saveData(JournalRepository repo){
		return () -> {
			repo.save(new Journal("Get to know Spring Boot", "Today I will learn Spring Boot", "01/01/2016"));
			repo.save(new Journal("Simple Spring Boot Project", "I will do my first Spring Boot Project", "01/02/2016"));
			repo.save(new Journal("Spring Boot Reading", "Read more about Spring Boot", "02/01/2016"));
			repo.save(new Journal("Spring Boot in the Cloud", "Spring Boot using Cloud Foundry", "03/01/2016"));
		};
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SpringBootJournalApplication.class);
		//app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
		//SpringApplication.run(SpringBootJournalApplication.class, args);
	}
}

@Component
class MyComponent {

	private static final Logger log = LoggerFactory.getLogger(MyComponent.class);

	@Autowired
	public MyComponent(ApplicationArguments args){
		boolean enable = args.containsOption("enable");
		if (enable)
			log.info("## > You are enable!");
		List<String> _args = args.getNonOptionArgs();
		log.info("## > extra args ...");
		if (!_args.isEmpty())
			_args.forEach(file -> log.info(file));
	}

}