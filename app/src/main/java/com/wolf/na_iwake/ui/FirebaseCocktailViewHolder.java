package com.wolf.na_iwake.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wolf.na_iwake.Constants;
import com.wolf.na_iwake.R;
import com.wolf.na_iwake.models.Cocktail;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseCocktailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;

    public FirebaseCocktailViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindCocktail(Cocktail cocktail) {
        ImageView cocktailImageView = (ImageView) mView.findViewById(R.id.cocktailImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.cocktailNameTextView);
        TextView websiteTextView = (TextView) mView.findViewById(R.id.websiteTextView);

        Picasso.get().load(cocktail.getDrinkThumb()).into(cocktailImageView);

        nameTextView.setText(cocktail.getDrink());
        websiteTextView.setText(cocktail.getWebsite());

    }

    @Override
    public void onClick(View view) {
        final ArrayList<Cocktail> cocktails = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_COCKTAILS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    cocktails.add(snapshot.getValue(Cocktail.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, CocktailDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("cocktails", Parcels.wrap(cocktails));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}



