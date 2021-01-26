package me.subin.commonsuser.security;

import me.subin.commonsuser.filter.JwtAuthenticationTokenFilter;
import me.subin.commonsuser.service.IJwtDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;

/**
 * @Describe
 * @Author soda
 * @Create 2019/7/23 14:25
 **/
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
//    @Autowired
//    WebSecurityConfig(JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter){
//        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,"/**/**").permitAll()
                .antMatchers(HttpMethod.POST,"/jwt-user/login/**").permitAll()
                .antMatchers("/jwt-user/logout").permitAll()
                .antMatchers("/area/**").permitAll()
                .antMatchers("/product/**").permitAll()
                .antMatchers("/user-auth/**","/user-info/**","/user-role-auth/**","/user-role/**").permitAll()
                .anyRequest().permitAll();

        http.addFilterAfter(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
//        super.configure(http);
    }
}
