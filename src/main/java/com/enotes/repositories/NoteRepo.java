package com.enotes.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.enotes.entity.Note;
import com.enotes.entity.User;


public interface NoteRepo extends CrudRepository<Note, Integer> {
    public Note findById(int id);

    public List<Note> findNoteByUser(User user);
}
