package com.globallogic.usermanagement.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data


public class User {
    @Id
    private UUID  id;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
     private boolean isActive;


    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Phone> phones;



    public User() {
        this.id = UUID.randomUUID();
        this.isActive = true;
        this.created = LocalDateTime.now();
    }
}
