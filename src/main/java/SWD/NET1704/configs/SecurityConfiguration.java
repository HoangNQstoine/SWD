package SWD.NET1704.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import SWD.NET1704.jwts.CustomUserDetailsService;
import SWD.NET1704.jwts.JwtAuthenticationEntryPoint;
import SWD.NET1704.jwts.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final
    CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    public SecurityConfiguration(CustomUserDetailsService customUserDetailsService, JwtAuthenticationEntryPoint unauthorizedHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }


    public JwtAuthenticationFilter  jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/zoo-server/api/v1/product/**").permitAll()
                .antMatchers(HttpMethod.GET, "/zoo-server/api/v1/feedback/**").permitAll()
                .antMatchers(HttpMethod.GET, "/zoo-server/api/v1/animal/**").permitAll()
                .antMatchers(HttpMethod.GET, "/zoo-server/api/v1/new/**").permitAll()
                .antMatchers("/zoo-server/api/v1/auth/**").permitAll()

                // permission AnimalCage API
                .antMatchers(HttpMethod.GET, "/zoo-server/api/v1/animalCage/**").hasAnyRole("TRAINER", "ADMIN", "STAFF")
                .antMatchers(HttpMethod.PUT, "/zoo-server/api/v1/animalCage/**").hasAnyRole("ADMIN", "STAFF")
                .antMatchers(HttpMethod.POST, "/zoo-server/api/v1/animalCage/**").hasAnyRole("ADMIN", "STAFF")
                .antMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/animalCage/**").hasAnyRole("ADMIN", "STAFF")

                // permission Order API
                .antMatchers(HttpMethod.GET, "/zoo-server/api/v1/order/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER")
                .antMatchers(HttpMethod.POST, "/zoo-server/api/v1/order/**").hasAnyRole("STAFF", "CUSTOMER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/zoo-server/api/v1/order/**").hasAnyRole("STAFF", "ADMIN", "CUSTOMER")
                .antMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/order/**").hasAnyRole("ADMIN", "STAFF")

                // permission Product API
                .antMatchers(HttpMethod.PUT, "/zoo-server/api/v1/product/**").hasAnyRole("ADMIN", "STAFF")
                .antMatchers(HttpMethod.POST, "/zoo-server/api/v1/product/**").hasAnyRole("ADMIN", "STAFF")
                .antMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/product/**").hasAnyRole("ADMIN", "STAFF")

                // permission Feedback API
                .antMatchers(HttpMethod.POST, "/zoo-server/api/v1/feedback/**").hasAnyRole("STAFF", "CUSTOMER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/zoo-server/api/v1/feedback/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER")
                .antMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/feedback/**").hasRole("ADMIN")

                // permission Role API
                .antMatchers(HttpMethod.GET, "/zoo-server/api/v1/role/**").hasAnyRole("ADMIN", "STAFF")
                .antMatchers(HttpMethod.POST, "/zoo-server/api/v1/role/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/zoo-server/api/v1/role/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/role/**").hasRole("ADMIN")

                // permission Catalogue API
                .antMatchers(HttpMethod.GET, "/zoo-server/api/v1/catalogue/**").hasAnyRole("ADMIN", "STAFF")
                .antMatchers(HttpMethod.POST, "/zoo-server/api/v1/catalogue/**").hasAnyRole("STAFF", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/zoo-server/api/v1/catalogue/**").hasAnyRole("ADMIN", "STAFF")
                .antMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/catalogue/**").hasRole("ADMIN")

                // permission User API
                .antMatchers(HttpMethod.GET, "/zoo-server/api/v1/user/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.POST, "/zoo-server/api/v1/user/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.PUT, "/zoo-server/api/v1/user/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/user/**").hasRole("ADMIN")

                // permission AnimalCageDetail API
                .antMatchers(HttpMethod.GET, "/zoo-server/api/v1/AnimalCageDetail/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.POST, "/zoo-server/api/v1/AnimalCageDetail/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.PUT, "/zoo-server/api/v1/AnimalCageDetail/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/AnimalCageDetail/**").hasRole("ADMIN")

                // permission animal API
                .antMatchers(HttpMethod.POST, "/zoo-server/api/v1/animal/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.PUT, "/zoo-server/api/v1/animal/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/animal/**").hasRole("ADMIN")

                // permission animal-diet-management API
                .antMatchers(HttpMethod.GET, "/zoo-server/api/v1/animal-diet-management/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.POST, "/zoo-server/api/v1/animal-diet-management/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.PUT, "/zoo-server/api/v1/animal-diet-management/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/animal-diet-management/**").hasRole("ADMIN")

                // permission animal-management API
                .antMatchers(HttpMethod.GET, "/zoo-server/api/v1/animal-management/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.POST, "/zoo-server/api/v1/animal-management/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.PUT, "/zoo-server/api/v1/animal-management/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/animal-management/**").hasRole("ADMIN")

                // permission area-management API
                .antMatchers(HttpMethod.GET, "/zoo-server/api/v1/area-management/**").hasAnyRole("ADMIN", "STAFF")
                .antMatchers(HttpMethod.POST, "/zoo-server/api/v1/area-management/**").hasAnyRole("ADMIN", "STAFF")
                .antMatchers(HttpMethod.PUT, "/zoo-server/api/v1/area-management/**").hasAnyRole("ADMIN", "STAFF")
                .antMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/area-management/**").hasRole("ADMIN")

                // permission area API
                .antMatchers(HttpMethod.GET, "/zoo-server/api/v1/area/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.POST, "/zoo-server/api/v1/area/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.PUT, "/zoo-server/api/v1/area/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/area/**").hasRole("ADMIN")

                // permission diet API
                .antMatchers(HttpMethod.GET, "/zoo-server/api/v1/diet/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.POST, "/zoo-server/api/v1/diet/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.PUT, "/zoo-server/api/v1/diet/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/diet/**").hasRole("ADMIN")

                // permission food API
                .antMatchers(HttpMethod.GET, "/zoo-server/api/v1/food/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.POST, "/zoo-server/api/v1/food/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.PUT, "/zoo-server/api/v1/food/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/food/**").hasRole("ADMIN")

                // permission new API
                .antMatchers(HttpMethod.POST, "/zoo-server/api/v1/new/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.PUT, "/zoo-server/api/v1/new/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                .antMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/new/**").hasRole("ADMIN")

                .anyRequest()
                .authenticated();

        // add our custom jwt security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
