package com.example.demo.utils;

import com.example.demo.user.User;
import com.example.demo.user.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {
    public User mapToUser(UserDTO userDTO){
        User user = new User();
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setBirthDate(userDTO.getBirthDate());
        return user;
    };
}
