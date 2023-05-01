package com.chillin.hearting.db.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

@Slf4j
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Heart implements Serializable {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    private Long id;

    @Column(name = "name", unique = true, nullable = false, length = 100)
    private String name;

    @Column(name = "image_url", unique = true, nullable = false, length = 200)
    private String imageUrl;

    @Column(name = "short_description", nullable = false, length = 500)
    private String shortDescription;

    @Column(name = "long_description", nullable = false, length = 500)
    private String longDescription;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "acq_condition", nullable = true, length = 500)
    private String acqCondition;
}
