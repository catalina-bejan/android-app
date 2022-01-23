package com.example.museumhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Database extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList databaseList;
    String numeText;
    String taraText;
    String orasText;
    String notaText;
    String museumName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        EditText nume = (EditText) findViewById(R.id.nume);
        numeText = nume.getText().toString();
        EditText tara = (EditText) findViewById(R.id.tara);
        taraText = tara.getText().toString();
        EditText oras = (EditText) findViewById(R.id.oras);
        orasText = oras.getText().toString();
        EditText nota = (EditText) findViewById(R.id.nota);
        notaText = nota.getText().toString();

        databaseHelper = new DatabaseHelper(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.database);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.file:
                        startActivity(new Intent(getApplicationContext(), FileIO.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.database:
                        return true;
                }
                return false;
            }
        });
        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nume.getText().clear();
                tara.getText().clear();
                oras.getText().clear();
                nota.getText().clear();
                museumName = databaseList.get(position).toString().substring((databaseList.get(position).toString().indexOf(":")) + 2, databaseList.get(position).toString().indexOf(","));
                String taraName = databaseList.get(position).toString().substring((databaseList.get(position).toString().indexOf(",tara: ")) + 7, databaseList.get(position).toString().indexOf(",orasul: "));
                String orasName = databaseList.get(position).toString().substring((databaseList.get(position).toString().indexOf(",orasul: ")) + 9, databaseList.get(position).toString().indexOf(",nota: "));
                String notaName = databaseList.get(position).toString().substring((databaseList.get(position).toString().indexOf(",nota: ")) + 7, databaseList.get(position).toString().length());
                nume.setText(museumName);
                tara.setText(taraName);
                oras.setText(orasName);
                nota.setText(notaName);
            }
        });
    }

    public void insert(View v) {
        EditText nume = (EditText) findViewById(R.id.nume);
        numeText = nume.getText().toString();
        EditText tara = (EditText) findViewById(R.id.tara);
        taraText = tara.getText().toString();
        EditText oras = (EditText) findViewById(R.id.oras);
        orasText = oras.getText().toString();
        EditText nota = (EditText) findViewById(R.id.nota);
        notaText = nota.getText().toString();
        if (!numeText.equals("") && !taraText.equals("") && !orasText.equals("") && !notaText.equals("")) {
            if (databaseHelper.insert(numeText, taraText, orasText, notaText)) {
                Toast.makeText(this, "Data inserted successfully!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Something wrong!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Wrong data!", Toast.LENGTH_LONG).show();
        }

        nume.getText().clear();
        tara.getText().clear();
        oras.getText().clear();
        nota.getText().clear();
    }

    public void view(View v) {
        databaseList = databaseHelper.getData();

        listView = (ListView) findViewById(R.id.listview);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, databaseList);
        listView.setAdapter(adapter);
    }

    public void delete(View v) {
        databaseHelper.delete(museumName);
        EditText nume = (EditText) findViewById(R.id.nume);
        EditText tara = (EditText) findViewById(R.id.tara);
        EditText oras = (EditText) findViewById(R.id.oras);
        EditText nota = (EditText) findViewById(R.id.nota);
        nume.getText().clear();
        tara.getText().clear();
        oras.getText().clear();
        nota.getText().clear();
        Toast.makeText(this, "Deleted '" + museumName + "'", Toast.LENGTH_LONG).show();
    }

    public void update(View v) {
        EditText nume = (EditText) findViewById(R.id.nume);
        numeText = nume.getText().toString();
        EditText tara = (EditText) findViewById(R.id.tara);
        taraText = tara.getText().toString();
        EditText oras = (EditText) findViewById(R.id.oras);
        orasText = oras.getText().toString();
        EditText nota = (EditText) findViewById(R.id.nota);
        notaText = nota.getText().toString();
        databaseHelper.update(museumName, numeText, taraText, orasText, notaText);
        nume.getText().clear();
        tara.getText().clear();
        oras.getText().clear();
        nota.getText().clear();
        Toast.makeText(this, "Updated '" + museumName + "'", Toast.LENGTH_LONG).show();
    }
}