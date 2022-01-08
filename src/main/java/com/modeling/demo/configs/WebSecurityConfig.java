package com.modeling.demo.configs;

import com.modeling.demo.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailsService userSevice;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/catalog", "/","/order","/addInOrder","/actuator/httptrace",
                        "/delInOrder","/addProduct","/del", "/addProductPage", "/image/*",
                        "/styles/**","/editProduct","/editProduct/**","/catalogSearch","/registration", "/errortest", "/sendOrder",
                        "/noAuthOrder","/AuthOrder","/showOrder","/checkout","/charge").permitAll()
                .anyRequest()
                .authenticated()
              .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .defaultSuccessUrl("/catalog")

//                .failureUrl("/login-error")
                .permitAll()
                .and().rememberMe().key("uniqueAndSecret")
              .and()
                .logout().permitAll()
              .and()
                .rememberMe();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSevice)
                .passwordEncoder(passwordEncoder);
    }
}
//