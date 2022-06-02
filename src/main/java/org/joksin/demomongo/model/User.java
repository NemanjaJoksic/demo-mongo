package org.joksin.demomongo.model;

import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
public class User {

    private String id;
    private String name;
    private String email;
    private List<String> addresses;

}
