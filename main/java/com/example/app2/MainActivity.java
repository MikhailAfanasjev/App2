package com.example.app2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Note> noteList; // Список для хранения заметок
    private NoteAdapter noteAdapter; // Адаптер для отображения заметок в списке
    private RecyclerView recyclerViewNotes; // Вид для отображения списка
    private EditText editTitle; // Поля ввода для заголовка и описания заметки
    private EditText editDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Установка разметки для активности

        editTitle = findViewById(R.id.editTitle); // Находим поле для заголовка
        editDescription = findViewById(R.id.editDescription); // Находим поле для описания
        Button buttonSave = findViewById(R.id.buttonSave); // Находим кнопку сохранения
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes); // Находим RecyclerView для заметок

        noteList = new ArrayList<>(); // Инициализируем список заметок
        noteAdapter = new NoteAdapter(noteList, this); // Создаем адаптер с пустым списком
        recyclerViewNotes.setAdapter(noteAdapter); // Устанавливаем адаптер для RecyclerView
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this)); // Устанавливаем менеджер расположения для списка

        buttonSave.setOnClickListener(new View.OnClickListener() { // Устанавливаем слушатель для кнопки сохранения
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString(); // Получаем текст заголовка
                String description = editDescription.getText().toString(); // Получаем текст описания

                if (!title.isEmpty() && !description.isEmpty()) { // Проверяем, что поля не пустые
                    noteList.add(0, new Note(title, description)); // Создаем и добавляем новую заметку в начало списка
                    noteAdapter.notifyItemInserted(0); // Уведомляем адаптер о изменении данных
                    recyclerViewNotes.scrollToPosition(0);
                    editTitle.setText(""); // Очищаем поле заголовка
                    editDescription.setText(""); // Очищаем поле описания
                } else {
                    Toast.makeText(MainActivity.this, "Пожалуйста, введите заголовок и описание", Toast.LENGTH_SHORT).show(); // Показываем уведомление
                }
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
                noteAdapter.removeItem(position); // Удаляем элемент из адаптера
            }
        }).attachToRecyclerView(recyclerViewNotes); // Привязываем жесты к RecyclerView
    }
}