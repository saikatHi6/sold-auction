package com.nasdaq.auction.soldauction.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nasdaq.auction.soldauction.exception.RecordNotFoundException;
import com.nasdaq.auction.soldauction.model.Auction;
import com.nasdaq.auction.soldauction.model.Bid;
import com.nasdaq.auction.soldauction.model.User;
import com.nasdaq.auction.soldauction.service.AuctionService;
import com.nasdaq.auction.soldauction.service.BiderService;
import com.nasdaq.auction.soldauction.service.UserService;

@RestController
@RequestMapping("/auction")
public class BiderController {

	@Autowired
	BiderService biderService;
	
	@Autowired
	AuctionService auctionService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/{aucId}/bid")
	public ResponseEntity<Bid> bidForAnItem(@RequestBody Bid bid,@PathVariable("aucId") Long aucId) throws RecordNotFoundException{
		Auction existAuction = auctionService.getAuctionById(aucId);
		User buyer = userService.isUserExist(bid.getBuyer().getUserId());
		Bid bidDetail = biderService.bidForAuctionItem(existAuction, bid);
		return new ResponseEntity<Bid>(bidDetail, new HttpHeaders(), HttpStatus.OK);
	}
	
	@PutMapping("/{aucId}/bid/{bidId}")
	public ResponseEntity<Bid> updateBitAmount(@RequestBody Bid bid,@PathVariable("aucId") Long aucId,@PathVariable("bidId") Long bidId) throws RecordNotFoundException{
		Auction existAuction = auctionService.getAuctionById(aucId);
		//User buyer = userService.isUserExist(bid.getBuyer().getUserId());
		Bid bidDetail = biderService.updateAmountOfAuctionItem(existAuction, bid, bidId);
		return new ResponseEntity<Bid>(bidDetail, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	
}
