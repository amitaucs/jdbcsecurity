package com.amisoft.securityjdbc.config;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class UserConfig {

    @Bean
    UserDetailsManager memory(DataSource ds){

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(ds);
        return jdbcUserDetailsManager;
    }

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }


    //@Bean
    InitializingBean initializer(UserDetailsManager manager){

        return () -> {

            UserDetails amisoft = User.withDefaultPasswordEncoder().username("amisoft").password("password").roles("USER").build();
            manager.createUser(amisoft);

            UserDetails dev = User.withUserDetails(amisoft).username("dev").password("password2").build();
            manager.createUser(dev);
        };
    }
}
