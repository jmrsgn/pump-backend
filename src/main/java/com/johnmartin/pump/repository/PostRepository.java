package com.johnmartin.pump.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.johnmartin.pump.entities.PostEntity;
import com.johnmartin.pump.repository.custom.PostRepositoryCustom;

@Repository
public interface PostRepository extends MongoRepository<PostEntity, String>, PostRepositoryCustom {

    /**
     * Get all posts sorted by creation date (newest first)
     */
    Page<PostEntity> findAllByOrderByCreatedAtDesc(PageRequest pageRequest);
}
