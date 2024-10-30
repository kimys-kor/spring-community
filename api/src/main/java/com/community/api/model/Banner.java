package com.community.api.model;

import com.community.api.model.base.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partnerName;
    @Column(unique = true)
    private String thumbNail;

}
