package com.nasdaq.auction.soldauction.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nasdaq.auction.soldauction.dao.AuctionRepository;
import com.nasdaq.auction.soldauction.dao.UserRepository;
import com.nasdaq.auction.soldauction.exception.RecordNotFoundException;
import com.nasdaq.auction.soldauction.model.Auction;
import com.nasdaq.auction.soldauction.model.AuctionStatus;
import com.nasdaq.auction.soldauction.model.Bid;
import com.nasdaq.auction.soldauction.model.User;

@Service
public class AuctionService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuctionRepository auctionRepository;
	
	@Autowired
	BiderService bidService;

	public Auction auctionCreationByRegisterSeller(Auction auction) throws RecordNotFoundException{
		Optional<User> seller = userRepository.findById(auction.getSeller().getUserId());
        
        if(seller.isPresent())
        {
        	auction.setAuctionStatus(AuctionStatus.INPROGRESS);
        	auction = auctionRepository.save(auction);
        	auction.setSeller(seller.get());
        	return auction;
        } else {
        	throw new RecordNotFoundException("Seller does not exist, Please register first as a seller");
        }
	}
	
	public Auction getAuctionById(Long id) throws RecordNotFoundException{
		
		Optional<Auction> auction = auctionRepository.findById(id);
		if(auction.isPresent()){
			return auction.get();
		}
		else{
			throw new RecordNotFoundException("Auction Id is not avilable");
		}
	}
	
	
	public Auction isAuctionExistOrNot(Long id) throws RecordNotFoundException{
		Optional<Auction> bid = auctionRepository.findById(id);
		if(bid.isPresent()){
			return bid.get();
		}
		else {
			throw new RecordNotFoundException("Bid is not avilable");
		}
	}
	
	public Auction closeAuction(Long id) throws RecordNotFoundException{
		Auction existingAuction = isAuctionExistOrNot(id);
		if(existingAuction!=null){
			existingAuction.setAuctionStatus(AuctionStatus.CLOSED);
			existingAuction = auctionRepository.save(existingAuction);
		}
		return existingAuction;
	}
	
	
	public double calculateProfitLossBySellerAuctions(User seller){
		Collection<Auction> auctions = auctionRepository.getAuctionsBySeller(seller);
		double totalPrice = 0;
		for (Auction eachAuction : auctions) {
			totalPrice+= calculateProfitForEachAuction(eachAuction);
		}
		
		return totalPrice;
	}
	
	public Auction getAuctionBySellerId(Long auctionId,Long sellerId){
		
		return auctionRepository.getAuctionsBySellerAndId(userRepository.getOne(sellerId), auctionId);
	}
	
	public double calculateProfitForEachAuction(Auction auction){
		Collection<Bid> bids = bidService.getBidDetailsByAuction(auction);
		double totalBidAmount = bidService.totalParticipationCostByBuyersForAnAuction(auction, bids);
		double avgBidAmount = bidService.avarageOfLowestAndHiehst(auction, bids);
		double winningBid = bidService.returnWiningBid(auction).getBiderPrice();
		double profit = winningBid + (1/5)*(totalBidAmount-avgBidAmount);
		return profit;
	}
	
	
	
	
	
	
	
}
