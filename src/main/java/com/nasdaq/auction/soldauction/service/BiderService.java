package com.nasdaq.auction.soldauction.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nasdaq.auction.soldauction.dao.BidRepository;
import com.nasdaq.auction.soldauction.exception.RecordNotFoundException;
import com.nasdaq.auction.soldauction.model.Auction;
import com.nasdaq.auction.soldauction.model.Bid;
import com.nasdaq.auction.soldauction.model.BidStatus;
import com.nasdaq.auction.soldauction.model.User;

@Service
public class BiderService {

	@Autowired
	BidRepository bidRepository;

	public Bid bidForAuctionItem(Auction auction,Bid bid) throws RecordNotFoundException{

		if(auction!=null){
			bid.setBidStatus(BidStatus.ACTIVE);
			auction.getBiders().add(bid);
			bid.getAuctions().add(auction);
			//Bid updatedBid = bid;
			bidRepository.save(bid);
			return bidRepository.getOne(bid.getBidId());
		}
		else{
			throw new RecordNotFoundException("This Auction Item is not avilable for biding");
		}
	}
	
	public Bid isBidExistOrNot(Long id) throws RecordNotFoundException{
		Optional<Bid> bid = bidRepository.findById(id);
		if(bid.isPresent()){
			return bid.get();
		}
		else {
			throw new RecordNotFoundException("Bid is not avilable");
		}
	}

	public Bid updateAmountOfAuctionItem(Auction auction,Bid bid,Long existingBidId) throws RecordNotFoundException{

		Bid existingBid = isBidExistOrNot(existingBidId);
		
		
		if(auction!=null && existingBid!=null){
			//bid.setBidStatus(BidStatus.ACTIVE);
			bid.setBidId(existingBidId);
			
			if(bid.getBiderPrice()!=0){
				existingBid.setBiderPrice(bid.getBiderPrice());
			}
			if(bid.getBidStatus()!=null){
				existingBid.setBidStatus(bid.getBidStatus());
			}
			
			//auction.getBiders().add(bid);
			//bid.getAuctions().add(auction);
			bid = bidRepository.save(existingBid);
			return existingBid;
		}
		else{
			throw new RecordNotFoundException("This Auction Item is not avilable for biding");
		}
	}
	
	public Collection<Bid> getBidDetailsByAuction(Auction auction){
		return bidRepository.returnWinnerBidDetails(auction.getAuctionId());
	}
	
	public Bid returnWiningBid(Auction auction){
		return getBidDetailsByAuction(auction).stream().max(Comparator.comparingDouble(Bid::getBiderPrice)).get();
	}
	
	public double totalParticipationCostByBuyersForAnAuction(Auction auction,Collection<Bid> bids){
		return bids.stream().mapToDouble(b->b.getBiderPrice()).sum();
	}
	
	public double avarageOfLowestAndHiehst(Auction auction,Collection<Bid> bids){
		double maxBid = bids.stream().max(Comparator.comparingDouble(Bid::getBiderPrice)).get().getBiderPrice();
		double minBid = bids.stream().min(Comparator.comparingDouble(Bid::getBiderPrice)).get().getBiderPrice();
		return (maxBid+minBid)/2;
	}
	
	

}
