package com.example.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String>listHistory;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        anhXa();
        listView.setAdapter(adapter);
        Cursor data=MainActivity.database.GetData("SELECT * FROM History");
        listHistory.clear();
        while(data.moveToNext())
        {
            String ls=data.getString(1);
            listHistory.add(ls);
        }
        adapter.notifyDataSetChanged();
    }
    public void anhXa()
    {
        listView=(ListView)findViewById(R.id.listViewHistory);
        listHistory=new ArrayList<>();
        adapter=new ArrayAdapter(HistoryActivity.this, android.R.layout.simple_list_item_1,listHistory);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu_2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.back)
        {
            super.onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}