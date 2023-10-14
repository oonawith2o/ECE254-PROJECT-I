package com.example.doit.Adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doit.AddNewItem;
import com.example.doit.MainActivity;
import com.example.doit.Model.Item;
import com.example.doit.R;
import com.example.doit.Utils.DataBaseHelper;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> taskList;
    private MainActivity activity;
    private DataBaseHelper database;

    public ItemAdapter(DataBaseHelper database, MainActivity activity) {
        this.database = database;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Item item = taskList.get(position);
        holder.itemCheckBox.setChecked(item.isCompleted());
        holder.itemSubject.setText(item.getSubject());
        holder.itemNote.setText(item.getNote());
        @SuppressLint({"NewApi", "LocalSuppress"})
        String formattedCreation = item.getCreationDateTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
        holder.itemCreation.setText(formattedCreation);
        holder.itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    database.updateCompleted(item.getItemID() , 1);
                }else
                    database.updateCompleted(item.getItemID() , 0);
            }
        });
    }

    public MainActivity getContext() {
        return activity;
    }

    public void setTask(List<Item> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    public void deleteTask(int position) {
        Item item = taskList.get(position);
        database.deleteTask(item);
        taskList.remove(position);
        notifyItemRemoved(position);
    }

    public void editTask(int position) {
        Item item = taskList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getItemID());
        bundle.putString("subject", item.getSubject());
        bundle.putString("note", item.getNote());
        bundle.putInt("completed", (item.isCompleted() ? 1 : 0));
        AddNewItem task = new AddNewItem();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(), task.getTag());

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        CheckBox itemCheckBox;
        TextView itemSubject;
        TextView itemNote;
        TextView itemCreation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCheckBox = itemView.findViewById(R.id.checkboxCompleted);
            itemSubject = itemView.findViewById(R.id.itemSubject);
            itemNote = itemView.findViewById(R.id.itemNote);
            itemCreation = itemView.findViewById(R.id.itemCreation);
        }
    }
}
