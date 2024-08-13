package com.example.app2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private ArrayList<Note> noteList; // Список заметок для адаптера
    private Context context; // Контекст приложения

    public NoteAdapter(ArrayList<Note> noteList, Context context) { // Конструктор адаптера
        this.noteList = noteList; // Инициализация списка заметок
        this.context = context; // Инициализация контекста
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false); // Создаем элемент списка
        return new NoteViewHolder(view); // Возвращаем ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position); // Получаем заметку по позиции
        holder.titleTextView.setText(note.getTitle()); // Устанавливаем заголовок в TextView

        holder.itemView.setOnClickListener(v -> {
            NoteDetailFragment fragment = NoteDetailFragment.newInstance(note.getTitle(), note.getDescription()); // Создаем и показываем NoteDetailFragment
            fragment.show(((FragmentActivity) context).getSupportFragmentManager(), "note_detail"); // Показываем детальный фрагмент заметки
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size(); // Возвращаем размер списка заметок
    }

    public void updateNotes(List<Note> notes) {
        noteList.clear(); // Очищает текущий список заметок
        noteList.addAll(notes); // Добавляет все заметки из переданного списка
        notifyDataSetChanged(); // Уведомляет адаптер об обновлении данных, чтобы перерисовать список
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView; // TextView для отображения заголовка

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.noteTitle); // Инициализируем TextView
        }
    }
    public Note getNotePosition (int position){
        return noteList.get(position);
    }
}
