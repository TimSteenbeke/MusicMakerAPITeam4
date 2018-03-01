//package be.kdg.ip.security;
//
//
//import be.kdg.ip.services.api.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
///**
// * Created by wouter on 21.12.16.
// */
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class MultiHttpSecurityConfig {
//
//    // @Autowired
//    // private UserService userService;
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//     /*   auth.
//                userDetailsService(userService).
//                passwordEncoder(passwordEncoder());
//        auth.inMemoryAuthentication().withUser("dummy@kdg.be").password("dummy").roles("Administrator");
//        */
//    }
//
//    @Configuration
//    @Order(1)
//    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
//        protected void configure(HttpSecurity http) throws Exception {
//           /* http
//                    .antMatcher("/JOS/**")
//                    .authorizeRequests()
//                    .anyRequest().hasRole("Administrator")
//                    .and()
//                    .httpBasic(); */
//            http.csrf().disable();
//
//            // Om H2 in memory databank te bekijken op url localhost:port/console
//            http.headers().frameOptions().disable();
//
//
//        }
//    }
//
//    @Configuration
//    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//           /* http
//                    .authorizeRequests()
//                    .anyRequest().authenticated()
//                    .and()
//                    .formLogin();
//                    */
//            http.csrf().disable();
//            // Om H2 in memory databank te bekijken op url localhost:port/console
//            http.headers().frameOptions().disable();
//        }
//    }
//
//
//
//    /*
//
//    @Configuration
//    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.requestMatcher(request -> {
//                final String url = request.getServletPath() + request.getPathInfo();
//                return !(url.startsWith("/api/"));
//            });
//
//            http.authorizeRequests()
//                    //.antMatchers("/webjars/**","/js/**","/css/**","/images/**").permitAll()
//                    //.antMatchers("/webjars/**", "/signup.html", "/logout", "/login", "/").permitAll()
//                    .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//                    .antMatchers("/student/**").access("hasRole('ROLE_STUDENT')")
//                    .anyRequest().authenticated()    // remaining URL's require authentication
//                    .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .failureUrl("/login-error")
//                    .permitAll()
//                    .and()
//                    .logout()
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                    .logoutSuccessUrl("/")
//                    .permitAll()
//                    .and()
//                    .csrf()
//                    .and()
//                    .httpBasic();
//        }
//
//    }
//    */
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
