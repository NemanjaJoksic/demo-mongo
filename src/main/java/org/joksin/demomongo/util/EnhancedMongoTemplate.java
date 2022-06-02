package org.joksin.demomongo.util;

import org.bson.Document;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Component
public class EnhancedMongoTemplate extends MongoTemplate {

    public EnhancedMongoTemplate(MongoDatabaseFactory mongoDatabaseFactory, MongoConverter mongoConverter) {
        super(mongoDatabaseFactory, mongoConverter);
    }

    public <T> void insert(T object, String collectionName, DocumentWriter<T> documentWriter) {
        super.insert(documentWriter.mapToDocument(object), collectionName);
    }

    public <T> void insert(Collection<T> objects, String collectionName, DocumentWriter<T> documentWriter) {
        List<Document> basicDbObjects
                = objects.stream()
                .map(documentWriter::mapToDocument)
                .collect(Collectors.toList());
        super.insert(basicDbObjects, collectionName);
    }

    public List<Document> findAll(String collectionName) {
        return super.findAll(Document.class, collectionName);
    }
    public <T> List<T> findAll(String collectionName, DocumentReader<T> documentReader) {
        return findAll(collectionName)
                .stream()
                .map(documentReader::mapToObject)
                .collect(Collectors.toList());
    }

    public Document findOne(Query query, String collectionName) {
        return super.findOne(query, Document.class, collectionName);
    }

    public <T> T findOne(Query query, String collectionName, DocumentReader<T> documentReader) {
        Document document = findOne(query, collectionName);
        return document != null ? documentReader.mapToObject(document) : null;
    }

    public <T> List<T> aggregate(Aggregation aggregation, String collectionName, DocumentReader<T> documentReader) {
        List<Document> documents = super.aggregate(aggregation, collectionName, Document.class).getMappedResults();
        return documents.stream()
                .map(documentReader::mapToObject)
                .collect(Collectors.toList());
    }

}
