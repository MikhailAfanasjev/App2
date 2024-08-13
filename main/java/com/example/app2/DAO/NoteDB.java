package com.example.app2.DAO;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import com.example.app2.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDB extends RoomDatabase{
    public abstract NoteDAO noteDAO();

    private static volatile NoteDB INSTANCE;
    public static NoteDB getDatabase(final Context context) { // Получаем экземпляр базы данных
        if(INSTANCE == null) { // Проверяем, существует ли база данных
            synchronized (NoteDB.class) { // Синхронизируем доступ
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                NoteDB.class, "note_db")
                    .build(); // Создаем базу данных
                }
            }
        }
        return INSTANCE;
    }
}
