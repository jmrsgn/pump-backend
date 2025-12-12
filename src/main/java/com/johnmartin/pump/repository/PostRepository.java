package com.johnmartin.pump.repository;

import java.util.List;

import com.johnmartin.pump.repository.custom.PostRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.johnmartin.pump.entities.PostEntity;

@Repository
public interface PostRepository extends MongoRepository<PostEntity, String>, PostRepositoryCustom {

    /**
     * Get all posts sorted by creation date (newest first)
     */
    List<PostEntity> findAllByOrderByCreatedAtDesc();
}
