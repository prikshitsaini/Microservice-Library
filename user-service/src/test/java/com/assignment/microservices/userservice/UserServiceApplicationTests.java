package com.assignment.microservices.userservice;

import com.assignment.microservices.userservice.bean.User;
import com.assignment.microservices.userservice.controller.UserServiceController;
import com.assignment.microservices.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserServiceController.class)
class UserServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    User mockUser = new User(1, "Prikshit", "Saini", 26, 8968004913L);

    String exampleUserJson = "{\"id\":4,\"firstName\":\"Rahul\",\"lastName\":\"Saini\",\"age\":26,\"contactNo\":8968004914}";

    List<User> mockUsers = Arrays.asList(new User(1, "Prikshit", "Saini", 26, 8968004913L),
            new User(2, "Yatin", "Batra", 26, 8968004914L));


    @Test
    public void retrieveUserDetails() throws Exception {

        Mockito.when(
                userService.getUserDetail(Mockito.anyInt())).thenReturn(mockUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/user/1").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = "{id:1,firstName:Prikshit,lastName: Saini,age:26,contactNo:8968004913}";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);

    }

    @Test
    public void addUser() throws Exception {

        boolean userAdded = true;

        Mockito.when(
                userService.addUser(Mockito.any(User.class))).thenReturn(userAdded);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/add")
                .accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        String expectedResponse = "User Created Successfully of id:4";

        assertEquals(expectedResponse, result.getResponse().getContentAsString());

    }

    @Test
    public void retrieveUsers() throws Exception {
        Mockito.when(
                userService.getUsers()).thenReturn(mockUsers);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/users").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = "[{id:1, firstName: Prikshit, lastName: Saini, age: 26, contactNo: 8968004913},"
                + "			{id:2, firstName: Yatin, lastName: Batra, age: 26, contactNo: 8968004914}]";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void userExist() throws Exception {

        Mockito.when(
                userService.checkUserExist(Mockito.anyInt())).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/user/check/1").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());

        assertEquals("true", result.getResponse().getContentAsString());

    }

}
