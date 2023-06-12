package com.shedeurapps.to_do;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class AddTask extends AppCompatActivity {

    SharedPreferences shared;
    SharedPreferences.Editor editor;
    String sharedFile = "my_to_do";
    String toDoList = "to_do_items";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        shared = getSharedPreferences(sharedFile, MODE_PRIVATE);
        editor = getSharedPreferences(sharedFile, MODE_PRIVATE).edit();

        // get listView and button
        AppCompatEditText itemTextView = findViewById(R.id.todo_item);
        AppCompatButton saveButton = findViewById(R.id.save);

        saveButton.setOnClickListener(v -> {
            if(itemTextView.getText() != null) {
                String item = itemTextView.getText().toString();
                if(item.length() > 0) {
                    addToItems(item);
                    finish();
                }
            }
        });
    }

    private ArrayList<String> getItems() {
        Set<String> set = shared.getStringSet(toDoList, null);
        if(set != null) {
            return new ArrayList<>(set);
        }
        return null;
    }

    private void addToItems(String item) {
        Set<String> set;
        ArrayList<String> storedList = getItems();
        if(storedList != null && storedList.size() > 0) {
            set = new HashSet<>(storedList);
        }
        else {
            set = new HashSet<>();
        }
        set.add(item);
        editor.putStringSet(toDoList, set);
        editor.apply();
    }
}