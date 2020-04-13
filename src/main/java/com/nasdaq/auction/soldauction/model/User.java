package com.nasdaq.auction.soldauction.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="TBL_USER",uniqueConstraints = {
		@UniqueConstraint(columnNames = "user_id"),
		@UniqueConstraint(columnNames = "email") })
public class User implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
    private Long userId;
	
	@NotEmpty(message = "first name must not be empty")
	@Column(name="first_name")
    private String firstName;
    
	@NotEmpty(message = "last name must not be empty")
    @Column(name="last_name")
    private String lastName;
     
	@NotEmpty(message = "email must not be empty")
    @Column(name="email", nullable=false, length=200,unique = true)
    private String email;
    
	//@NotEmpty(message = "user type must not be empty")
    @Column(name="user_type",nullable=false)
    private UserType type;
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="seller_id")
    private Set<Auction> sellers;
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="buyer_id")
    private Set<Bid> buyers;

	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", type=" + type + "]";
	}

	public Set<Auction> getSellers() {
		return sellers;
	}

	public void setSellers(Set<Auction> sellers) {
		this.sellers = sellers;
	}

	public Set<Bid> getBuyers() {
		return buyers;
	}

	public void setBuyers(Set<Bid> buyers) {
		this.buyers = buyers;
	}

	
	
    
    
    
    
}
