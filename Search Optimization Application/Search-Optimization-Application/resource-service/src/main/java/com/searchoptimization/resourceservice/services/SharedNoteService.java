package com.searchoptimization.resourceservice.services;

import com.searchoptimization.reposervice.entities.Note;
import com.searchoptimization.reposervice.entities.SharingTransaction;
import com.searchoptimization.reposervice.entities.User;
import com.searchoptimization.reposervice.repo.NoteRepository;
import com.searchoptimization.reposervice.repo.SharingTransactionRepository;
import com.searchoptimization.reposervice.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SharedNoteService {
    private final SharingTransactionRepository sharingTransactionRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;


    public String shareNote(int noteId, String emailToShareWith) {
        //First we will check whether the given note is owned by the user
        String email = getEmailOfCurrentAuthenticatedUserFromContext();
        User ownerUser = userRepository.findByEmail(email);

        Note note = noteRepository.findByNoteId(noteId);
        if(note.getOwner().getUserId() != ownerUser.getUserId()){
            return "This note is not owned by you";
        }

        User userToShareWith = userRepository.findByEmail(emailToShareWith);
        if(userToShareWith == null){
            return "Recipient user does not exist";
        }

        //Establishing parent child relationship between sharednoteentity, userentity
        //and noteentity
        SharingTransaction sharingTransaction = SharingTransaction.builder()
                .note(note)
                .recipient(userToShareWith)
                .build();

        SharingTransaction sharingTransactionFromDB = sharingTransactionRepository.save(sharingTransaction);

        note.getSharingTransactionList().add(sharingTransactionFromDB);
        userToShareWith.getSharedNoteList().add(sharingTransactionFromDB);

        return "Note shared successfully";
    }

    @Transactional
    public String unShareNote(int noteId, String emailToUnShareFrom) {
        //First we will check whether the given note is owned by the user
        String email = getEmailOfCurrentAuthenticatedUserFromContext();
        User ownerUser = userRepository.findByEmail(email);

        Note note = noteRepository.findByNoteId(noteId);
        if(note.getOwner().getUserId() != ownerUser.getUserId()){
            return "This note is not owned by you";
        }

        User userToUnShareFrom = userRepository.findByEmail(emailToUnShareFrom);
        if(userToUnShareFrom == null){
            return "Recipient user does not exist";
        }

        SharingTransaction sharingTransaction = userToUnShareFrom.getSharedNoteList().stream()
                .filter(sharedNote -> sharedNote.getNote().getNoteId() == note.getNoteId())
                .findFirst()
                .orElse(null);


        if(sharingTransaction == null){
            return "Note is already not shared";
        }

        // Remove the shared note from the collections in User and Note
        note.getSharingTransactionList().remove(sharingTransaction);
        userToUnShareFrom.getSharedNoteList().remove(sharingTransaction);

        int idToBeDeleted = sharingTransaction.getSharingTransactionId();
        sharingTransactionRepository.deleteBySharingTransactionId(idToBeDeleted);

        return "Note unshared successfully";
    }

    //================================================================
    private String getEmailOfCurrentAuthenticatedUserFromContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
