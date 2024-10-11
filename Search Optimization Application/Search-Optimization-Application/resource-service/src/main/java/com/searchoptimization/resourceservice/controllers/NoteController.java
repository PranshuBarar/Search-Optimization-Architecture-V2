package com.searchoptimization.resourceservice.controllers;

import com.searchoptimization.reposervice.Dto.NoteDto;
import com.searchoptimization.resourceservice.services.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
@AllArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PutMapping("/add_note")
    public ResponseEntity<?> addNote(@RequestBody NoteDto noteDto) {
        String response = noteService.addNote(noteDto.getNote());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete_note/{note_id}")
    public ResponseEntity<?> deleteNote(@PathVariable(name = "note_id") int noteId) {
        String response = noteService.deleteNote(noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get_note/{note_id}")
    public ResponseEntity<?> getNote(@PathVariable(name = "note_id") int noteId) {
        String response = noteService.getNote(noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/get_all_notes")
    public ResponseEntity<?> getAllNotes() {
        Object response = noteService.getAllNotes();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
