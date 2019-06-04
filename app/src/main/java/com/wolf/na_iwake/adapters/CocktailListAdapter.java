package com.wolf.na_iwake.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wolf.na_iwake.Constants;
import com.wolf.na_iwake.R;
import com.wolf.na_iwake.models.Cocktail;
import com.wolf.na_iwake.ui.CocktailDetailActivity;
import com.wolf.na_iwake.ui.CocktailDetailFragment;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CocktailListAdapter extends RecyclerView.Adapter<CocktailListAdapter.CocktailsViewHolder>  {
    private ArrayList<Cocktail> mCocktails = new ArrayList<>();
    private Context mContext;
    private int mOrientation;


    public CocktailListAdapter(Context context, ArrayList <Cocktail> cocktails) {
        mContext = context;
        mCocktails = cocktails;
    }


    @Override
    public CocktailListAdapter.CocktailsViewHolder onCreateViewHolder(ViewGroup parent, int Position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cocktail_list_item, parent, false);
        CocktailsViewHolder viewHolder = new CocktailsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (CocktailListAdapter.CocktailsViewHolder holder, int position) {
        holder.bindCocktail(mCocktails.get(position));
    }
    @Override
    public int getItemCount() {
        return mCocktails.size();
    }

    public class CocktailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.cocktailsImageView)
        ImageView mCocktailsImageVIew;
        @BindView(R.id.cocktailNameTextView)
        TextView mCockTailNameTextView;

        private Context mContext;

    public CocktailsViewHolder (View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        mContext = itemView.getContext ();
        mOrientation = itemView.getResources().getConfiguration().orientation;

        // Checks if the recorded orientation matches Android's landscape configuration.
        // if so, we create a new DetailFragment to display in our special landscape layout:
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            createDetailFragment(0);
        }
    }
    public void bindCocktail (Cocktail cocktail) {
        Picasso.get().load(cocktail.getDrinkThumb()).into(mCocktailsImageVIew);
        mCockTailNameTextView.setText(cocktail.getDrink());
    }
        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(itemPosition);
            } else {
                Intent intent = new Intent(mContext, CocktailDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                intent.putExtra(Constants.EXTRA_KEY_COCKTAILS, Parcels.wrap(mCocktails));
                mContext.startActivity(intent);
            }
            Intent intent = new Intent(mContext, CocktailDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("cocktails", Parcels.wrap(mCocktails));
            mContext.startActivity(intent);

        }

    }
    private void createDetailFragment(int position) {
        CocktailDetailFragment detailFragment = CocktailDetailFragment.newInstance(mCocktails, position);
        FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.cocktailDetailContainer, detailFragment);
        ft.commit();
    }
}
