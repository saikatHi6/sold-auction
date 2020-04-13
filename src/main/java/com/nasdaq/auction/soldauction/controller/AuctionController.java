package com.nasdaq.auction.soldauction.controller;

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
import com.nasdaq.auction.soldauction.model.Bid;
import com.nasdaq.auction.soldauction.model.User;
import com.nasdaq.auction.soldauction.service.AuctionService;
import com.nasdaq.auction.soldauction.service.BiderService;

@RestController
@RequestMapping("/auction")
public class AuctionController {

	@Autowired
	AuctionService auctionService;
	
	@Autowired
	BiderService biderService;
	
	@PostMapping
	public ResponseEntity<Auction> createAuctionByExistingSeller(@RequestBody Auction auction) throws RecordNotFoundException{
		Auction updatedAuction = auctionService.auctionCreationByRegisterSeller(auction);
		return new ResponseEntity<Auction>(updatedAuction, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/{aucId}/close")
	public ResponseEntity<Bid> closeAuction(@PathVariable Long aucId) throws RecordNotFoundException{
		Auction auction = auctionService.closeAuction(aucId);
		Bid winnerBid = biderService.returnWiningBid(auction);
		return new ResponseEntity<Bid>(winnerBid, new HttpHeaders(), HttpStatus.OK);
	}
	
}
