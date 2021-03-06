package vn.elca.training;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.ApplicationMapper;
import vn.elca.training.web.AbstractApplicationController;

/**
 * @author gtn
 *
 */
@SpringBootApplication(scanBasePackages = "vn.elca.training")
@ComponentScan(basePackageClasses = {
        AbstractApplicationController.class,
        ApplicationMapper.class,
        ProjectService.class
}, basePackages = "vn.elca.training")
@PropertySource({"classpath:/application.properties", "classpath:/messages.properties"})
public class ApplicationWebConfig extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ApplicationWebConfig.class);
    }

}
