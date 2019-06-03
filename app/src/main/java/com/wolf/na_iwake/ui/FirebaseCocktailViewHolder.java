package com.wolf.na_iwake.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wolf.na_iwake.Constants;
import com.wolf.na_iwake.R;
import com.wolf.na_iwake.models.Cocktail;
import com.wolf.na_iwake.util.ItemTouchHelperViewHolder;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseCocktailViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;


    View mView;
    Context mContext;
    public ImageView mCocktailImageView;

    public FirebaseCocktailViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();

    }

    public void bindCocktail(Cocktail cocktail) {
        mCocktailImageView = (ImageView) mView.findViewById(R.id.cocktailsImageView);
       /* ImageView cocktailImageView = (ImageView) mView.findViewById(R.id.cocktailsImageView);*/
        TextView nameTextView = (TextView) mView.findViewById(R.id.cocktailNameTextView);
    /*    TextView websiteTextView = (TextView) mView.findViewById(R.id.websiteTextView);*/

        Picasso.get().load(cocktail.getDrinkThumb()).into(mCocktailImageView);

        nameTextView.setText(cocktail.getDrink());
        /*websiteTextView.setText(cocktail.getWebsite());*/

    }
    @Override
    public void onItemSelected() {
        itemView.animate()
                .alpha(0.7f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);
    }

    @Override
    public void onItemClear() {
        itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f);
    }




    public void onClick(View view) {
        final ArrayList<Cocktail> cocktails = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_COCKTAILS).child(uid);
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



