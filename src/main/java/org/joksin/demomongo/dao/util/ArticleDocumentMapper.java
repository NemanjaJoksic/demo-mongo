package org.joksin.demomongo.dao.util;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.joksin.demomongo.model.Article;
import org.joksin.demomongo.model.Comment;
import org.joksin.demomongo.util.DocumentMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleDocumentMapper implements DocumentMapper<Article> {

    private final CommentDocumentMapper commentDocumentMapper;
    private final UserDocumentMapper userDocumentMapper;

    public ArticleDocumentMapper(CommentDocumentMapper commentBsonObjectMapper, UserDocumentMapper userDocumentMapper) {
        this.commentDocumentMapper = commentBsonObjectMapper;
        this.userDocumentMapper = userDocumentMapper;
    }

    @Override
    public Article mapToObject(Document document) {
        Article article = Article.builder()
                .id(document.getObjectId("_id").toString())
                .name(document.getString("name"))
                .publishDate(document.getDate("publishDate"))
                .text(document.getString("text"))
                .comments(documentsToComments((List<Document>) document.get("comments")))
                .build();

        if (document.containsKey("authorDocuments")) {
            List<Document> authorDocuments = (List<Document>) document.get("authorDocuments");
            if (!authorDocuments.isEmpty()) {
                article.setAuthor(userDocumentMapper.mapToObject(authorDocuments.get(0)));
            }
        }

        return article;
    }

    @Override
    public Document mapToDocument(Article article) {
        return new Document()
                .append("_id", new ObjectId())
                .append("name", article.getName())
                .append("publishDate", article.getPublishDate())
                .append("text", article.getText())
                .append("author", article.getAuthor() != null ? new ObjectId(article.getAuthor().getId()) : null)
                .append("comments", commentsToDocuments(article.getComments()));
    }

    private List<Document> commentsToDocuments(List<Comment> comments) {
        return comments.stream()
                        .map(commentDocumentMapper::mapToDocument)
                        .collect(Collectors.toList());
    }

    private List<Comment> documentsToComments(List<Document> documents) {
        return documents.stream()
                .map(commentDocumentMapper::mapToObject)
                .collect(Collectors.toList());
    }

}
