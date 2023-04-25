package kz.diplomaproject.springboot.springDIploma.config;

import kz.diplomaproject.springboot.springDIploma.beans.DatabaseBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean(name = "dbEventBean")
    public DatabaseBean databaseBean(){
        return new DatabaseBean();
    }

}
