package com.community.api.model;

import jakarta.persistence.*;
import lombok.*;


@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ImgFile")
public class ImgFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "varchar(250)")
    private String origFileName;
    @Column(nullable = false, columnDefinition = "varchar(250)")
    private String fileName;
    @Column(nullable = false, columnDefinition = "varchar(250)")
    private String filePath;
}

