package com.streamershelper.streamers.controller.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.streamershelper.streamers.config.jwt.JwtTokenProvider;
import com.streamershelper.streamers.dto.user.*;
import com.streamershelper.streamers.exception.UserAlreadyExistAuthenticationException;
import com.streamershelper.streamers.model.user.GoogleToken;
import com.streamershelper.streamers.model.user.User;
import com.streamershelper.streamers.service.user.JwtService;
import com.streamershelper.streamers.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/api/auth")
public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtGeneratorService;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody String refreshToken) {
        try {
            String newAccessToken = jwtGeneratorService.generateNewAccessToken(refreshToken);
            return ResponseEntity.ok(newAccessToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Refresh token has expired");
        }
    }

    @PostMapping("/signin")
    @Operation(summary = "Вход", description = "Входи не бойс")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(signinUser(loginRequest.getEmail(), loginRequest.getPassword()));
    }


    @PostMapping("/signup")
    @Operation(summary = "Регистрация", description = "Ну зарегестрируйся теперь")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            User user = userService.registerNewUser(signUpRequest);
        } catch (UserAlreadyExistAuthenticationException e) {
            log.error("Exception Occurred", e);
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));
    }


    @PostMapping("/google")
    public ResponseEntity<?> authenticateWithGoogle(@RequestBody GoogleToken googleToken) {
        String idToken = googleToken.getIdToken();

        // Теперь используйте ваш метод верификации для проверки токена
        UserDto userInfo = verifyToken(idToken);

        if (userInfo != null) {
            UserDto user = userService.getUserInfoByEmail(userInfo.getEmail());
            if (user != null) {
                return ResponseEntity.ok(signinUser(user.getEmail(),"" ));
            } else {
                userService.registerNewUserByGoogle(userInfo);
                return ResponseEntity.ok().body("User registered successfully");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid Google authentication token");
        }
    }



    private UserDto verifyToken(String idTokenString) {
        UserDto userInfo = null;

        try {
            // Подготовка верификатора токена
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                    .setAudience(Collections.singletonList("YOUR_CLIENT_ID"))
                    .build();

            // Проверка токена
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String userId = payload.getSubject();
                String email = payload.getEmail();
                String name = (String) payload.get("name");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");
                userInfo = new UserDto(email, name, true, true);
            }
        } catch (Exception e) {
            // Обработка исключений
            e.printStackTrace();
        }

        return userInfo;
    }

    private JwtAuthenticationResponse signinUser(final String email, final String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LocalUser localUser = (LocalUser) authentication.getPrincipal();
        return jwtTokenProvider.generateToken(localUser);
    }

}
