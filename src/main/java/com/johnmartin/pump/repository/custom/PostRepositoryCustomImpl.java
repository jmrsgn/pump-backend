package com.johnmartin.pump.repository.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.johnmartin.pump.constants.entities.CommentEntityConstants;
import com.johnmartin.pump.entities.PostEntity;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void incrementCommentsCount(String postId) {
        Update update = new Update().inc(CommentEntityConstants.COLUMN_COMMENTS_COUNT, 1);
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(postId)), update, PostEntity.class);
    }
}
