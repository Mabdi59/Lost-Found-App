package com.lostfound.dao;

import com.lostfound.model.RegisterUserDto;
import com.lostfound.model.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

    User createUser(RegisterUserDto user);
}
