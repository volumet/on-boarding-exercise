package vn.elca.training;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"vn.elca.training.repository",
        "vn.elca.training.service",
        "vn.elca.training.util",
        "vn.elca.training.model"})
@PropertySource({"classpath:/application-unit-test.properties"})
public class PimTestConfiguration {
}
