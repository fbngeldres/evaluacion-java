package com.globallogic.usermanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    @Id
    private UUID id;
    private Long number;
    private Integer citycode;
    private String countrycode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
