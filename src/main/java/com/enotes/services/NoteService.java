package com.enotes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enotes.entity.Note;
import com.enotes.entity.User;
import com.enotes.repositories.NoteRepo;
@Component
public class NoteService {
    @Autowired
    private NoteRepo noteRepo;

    public List<Note> getNotesByUser(User user ) {
        return noteRepo.findNoteByUser(user);
    }

    public Note findById(int id) {
        Note note = noteRepo.findById(id);
        return note;
    }

    public Note addNote(Note note) {
        Note n = noteRepo.save(note);
        return n;
    }

    public void deleteNote(int id) {
        noteRepo.deleteById(id);
    }

    public void deleteAllNote() {
        noteRepo.deleteAll();
    }

    public Note updateNote(Note note, int id) {
        note.setId(id);
        noteRepo.save(note);
        return note;
    }

}
