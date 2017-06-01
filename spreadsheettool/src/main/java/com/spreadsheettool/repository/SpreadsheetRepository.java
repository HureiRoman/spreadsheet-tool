package com.spreadsheettool.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spreadsheettool.entity.Spreadsheet;

@Repository
public interface SpreadsheetRepository extends MongoRepository<Spreadsheet, ObjectId>, SpreadsheetRepositoryCustom{

	Spreadsheet findByName(String name);
	Spreadsheet findById(String id);
	
}
