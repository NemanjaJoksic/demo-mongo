package org.joksin.demomongo.dao;

import org.joksin.demomongo.dao.util.UserDocumentMapper;
import org.joksin.demomongo.model.User;
import org.joksin.demomongo.util.EnhancedMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class UserDao {

    private final EnhancedMongoTemplate bsonMongoTemplate;
    private final UserDocumentMapper userBsonObjectMapper;

    public UserDao(EnhancedMongoTemplate bsonMongoTemplate, UserDocumentMapper userDocumentMapper) {
        this.bsonMongoTemplate = bsonMongoTemplate;
        this.userBsonObjectMapper = userDocumentMapper;
    }

    public void createUser(User user) {
        bsonMongoTemplate.insert(user, "users", userBsonObjectMapper);
    }

    public void createUsers(Collection<User> users) {
        bsonMongoTemplate.insert(users, "users", userBsonObjectMapper);
    }

    public List<User> getUsers() {
        return bsonMongoTemplate.findAll("users", userBsonObjectMapper);
    }

    public User getUserByName(String name) {
        return bsonMongoTemplate.findOne(new Query().addCriteria(Criteria.where("name").is(name)), "users", userBsonObjectMapper);
    }

}
