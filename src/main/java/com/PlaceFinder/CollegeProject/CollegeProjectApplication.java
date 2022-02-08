package com.PlaceFinder.CollegeProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.PlaceFinder.CollegeProject.Config.SwaggerConfig;

@SpringBootApplication
@Import(SwaggerConfig.class)
public class CollegeProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollegeProjectApplication.class, args);
	}

}
