package org.joksin.demomongo.model;

import lombok.*;

import java.util.Date;

@Getter
@AllArgsConstructor
@ToString
public class Comment {

    private String text;
    private Date date;
    private String author;

}
