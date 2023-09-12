package com.streamershelper.streamers.controller;

import com.streamershelper.streamers.dto.user.*;
import com.streamershelper.streamers.exception.UserAlreadyExistAuthenticationException;
import com.streamershelper.streamers.model.user.User;
import com.streamershelper.streamers.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/api/auth")
public class AuthController {

    final UserService userService;
    final JwtEncoder encoder;

    final AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LocalUser localUser = (LocalUser) authentication.getPrincipal();
        String jwt = getToken(localUser);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    private String getToken(LocalUser localUser) {
        Instant now = Instant.now();
        long expiry = 36000L;
        // @formatter:off
        List<String> scope = localUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(localUser.getUsername())
                .claim("scope", scope)
                .build();
        // @formatter:on
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            User user = userService.registerNewUser(signUpRequest);
        } catch (UserAlreadyExistAuthenticationException e) {
            log.error("Exception Occurred", e);
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));
    }
}
