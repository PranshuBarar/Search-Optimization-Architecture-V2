package com.searchoptimization.reposervice.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noteId")
    private int noteId;

    @Column(name = "note")
    private String note;

    //PARENT//
    /*
    Parent of this Note is User.
    As a user can have multiple notes so the relationship between Note and User is
    Note:User :: Many:One (i.e. @ManyToOne)
    */
    @ManyToOne
    @JoinColumn(name = "note_owner_id")
    private User owner;


    //CHILD//
    /*
    Child of this Note is SharingTransaction as a Note can behave like a SharingTransaction
    for many different users.
    So the relationship between Note and SharingTransaction is
    Note:SharingTransaction :: One:Many (i.e. @OneToMany)
    */
    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SharingTransaction> sharingTransactionList = new ArrayList<>();
}
