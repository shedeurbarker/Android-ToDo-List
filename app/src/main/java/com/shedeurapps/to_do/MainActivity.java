package com.shedeurapps.to_do;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> itemsAdapter;
    ArrayList<String> itemsList = new ArrayList<>();
    SharedPreferences shared;
    SharedPreferences.Editor editor;
    String sharedFile = "my_to_do";
    String toDoList = "to_do_items";
    TextView counterView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize storage
        shared = getSharedPreferences(sharedFile, MODE_PRIVATE);
        editor = getSharedPreferences(sharedFile, MODE_PRIVATE).edit();

        // get listView and button
        listView = findViewById(R.id.listView);
        FloatingActionButton addButton = findViewById(R.id.add);
        counterView = findViewById(R.id.total);

        // create a simple multi choice adapter for the listview
        ArrayList<String> storedList = getItems();
        if(storedList != null && storedList.size() > 0) {
            itemsList.clear();
            Collections.sort(storedList);
            itemsList.addAll(storedList);
        }
        else {
            itemsList.add("Visit the Dentist");
            itemsList.add("Go to The Math Dept.");
            itemsList.add("Buy a new Notebook");

        }
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, itemsList);

        // set adapter to list
        listView.setAdapter(itemsAdapter);
        updateCounter();

        addButton.setOnClickListener(v -> {
            Intent i = new Intent(this, AddTask.class);
            startActivity(i);
        });

        listView.setOnItemLongClickListener((arg0, arg1, pos, id) -> {
            deleteItem(pos);
            return true;
        });
    }

    @Override
    protected void onResume() {
        ArrayList<String> newList = getItems();
        if (newList != null && newList.size() > 0) {
            System.out.println(newList);
            itemsList.clear();
            itemsList.addAll(newList);
            itemsAdapter.notifyDataSetChanged();
        }
        super.onResume();
    }

    private ArrayList<String> getItems() {
        Set<String> set = shared.getStringSet(toDoList, null);
        if(set != null) {
            return new ArrayList<>(set);
        }
        return null;
    }

    private void updateCounter() {
        String totalText = "Total - " + itemsList.size();
        counterView.setText(totalText);
    }

    private void deleteItem(int position) {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you wish to delete: \n\n\"" + itemsList.get(position) + "\"")
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                })
                .setPositiveButton("Delete", (dialog, which) -> {
                    itemsList.remove(position);
                    itemsAdapter.notifyDataSetChanged();
                    updateCounter();
                    // remove from shared preference too
                    updateList();
                })
                .show();
    }

    private void updateList() {
        Set<String> set;
        ArrayList<String> storedList = getItems();
        if(storedList != null && storedList.size() > 0) {
            if(storedList.size() > itemsList.size()) {
                set = new HashSet<>(itemsList);
                editor.putStringSet(toDoList, set);
                editor.apply();
            }
        }
    }
}