package com.chillin.hearting.db.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

@Slf4j
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@ToString
public class Heart implements Serializable {

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

    @Column(name = "type", unique = true, nullable = false)
    String type;

    @Column(name = "acq_condition", unique = true, nullable = true)
    String acqCondition;
}
