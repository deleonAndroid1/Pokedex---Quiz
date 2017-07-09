package com.training.android.pokedex_volley.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.training.android.pokedex_volley.Adapter.RecyclerViewAdapter;
import com.training.android.pokedex_volley.DataModel.Pokemon_Model;
import com.training.android.pokedex_volley.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mrvPokelist;
    private List<Pokemon_Model> PokeObj;
    private RecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<String> urlList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mrvPokelist = (RecyclerView) findViewById(R.id.rvPokemon);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);

        fetch();

        //Swipe to Refresh
        mSwipeRefreshLayout.setColorSchemeColors(Color.GREEN);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fetch();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });
    }

    public void fetch() {
        urlList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://pokeapi.co/api/v2/pokemon/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            PokeObj = new ArrayList<>();
                            try {

                                JSONArray res = response.getJSONArray("results");
                                for (int i = 0; i < res.length(); i++) {
                                    JSONObject object = res.getJSONObject(i);
                                    String url = object.getString("url");
                                    urlList.add(url);
                                }

                                fetchpoke();
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
                        Toast.makeText(MainActivity.this, "super", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    public void fetchpoke() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        PokeObj = new ArrayList<>();

        for (String url : urlList) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (response != null) {

                                try {
                                    String name = response.getString("name");
                                    JSONObject url = response.getJSONObject("sprites");
                                    String IMGurl = url.getString("front_default");
                                    String id = response.getString("id");

                                    Pokemon_Model pokemon_model = new Pokemon_Model(IMGurl, name);
                                    PokeObj.add(pokemon_model);

                                    mAdapter = new RecyclerViewAdapter(MainActivity.this, PokeObj, R.layout.pokemon_layout);
                                    mrvPokelist.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                    mrvPokelist.setAdapter(mAdapter);

                                } catch (JSONException e) {
                                    Log.e("Error", e.getMessage());
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Error", error.getMessage());

                        }
                    }
            );
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);

        }
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
                Intent a = new Intent(MainActivity.this, Items.class);
                startActivity(a);
                break;
            case R.id.Berries:
                Intent b = new Intent(MainActivity.this, Berries.class);
                startActivity(b);
                break;
            case R.id.Moves:
                Intent c = new Intent(MainActivity.this, Moves.class);
                startActivity(c);
                break;
            case R.id.Location:
                Intent d = new Intent(MainActivity.this, Location.class);
                startActivity(d);
                break;
            case R.id.Pokemons:
                return true;

        }
    return false;
    }

}
