package com.wolf.na_iwake.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wolf.na_iwake.R;
import com.wolf.na_iwake.adapters.FriendlyMessage;
import com.wolf.na_iwake.adapters.MessageAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatRoomActivity extends AppCompatActivity {

    private static final String TAG = "Chatroom";

    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    public static final int RC_SIGN_IN = 1;
    private static final int RC_PHOTO_PICKER = 2;

    @BindView(R.id.messageListView)
    ListView mMessageListView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.photoPickerButton)
    ImageButton mPhotoPickerButton;
    @BindView(R.id.messageEditText)
    EditText mMessageEditText;
    @BindView(R.id.sendButton)
    Button mSendButton;
    private MessageAdapter mMessageAdapter;


    private String mUsername;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFireBaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ButterKnife.bind(this);

        mUsername = ANONYMOUS;

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFireBaseAuth = FirebaseAuth.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();

        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("messages");
        mChatPhotosStorageReference = mFirebaseStorage.getReference().child("chat_photos");


        List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);

        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });



        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                FriendlyMessage friendlyMessage = new FriendlyMessage(mMessageEditText.getText().toString(), mUsername, null);
                mMessagesDatabaseReference.push().setValue(friendlyMessage);
                // Clear input box
                mMessageEditText.setText("");
            }
        });



        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    onSignedInInitialize(user.getDisplayName());
                    Toast.makeText(ChatRoomActivity.this, "Welcome to the Chatroom, Na iwake!", Toast.LENGTH_SHORT).show();
                } else {
                    onSignedOutCleanUp();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                           /* new AuthUI.IdpConfig.FacebookBuilder().build(),
                                            new AuthUI.IdpConfig.TwitterBuilder().build(),
                                            new AuthUI.IdpConfig.GitHubBuilder().build(),*/
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            /*new AuthUI.IdpConfig.PhoneBuilder().build(),*/
                                            new AuthUI.IdpConfig.AnonymousBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFireBaseAuth.removeAuthStateListener(mAuthStateListener);
        if (mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
        }
        detachDataBaseListener();
        mMessageAdapter.clear();
    }
    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign In Cancelled", Toast.LENGTH_SHORT).show();
                finish();

            } else if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();

                StorageReference photoRef = mChatPhotosStorageReference.child(selectedImageUri.getLastPathSegment());

                photoRef.putFile(selectedImageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                        FriendlyMessage friendlyMessage = new FriendlyMessage(null, mUsername, downloadUrl.toString());
                        mMessagesDatabaseReference.push().setValue(friendlyMessage);
                    }
                });
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mFireBaseAuth.addAuthStateListener(mAuthStateListener);

    }

    private void onSignedInInitialize(String username) {
        mUsername = username;
        attachDataBaseReadListener();


    }

    private void onSignedOutCleanUp() {
        mUsername = ANONYMOUS;
        mMessageAdapter.clear();
        detachDataBaseListener();

    }

    private void attachDataBaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);
                    mMessageAdapter.add(friendlyMessage);
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
            };
            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }
        private void detachDataBaseListener () {
            if (mChildEventListener != null) {
                mMessagesDatabaseReference.removeEventListener(mChildEventListener);
                mChildEventListener = null;
            }
        }
    }


