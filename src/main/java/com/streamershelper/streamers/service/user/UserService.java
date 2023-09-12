package com.streamershelper.streamers.service.user;

import com.streamershelper.streamers.dto.user.SignUpRequest;
import com.streamershelper.streamers.exception.UserAlreadyExistAuthenticationException;
import com.streamershelper.streamers.model.user.User;

public interface UserService {

    User findUserByEmail(String email);

    User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;
}
