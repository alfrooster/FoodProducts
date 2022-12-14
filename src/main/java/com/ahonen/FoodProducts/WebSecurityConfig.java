package com.ahonen.FoodProducts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ahonen.FoodProducts.web.UserDetailServiceImpl;


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
    		.authorizeRequests()
    			//allow access to these endpoints without authentication
    		  .antMatchers("/", "/foodlist", "/register", "/saveuser").permitAll()
	    	  .anyRequest().authenticated()
	    	  .and()
	    	.formLogin()
	    	  .loginPage("/login") //endpoint for login page
	    	  .defaultSuccessUrl("/foodlist", true) //redirects to foodlist after login
	    	  .permitAll()
	    	  .and()
	    	.logout()
	    	  .permitAll();
    }


    @Autowired
    private UserDetailServiceImpl userDetailsService;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

}
