package com.quest.etna.service;

import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.quest.etna.config.JwtTokenUtil;
import com.quest.etna.model.User;
import com.quest.etna.repository.UserRepository;

@Service
public class UserService implements IModelService<User> {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public List<User> getList(Integer page, Integer Limit) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getToken(String username, String password) throws AuthenticationException {
		User user = userRepository.findByUsernameAndPassword(username, password);
		if (user != null) {
			return jwtTokenUtil.generateToken((UserDetails) user);
		}
		return null;
	}
	
	public Optional<User> getByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		return user;
	}

	@Override
	public User getOneById(Integer id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) {
			return null;
		}

		return user.get();
	}

	@Override
	public User create(User entity) {
		userRepository.save(entity);
		return entity;
	}

	@Override
	public User update(Integer id, User entity) {
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty()) {
			return null;
		}

		User userFound = user.get();
		userFound.setUsername(entity.getUsername());
		userRepository.save(userFound);
		return userFound;
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			Optional<User> user = userRepository.findById(id);
			
			if(user.isEmpty()) {
				return false;
			}

			User userFound = user.get();
			userRepository.delete(userFound);
			
		}catch(Exception e) {
			return false;
		}
		return true;
	}

}
