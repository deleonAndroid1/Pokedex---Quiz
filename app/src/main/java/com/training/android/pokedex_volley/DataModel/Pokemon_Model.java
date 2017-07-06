package com.training.android.pokedex_volley.DataModel;

/**
 * Created by Dyste on 7/6/2017.
 */

public class Pokemon_Model {

    private String url;
    private String PokemonName;

    public Pokemon_Model(String url, String pokemonName) {
        this.url = url;
        PokemonName = pokemonName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPokemonName() {
        return PokemonName;
    }

    public void setPokemonName(String pokemonName) {
        PokemonName = pokemonName;
    }
}
