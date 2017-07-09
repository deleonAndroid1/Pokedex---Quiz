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

public class Moves extends AppCompatActivity {

    private ListView mlvMoves;
    private List<String> MovesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moves);

        mlvMoves = (ListView) findViewById(R.id.lvMOves);
        fetch();
    }

    public void fetch() {
        MovesList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://pokeapi.co/api/v2/move/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {

                            try {
                                JSONArray res = response.getJSONArray("results");
                                for (int i = 0; i < res.length(); i++) {
                                    JSONObject object = res.getJSONObject(i);
                                    String name = object.getString("name");
                                    MovesList.add(name);
                                }

                                ListAdapter Adapter = new ArrayAdapter<String>(Moves.this, android.R.layout.simple_list_item_1, MovesList);
                                mlvMoves.setAdapter(Adapter);
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
                Intent a = new Intent(Moves.this, Items.class);
                startActivity(a);
                break;
            case R.id.Berries:
                Intent b = new Intent(Moves.this, Berries.class);
                startActivity(b);
                break;
            case R.id.Moves:
                return true;
            case R.id.Location:
                Intent d = new Intent(Moves.this, Location.class);
                startActivity(d);
                break;
            case R.id.Pokemons:
                Intent c = new Intent(Moves.this, MainActivity.class);
                startActivity(c);
                break;

        }
        return false;
    }

}
