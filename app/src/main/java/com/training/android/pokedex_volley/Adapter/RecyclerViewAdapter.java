package com.training.android.pokedex_volley.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.training.android.pokedex_volley.DataModel.Pokemon_Model;
import com.training.android.pokedex_volley.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Pokemon_Model current;
    private Context context;
    private List<Pokemon_Model> models;
    private int layout;
    private ViewHolder holder;

    public RecyclerViewAdapter(Context context, List<Pokemon_Model> models, int layout) {
        this.context = context;
        this.models = models;
        this.layout = layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(layout, null);
        holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        current = models.get(position);

        holder.mtvPokeName.setText(current.getPokemonName());
        Picasso.with(context)
                .load(current.getUrl())
                .resize(100,100)
                .into(holder.mimgPokeImg);

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mtvPokeName;
        ImageView mimgPokeImg;

        ViewHolder(View view) {
            super(view);
            mimgPokeImg = itemView.findViewById(R.id.imgPokemon);
            mtvPokeName = itemView.findViewById(R.id.tvPokeName);
        }
    }
}
