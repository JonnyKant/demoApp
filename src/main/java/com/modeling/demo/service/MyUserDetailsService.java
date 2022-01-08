package com.modeling.demo.service;

import com.modeling.demo.domains.Role;
import com.modeling.demo.domains.User;
import com.modeling.demo.dto.UserDto;
import com.modeling.demo.dto.UserWithOrderDto;
import com.modeling.demo.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Collections;

@Service
@Transactional
public class MyUserDetailsService implements IUserService, UserDetailsService {
    @Autowired
    private UserRepos repository;
    @Autowired
    private GeneratePassword generatePassword;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User registerNewUserAccount(UserDto userDto) throws Exception {
        if (emailExists(userDto.getEmail())) {
            throw new Exception("There is an account with that email address: "
                    + userDto.getEmail());
        }

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword((passwordEncoder.encode(userDto.getPassword())));
        user.setEmail(userDto.getEmail());
        user.setRoles(Collections.singleton(Role.CLIENT));

        return repository.save(user);
    }

    private boolean emailExists(String email) {
        return repository.findByEmail(email) != null;
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = repository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }
    @Override
    public User registerNewUserAccountInForm(UserWithOrderDto userWithOrderDto) throws AuthenticationServiceException{
        if (emailExists(userWithOrderDto.getEmail())) {
            throw new AuthenticationServiceException("There is an account with that email address: "
                    + userWithOrderDto.getEmail());
        }
        User user = new User();
        user.setFirstName(userWithOrderDto.getFirstName());
        user.setLastName(userWithOrderDto.getLastName());
        String password = generatePassword.alphaNumericString(10);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(userWithOrderDto.getEmail());
        user.setRoles(Collections.singleton(Role.CLIENT));
        sendmail(userWithOrderDto.getEmail(),password);
        return repository.save(user);
    }

    private void sendmail(String email, String password) {
        mailSender.sendEmail(email,"Registration","Вы зарегистрировались в системе Химинформатика,\n" +
                "Ваш пароль: " + password);
    }
}



