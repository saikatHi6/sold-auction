package com.nasdaq.auction.soldauction.dao;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nasdaq.auction.soldauction.model.Bid;
@Repository
public interface BidRepository extends JpaRepository<Bid, Long>{

	@Query(value ="select b.* from TBL_BID b,AUCTION_BID ab where b.BID_ID = ab.BID_ID  and ab.AUCTION_ID = ?1",nativeQuery=true)
	Collection<Bid> returnWinnerBidDetails(@Param("auctionId") Long auctionId);
	
	
}
