package cloudcomputing.uni7.cloudapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class CloudapiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CloudapiApplication.class, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CloudapiApplication.class);
    }

}
