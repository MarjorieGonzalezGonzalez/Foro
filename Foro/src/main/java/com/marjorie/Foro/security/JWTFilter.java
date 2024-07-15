package com.marjorie.Foro.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import com.marjorie.Foro.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
//
//@Component
//public class JWTFilter extends OncePerRequestFilter {
//    @Autowired
//    private TokenService tokenService;
//
//    @Autowired
//    private MyUserDetailsService userDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//            String email = tokenService.getUsernameFromToken(token);
//            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//                if (tokenService.validateToken(token, userDetails)) {
//                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                            userDetails, null, userDetails.getAuthorities());
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                }
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//}

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        // JWT Token está en el form "Bearer token". Remover el texto "Bearer " y obtener el JWT token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = tokenService.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("No se puede obtener el JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("El JWT Token ha expirado");
            }
        } else {
            logger.warn("JWT Token no comienza con Bearer");
        }

        // Una vez obtenido el token, validar el mismo.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Si el token es válido, configurar Spring Security para que se autentique manualmente
            if (tokenService.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Después de configurar la autenticación en el contexto, especificamos
                // que el usuario actual está autenticado. Así que pasa la configuración de Spring Security con éxito.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}

