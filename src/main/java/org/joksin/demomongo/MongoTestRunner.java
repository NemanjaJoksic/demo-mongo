package org.joksin.demomongo;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.joksin.demomongo.dao.ArticleDao;
import org.joksin.demomongo.dao.UserDao;
import org.joksin.demomongo.model.Article;
import org.joksin.demomongo.model.Comment;
import org.joksin.demomongo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class MongoTestRunner implements CommandLineRunner {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ArticleDao articleDao;

    @Override
    public void run(String... args) throws Exception {

        createCollection("users");
        createCollection("articles");

        User user1 = User.builder()
                        .name("nemanja")
                        .email("nemanja@mail.com")
                        .addresses(Arrays.asList("Kneza Milosa 30", "Dalmatinska 11"))
                        .build();

        User user2 = User.builder()
                        .name("petar")
                        .email("petar@mail.com")
                        .addresses(Arrays.asList("Bulevar Mihaila Pupina 5"))
                        .build();

        User user3 = User.builder()
                        .name("milan")
                        .email("milan@mail.com")
                        .build();

        userDao.createUser(user1);
        userDao.createUsers(Arrays.asList(user2, user3));

        List<User> users = userDao.getUsers();
        log.info("Users:");
        users.forEach(user -> log.info(user.toString()));

        user1 = userDao.getUserByName("nemanja");
        log.info(user1.toString());
        user2 = userDao.getUserByName("petar");
        log.info(user2.toString());
        user3 = userDao.getUserByName("milan");
        log.info(user3.toString());

        Article article1 = Article.builder()
                .author(user1)
                .name("Article_1")
                .publishDate(new Date())
                .text("Article_1 text.")
                .comments(Arrays.asList(
                        new Comment("Petar's Comment.", new Date(), user2.getName()),
                        new Comment("Milan's Comment.", new Date(), user3.getName())

                )).build();

        Article article2 = Article.builder()
//                .author(user2)
                .name("Article_2")
                .publishDate(new Date())
                .text("Article_2 text.")
                .comments(Arrays.asList(
                        new Comment("Petar's Comment.", new Date(), user2.getName()),
                        new Comment("Milan's Comment.", new Date(), user3.getName())

                )).build();

        articleDao.createArticle(article1);
        articleDao.createArticle(article2);

        List<Article> articles = articleDao.getArticles();
        log.info("Articles:");
        articles.forEach(article -> log.info(article.toString()));

        List<Document> articleDocuments = articleDao.getArticleDocuments();
        log.info("Article Documents:");
        articleDocuments.forEach(articleDocument -> log.info(articleDocument.toString()));

        List<Article> articlesWithAuthor = articleDao.getArticlesWithAuthor();
        log.info("Articles with author:");
        articlesWithAuthor.forEach(article -> log.info(article.toString()));

    }

    private void createCollection(String collectionName) {
        boolean usersCollectionExists = mongoTemplate.collectionExists(collectionName);
        if (!usersCollectionExists) {
            mongoTemplate.createCollection(collectionName);
            log.info("Collection {} is created", collectionName);
        } else {
            log.info("Collection {} already exists", collectionName);
        }
    }

}
