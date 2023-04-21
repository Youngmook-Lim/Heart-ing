package com.chillin.hearting.db.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@ToString
public class Heart {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    String name;

    @Column(name = "image_url", unique = true, nullable = false)
    String imageUrl;

    @Column(name = "short_description", unique = true, nullable = false)
    String shortDescription;

    @Column(name = "long_description", unique = true, nullable = false)
    String longDescription;

    @Column(name = "category", unique = true, nullable = false)
    String category;

    @Column(name = "acq_condition", unique = true, nullable = true)
    String acqCondition;
}
