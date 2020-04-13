package com.nasdaq.auction.soldauction.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="TBL_AUCTION",uniqueConstraints = {
		@UniqueConstraint(columnNames = "auction_id")})
public class Auction {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_id", unique = true, nullable = false)
    private Long auctionId;
	
	@Column(name = "auction_item", unique = false, nullable = false, length = 100)
	private String auctionItem;
	
	@Column(name = "seller_price", unique = false, nullable = false)
	private double sellerPrice;
	
	@Column(name = "auction_status", unique = false, nullable = false)
	private AuctionStatus auctionStatus;
	
	@ManyToOne
    private User seller;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "auctions")
	private Set<Bid> biders = new HashSet<Bid>(0);

	

	public Long getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(Long auctionId) {
		this.auctionId = auctionId;
	}

	public String getAuctionItem() {
		return auctionItem;
	}

	public void setAuctionItem(String auctionItem) {
		this.auctionItem = auctionItem;
	}

	public double getSellerPrice() {
		return sellerPrice;
	}

	public void setSellerPrice(double sellerPrice) {
		this.sellerPrice = sellerPrice;
	}

	public AuctionStatus getAuctionStatus() {
		return auctionStatus;
	}

	public void setAuctionStatus(AuctionStatus auctionStatus) {
		this.auctionStatus = auctionStatus;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public Set<Bid> getBiders() {
		return biders;
	}

	public void setBiders(Set<Bid> biders) {
		this.biders = biders;
	}
	
	
	
}
