package com.streamershelper.streamers.service.user;

import com.streamershelper.streamers.dto.user.LocalUser;
import com.streamershelper.streamers.model.user.User;
import com.streamershelper.streamers.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocalUserDetailService implements UserDetailsService
{

    private final UserService userService;

    @Override
    @Transactional
    public LocalUser loadUserByUsername(final String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }
        return createLocalUser(user);
    }

    /**
     * @param user The user entity
     * @return LocalUser The spring user object
     */
    private LocalUser createLocalUser(User user) {
        return new LocalUser(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, CommonUtils.buildSimpleGrantedAuthorities(user.getRoles()));
    }
}
