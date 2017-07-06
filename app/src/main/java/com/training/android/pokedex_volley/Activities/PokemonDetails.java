package com.training.android.pokedex_volley.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.training.android.pokedex_volley.DataModel.Pokemon_Sprites;
import com.training.android.pokedex_volley.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PokemonDetails extends AppCompatActivity {

    private ImageView mImgNormF;
    private ImageView mImgNormB;
    private ImageView mImgShinF;
    private ImageView mImgShinB;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details);

        mImgNormB = (ImageView) findViewById(R.id.imgSpriteNormB);
        mImgNormF = (ImageView) findViewById(R.id.imgSpriteNormF);
        mImgShinB = (ImageView) findViewById(R.id.imgSpriteShiB);
        mImgShinF = (ImageView) findViewById(R.id.imgSpriteShiF);

        Intent i = getIntent();
        url = i.getStringExtra("url");
        fetch();
    }

    public void fetch() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {

                            try {
                                //GET SPRITES
                                JSONObject sprites = response.getJSONObject("sprites");
                                String nF = sprites.getString("front_default");
                                String nB = sprites.getString("back_default");
                                String sF = sprites.getString("front_shiny");
                                String sB = sprites.getString("back_shiny");
                                loadSprites(nF,nB,sF,sB);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", "Parsing");
                        Toast.makeText(PokemonDetails.this, "super", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);

    }

    public void loadSprites(String nF,String nB, String sF, String sB){
        Picasso.with(this)
                .load(nF)
                .resize(200,200)
                .into(mImgNormF);

        Picasso.with(this)
                .load(nB)
                .resize(200,200)
                .into(mImgNormB);

        Picasso.with(this)
                .load(sF)
                .resize(200,200)
                .into(mImgShinF);

        Picasso.with(this)
                .load(sB)
                .resize(200,200)
                .into(mImgShinB);
    }
}
