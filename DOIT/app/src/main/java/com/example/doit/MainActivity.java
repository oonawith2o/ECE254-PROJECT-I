package com.example.doit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.doit.Adapter.ItemAdapter;
import com.example.doit.Model.Item;
import com.example.doit.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListner {

    private RecyclerView mainRecyclerView;
    private FloatingActionButton mainAddButton;
    private DataBaseHelper mainDatabase;
    private List<Item> mainList;
    private ItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
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

        mainAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = AddNewItem.newInstance();
                dialog.show(getSupportFragmentManager(), AddNewItem.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mainRecyclerView);
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mainList = mainDatabase.getAllTasks();
        Collections.reverse(mainList);
        adapter.setTask(mainList);
        adapter.notifyDataSetChanged();
    }
}