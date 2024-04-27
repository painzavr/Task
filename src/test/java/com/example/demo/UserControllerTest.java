package com.example.demo;

import com.example.demo.user.User;
import com.example.demo.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User existingUser;
    @BeforeEach
    public void setup() {
        // Set up existing user
        existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmail("test@example.com");
        existingUser.setFirstName("Test");
        existingUser.setLastName("User");
        existingUser.setAddress(null);
        existingUser.setPhoneNumber(null);
        existingUser.setBirthDate(LocalDate.of(2000,2,2));
    }

    //Birthdate range testing
    //Positive testing
    @Test
    public void positiveTestGetUsersByBirthdayRange() throws Exception {
        when(userService.searchUsersByBirthDateRange(any())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromDate\":\"2000-01-01\",\"toDate\":\"2000-12-31\"}"))
                .andExpect(status().isOk());
    }
    //Negative testing
    @Test
    public void negativeTestGetUsersByBirthdayRange() throws Exception {
        when(userService.searchUsersByBirthDateRange(any())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromDate\":\"2002-01-01\",\"toDate\":\"2001-12-31\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{'dateRange': 'Invalid date range'}"));
    }

    //Creation&Fields validation testing
    //Positive testing
    @Test
    public void positiveTestRegisterNewUser() throws Exception {

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\",\"firstName\":\"Test\",\"lastName\":\"User\",\"birthDate\":\"2000-01-01\"}"))
                .andExpect(status().isCreated());
    }
    //Negative testing
    @Test
    public void negativeTestRegisterNewUser() throws Exception {

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"email\":\"testexample.com\"," +
                                "\"firstName\":\"\"," +
                                "\"lastName\":\"\"," +
                                "\"birthDate\":\"2018-01-01\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{" +
                        "    \"firstName\": \"Must not be empty\",\n" +
                        "    \"lastName\": \"Must not be empty\",\n" +
                        "    \"birthDate\": \"Must be older\",\n" +
                        "    \"email\": \"Must be in email address format\"\n" +
                        "}"));
    }

    //User deletion testing
    //Positive testing
    @Test
    public void positiveTestDeleteUser() throws Exception {
        when(userService.deleteUser(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/users")
                        .param("id", "1"))
                .andExpect(status().isOk());
    }
    //Negative testing
    @Test
    public void negativeTestDeleteUser() throws Exception {
        when(userService.deleteUser(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/api/users")
                        .param("id", "10"))
                .andExpect(status().isNotFound());
    }

    //All the fields updating
    //Positive testing
    @Test
    public void positiveTestUpdateAllUserFields() throws Exception {
        when(userService.updateAllUserFields(anyLong(), any())).thenReturn(existingUser);
        mockMvc.perform(put("/api/users")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\"," +
                                "\"firstName\":\"Test\"," +
                                "\"lastName\":\"User\"," +
                                "\"birthDate\":\"2000-02-02\"," +
                                "\"address\": \"\"," +
                                "\"phoneNumber\": \"\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().json(("{\"email\":\"test@example.com\"," +
                        "\"firstName\":\"Test\"," +
                        "\"lastName\":\"User\"," +
                        "\"birthDate\":\"2000-02-02\"," +
                        "\"address\": null," +
                        "\"phoneNumber\": null" +
                        "}")));
    }

    //Negative testing
    @Test
    public void negativeTestUpdateAllUserFields() throws Exception {
        when(userService.updateAllUserFields(anyLong(), any())).thenReturn(null);

        mockMvc.perform(put("/api/users")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\"," +
                                "\"firstName\":\"Test2\"," +
                                "\"lastName\":\"User\"," +
                                "\"birthDate\":\"2000-01-01\", " +
                                "\"address\":\"\"," +
                                "\"phoneNumber\":\"\"}"))
                .andExpect(status().isNotFound());
    }

    //One/some the fields updating
    //Positive testing
    @Test
    public void positiveTestUpdateSomeUserFields() throws Exception {
        when(userService.updateAllUserFields(anyLong(), any())).thenReturn(existingUser);
        mockMvc.perform(patch("/api/users")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\"," +
                                "\"firstName\":\"Test\"," +
                                "\"lastName\":\"User\"," +
                                "\"birthDate\":\"2000-02-02\"," +
                                "\"address\": \"\"," +
                                "\"phoneNumber\": \"\"" +
                                "}"))
                .andExpect(status().isOk());
    }

}
