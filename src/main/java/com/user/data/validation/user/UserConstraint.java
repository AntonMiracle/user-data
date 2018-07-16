package com.user.data.validation.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {UserValidation.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface UserConstraint {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String usernameRegExp() default "[A-Za-z]+";

    String passwordRegExp() default "[A-Za-z]+";

    String emailRegExp() default "[A-Za-z]+";

    String phoneRegExp() default "[A-Za-z]+";

//    String usernameRegExp() default "^.{3,}$";
//
//    String passwordRegExp() default "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[#&%!])(?=\\S+$).{8,}$";
//
//    String emailRegExp() default "^(?=.*[@])(?=\\S+$).{5,}$";
//
//    String phoneRegExp() default "^[+]?[0-9](?=\\S+$).{5,}";
}
