package com.modeling.demo.service;

import com.modeling.demo.domains.User;
import com.modeling.demo.dto.UserDto;
import com.modeling.demo.dto.UserWithOrderDto;


public interface IUserService {
    User registerNewUserAccount(UserDto userDto) throws Exception;
    User registerNewUserAccountInForm(UserWithOrderDto dto) throws Exception;
}