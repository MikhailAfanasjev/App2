package com.example.app2.DAO;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import com.example.app2.Note;
import java.util.List;

@Dao // Аннотация, указывающая, что это интерфейс DAO для работы с базой данных

public interface NoteDAO {
    @Insert // Аннотация для метода вставки новой записи в таблицу
    void insert(Note note); // Метод для вставки заметки в базу данных

    @Query("SELECT * FROM notes ORDER BY id DESC") // SQL-запрос для получения всех заметок, отсортированных по ID в обратном порядке
    List<Note> getAllNotes(); // Метод для получения списка всех заметок

    @Delete // Аннотация для метода удаления записи из таблицы
    void delete(Note note); // Метод для удаления указанной заметки из базы данных
}
