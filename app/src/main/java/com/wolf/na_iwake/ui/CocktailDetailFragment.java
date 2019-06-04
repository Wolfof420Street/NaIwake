package com.wolf.na_iwake.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wolf.na_iwake.Constants;
import com.wolf.na_iwake.R;
import com.wolf.na_iwake.models.Cocktail;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wolf.na_iwake.ui.FirebaseCocktailViewHolder.decodeFromFirebaseBase64;

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
    private static final int REQUEST_IMAGE_CAPTURE = 111;
    private String mSource;

    public static CocktailDetailFragment newInstance(ArrayList<Cocktail> cocktails, Integer position, String source) {
        CocktailDetailFragment cocktailDetailFragment = new CocktailDetailFragment();
        Bundle args = new Bundle();

        args.putParcelable(Constants.EXTRA_KEY_COCKTAILS, Parcels.wrap(cocktails));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        args.putString(Constants.KEY_SOURCE, source);
        cocktailDetailFragment.setArguments(args);
        return cocktailDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      mCocktail = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_COCKTAILS));
      mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
      mCocktail= mCocktailss.get(mPosition);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cocktail_detail, container, false);
        /*return inflater.inflate(R.layout.fragment_cocktail_detail, container, false);*/
        ButterKnife.bind(this, view);
        if (!mCocktail.getDrinkThumb().contains("http")) {
            try {
                Bitmap imageBitmap = decodeFromFirebaseBase64(mCocktail.getDrinkThumb());
                mCocktailImageLabel.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            Picasso.get().load(mCocktail.getDrinkThumb()).into(mCocktailImageLabel);

            mCocktailLabel.setText(mCocktail.getDrink());

            mWebsiteLabel.setOnClickListener(this);

            mSaveCocktailButton.setOnClickListener(this);

        }
            return view;
        }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mSource.equals(Constants.SOURCE_SAVED)) {
            inflater.inflate(R.menu.menu_photo, menu);
        } else {
            inflater.inflate(R.menu.main_menu, menu);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_photo:
                onLaunchCamera();
            default:
                break;
        }
        return false;
    }
    public void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mCocktailImageLabel.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_COCKTAILS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mCocktail.getPushId())
                .child("imageUrl");
        ref.setValue(imageEncoded);
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