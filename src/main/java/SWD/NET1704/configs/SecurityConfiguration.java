package SWD.NET1704.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class SecurityConfiguration {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    public SecurityConfiguration(CustomUserDetailsService customUserDetailsService, JwtAuthenticationEntryPoint unauthorizedHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(authorizeRequestsConfigurer ->
                        authorizeRequestsConfigurer
                                .requestMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg",
                                        "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                                .requestMatchers(HttpMethod.GET, "/zoo-server/api/v1/product/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/zoo-server/api/v1/feedback/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/zoo-server/api/v1/animal/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/zoo-server/api/v1/new/**").permitAll()
                                .requestMatchers("/zoo-server/api/v1/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/zoo-server/api/v1/animalCage/**").hasAnyRole("TRAINER", "ADMIN", "STAFF")
                                .requestMatchers(HttpMethod.PUT, "/zoo-server/api/v1/animalCage/**").hasAnyRole("ADMIN", "STAFF")
                                .requestMatchers(HttpMethod.POST, "/zoo-server/api/v1/animalCage/**").hasAnyRole("ADMIN", "STAFF")
                                .requestMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/animalCage/**").hasAnyRole("ADMIN", "STAFF")
                                .requestMatchers(HttpMethod.GET, "/zoo-server/api/v1/order/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER")
                                .requestMatchers(HttpMethod.POST, "/zoo-server/api/v1/order/**").hasAnyRole("STAFF", "CUSTOMER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/zoo-server/api/v1/order/**").hasAnyRole("STAFF", "ADMIN", "CUSTOMER")
                                .requestMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/order/**").hasAnyRole("ADMIN", "STAFF")
                                .requestMatchers(HttpMethod.PUT, "/zoo-server/api/v1/product/**").hasAnyRole("ADMIN", "STAFF")
                                .requestMatchers(HttpMethod.POST, "/zoo-server/api/v1/product/**").hasAnyRole("ADMIN", "STAFF")
                                .requestMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/product/**").hasAnyRole("ADMIN", "STAFF")
                                .requestMatchers(HttpMethod.POST, "/zoo-server/api/v1/feedback/**").hasAnyRole("STAFF", "CUSTOMER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/zoo-server/api/v1/feedback/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER")
                                .requestMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/feedback/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/zoo-server/api/v1/role/**").hasAnyRole("ADMIN", "STAFF")
                                .requestMatchers(HttpMethod.POST, "/zoo-server/api/v1/role/**").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/zoo-server/api/v1/role/**").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/role/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/zoo-server/api/v1/catalogue/**").hasAnyRole("ADMIN", "STAFF")
                                .requestMatchers(HttpMethod.POST, "/zoo-server/api/v1/catalogue/**").hasAnyRole("STAFF", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/zoo-server/api/v1/catalogue/**").hasAnyRole("ADMIN", "STAFF")
                                .requestMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/catalogue/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/zoo-server/api/v1/user/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.POST, "/zoo-server/api/v1/user/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.PUT, "/zoo-server/api/v1/user/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/user/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/zoo-server/api/v1/AnimalCageDetail/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.POST, "/zoo-server/api/v1/AnimalCageDetail/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.PUT, "/zoo-server/api/v1/AnimalCageDetail/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/AnimalCageDetail/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/zoo-server/api/v1/animal/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.PUT, "/zoo-server/api/v1/animal/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/animal/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/zoo-server/api/v1/animal-diet-management/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.POST, "/zoo-server/api/v1/animal-diet-management/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.PUT, "/zoo-server/api/v1/animal-diet-management/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/animal-diet-management/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/zoo-server/api/v1/animal-management/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.POST, "/zoo-server/api/v1/animal-management/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.PUT, "/zoo-server/api/v1/animal-management/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/animal-management/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/zoo-server/api/v1/area-management/**").hasAnyRole("ADMIN", "STAFF")
                                .requestMatchers(HttpMethod.POST, "/zoo-server/api/v1/area-management/**").hasAnyRole("ADMIN", "STAFF")
                                .requestMatchers(HttpMethod.PUT, "/zoo-server/api/v1/area-management/**").hasAnyRole("ADMIN", "STAFF")
                                .requestMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/area-management/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/zoo-server/api/v1/area/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.POST, "/zoo-server/api/v1/area/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.PUT, "/zoo-server/api/v1/area/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/area/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/zoo-server/api/v1/diet/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.POST, "/zoo-server/api/v1/diet/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.PUT, "/zoo-server/api/v1/diet/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/diet/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/zoo-server/api/v1/food/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.POST, "/zoo-server/api/v1/food/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.PUT, "/zoo-server/api/v1/food/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/food/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/zoo-server/api/v1/new/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.PUT, "/zoo-server/api/v1/new/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER", "TRAINER")
                                .requestMatchers(HttpMethod.DELETE, "/zoo-server/api/v1/new/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                );

        // Add custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
