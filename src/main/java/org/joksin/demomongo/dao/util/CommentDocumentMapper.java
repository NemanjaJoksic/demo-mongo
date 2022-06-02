package org.joksin.demomongo.dao.util;

import org.bson.Document;
import org.joksin.demomongo.model.Comment;
import org.joksin.demomongo.util.DocumentMapper;
import org.springframework.stereotype.Component;

@Component
public class CommentDocumentMapper implements DocumentMapper<Comment> {

    @Override
    public Comment mapToObject(Document document) {
        return new Comment(
                document.getString("text"),
                document.getDate("date"),
                document.getString("author")
        );
    }

    @Override
    public Document mapToDocument(Comment comment) {
        return new Document()
                .append("text", comment.getText())
                .append("date", comment.getDate())
                .append("author", comment.getAuthor());
    }
}
