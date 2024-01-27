package com.enotes.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.enotes.entity.Note;
import com.enotes.entity.User;


public interface NoteRepo extends CrudRepository<Note, Integer> {
    public Note findById(int id);

    public Page <Note> findNoteByUser(User user,Pageable page);
}
