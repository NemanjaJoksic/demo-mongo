package org.joksin.demomongo.dao.util;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.joksin.demomongo.model.User;
import org.joksin.demomongo.util.DocumentMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDocumentMapper implements DocumentMapper<User> {

    @Override
    public User mapToObject(Document document) {
        return User.builder()
                    .id(document.getObjectId("_id").toString())
                    .name(document.getString("name"))
                    .email(document.getString("email"))
                    .addresses((List<String>) document.get("addresses"))
                    .build();
    }

    @Override
    public Document mapToDocument(User user) {
        return new Document()
                .append("_id", new ObjectId())
                .append("name", user.getName())
                .append("email", user.getEmail())
                .append("addresses", user.getAddresses());
    }

}
