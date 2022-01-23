package com.example.museumhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileIO extends AppCompatActivity {
    EditText museumForBucketList;
    EditText dateForBucketList;
    TextView showBucketList;
    String fileName = "BucketList.txt";
    StringBuffer buffer = new StringBuffer();
    StringBuffer bufferExtern = new StringBuffer();
    File fileExtern;

    private String filepath = "fileIOBucketList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_io);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.file);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.file:
                        return true;
                    case R.id.database:
                        startActivity(new Intent(getApplicationContext(),Database.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        museumForBucketList = findViewById(R.id.editTextMuseum);
        dateForBucketList = findViewById(R.id.editTextDate);
        showBucketList = findViewById(R.id.textViewShowFile);
        Button buttonScrieExtern = (Button) findViewById(R.id.buttonWriteExtern);
        Button buttonCitesteExtern = (Button) findViewById(R.id.buttonReadExtern);
        if(!isExternalStorageWritable()){
            buttonScrieExtern.setEnabled(false);
            buttonCitesteExtern.setEnabled(false);
        } else {
            fileExtern = new File(getExternalFilesDir(filepath), fileName);
        }
    }

    public void writeFileIntern(View v) {
        try {
            FileOutputStream fileOut = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOut);
            outputWriter.write(museumForBucketList.getText().toString() + " " + dateForBucketList.getText().toString());
            outputWriter.close();
            museumForBucketList.getText().clear();
            dateForBucketList.getText().clear();
            Toast.makeText(this,"Saved to " + getFilesDir() + "/" + fileName, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFileIntern(View v) {
        try {
            FileInputStream fileIn = openFileInput(fileName);
            DataInputStream in = new DataInputStream(fileIn);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            String sirCitit = "";
            while ((strLine = br.readLine()) != null) {
                //sirCitit += strLine+"\n";
                buffer.append(strLine + '\n');
            }
            in.close();
            showBucketList.setText("Fisier intern\n"+buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //verifica daca memoria externa este disponibila pentru scriere
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Toast.makeText(getApplicationContext(), "External Storage is Writable ", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    public void writeFileExtern(View v) {
        try {
            FileOutputStream outputStream = new FileOutputStream(fileExtern);
            OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream);
            outputWriter.write(museumForBucketList.getText().toString() + " " + dateForBucketList.getText().toString());
            outputWriter.close();
            Toast.makeText(this,"Saved to " + getExternalFilesDir(filepath) + "/" +fileName, Toast.LENGTH_LONG).show();
            museumForBucketList.getText().clear();
            dateForBucketList.getText().clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFileExtern(View v) {
        String sirCitit = "";
        try {
            FileInputStream fis = new FileInputStream(fileExtern);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                //sirCitit += "\n"+strLine ;
                bufferExtern.append(strLine + '\n');
            }
            in.close();
            showBucketList.setText("Fisier extern\n"+bufferExtern.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}