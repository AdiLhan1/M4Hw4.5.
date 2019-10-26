package com.taskapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import com.taskapp.interfaces.OnItemClickListener;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> list;
    private OnItemClickListener onItemClickListener;

    public TaskAdapter(List<Task> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        SQLiteDatabase db;
        Database saveDatabase;
        String saveString;
        private TextView textTitle;
        private TextView textDesc;
        private CheckBox checkBox;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDesc);
            checkBox = itemView.findViewById(R.id.view_checkbox);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

        public void bind(final Task task) {

            boolean isChecked = App.getInstance().getSharedPreferences("name_of_your_shared_pref", Context.MODE_PRIVATE).getBoolean(String.valueOf(task.getId()), false);
            textTitle.setText(task.getTitle());
            textDesc.setText(task.getDesc());
            checkBox.setChecked(isChecked);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    App.getInstance().getSharedPreferences("name_of_your_shared_pref", Context.MODE_PRIVATE).edit().putBoolean(String.valueOf(task.getId()), checkBox.isChecked()).apply();
                }

            });
        }
    }
}
