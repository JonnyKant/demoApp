package com.modeling.demo.dto;


import com.modeling.demo.validation.PasswordMatches;
import com.modeling.demo.validation.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@PasswordMatches(message = "{password.mathes}")
public class UserDto {
//    @Size(min = 5, message = " {lastName.notempty} The author email '${validatedValue}' ${validatedValue} must be between {min} and '{max}' characters long")
    @Size(min = 5, max =20, message = "{firstName.notempty}")
    private String firstName;

    @Size(min = 5, max =20, message = "{lastName.notempty}")
    private String lastName;

    @NotEmpty(message = "{password.notempty}")
    private String password;

    private String matchingPassword;

    @ValidEmail
    @NotEmpty(message = "{email.notempty}")
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}