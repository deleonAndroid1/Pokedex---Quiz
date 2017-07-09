package com.training.android.pokedex_volley.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.training.android.pokedex_volley.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Location extends AppCompatActivity {

    private ListView mlvLocation;
    private List<String> LocationsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mlvLocation = (ListView) findViewById(R.id.lvLocations);
        fetch();
    }

    public void fetch() {
        LocationsList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://pokeapi.co/api/v2/location/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {

                            try {
                                JSONArray res = response.getJSONArray("results");
                                for (int i = 0; i < res.length(); i++) {
                                    JSONObject object = res.getJSONObject(i);
                                    String url = object.getString("name");
                                    LocationsList.add(url);
                                }

                                ListAdapter Adapter = new ArrayAdapter<String>(Location.this, android.R.layout.simple_list_item_1, LocationsList);
                                mlvLocation.setAdapter(Adapter);
                            } catch (JSONException e) {
                                Log.e("Error", e.getMessage());
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", "Parsing");

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pokedex_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.Items:
                Intent a = new Intent(Location.this, Items.class);
                startActivity(a);
                break;
            case R.id.Berries:
                Intent b = new Intent(Location.this, Berries.class);
                startActivity(b);
                break;
            case R.id.Moves:
                Intent d = new Intent(Location.this, Moves.class);
                startActivity(d);
                break;
            case R.id.Location:
                return true;
            case R.id.Pokemons:
                Intent c = new Intent(Location.this, MainActivity.class);
                startActivity(c);
                break;

        }
        return false;
    }
}
