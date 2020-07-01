package com.assignment.microservices.bookservice.dao;

import com.assignment.microservices.bookservice.bean.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

	 List<Record> findAllByUid(Integer uid);

	 List<Record> findAllByBid(Integer bid);
	
}
