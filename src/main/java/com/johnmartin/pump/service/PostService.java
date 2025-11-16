package com.johnmartin.pump.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.johnmartin.pump.constants.CommentEntityConstants;
import com.johnmartin.pump.entities.PostEntity;
import com.johnmartin.pump.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<PostEntity> getAllPosts() {
        return postRepository.findAll();
    }

    public List<PostEntity> getAllPostsFromUser(UUID userId) {
        return postRepository.findAll().stream().filter(post -> post.getUserId().equals(userId.toString())).toList();
    }

    public PostEntity savePost(PostEntity post) {
        return postRepository.save(post);
    }

    public PostEntity getPostById(String postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public void incrementCommentsCount(String postId) {
        Update update = new Update().inc(CommentEntityConstants.COLUMN_COMMENTS_COUNT, 1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(postId)), update, PostEntity.class);
    }
}
