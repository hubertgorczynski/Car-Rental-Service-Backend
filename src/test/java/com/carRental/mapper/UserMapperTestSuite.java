package com.carRental.mapper;

import com.carRental.domain.User;
import com.carRental.domain.dto.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTestSuite {

    @InjectMocks
    private UserMapper userMapper;

    User user = new User(
            1L,
            "Jack",
            "Smith",
            "email",
            "password",
            123456);

    UserDto userDto = new UserDto(
            1L,
            "Jack",
            "Smith",
            "email",
            "password",
            123456,
            LocalDate.now());

    @Test
    public void mapToUserTest() {
        //Given

        //When
        User mappedUser = userMapper.mapToUser(userDto);

        //Then
        assertEquals(mappedUser.getId(), user.getId());
        assertEquals(mappedUser.getName(), user.getName());
        assertEquals(mappedUser.getLastName(), user.getLastName());
        assertEquals(mappedUser.getEmail(), user.getEmail());
        assertEquals(mappedUser.getPassword(), user.getPassword());
        assertEquals(mappedUser.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(mappedUser.getAccountCreated(), user.getAccountCreated());
    }

    @Test
    public void mapToUserDtoTest() {
        //Given
        user.setAccountCreated(LocalDate.now());

        //When
        UserDto mappedUserDto = userMapper.mapToUserDto(user);

        //Then
        assertEquals(mappedUserDto.getId(), userDto.getId());
        assertEquals(mappedUserDto.getName(), userDto.getName());
        assertEquals(mappedUserDto.getLastName(), userDto.getLastName());
        assertEquals(mappedUserDto.getEmail(), userDto.getEmail());
        assertEquals(mappedUserDto.getPassword(), userDto.getPassword());
        assertEquals(mappedUserDto.getPhoneNumber(), userDto.getPhoneNumber());
        assertEquals(mappedUserDto.getAccountCreated(), userDto.getAccountCreated());
    }

    @Test
    public void mapToUserDtoListTest() {
        //Given
        user.setAccountCreated(LocalDate.now());
        List<User> userList = Collections.singletonList(user);

        //When
        List<UserDto> mappedUserDtoList = userMapper.mapToUserDtoList(userList);

        //Then
        assertNotNull(mappedUserDtoList);
        assertEquals(1, mappedUserDtoList.size());

        mappedUserDtoList.forEach(u -> {
            assertEquals(u.getId(), userDto.getId());
            assertEquals(u.getName(), userDto.getName());
            assertEquals(u.getLastName(), userDto.getLastName());
            assertEquals(u.getEmail(), userDto.getEmail());
            assertEquals(u.getPassword(), userDto.getPassword());
            assertEquals(u.getPhoneNumber(), userDto.getPhoneNumber());
            assertEquals(u.getPhoneNumber(), userDto.getPhoneNumber());
            assertEquals(u.getAccountCreated(), userDto.getAccountCreated());
        });
    }
}
