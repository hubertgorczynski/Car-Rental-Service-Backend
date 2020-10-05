package com.carRental.controller;

import com.carRental.domain.dto.CarDto;
import com.carRental.domain.dto.UserDto;
import com.carRental.facade.UserFacade;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserFacade userFacade;

    @Test
    public void shouldFetchUserById() throws Exception {
        //Given
        UserDto userDto = initUserDto();
        when(userFacade.getUserById(1L)).thenReturn(userDto);

        //When & Then
        mockMvc.perform(get("/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Jack")));
    }

    @Test
    public void shouldFetchUserByEmail() throws Exception {
        //Given
        UserDto userDto = initUserDto();
        when(userFacade.getUserByEmail("simpleEmail")).thenReturn(userDto);

        //When & Then
        mockMvc.perform(get("/v1/users/by_email/simpleEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("email", "simpleEmail"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.email", is("simpleEmail")));
    }

    @Test
    public void shouldFetchUserByPhoneNumber() throws Exception {
        //Given
        UserDto userDto = initUserDto();
        when(userFacade.getUserByPhoneNumber(123456)).thenReturn(userDto);

        //When & Then
        mockMvc.perform(get("/v1/users/by_phone/123456")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("number", String.valueOf(123456)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.phoneNumber", is(123456)));
    }

    @Test
    public void shouldFetchAllUsers() throws Exception {
        //Given
        UserDto userDto = initUserDto();
        List<UserDto> userDtoList = Collections.singletonList(userDto);
        when(userFacade.getAllUsers()).thenReturn(userDtoList);

        //When & Then
        mockMvc.perform(get("/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Jack")))
                .andExpect(jsonPath("$[0].phoneNumber", is(123456)));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        //Given
        //When & Then
        mockMvc.perform(delete("/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1"))
                .andExpect(status().is(200));
    }

    @Test
    public void isUserAlreadyRegistered() throws Exception {
        //Given
        when(userFacade.isUserAlreadyRegistered("email")).thenReturn(true);

        //When & Then
        mockMvc.perform(get("/v1/users/is_user_registered/email")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    private UserDto initUserDto() {
        return new UserDto(
                1L,
                "Jack",
                "Smith",
                "simpleEmail",
                "password",
                123456,
                LocalDate.now());
    }
}
