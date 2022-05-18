package ru.animalcare.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.animalcare.domain.User;
import ru.animalcare.service.UserDetailsServiceImpl;

@Component
public class UserValidator implements Validator {
    private final UserDetailsServiceImpl userService;

    @Autowired
    public UserValidator(@Qualifier("UserDetailsServiceImpl") UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.not_empty");

        if (user.getEmail().length() < 4) {
            errors.rejectValue("email", "register.error.username.less_4");
        }
        if (user.getEmail().length() > 32) {
            errors.rejectValue("email", "register.error.username.over_32");
        }
        if (userService.checkByEmail(user.getEmail())) {
            errors.rejectValue("email", "register.error.duplicated.email");
        }
        if (user.getPassword().length() < 8) {
            errors.rejectValue("password", "register.error.password.less_8");
        }
        if (user.getPassword().length() > 32) {
            errors.rejectValue("password", "register.error.password.over_32");
        }
    }
}