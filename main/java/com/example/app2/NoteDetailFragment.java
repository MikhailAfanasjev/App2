package com.example.app2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NoteDetailFragment extends DialogFragment {

    private static final String ARG_TITLE = "title"; // Аргумент для заголовка заметки
    private static final String ARG_DESCRIPTION = "description"; // Аргумент для описания заметки

    public static NoteDetailFragment newInstance(String title, String description) {
        NoteDetailFragment fragment = new NoteDetailFragment(); // Создаем новый экземпляр фрагмента
        Bundle args = new Bundle(); // Создаем Bundle для передачи данных
        args.putString(ARG_TITLE, title); // Добавляем заголовок
        args.putString(ARG_DESCRIPTION, description); // Добавляем описание
        fragment.setArguments(args); // Устанавливаем аргументы для фрагмента
        return fragment; // Возвращаем новый фрагмент
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_detail, container, false); // Создаем разметку для фрагмента

        TextView titleTextView = view.findViewById(R.id.noteTitle); // Находим TextView для заголовка
        TextView descriptionTextView = view.findViewById(R.id.noteDescription); // Находим TextView для описания

        Bundle args = getArguments(); // Проверяем, есть ли аргументы
        if (args != null) {
            titleTextView.setText(args.getString(ARG_TITLE)); // Устанавливаем заголовок
            descriptionTextView.setText(args.getString(ARG_DESCRIPTION)); // Устанавливаем описание
        }

        return view; // Возвращаем разметку фрагмента
    }
}
