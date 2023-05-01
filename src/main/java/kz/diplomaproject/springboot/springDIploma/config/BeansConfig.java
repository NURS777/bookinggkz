package kz.diplomaproject.springboot.springDIploma.config;

import kz.diplomaproject.springboot.springDIploma.beans.DatabaseBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//configuration by name excaxtly to get which configuration to use in future
@Configuration
public class BeansConfig {

    @Bean(name = "dbEventBean")
    public DatabaseBean databaseBean(){
        return new DatabaseBean();
    }

}
