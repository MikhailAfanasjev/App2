package com.example.app2.DAO;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import com.example.app2.Note;
import java.util.List;

@Dao

public interface NoteDAO {
    @Insert
    void insert(Note note);

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Note> getAllNotes();

    @Delete
    void delete(Note note);
}
