package com.example.apnadarzi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.apnadarzi.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;


public class AccountFragment extends Fragment {

    private DatabaseReference parentDbName;
    private TextView profile_name, my_order, upload_design, profile_sett;
    private CircleImageView profile_image;
    private Button logout;
    private ProgressBar progressBar;


    private static final int GalleryPick = 1;
    private Uri ImageUri;
    public String downloadImageUrl;
    private StorageReference ProfileImagesRef;
    public ProgressDialog loadingBar;


    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, null);

        ProfileImagesRef = FirebaseStorage.getInstance().getReference().child(" UserProfile");
        parentDbName = FirebaseDatabase.getInstance().getReference().child("Users");
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profile_name = view.findViewById(R.id.pofile_name);
        my_order = view.findViewById(R.id.profile_order);
        upload_design = view.findViewById(R.id.pofile_dupload);

        //profile_image = view.findViewById(R.id.profile_image);
        logout = view.findViewById(R.id.sign_out);
        profile_sett = view.findViewById(R.id.profile_setting);
        loadingBar = new ProgressDialog(getContext());
      //  progressBar = view.findViewById(R.id.progress);


        profile_image = view.findViewById(R.id.profile_image);

        userInfoDisplay(profile_image, profile_name);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        profile_sett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Profile_Update.class);
                startActivity(intent);
            }
        });

        my_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), My_Order.class);
                startActivity(intent);

            }
        });

        upload_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Upload_Design.class);
                String d_name = profile_name.getText().toString();
                intent.putExtra("d_name", d_name);
                startActivity(intent);
                // finish();

            }
        });

      /*  profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload_image.setVisibility(View.VISIBLE);
                OpenGallery();
            }
        });

       */
/*
        //upload image
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                ValidateProductData();//change
            }
        });

    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            profile_image.setImageURI(ImageUri);
        }
    }

    private void ValidateProductData() {
        StoreProductInformation();

    }

    private void StoreProductInformation() {
         loadingBar.setTitle("Upload Profile Image");
         loadingBar.setMessage("Saving Profile");
         loadingBar.setCanceledOnTouchOutside(false);
         loadingBar.show();


        final StorageReference filePath = ProfileImagesRef.child(Prevalent.currentOnlineUser.getPhone() +".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "Profile Successfully Saved", Toast.LENGTH_SHORT);
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();

                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(getActivity(), "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

 */

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getLifecycle().addObserver(new TimberLogger(this));
    }

   /* @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.message, menu);
    }

    */


/*
    private void SaveProductInfoToDatabase()
    {
        HashMap<String, Object> userdataMap = new HashMap<>();

        userdataMap.put("image", downloadImageUrl);

        parentDbName.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                           loadingBar.dismiss();
                            profile_image.setImageURI(ImageUri);
                            Toast.makeText(getActivity(), "Profile is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                           loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

 */

    private void userInfoDisplay(final ImageView profile_image, final TextView profile_name) {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("image").exists()) {
                        String image = dataSnapshot.child("image").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.profile).into(profile_image);
                        profile_name.setText(name);
                        //   progressBar.setVisibility(View.INVISIBLE);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
