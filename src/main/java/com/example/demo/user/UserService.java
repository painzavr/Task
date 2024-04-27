package com.example.demo.user;

import com.example.demo.annotation.periodValidator.DateRange;
import com.example.demo.utils.MappingUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private MappingUtils mappingUtils;

    // Method to update one/some user fields
    /*public void updateAllUserFields(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElse(null);
        if (firstName != null && !firstName.isEmpty()) {
            user.setFirstName(firstName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            user.setLastName(lastName);
        }
        if (birthDate != null) {
            user.setBirthDate(birthDate);
        }
        if (address != null) {
            user.setAddress(address);
        }
        if (phoneNumber != null) {
            user.setPhoneNumber(phoneNumber);
        }
        // Update user in database or perform necessary operations
    }*/
    public boolean deleteUser(Long userId) {
        if(userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }else{
            return false;
        }

    }

    // Method to search for users by birth date range
    public List<User> searchUsersByBirthDateRange(DateRange dateRange) {
        return new ArrayList<>(userRepository.findUsersByBirthDateBetween(dateRange.getFromDate(), dateRange.getToDate()));
    }

    public User addNewUser(UserDTO userDTO) {
        User user = mappingUtils.mapToUser(userDTO);
        userRepository.save(user);
        return user;
    }

    public User updateAllUserFields(Long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setAddress(userDTO.getAddress());
            user.setBirthDate(userDTO.getBirthDate());
            userRepository.save(user);
            return user;
        }else{
            return null;
        }
    }

    public HashMap<String, String> updateSomeUserFields(Long id, UserDTO userDTO) {
        HashMap<String, String> errors = new HashMap<>();
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(userDTO.getFirstName() != null) {
                if(!userDTO.getFirstName().isEmpty()){
                    user.setFirstName(userDTO.getFirstName());
                }else{
                    errors.put("firstName", "Must not be empty");
                }
            }
            if(userDTO.getLastName() != null) {
                if(!userDTO.getLastName().isEmpty()){
                    user.setLastName(userDTO.getLastName());
                }else{
                    errors.put("lastName", "Must not be empty");
                }

            }
            if(userDTO.getEmail() != null) {
                String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(userDTO.getEmail());
                if(matcher.matches()){
                    user.setEmail(userDTO.getEmail());
                }else{
                    errors.put("email", "Must be in email address format");
                }
            }
            if(userDTO.getPhoneNumber() != null) {
                user.setPhoneNumber(userDTO.getPhoneNumber());
            }
            if(userDTO.getAddress() != null) {
                user.setAddress(userDTO.getAddress());
            }
            if(userDTO.getBirthDate() != null) {
                if(userDTO.getBirthDate().isBefore(LocalDate.now())){
                    LocalDate today = LocalDate.now();
                    Period period = Period.between(userDTO.getBirthDate(), today);
                    if(period.getYears() > 18){
                        user.setBirthDate(userDTO.getBirthDate());
                    }else{
                        errors.put("birthDate", "Must be older");
                    }
                }else{
                    errors.put("birthDate", "Invalid birthdate");
                }
            }
            userRepository.save(user);
            return errors;
        }else{
            return null;
        }
    }
}
