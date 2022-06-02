package org.joksin.demomongo.util;

import org.bson.Document;

@FunctionalInterface
public interface DocumentReader<T> {

    T mapToObject(Document document);

}
