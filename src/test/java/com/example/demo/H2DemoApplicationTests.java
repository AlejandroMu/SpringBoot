package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.User;
import com.example.demo.services.interfaces.UserService;

// @ExtendWith(MockitoExtension.class)
@SpringBootTest
class H2DemoApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		User user = new User();
		user.setUsername("testUser");
		user.setPassword("testUser");
		user.setEmail("testUser@mail.com");
		user.setName("User");
		user.setLastName("Test");
		userService.saveUser(user);

		User userFound = userService.findByUsername("testUser");

		assertEquals(user.getId(), userFound.getId());
	}
	
}
