package com.searchoptimization.resourceservice.services;

import com.searchoptimization.reposervice.entities.Note;
import com.searchoptimization.reposervice.entities.SharingTransaction;
import com.searchoptimization.reposervice.entities.User;
import com.searchoptimization.reposervice.repo.NoteRepository;
import com.searchoptimization.reposervice.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public String addNote(String note) {

        if (note == null || note.isEmpty()) {
            return "Note cannot be empty"; // Handling empty note
        }

        String email = getEmailOfCurrentAuthenticatedUserFromContext();
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return "User not found"; // Handling user not found scenario
        }

        Note noteEntity = Note.builder()
                .note(note)
                .owner(user)
                .build();

        user.getSelfNotesList().add(noteEntity);

        userRepository.save(user);
        //Note will be saved automatically due to cascading effect
        return "Note saved successfully";
    }

    public String deleteNote(int noteId) {

        //First we will check whether the given note is owned by the user
        String email = getEmailOfCurrentAuthenticatedUserFromContext();
        User user = userRepository.findByEmail(email);
        Note note = noteRepository.findByNoteId(noteId);
        if(note.getOwner().getUserId() != user.getUserId()){
            return "This note is not owned by you";
        }

        //If it is indeed owned by the user then the note will be deleted
        noteRepository.deleteById(noteId);
        return "Note deleted successfully";
    }

    public String getNote(int noteId) {

        //First we will check whether the given note is owned by the user
        String email = getEmailOfCurrentAuthenticatedUserFromContext();
        User user = userRepository.findByEmail(email);

        Note note = noteRepository.findByNoteId(noteId);
        if(note.getOwner().getUserId() != user.getUserId()){
            return "This note is not owned by you";
        }

        //If it is indeed owned by the user then the note will be returned
        return note.getNote();


    }

    public List<Object> getAllNotes() {
        String email = getEmailOfCurrentAuthenticatedUserFromContext();
        User user = userRepository.findByEmail(email);
        List<Note> selfNoteList = user.getSelfNotesList();
        List<SharingTransaction> sharingTransactionList = user.getSharedNoteList();
        List<Object> finalList = new ArrayList<>(selfNoteList);
        finalList.addAll(sharingTransactionList);
        return finalList;

    }

    //================================================================
    private String getEmailOfCurrentAuthenticatedUserFromContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
