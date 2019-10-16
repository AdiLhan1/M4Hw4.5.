package com.taskapp.ui.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.InvalidationTracker;

import com.taskapp.App;
import com.taskapp.FormActivity;
import com.taskapp.interfaces.OnItemClickListener;
import com.taskapp.R;
import com.taskapp.Task;
import com.taskapp.TaskAdapter;

import java.util.List;
import java.util.Observer;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> list;
    private int pos;
    private CheckBox checkBox;
    String text;
    Task task;
    SharedPreferences myPrefs;
    SharedPreferences.Editor myPrefsPrefsEditor;
    static final String MY_SHARED_PREF = "name_of_your_shared_pref";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        checkBox=root.findViewById(R.id.view_checkbox);
        recyclerView = root.findViewById(R.id.recyclerView);
        initList();
//        if (checkBox.isChecked()){
//             text=checkBox.getText().toString();
//        }
       // setCheckBox();
        return root;
    }

    private void initList() {
        list = App.getInstance().getDatabase().taskDao().getAll();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TaskAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                pos=position;
                 task = list.get(position);
                Toast.makeText(getContext(), task.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), FormActivity.class);
                intent.putExtra("task", task);
                startActivityForResult(intent,101);
            }

            @Override
            public void onItemLongClick(final int position) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete this view");
                builder.setMessage("Would you like delete this words?");

                // add the buttons
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                         task = list.get(position);
                        App.getInstance().getDatabase().taskDao().delete(task);
                        list.remove(task);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", null);

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
    }
    boolean check=true;
    public void setCheckBox(){
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (check){
                    myPrefs = getContext().getSharedPreferences(MY_SHARED_PREF, getContext().MODE_PRIVATE);
                    myPrefsPrefsEditor = myPrefs.edit();
                    myPrefsPrefsEditor.putBoolean("key",check);
                    myPrefsPrefsEditor.commit();
                }
            }
        });
    }
    public void sortList(){
        list.clear();
        list.addAll(App.getInstance().getDatabase().taskDao().getAllsorted());
        adapter.notifyDataSetChanged();
    }
    public void initialList(){
        list.clear();
        list.addAll(App.getInstance().getDatabase().taskDao().getAll());
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "onActivityResult fragment");
        if (resultCode == Activity.RESULT_OK ) {
            Task task = (Task) data.getSerializableExtra("task");
            if (requestCode==100) {
                list.add(task);
            }else if (requestCode==101){
               list.set(pos,task);
            }
            adapter.notifyDataSetChanged();
        }
    }
}