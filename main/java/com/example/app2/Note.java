package com.example.app2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes")  // Сущность таблицы "Notes"
public class Note {
    @PrimaryKey(autoGenerate = true) // Автоматически генерируемый первичный ключ
    private int id;
    private String title; // Заголовок заметки
    private String description; // Описание заметки

    public Note(String title, String description) { // Конструктор заметки
        this.title = title;
        this.description = description;
    }

    public int getId(){ // Возвращает ID
        return id;
    }

    public void setId(int id){ // Устанавливает ID
        this.id=id;
    }

    public String getTitle() { // Возвращает заголовок
        return title;
    }

    public String getDescription() { // Возвращает описание
        return description;
    }

    @Override
    public String toString() { // Возвращает заголовок как строку
        return title;
    }
}
