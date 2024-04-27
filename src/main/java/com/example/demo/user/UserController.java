package com.example.demo.user;

import com.example.demo.annotation.ageValidator.ValidAge;
import com.example.demo.annotation.birthdateValidator.ValidBirthdate;
import com.example.demo.annotation.periodValidator.DateRange;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/users")
@Validated
public class UserController {
    private UserService userService;

    @GetMapping
    public List<User> getUsersByBirthdayRange(@RequestBody @Valid DateRange dateRange) {
        return userService.searchUsersByBirthDateRange(dateRange);
    }

    @PostMapping
    public ResponseEntity<User> registerNewUser(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(userService.addNewUser(userDTO), HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser(@RequestParam Long id) throws ResponseStatusException {
        if(userService.deleteUser(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping
    public ResponseEntity<User> updateAllUserFields(@RequestParam Long id, @RequestBody UserDTO userDTO) {
        User user = userService.updateAllUserFields(id, userDTO);
        if(user!=null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping
    public ResponseEntity<?> updateUser(@RequestParam Long id,
                                           @RequestParam(required = false) String email,
                                           @RequestParam(required = false) String firstName,
                                           @RequestParam(required = false) String lastName,
                                           @RequestParam(required = false) LocalDate birthDate,
                                           @RequestParam(required = false) String address,
                                           @RequestParam(required = false) String phoneNumber
    ) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setBirthDate(birthDate);
        userDTO.setAddress(address);
        userDTO.setPhoneNumber(phoneNumber);
        HashMap<String, String> errors = userService.updateSomeUserFields(id, userDTO);
        if(errors!=null){
            if(errors.isEmpty()){
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
