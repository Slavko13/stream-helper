package com.streamershelper.streamers.validator;

import com.streamershelper.streamers.dto.user.SignUpRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, SignUpRequest>
{

    @Override
    public boolean isValid(final SignUpRequest user, final ConstraintValidatorContext context) {
        return user.getPassword().equals(user.getMatchingPassword());
    }

}
