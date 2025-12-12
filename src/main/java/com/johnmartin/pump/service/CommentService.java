package com.johnmartin.pump.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.johnmartin.pump.constants.UIConstants;
import com.johnmartin.pump.entities.CommentEntity;
import com.johnmartin.pump.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public CommentEntity saveComment(CommentEntity comment) {
        return commentRepository.save(comment);
    }

    public List<CommentEntity> getLatestComments(String postId) {
        Pageable pageable = PageRequest.of(0, UIConstants.MINIMUM_COMMENTS_PER_POST);
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId, pageable);
    }

    public List<CommentEntity> loadMoreComments(String postId, Instant lastLoadedCreatedAt) {
        Pageable pageable = PageRequest.of(0, UIConstants.MINIMUM_COMMENTS_PER_POST);

        return commentRepository.findByPostIdAndCreatedAtLessThanOrderByCreatedAtDesc(postId,
                                                                                      lastLoadedCreatedAt,
                                                                                      pageable);
    }
}
