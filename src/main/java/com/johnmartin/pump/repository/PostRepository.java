package com.johnmartin.pump.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.johnmartin.pump.entities.PostEntity;

@Repository
public interface PostRepository extends MongoRepository<PostEntity, String> {
}
