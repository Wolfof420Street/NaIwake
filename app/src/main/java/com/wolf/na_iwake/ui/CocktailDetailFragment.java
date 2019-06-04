package com.wolf.na_iwake.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.wolf.na_iwake.Constants;
import com.wolf.na_iwake.R;
import com.wolf.na_iwake.models.Cocktail;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CocktailDetailFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.cocktailDetailNameTextView)
    TextView mCocktailLabel;
    /* @BindView(R.id.cocktailNameTextView) TextView mCocktailNameLabel;*/
    @BindView(R.id.websiteTextView)
    TextView mWebsiteLabel;
    @BindView(R.id.cocktailImageView)
    ImageView mCocktailImageLabel;
    @BindView(R.id.glassTextView)
    TextView mGlassLabel;
    @BindView(R.id.ingredientsTextView)
    TextView mIngredientsLabel;
    @BindView(R.id.instructionsTextView)
    TextView mInstructionsLabel;
    @BindView(R.id.saveCocktailButton)
    Button mSaveCocktailButton;

    private Cocktail mCocktail;
    private ArrayList<Cocktail> mCocktailss;
    private int mPosition;

    public static CocktailDetailFragment newInstance(ArrayList<Cocktail> cocktails, Integer position) {
        CocktailDetailFragment cocktailDetailFragment = new CocktailDetailFragment();
        Bundle args = new Bundle();

        args.putParcelable(Constants.EXTRA_KEY_COCKTAILS, Parcels.wrap(cocktails));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        cocktailDetailFragment.setArguments(args);
        return cocktailDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      mCocktail = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_COCKTAILS));
      mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
      mCocktail= mCocktailss.get(mPosition);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cocktail_detail, container, false);
        /*return inflater.inflate(R.layout.fragment_cocktail_detail, container, false);*/
        ButterKnife.bind(this, view);


        Picasso.get().load(mCocktail.getDrinkThumb()).into(mCocktailImageLabel);

        mCocktailLabel.setText(mCocktail.getDrink());

        mWebsiteLabel.setOnClickListener(this);

        mSaveCocktailButton.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mWebsiteLabel) {

            Uri webpage = Uri.parse("https://www.thecocktaildb.com");
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    webpage);
            startActivity(webIntent);
        }
        if (v == mSaveCocktailButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference cocktailRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_COCKTAILS)
                    .child(uid);

            DatabaseReference pushRef = cocktailRef.push();
            String pushId = pushRef.getKey();
            mCocktail.setPushId(pushId);
            pushRef.setValue(mCocktail);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }

}