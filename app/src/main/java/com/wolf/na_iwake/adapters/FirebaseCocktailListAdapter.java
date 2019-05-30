package com.wolf.na_iwake.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.wolf.na_iwake.R;
import com.wolf.na_iwake.models.Cocktail;
import com.wolf.na_iwake.ui.FirebaseCocktailViewHolder;
import com.wolf.na_iwake.util.ItemTouchHelperAdapter;
import com.wolf.na_iwake.util.OnStartDragListener;

public class FirebaseCocktailListAdapter extends FirebaseRecyclerAdapter <Cocktail, FirebaseCocktailViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    public FirebaseCocktailListAdapter(FirebaseRecyclerOptions<Cocktail> options, DatabaseReference ref, OnStartDragListener onStartDragListener, Context context) {
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final FirebaseCocktailViewHolder viewHolder, int position, @NonNull Cocktail model) {
        viewHolder.bindCocktail(model);
        viewHolder.mCocktailImageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
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
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}


