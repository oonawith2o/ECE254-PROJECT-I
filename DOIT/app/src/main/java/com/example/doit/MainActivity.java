package com.example.doit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.doit.Adapter.ItemAdapter;
import com.example.doit.Model.Item;
import com.example.doit.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mainRecyclerView;
    private FloatingActionButton mainAddButton;
    private DataBaseHelper mainDatabase;
    private ArrayList<Item> mainList;
    private ItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRecyclerView = findViewById(R.id.recyclerView);
        mainAddButton = findViewById(R.id.addButton);
        mainDatabase = new DataBaseHelper(MainActivity.this);
        mainList = new ArrayList<>();
        adapter = new ItemAdapter(mainDatabase , MainActivity.this);

        mainRecyclerView.setHasFixedSize(true);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainRecyclerView.setAdapter(adapter);

        mainList = mainDatabase.getAllTasks();
        Collections.reverse(mainList);
        adapter.setTask(mainList);

        mainAddButton.setOnClickListener(view -> AddNewItem.newInstance().show(getSupportFragmentManager() , AddNewItem.TAG));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mainRecyclerView);
    }
}