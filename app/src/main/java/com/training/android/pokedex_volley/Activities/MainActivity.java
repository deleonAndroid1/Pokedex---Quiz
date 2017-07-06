package com.training.android.pokedex_volley.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mrvPokelist = (RecyclerView) findViewById(R.id.rvPokemon);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);

        fetch();
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://pokeapi.co/api/v2/pokemon/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            PokeObj = new ArrayList<>();
                            try {
                                JSONArray jsonArray = response.getJSONArray("results");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject res = jsonArray.getJSONObject(i);

                                    String url = res.getString("url");
                                    String name = res.getString("name").toUpperCase();
                                    String nextpage = response.getString("next");
                                    String prevpage = response.getString("previous");
                                    Pokemon_Model pokemon_model = new Pokemon_Model(url, name);
                                    PokeObj.add(pokemon_model);
                                }

                                mAdapter = new RecyclerViewAdapter(MainActivity.this, PokeObj, R.layout.pokemon_layout);
                                mrvPokelist.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                mrvPokelist.setAdapter(mAdapter);
                            } catch (JSONException e) {
                                Log.e("Error", "JSON Exception");
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
}
