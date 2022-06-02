package org.joksin.demomongo.util;

import org.bson.Document;

@FunctionalInterface
public interface DocumentWriter<T> {

    Document mapToDocument(T data);

}
