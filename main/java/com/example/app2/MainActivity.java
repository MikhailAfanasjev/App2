package com.example.app2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app2.DAO.NoteDAO;
import com.example.app2.DAO.NoteDB;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Note> noteList; // Список для хранения заметок
    private NoteAdapter noteAdapter; // Адаптер для отображения заметок в списке
    private RecyclerView recyclerViewNotes; // Вид для отображения списка
    private EditText editTitle, editDescription; // Поля ввода для заголовка и описания заметки
    private ExecutorService executorService;
    private NoteDAO noteDAO;

    private NoteDB noteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Установка разметки для активности

        editTitle = findViewById(R.id.editTitle); // Находим поле для заголовка
        editDescription = findViewById(R.id.editDescription); // Находим поле для описания
        Button buttonSave = findViewById(R.id.buttonSave); // Находим кнопку сохранения
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes); // Находим RecyclerView для заметок

        noteDB = NoteDB.getDatabase(this);
        noteDAO = noteDB.noteDAO();
        executorService = Executors.newSingleThreadExecutor();

        noteList = new ArrayList<>(); // Инициализируем список заметок
        noteAdapter = new NoteAdapter(noteList, this); // Создаем адаптер с пустым списком
        recyclerViewNotes.setAdapter(noteAdapter); // Устанавливаем адаптер для RecyclerView
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this)); // Устанавливаем менеджер расположения для списка

        loadNote();

        // Устанавливаем слушатель для кнопки сохранения
        buttonSave.setOnClickListener(v -> {
            String title = editTitle.getText().toString(); // Получаем текст заголовка
            String description = editDescription.getText().toString(); // Получаем текст описания

            if (!title.isEmpty() && !description.isEmpty()) { // Проверяем, что поля не пустые
                Note newNote = new Note(title, description);
                executorService.execute(() -> {
                    noteDAO.insert(newNote); // Сохраняем заметку в базе данных
                    loadNote(); // Загружаем обновленный список заметок
                });
                editTitle.setText(""); // Очищаем поле заголовка
                editDescription.setText(""); // Очищаем поле описания
            } else {
                Toast.makeText(MainActivity.this, "Пожалуйста, введите заголовок и описание", Toast.LENGTH_SHORT).show(); // Показываем уведомление
            }
        });

        // Настройка жестов для удаления заметок
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // Перемещение не поддерживается
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition(); // Получаем позицию смахнутого элемента

                Note noteToRemove = noteAdapter.getNotePosition(position);

                executorService.execute(() -> {
                    noteDAO.delete(noteToRemove);
                    loadNote();
                });
            }
        }).attachToRecyclerView(recyclerViewNotes); // Привязываем жесты к RecyclerView
    }

    private void loadNote() {
        executorService.execute(() -> {
            List<Note> notes = noteDAO.getAllNotes();
            runOnUiThread(() -> {
                noteAdapter.updateNotes(notes);
            });
        });
    }
}