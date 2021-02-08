package com.spring_boot.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    /**
     * configure HttpSecurity settings
     */
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .mvcMatchers("api/account")
                    .hasRole("ADMIN")
                .anyRequest()
                    .permitAll()

        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository())
                .and()
            .cors().configurationSource(getCorsConfigurationSource())
    }

    /**
     * set in memory user details
     */
    override fun userDetailsService(): UserDetailsService {
        val user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build()
        return InMemoryUserDetailsManager(user)
    }


    /**
     * @return [CorsConfigurationSource] which defines CORS settings
     */
    private fun getCorsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.addAllowedOrigin("https://engineer_review.com")
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL)
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL)
        corsConfiguration.allowCredentials = true

        val corsSource = UrlBasedCorsConfigurationSource()
        corsSource.registerCorsConfiguration("/**", corsConfiguration)

        return corsSource
    }
}