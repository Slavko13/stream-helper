package com.streamershelper.streamers.service.user;

import com.streamershelper.streamers.dto.user.SignUpRequest;
import com.streamershelper.streamers.dto.user.UserDto;
import com.streamershelper.streamers.exception.UserAlreadyExistAuthenticationException;
import com.streamershelper.streamers.exception.http.NotFoundException;
import com.streamershelper.streamers.model.user.Role;
import com.streamershelper.streamers.model.user.User;
import com.streamershelper.streamers.repository.user.RoleRepository;
import com.streamershelper.streamers.repository.user.UserRepository;
import com.streamershelper.streamers.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseService implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    @Transactional(value = "transactionManager")
    public User registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException
    {
        if (userRepository.existsByEmailIgnoreCase(signUpRequest.getEmail())) {
            throw new UserAlreadyExistAuthenticationException("User with email id " + signUpRequest.getEmail() + " already exist");
        }
        User user = map(signUpRequest, User.class);
        user.addRole(roleRepository.findByName(Role.ROLE_USER));
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user = userRepository.save(user);
        userRepository.flush();
        return user;
    }


    @Override
    @Transactional(value = "transactionManager")
    public UserDto registerNewUserByGoogle(final UserDto userDto) throws UserAlreadyExistAuthenticationException
    {
        User user = map(userDto, User.class);
        user.addRole(roleRepository.findByName(Role.ROLE_USER));
        user.setEnabled(true);
        userRepository.save(user);
        return map(getUserByEmail(userDto.getEmail()), UserDto.class);
    }

    @Override
    @Transactional(value = "transactionManager")
    public UserDto getUserInfoByEmail(final String email)
    {
        if (!userRepository.existsByEmailIgnoreCase(email)) {
            throw new NotFoundException("User not found");
        }
        UserDto user = map(getUserByEmail(email), UserDto.class);
        return user;
    }

    private User getUserByEmail(final  String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

}

