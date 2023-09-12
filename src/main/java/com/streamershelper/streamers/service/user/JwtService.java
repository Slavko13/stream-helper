package com.streamershelper.streamers.service.user;


import com.streamershelper.streamers.dto.user.JwtAuthenticationResponse;
import com.streamershelper.streamers.dto.user.LocalUser;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Data
public class JwtService
{

    @Value("${jwt.access.token.exp}")
    private String accessTokenExpirationTime;

    @Value("${jwt.refresh.token.exp}")
    private String refreshTokenExpirationTime;

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;


    public JwtAuthenticationResponse generateTokenResponse(LocalUser localUser) {
        return new JwtAuthenticationResponse(generateToken(localUser, Long.valueOf(accessTokenExpirationTime)), generateToken(localUser, Long.valueOf(refreshTokenExpirationTime)));
    }


    public String generateToken(LocalUser localUser, Long expirationTime) {
        Instant now = Instant.now();

        List<String> scope = localUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtClaimsSet claims = createMainBuilderJwtClaims(localUser.getUsername(), scope)
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirationTime))
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private JwtClaimsSet.Builder createMainBuilderJwtClaims(final String username, final List<String> scope) {
        return JwtClaimsSet.builder().claim("scope", scope).subject(username);
    }

    public Map<String, Object> parseToken(String token) {
        Jwt jwt = decoder.decode(token);
        return jwt.getClaims();
    }

    public String generateNewAccessToken(String token) {
        Instant now = Instant.now();
        Map<String, Object> refreshTokenClaims = parseToken(token);
        JwtClaimsSet claims = createMainBuilderJwtClaims((String) refreshTokenClaims.get("sub"), (List<String>) refreshTokenClaims.get("scope"))
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(Long.valueOf(accessTokenExpirationTime)))
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
