package com.wolf.na_iwake.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wolf.na_iwake.Constants;
import com.wolf.na_iwake.R;
import com.wolf.na_iwake.adapters.FirebaseCocktailListAdapter;
import com.wolf.na_iwake.models.Cocktail;
import com.wolf.na_iwake.util.OnStartDragListener;
import com.wolf.na_iwake.util.SimpleItemTouchHelperCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedCocktailsActivity extends AppCompatActivity implements OnStartDragListener {
    private DatabaseReference mCocktailReference;
    private FirebaseCocktailListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktails);
        ButterKnife.bind(this);


        mCocktailReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_COCKTAILS);
        setUpFirebaseAdapter();
    }
    private void setUpFirebaseAdapter(){
       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mCocktailReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_COCKTAILS).child(uid);
        FirebaseRecyclerOptions<Cocktail> options =
                new FirebaseRecyclerOptions.Builder<Cocktail>()
                .setQuery(mCocktailReference, Cocktail.class)
                .build();



        mFirebaseAdapter = new FirebaseCocktailListAdapter(options, mCocktailReference, this, this);
           /* @Override
            protected void onBindViewHolder(@NonNull FirebaseCocktailViewHolder firebaseCocktailViewHolder, int position, @NonNull Cocktail cocktail) {
            firebaseCocktailViewHolder.bindCocktail(cocktail);
            }

            @NonNull
            @Override
            public FirebaseCocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cocktail_list_item_drag, parent, false);
                return new FirebaseCocktailViewHolder(view);
            }
        };*/
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
        mRecyclerView.setHasFixedSize(true);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }
    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        mItemTouchHelper.startDrag(viewHolder);
    }
}
