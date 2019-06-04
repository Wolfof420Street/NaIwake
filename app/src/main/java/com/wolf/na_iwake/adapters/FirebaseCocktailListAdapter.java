package com.wolf.na_iwake.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.wolf.na_iwake.R;
import com.wolf.na_iwake.models.Cocktail;
import com.wolf.na_iwake.ui.CocktailDetailActivity;
import com.wolf.na_iwake.ui.CocktailDetailFragment;
import com.wolf.na_iwake.ui.FirebaseCocktailViewHolder;
import com.wolf.na_iwake.util.ItemTouchHelperAdapter;
import com.wolf.na_iwake.util.OnStartDragListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseCocktailListAdapter extends FirebaseRecyclerAdapter <Cocktail, FirebaseCocktailViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Cocktail> mCocktails = new ArrayList<>();
    private int mOrientation;

    public FirebaseCocktailListAdapter(FirebaseRecyclerOptions<Cocktail> options, DatabaseReference ref, OnStartDragListener onStartDragListener, Context context) {
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mCocktails.add(dataSnapshot.getValue(Cocktail.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onBindViewHolder(@NonNull final FirebaseCocktailViewHolder viewHolder, int position, @NonNull Cocktail model) {
        viewHolder.bindCocktail(model);
        mOrientation = viewHolder.itemView.getResources().getConfiguration().orientation;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            createDetailFragment(0);
        }
        viewHolder.mCocktailImageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = viewHolder.getAdapterPosition();
                if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    createDetailFragment(itemPosition);
                } else {
                    Intent intent = new Intent(mContext, CocktailDetailActivity.class);
                    intent.putExtra("position", viewHolder.getAdapterPosition());
                    intent.putExtra("cocktails", Parcels.wrap(mCocktails));
                    mContext.startActivity(intent);
                }
            }
        });
    }



    @NonNull
    @Override
    public FirebaseCocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cocktail_list_item_drag, parent, false);
        return new FirebaseCocktailViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mCocktails, fromPosition , toPosition);
        notifyItemMoved(fromPosition, toPosition);
        setIndexInFireBase();
        return false;
    }
    @Override
    public void stopListening () {
        super.stopListening();
        mRef.removeEventListener(mChildEventListener);
    }

    @Override
    public void onItemDismiss(int position) {
        mCocktails.remove(position);
        getRef(position).removeValue();
    }
    private void setIndexInFireBase() {
        for (Cocktail cocktail : mCocktails) {
            int index = mCocktails.indexOf(cocktail);
            DatabaseReference ref = getRef(index);
            cocktail.setIndex(Integer.toString(index));
            ref.setValue(cocktail);
        }
    }
    private void createDetailFragment(int position) {
        // Creates new RestaurantDetailFragment with the given position:
        CocktailDetailFragment detailFragment = CocktailDetailFragment.newInstance(mCocktails, position);
        // Gathers necessary components to replace the FrameLayout in the layout with the RestaurantDetailFragment:
        FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        //  Replaces the FrameLayout with the RestaurantDetailFragment:
        ft.replace(R.id.cocktailDetailContainer, detailFragment);
        // Commits these changes:
        ft.commit();
    }
}


