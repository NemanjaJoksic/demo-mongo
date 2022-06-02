package org.joksin.demomongo.dao;

import org.bson.Document;
import org.joksin.demomongo.dao.util.ArticleDocumentMapper;
import org.joksin.demomongo.model.Article;
import org.joksin.demomongo.util.DocumentMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleDao {

    private final DocumentMongoTemplate mongoTemplate;
    private final ArticleDocumentMapper articleBsonObjectMapper;

    public ArticleDao(DocumentMongoTemplate mongoTemplate, ArticleDocumentMapper articleBsonObjectMapper) {
        this.mongoTemplate = mongoTemplate;
        this.articleBsonObjectMapper = articleBsonObjectMapper;
    }

    public void createArticle(Article article) {
        mongoTemplate.insert(article, "articles", articleBsonObjectMapper);
    }

    public List<Article> getArticles() {
        return mongoTemplate.findAll("articles", articleBsonObjectMapper);
    }

    public List<Document> getArticleDocuments() {
        return mongoTemplate.findAll("articles");
    }
    public List<Article> getArticlesWithAuthor() {
        LookupOperation lookup = Aggregation.lookup("users", "author", "_id", "authorDocuments");
        return mongoTemplate.aggregate(Aggregation.newAggregation(lookup), "articles", articleBsonObjectMapper);
    }

}
