package com.searchoptimization.reposervice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "shared_notes")
public class SharingTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sharing_transaction_id")
    private int sharingTransactionId;


    //PARENT//
    /*
    * Same Note can behave as different SharingTransaction for different users right?
    * Hence, the relationship between SharingTransaction and Note will be @ManyToOne relationship:
    * SharingTransaction:Note :: Many:One (i.e. @ManyToOne)
    * (read this and simultaneously refer to SharedNoteEntities field in Note class for better clarity)
    * */
    @ManyToOne
    @JoinColumn(name = "note_id")
    private Note note;

    //PARENT//
    /*
    * Now, see, a user can own multiple notes that is clear but a user can also can have multiple
    * notes shared with him, so there will also be a relationship between this SharingTransaction and
    * User
    *
    * A user can have multiple shared notes, hence the relationship between them will be:
    * SharingTransaction:User :: Many:One (i.e. @ManyToOne)
    * (read this and simultaneously refer to SharedNoteEntities field in the User class for better clarity)
    * */
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;
}
