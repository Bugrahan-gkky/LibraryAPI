package com.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 1. Gelen istekten Token'ı al
            String jwt = getJwtFromRequest(request);

            // 2. Token doluysa ve geçerliyse
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

                // Token içinden kullanıcının e-postasını çıkar
                String email = tokenProvider.getEmailFromToken(jwt);

                // E-posta ile kullanıcı detaylarını veritabanından getir
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                // Spring Security bağlamına (Context) kullanıcıyı yerleştir (Kimlik doğrulaması başarılı)
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Security Context'e kullanıcı doğrulama bilgisi set edilemedi", ex);
        }

        // 3. Filtre zincirini devam ettir (İsteği Controller'a yolla)
        filterChain.doFilter(request, response);
    }

    // HTTP İsteğinin "Authorization" başlığından "Bearer [TOKEN]" kısmını parse eden yardımcı metod
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}