package com.searchoptimization.reposervice.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "username")
    private String username;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    //CHILD//
    /*
    * A user can own multiple notes hence the relationship between them will be:
    * User:Note :: One:Many (i.e. @OneToMany)
    * */
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Note> selfNotesList = new ArrayList<>();

    //CHILD//
    /*
    * A user can have multiple notes shared with him hence, the relationship between them will be:
    * User:SharingTransaction :: One:Many (i.e. @OneToMany)
    * */
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SharingTransaction> sharedNoteList = new ArrayList<>();
}