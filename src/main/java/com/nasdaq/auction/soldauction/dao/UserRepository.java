package com.nasdaq.auction.soldauction.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasdaq.auction.soldauction.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
