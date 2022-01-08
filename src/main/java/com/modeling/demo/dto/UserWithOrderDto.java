package com.modeling.demo.dto;

import com.modeling.demo.domains.Order;
import com.modeling.demo.validation.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

    public class UserWithOrderDto {
        @NotEmpty
        @Size(min = 5, message = "The author email '${validatedValue}' ${validatedValue} must be between {min} and '{max}' characters long")
        private String firstName;

        @NotEmpty
        @Size(min = 5, message = "{lastName.notempty}")
        private String lastName;

        @ValidEmail
        @NotEmpty
        @Size(min = 5, message = "{email.notempty}")
        private String email;

        @NotEmpty
        @Size(min = 5, message = "The author email '${validatedValue}' ${validatedValue} must be between {min} and '{max}' characters long")
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


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }