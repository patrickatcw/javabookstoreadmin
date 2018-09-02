package com.JavaBookstoreAdmin.config;


import com.JavaBookstoreAdmin.service.impl.UserSecurityService;
import com.JavaBookstoreAdmin.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration        //a configuration class sprong boot will enable configuration
@EnableWebSecurity   //enables the web security provided by spring
@EnableGlobalMethodSecurity(prePostEnabled=true)    //allows for more detailed users rows, etc.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private Environment env;

    @Autowired
    private UserSecurityService userSecurityService;   //need to make UserSecurityService after made this class

    private BCryptPasswordEncoder passwordEncoder() {
        return SecurityUtility.passwordEncoder();       //need to make SecurityUtility
    }

    //files available without security, easily accessed
    //https://docs.oracle.com/javase/7/docs/api/java/util/regex/Matcher.html
    private static final String[] PUBLIC_MATCHERS = {
            "/css/**",
            "/js/**",
            //paths important for new user login and email notification
            "/newUser",
            "/forgetPassword",
            "/login",
            //"/addBook",
            "/fonts/**"

    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().
                        antMatchers(PUBLIC_MATCHERS).
                permitAll().anyRequest().authenticated(); //any files easily available, otherwise requires authentication

        http
                .csrf().disable().cors().disable()          //disables crossite requests
                .formLogin().failureUrl("/login?error")
                    //the default for sussesful login should be the home path
                .defaultSuccessUrl("/home")
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
                .and()
                .rememberMe();  //enables rememberme
    }

    //authenticates encrypted stored password by invoking userSecurityServices
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
    }

}

