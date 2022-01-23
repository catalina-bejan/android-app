package com.example.museumhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Spinner countrySpinner;
    List<Museum> muzeeList = new ArrayList<>();
    ArrayList<String> countrySpinnerList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    EditText searchBar;
    ArrayList<String> museumListViewItems = new ArrayList<>();
    SwitchCompat descriptionSwitch;
    FrameLayout descriptionFrame;
    private RequestQueue queue;
    int checked=0;

    void setMuseums() {
        Country austria = new Country(1, "Austria");
        City viena = new City(1, "Viena", austria);
        ArtMovement modernism = new ArtMovement(1,"Modernism");
        ArtMovement simbolism = new ArtMovement(2,"Simbolism");
        List<ArtMovement> leopoldAMList = new ArrayList<>();
        leopoldAMList.add(modernism);
        leopoldAMList.add(simbolism);
        Museum leopoldMuseum = new Museum(1,"Leopold Museum",austria,viena,leopoldAMList);

        Country cehia = new Country(2, "Czech Republic");
        City praga = new City(2, "Praga", cehia);
        ArtMovement impresionism = new ArtMovement(3,"Impresionism");
        ArtMovement expresionism = new ArtMovement(4,"Expresionism");
        ArtMovement cubism = new ArtMovement(5,"Cubism");
        ArtMovement suprarealism = new ArtMovement(6,"Suprarealism");
        ArtMovement realism = new ArtMovement(7,"Realism");
        ArtMovement postimpresionism = new ArtMovement(8,"Post-impresionism");
        List<ArtMovement> ngPragueAMList = new ArrayList<>();
        ngPragueAMList.add(modernism);
        ngPragueAMList.add(impresionism);
        ngPragueAMList.add(expresionism);
        ngPragueAMList.add(simbolism);
        ngPragueAMList.add(cubism);
        ngPragueAMList.add(suprarealism);
        ngPragueAMList.add(realism);
        ngPragueAMList.add(postimpresionism);
        Museum nationalGalleryPrague = new Museum(2,"National Gallery Prague",cehia,praga,ngPragueAMList);

        Country franta = new Country(3, "France");
        City paris = new City(3, "Paris", franta);
        List<ArtMovement> museeDOrsayAMList = new ArrayList<>();
        museeDOrsayAMList.add(modernism);
        museeDOrsayAMList.add(impresionism);
        museeDOrsayAMList.add(expresionism);
        museeDOrsayAMList.add(simbolism);
        museeDOrsayAMList.add(realism);
        museeDOrsayAMList.add(postimpresionism);
        Museum museeDOrsay = new Museum(3,"Musee D'Orsay",franta,paris,museeDOrsayAMList);

        Country romania = new Country(4, "Romania");
        City galati = new City(4, "Galati", romania);
        ArtMovement romantism = new ArtMovement(9,"Romantism");
        List<ArtMovement> artaVizualaGalatiAMList = new ArrayList<>();
        artaVizualaGalatiAMList.add(impresionism);
        artaVizualaGalatiAMList.add(expresionism);
        artaVizualaGalatiAMList.add(realism);
        artaVizualaGalatiAMList.add(postimpresionism);
        artaVizualaGalatiAMList.add(romantism);
        Museum muzeulAVGalati = new Museum(4,"Muzeul de Arta Vizuala din Galati",romania,galati,artaVizualaGalatiAMList);

        ArtMovement renascentism = new ArtMovement(10,"Renascentism");
        ArtMovement baroc = new ArtMovement(11,"Baroc");
        ArtMovement neoclasicism = new ArtMovement(12,"Neoclasicism");
        List<ArtMovement> museeDuLouvreAMList = new ArrayList<>();
        museeDuLouvreAMList.add(renascentism);
        museeDuLouvreAMList.add(baroc);
        museeDuLouvreAMList.add(neoclasicism);
        museeDuLouvreAMList.add(romantism);
        Museum museeDuLouvre = new Museum(5,"Musee Du Louvre",franta,paris,museeDuLouvreAMList);

        City sibiu = new City(5, "Sibiu", romania);
        List<ArtMovement> muzeulBrukenthalAMList = new ArrayList<>();
        muzeulBrukenthalAMList.add(renascentism);
        muzeulBrukenthalAMList.add(baroc);
        Museum muzeulBrukenthalSibiu = new Museum(6,"Muzeul Brukenthal Sibiu",romania,sibiu,muzeulBrukenthalAMList);

        countrySpinnerList.add("Select country");
        //countrySpinnerList.add(austria.getName());
        //countrySpinnerList.add(cehia.getName());
        //countrySpinnerList.add(franta.getName());
        //countrySpinnerList.add(romania.getName());

        muzeeList.add(leopoldMuseum);
        muzeeList.add(nationalGalleryPrague);
        muzeeList.add(museeDOrsay);
        muzeeList.add(muzeulAVGalati);
        muzeeList.add(museeDuLouvre);
        muzeeList.add(muzeulBrukenthalSibiu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setMuseums();
        showFragment(new ApplicationFragment());
        queue = Volley.newRequestQueue(this);
        jsonParse();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.file:
                        startActivity(new Intent(getApplicationContext(),FileIO.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.database:
                        startActivity(new Intent(getApplicationContext(),Database.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        descriptionFrame = (FrameLayout) findViewById(R.id.descriptionFragment);

        museumListViewItems = new ArrayList<>();
        for(Museum museum : muzeeList){
            museumListViewItems.add(museum.getName());
        }

        //search and filter by name
        searchBar = (EditText) findViewById(R.id.searchEditText);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override public void afterTextChanged(Editable searchText) {
                    //Toast.makeText(MainActivity.this, "Typed: " + s, Toast.LENGTH_LONG).show();
                countrySpinner = (Spinner) findViewById(R.id.spinner1);
                filter(countrySpinner.getSelectedItem().toString(),searchText.toString());
            }
        });

        //filter by country
        countrySpinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, countrySpinnerList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter1);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), "Selected: " + selectedCountry, Toast.LENGTH_LONG).show();
                searchBar = (EditText) findViewById(R.id.searchEditText);
                filter(selectedCountry,searchBar.getText().toString());
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        //list view
        listView = (ListView) findViewById(R.id.museumListView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, museumListViewItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Museum museum = muzeeList.get(position);
                String museumName = museumListViewItems.get(position);
                Intent intent = new Intent(MainActivity.this, MuseumDescriptionActivity.class);
                //String museumDescription = museumListViewItems[position];
                for(Museum m : muzeeList) {
                    if(m.getName().equals(museumName)){
                        intent.putExtra("museumName", m.getName());
                        intent.putExtra("museumDescription", m.toString());
                    }
                }
                startActivity(intent);
            }
        });
    }

    public void filter(String countryFilter, String searchFilter){
        museumListViewItems.clear();
        //search by country
        if (!countryFilter.equals("Select country") && searchFilter.equals("")) {
            for (Museum museum : muzeeList) {
                if (museum.getCountry().getName().equals(countryFilter)) {
                    museumListViewItems.add(museum.getName());
                }
            }
        }
        //search by country and museum name
        else if(!countryFilter.equals("Select country") && !searchFilter.equals("")){
            for (Museum museum : muzeeList) {
                if (museum.getCountry().getName().equals(countryFilter) && museum.getName().toLowerCase().contains(searchFilter.toLowerCase())) {
                    museumListViewItems.add(museum.getName());
                }
            }
            //search by museum name
        }
        else if(countryFilter.equals("Select country") && !searchFilter.equals("")){
            for (Museum museum : muzeeList) {
                if (museum.getName().toLowerCase().contains(searchFilter.toLowerCase())) {
                    museumListViewItems.add(museum.getName());
                }
            }
        }
        //no filtering
        else {
            for (Museum museum : muzeeList) {
                museumListViewItems.add(museum.getName());
            }
        }

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_2, android.R.id.text1, museumListViewItems);
        listView.setAdapter(adapter);
    }

    private void jsonParse() {
        String url = "https://restcountries.eu/rest/v2/regionalbloc/eu?fields=name";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject country = response.getJSONObject(i);
                                String countryName = country.getString("name");
                                countrySpinnerList.add(countryName);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    public void showFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.descriptionFragment,fragment).commit();
    }

    public void onClickListenerDescriptionSwitch(View view) {
        checked++;
        if(checked == 10)
            checked=0;
        if(checked %2 == 1)
        {
//            descriptionFrame.setBackgroundColor(Color.GREEN);
            showFragment(new DescriptionFragment());
        }
        else
        {
//            descriptionFrame.setBackgroundColor(Color.TRANSPARENT);
            showFragment(new ApplicationFragment());
        }
    }
}