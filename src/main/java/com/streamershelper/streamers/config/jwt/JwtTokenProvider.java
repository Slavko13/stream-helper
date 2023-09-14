package com.streamershelper.streamers.config.jwt;

import com.streamershelper.streamers.dto.user.JwtAuthenticationResponse;
import com.streamershelper.streamers.dto.user.LocalUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider
{


    private final String secret = "mySuperSecretKeyThatIsAtLeast32BytesLonfdpdsafasgdfjahfoadfgaskdfgaksdgfakdfgaskdfgasdfgkakdfgakfdgkafgkafgfaisdf";

    public JwtAuthenticationResponse generateToken(LocalUser localUser) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000); // Установите срок действия токена по своему усмотрению

        String token = Jwts.builder()
                .setSubject(localUser.getUsername())
                .claim("roles", localUser.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secret) // Замените "yourSecret" на ваш секретный ключ
                .compact();

        return new JwtAuthenticationResponse(token, "");
    }


    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}
