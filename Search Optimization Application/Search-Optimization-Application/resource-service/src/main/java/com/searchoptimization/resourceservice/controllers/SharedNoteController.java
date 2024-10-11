package com.searchoptimization.resourceservice.controllers;

import com.searchoptimization.reposervice.Dto.ShareUnShareNoteRequestDTO;
import com.searchoptimization.resourceservice.services.SharedNoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shared_notes")
@AllArgsConstructor
public class SharedNoteController {

    private final SharedNoteService sharedNoteService;

    @PutMapping("/share_note")
    public ResponseEntity<?> shareNote(@RequestBody ShareUnShareNoteRequestDTO shareUnShareNoteRequestDTO){
        int noteId = shareUnShareNoteRequestDTO.getNoteId();
        String emailToShareWith = shareUnShareNoteRequestDTO.getRecipientEmail();
        String response = sharedNoteService.shareNote(noteId, emailToShareWith);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/unshare_note")
    public ResponseEntity<?> unShareNote(@RequestBody ShareUnShareNoteRequestDTO shareUnShareNoteRequestDTO){
        int noteId = shareUnShareNoteRequestDTO.getNoteId();
        String emailToUnShareFrom = shareUnShareNoteRequestDTO.getRecipientEmail();
        String response = sharedNoteService.unShareNote(noteId, emailToUnShareFrom);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
