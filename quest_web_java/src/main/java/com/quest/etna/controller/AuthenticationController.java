package com.quest.etna.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.quest.etna.config.JwtTokenUtil;
import com.quest.etna.model.User;
import com.quest.etna.service.JwtUserDetailsService;
import com.quest.etna.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class AuthenticationController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> me(Authentication authentication) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			User user = userService.getByUsername(authentication.getName()).get();
			response.put("username", user.getUsername());
			response.put("role", user.getRole());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch(UsernameNotFoundException e) {
			response.put("error", "No token specified");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.UNAUTHORIZED);
		}
		
	}

	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Map<String, Object>> register(@RequestBody JsonNode payload) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			String username = payload.get("username").textValue();
			String password = payload.get("password").textValue();
			
			Optional<User> user = userService.getByUsername(username);
			if(!user.isEmpty()) {
				response.put("error", "User with this username already exists");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
			}
			else {
				User newUser = new User(username, password, "ROLE_USER", new Timestamp(System.currentTimeMillis()));
				newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
				userService.create(newUser);
				response.put("username", username);
				response.put("role", newUser.getRole());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
			}
		}
		catch(Exception e) {
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
	}

		
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Map<String, Object>> authenticate(@RequestBody JsonNode payload) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			String username = payload.get("username").textValue();
			String password = payload.get("password").textValue();
			authenticate(username, password);
			UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
			String token = jwtTokenUtil.generateToken(userDetails);
			response.put("token", token);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
		}catch (Exception e) {
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
	}


	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch(DisabledException e) {
			throw new Exception("User Disabled");
		} catch(BadCredentialsException e) {
			e.printStackTrace();
			throw new Exception("Invalid Credentials");
		}
	}

}
