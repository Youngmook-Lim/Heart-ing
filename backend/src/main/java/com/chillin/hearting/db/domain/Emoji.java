package com.chillin.hearting.db.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@ToString
public class Emoji implements Serializable {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    private Long id;

    @Column(length = 100, name= "name")
    private String name;

    @Column(length = 200, name= "image_url")
    private String imageUrl;

    @Builder
    public Emoji(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

}
