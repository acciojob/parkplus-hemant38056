package com.driver.services.impl;

import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.model.User;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository4;
    @Override
    public void deleteUser(Integer userId) {
        userRepository4.deleteById(userId);
    }

    @Override
    public User updatePassword(Integer userId, String password) {
        User user = userRepository4.findById(userId).get();
        if(user == null){
            return null;
        }
        user.setPassword(password);
        userRepository4.save(user);
        return user;
    }

    @Override
    public void register(String name, String phoneNumber, String password) {
        User user = new User(name, phoneNumber, password);
        userRepository4.save(user);
    }

    @Override
    public Integer countReserves(Integer userId){
        User user = userRepository4.findById(userId).get();
        return user.getReservationList().size();
    }
}
