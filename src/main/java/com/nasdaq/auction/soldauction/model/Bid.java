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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TBL_BID",uniqueConstraints = {
		@UniqueConstraint(columnNames = "bid_id")})
public class Bid implements Cloneable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_id", unique = true, nullable = false)
    private Long bidId;
	
	@Column(name = "bider_price", unique = false, nullable = false)
	private double biderPrice;
	
	@ManyToOne
    private User buyer;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "auction_bid", joinColumns = { 
			@JoinColumn(name = "bid_id", nullable = false, updatable = false) },
			inverseJoinColumns = {@JoinColumn(name = "auction_id", 
					nullable = false, updatable = false)} )
	private Set<Auction> auctions = new HashSet<Auction>(0);
	
	private BidStatus bidStatus;

	

	public Long getBidId() {
		return bidId;
	}

	public void setBidId(Long bidId) {
		this.bidId = bidId;
	}

	public double getBiderPrice() {
		return biderPrice;
	}

	public void setBiderPrice(double biderPrice) {
		this.biderPrice = biderPrice;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public Set<Auction> getAuctions() {
		return auctions;
	}

	public void setAuctions(Set<Auction> auctions) {
		this.auctions = auctions;
	}

	public BidStatus getBidStatus() {
		return bidStatus;
	}

	public void setBidStatus(BidStatus bidStatus) {
		this.bidStatus = bidStatus;
	}

	
}
