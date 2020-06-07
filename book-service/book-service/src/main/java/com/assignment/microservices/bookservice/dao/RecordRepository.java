package com.assignment.microservices.bookservice.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.microservices.bookservice.bean.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

	 Optional<List<Record>> findAllByUid(Integer uid);

	Optional<List<Record>> findAllByBid(Integer bid);
	
}
