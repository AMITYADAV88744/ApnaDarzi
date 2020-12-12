package com.example.apnadarzi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.example.apnadarzi.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.firebase.ui.storage.images.FirebaseImageLoader;


import java.util.HashMap;


import javax.sql.DataSource;

import io.paperdb.Paper;

import static android.app.Activity.RESULT_OK;


public class AccountFragment extends Fragment {

    private DatabaseReference parentDbName;
    private TextView profile_name, my_order,upload_design;
    private ImageView profile_image, upload_image;
    private Button logout;


    private static final int GalleryPick = 1;
    private Uri ImageUri;
    public String  downloadImageUrl;
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
        profile_name = (TextView) view.findViewById(R.id.pofile_name);
        my_order = (TextView) view.findViewById(R.id.profile_order);
        upload_design = (TextView) view.findViewById(R.id.pofile_dupload);

        profile_image = (ImageView) view.findViewById(R.id.profile_image);
        logout = (Button) view.findViewById(R.id.sign_out);
        upload_image = (ImageView) view.findViewById(R.id.photo_upload);
        loadingBar = new ProgressDialog(getContext());
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);


        // CircleImageView profile_image = view.findViewById(R.id.profile_image);
       profile_name.setText(Prevalent.currentOnlineUser.getName());
       Picasso.get().load(String.valueOf(ProfileImagesRef)).placeholder(R.drawable.applogo).into(profile_image);

        Glide.with(this)
                .load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.applogo)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                })
                .into(profile_image);



      //  Glide.with(this /* context */)
        //        .load(ProfileImagesRef)
          //      .into(profile_image);


// Load the image using Glide
  //      Glide.with(this ).using(new FirebaseImageLoader()).load(ProfileImagesRef).into(profile_image);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
               String mName = profile_name.getText().toString();

                intent.putExtra("d_name",mName);

                startActivity(intent);
               // finish();

            }
        });
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload_image.setVisibility(View.VISIBLE);
                OpenGallery();
            }
        });

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
   /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getLifecycle().addObserver(new TimberLogger(this));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.message, menu);
    }

    */

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
}
