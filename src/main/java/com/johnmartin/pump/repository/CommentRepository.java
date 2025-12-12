package com.johnmartin.pump.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.johnmartin.pump.entities.CommentEntity;

@Repository
public interface CommentRepository extends MongoRepository<CommentEntity, String> {
    List<CommentEntity> findByPostIdOrderByCreatedAtDesc(String postId, Pageable pageable);

    List<CommentEntity> findByPostIdAndCreatedAtLessThanOrderByCreatedAtDesc(String postId,
                                                                             Instant createdAt,
                                                                             Pageable pageable);
}
