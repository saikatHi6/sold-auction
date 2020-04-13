package com.nasdaq.auction.soldauction.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nasdaq.auction.soldauction.dao.UserRepository;
import com.nasdaq.auction.soldauction.exception.RecordNotFoundException;
import com.nasdaq.auction.soldauction.model.User;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public User registerUser(User user) throws RecordNotFoundException{
		
		if(user.getUserId()==null)
			user.setUserId(0L);	
		
		Optional<User> users = userRepository.findById(user.getUserId());
        
        if(users.isPresent())
        {
        	User newEntity = users.get();
            newEntity.setEmail(user.getEmail());
            newEntity.setFirstName(user.getFirstName());
            newEntity.setLastName(user.getLastName());
 
            newEntity = userRepository.save(newEntity);
             
            return newEntity;
        } else {
        	user = userRepository.save(user);
             
            return user;
        }
	}
	
	public User isUserExist(Long id) throws RecordNotFoundException{
		Optional<User> users = userRepository.findById(id);
		if(users.isPresent()){
			return users.get();
		}
		else {
			throw new RecordNotFoundException("User does not eist");
		}
	}
	
	
	public Optional<User> getUser(Long userId){
		return userRepository.findById(userId);
	}
}
