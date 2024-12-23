package cm.ex.bug.security.filter;

import cm.ex.bug.entity.Authority;
import cm.ex.bug.entity.User;
import cm.ex.bug.repository.UserRepository;
import cm.ex.bug.security.authentication.UserAuth;
import cm.ex.bug.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String username = extractUsernameAndPassword(authHeader)[0];
            String password = extractUsernameAndPassword(authHeader)[1];

            UserAuth auth = userCredentialAuth(username, password);
            if (!auth.isAuthenticated()) {
                throw new AccessDeniedException("Bad Credentials");
            }
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(7);

            UserAuth auth = userTokenAuth(token);

            System.out.println("## auth bearer : " + auth.toString());
            if (!auth.isAuthenticated()) {
                throw new AccessDeniedException("Bad Credentials");
            }
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private UserAuth userCredentialAuth(String username, String password) throws AccessDeniedException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            throw new AccessDeniedException("Bad Credentials");
        }

        return new UserAuth(user.get().getId().toString(), true, username, null, null, user.get().getName(), convertToGrantedAuthorities(user.get().getAuthoritySet()), user.get());
    }

    private UserAuth userTokenAuth(String token) throws AccessDeniedException {
        final String userEmail = jwtService.extractEmail(token);
        Optional<User> user = userRepository.findByEmail(userEmail);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }

        UserAuth userAuth = new UserAuth(user.get().getId().toString(), true, user.get().getEmail(), null, null, user.get().getName(), convertToGrantedAuthorities(user.get().getAuthoritySet()), user.get());
        if (!jwtService.isTokenValid(token, userAuth)) {
            throw new JwtException("Invalid token");
        }
        return userAuth;
    }

    private String[] extractUsernameAndPassword(String authorization) {
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        return credentials.split(":", 2); // values = [username, password]
    }

    private List<GrantedAuthority> convertToGrantedAuthorities(Set<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }

}
