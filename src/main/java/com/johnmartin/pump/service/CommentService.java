package com.johnmartin.pump.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johnmartin.pump.entities.CommentEntity;
import com.johnmartin.pump.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public CommentEntity saveComment(CommentEntity comment) {
        return commentRepository.save(comment);
    }

    public List<CommentEntity> getAllCommentsFromPost(String postId) {
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
    }
}
