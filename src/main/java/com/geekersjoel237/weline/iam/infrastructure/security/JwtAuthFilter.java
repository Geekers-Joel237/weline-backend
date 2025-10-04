package com.geekersjoel237.weline.iam.infrastructure.security;

import com.geekersjoel237.weline.iam.domain.repositories.CustomerRepository;
import com.geekersjoel237.weline.iam.domain.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final CustomerRepository customerRepository;

    public JwtAuthFilter(TokenService tokenService, CustomerRepository customerRepository) {
        this.tokenService = tokenService;
        this.customerRepository = customerRepository;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // 1. Extraire l'en-tête "Authorization"
        final String authHeader = request.getHeader("Authorization");

        // 2. Vérifier si l'en-tête existe et commence bien par "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Si non, on passe au filtre suivant
            return;
        }

        // 3. Extraire le token (la chaîne de caractères après "Bearer ")
        final String token = authHeader.substring(7);
        final String customerId = tokenService.getCustomerIdFromToken(token);

        // 4. Si on a un ID et que l'utilisateur n'est pas déjà authentifié
        if (customerId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // On charge l'utilisateur depuis la base de données (c'est notre UserDetailsService "maison")
            customerRepository.ofId(customerId).ifPresent(customer -> {

                // On vérifie que le token est valide
                if (tokenService.isTokenValid(token)) {
                    // Si tout est bon, on crée un objet d'authentification pour Spring Security
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            customer, // L'objet principal (notre Customer)
                            null,     // On n'a pas besoin des credentials (mot de passe)
                            new ArrayList<>() // On pourrait mettre les rôles/autorisations ici
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // On met à jour le Contexte de Sécurité. L'utilisateur est maintenant authentifié pour cette requête.
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            });
        }

        // On passe la main au filtre suivant dans la chaîne
        filterChain.doFilter(request, response);

    }
}
