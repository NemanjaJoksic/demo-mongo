package org.joksin.demomongo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@ToString
public class Article {

    private String id;
    private String name;
    private Date publishDate;
    private String text;
    @Setter
    private User author;
    private List<Comment> comments;

}
