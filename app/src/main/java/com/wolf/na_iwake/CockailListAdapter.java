package com.wolf.na_iwake;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CockailListAdapter extends RecyclerView.Adapter<CockailListAdapter.CocktailsViewHolder> {
    private ArrayList<Cocktail> mCocktails = new ArrayList<>();
    private Context mContext;


    public CockailListAdapter(Context context, ArrayList <Cocktail> cocktails) {
        mContext = context;
        mCocktails = cocktails;
    }

    @Override
    public CockailListAdapter.CocktailsViewHolder onCreateViewHolder(ViewGroup parent, int Position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cocktail_list_item, parent, false);
        CocktailsViewHolder viewHolder = new CocktailsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (CockailListAdapter.CocktailsViewHolder holder, int position) {
        holder.bindCocktail(mCocktails.get(position));
    }
    @Override
    public int getItemCount() {
        return mCocktails.size();
    }

    public class CocktailsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cocktailsImageView)
        ImageView mCocktailsImageVIew;
        @BindView(R.id.cocktailNameTextView)
        TextView mCockTailNameTextView;

        private Context mContext;

    public CocktailsViewHolder (View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext ();

    }
    public void bindCocktail (Cocktail cocktail) {
        Picasso.get().load(cocktail.getDrinkThumb()).into(mCocktailsImageVIew);
        mCockTailNameTextView.setText(cocktail.getDrink());


    }
    }

}
