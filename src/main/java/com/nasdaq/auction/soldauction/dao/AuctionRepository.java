package com.nasdaq.auction.soldauction.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nasdaq.auction.soldauction.model.Auction;
import com.nasdaq.auction.soldauction.model.User;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long>{
	
	@Query("select a from Auction a where a.seller = ?1")
	Collection<Auction> getAuctionsBySeller(@Param("seller") User seller);
	
	@Query("select a from Auction a where a.seller = ?1 and a.auctionId = ?1")
	Auction getAuctionsBySellerAndId(@Param("seller") User seller,@Param("auctionId") Long auctionId);
}
