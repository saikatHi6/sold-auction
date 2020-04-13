package com.nasdaq.auction.soldauction.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nasdaq.auction.soldauction.exception.RecordNotFoundException;
import com.nasdaq.auction.soldauction.model.Auction;
import com.nasdaq.auction.soldauction.model.User;
import com.nasdaq.auction.soldauction.model.UserType;
import com.nasdaq.auction.soldauction.service.AuctionService;
import com.nasdaq.auction.soldauction.service.UserService;

@RestController
@RequestMapping("/auction")
public class UserController {
	
	@Autowired
    UserService service;
	
	@Autowired
	AuctionService auctionService;

	@PostMapping("/register/seller")
    public ResponseEntity<User> registerSeller(@Valid @RequestBody User user)
                                                    throws RecordNotFoundException {
		user.setType(UserType.SELLER);
		User updated = service.registerUser(user);
        return new ResponseEntity<User>(updated, new HttpHeaders(), HttpStatus.OK);
    }
	
	@PostMapping("/register/buyer")
    public ResponseEntity<User> registerBuyer(@Valid @RequestBody User user)
                                                    throws RecordNotFoundException {
		user.setType(UserType.BUYER);
		User updated = service.registerUser(user);
        return new ResponseEntity<User>(updated, new HttpHeaders(), HttpStatus.OK);
    }
	
	@GetMapping("{auctionId}/seller/{sellerId}")
	public ResponseEntity<Double> profitByAuctionAndSeller(@PathVariable Long auctionId,@PathVariable Long sellerId){
		Auction auction = auctionService.getAuctionBySellerId(auctionId, sellerId);
		double calculatedProfit =  auctionService.calculateProfitForEachAuction(auction);
		return new ResponseEntity<Double>(calculatedProfit, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/seller/{sellerId}")
	public ResponseEntity<User> getSeller(@PathVariable Long sellerId) throws RecordNotFoundException{
		
		Optional<User> user = service.getUser(sellerId);
		
		
		if(!user.isPresent()){
			throw new RecordNotFoundException("Invalid Seller Id: "+sellerId);
		}
		
		return new ResponseEntity<User>(user.get(), new HttpHeaders(), HttpStatus.OK);
	}
	
}
