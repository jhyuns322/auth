package com.son.auth.service;

import com.son.auth.dto.UserDto;

public interface UserService {
    void addUser(UserDto req);

    UserDto findUser(String userId);
}
