package com.carRental.mapper;

import com.carRental.domain.User;
import com.carRental.domain.dto.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTestSuite {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void mapToUserTest() {
        //Given
        UserDto userDto = initUserDto();

        //When
        User mappedUser = userMapper.mapToUser(userDto);
        mappedUser.setAccountCreated(LocalDate.now());

        //Then
        assertEquals(1L, (long) mappedUser.getId());
        assertEquals("Jack", mappedUser.getName());
        assertEquals("Smith", mappedUser.getLastName());
        assertEquals("email", mappedUser.getEmail());
        assertEquals("password", mappedUser.getPassword());
        assertEquals(123456, mappedUser.getPhoneNumber());
        assertEquals(LocalDate.now(), mappedUser.getAccountCreated());
    }

    @Test
    public void mapToUserDtoTest() {
        //Given
        User user = initUser();
        user.setAccountCreated(LocalDate.now());

        //When
        UserDto mappedUserDto = userMapper.mapToUserDto(user);

        //Then
        assertEquals(1L, (long) mappedUserDto.getId());
        assertEquals("Jack", mappedUserDto.getName());
        assertEquals("Smith", mappedUserDto.getLastName());
        assertEquals("email", mappedUserDto.getEmail());
        assertEquals("password", mappedUserDto.getPassword());
        assertEquals(123456, mappedUserDto.getPhoneNumber());
        assertEquals(LocalDate.now(), mappedUserDto.getAccountCreated());
    }

    @Test
    public void mapToUserDtoListTest() {
        //Given
        User user = initUser();
        user.setAccountCreated(LocalDate.now());
        List<User> userList = Collections.singletonList(user);

        //When
        List<UserDto> mappedUserDtoList = userMapper.mapToUserDtoList(userList);

        //Then
        assertNotNull(mappedUserDtoList);
        assertEquals(1, mappedUserDtoList.size());

        assertEquals(1L, (long) mappedUserDtoList.get(0).getId());
        assertEquals("Jack", mappedUserDtoList.get(0).getName());
        assertEquals("Smith", mappedUserDtoList.get(0).getLastName());
        assertEquals("email", mappedUserDtoList.get(0).getEmail());
        assertEquals("password", mappedUserDtoList.get(0).getPassword());
        assertEquals(123456, mappedUserDtoList.get(0).getPhoneNumber());
        assertEquals(LocalDate.now(), mappedUserDtoList.get(0).getAccountCreated());
    }

    private User initUser() {
        return new User(
                1L,
                "Jack",
                "Smith",
                "email",
                "password",
                123456);
    }

    private UserDto initUserDto() {
        return new UserDto(
                1L,
                "Jack",
                "Smith",
                "email",
                "password",
                123456,
                LocalDate.now());
    }
}
