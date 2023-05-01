package kz.diplomaproject.springboot.springDIploma.config;

import kz.diplomaproject.springboot.springDIploma.services.UserService;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


//configure web spring security by/from login inteface
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,proxyTargetClass = true,securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    //default configuration
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling().accessDeniedPage("/accessdeniedpage");

        http.authorizeRequests().
                antMatchers("/css/**").permitAll().
                antMatchers("/js/**").permitAll().
                antMatchers("/").permitAll();

        http.formLogin().
                loginPage("/mainpage").permitAll().
                loginProcessingUrl("/login").permitAll().
                usernameParameter("user_logmail").
                passwordParameter("user_logpassword").
                defaultSuccessUrl("/homepage").
                failureUrl("/?loginerror");

        http.logout().permitAll().
                logoutUrl("/tologout").
                logoutSuccessUrl("/mainpage");
    }

    //bean for use password encoder in future/for hashing passwords
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
