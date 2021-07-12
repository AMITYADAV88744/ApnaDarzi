package com.example.apnadarzi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeChildFragmentMen extends Fragment implements View.OnClickListener {
    GridView grid;

    ImageView profile_image;
    String pid;

    ArrayList<String> ProductImageUrl = new ArrayList<String>();
    ArrayList<String> ProductTitle = new ArrayList<String>();
    ArrayList<String> ProductPrice = new ArrayList<>();
    ArrayList<String> ProductId = new ArrayList<String>();

    DatabaseReference mdata;
    MyCustomAdapter myCustomAdapter;

    public static HomeChildFragmentMen newInstance(int position, String name) {


        HomeChildFragmentMen fragment = new HomeChildFragmentMen();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
            getLifecycle().addObserver(new TimberLogger(this));
        }


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_child, null);

        grid = rootView.findViewById(R.id.gridviewhome);
        mdata = FirebaseDatabase.getInstance().getReference().child("Products").child("Male");
        LoadDataFromFirebase();
        myCustomAdapter = new MyCustomAdapter(ProductImageUrl, ProductTitle, ProductPrice, ProductId);
        grid.setAdapter(myCustomAdapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), Product_Detail_Men.class);

                intent.putExtra("pid", String.valueOf(ProductId.get(i)));

                startActivity(intent); //start activity

            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profile_image = view.findViewById(R.id.product_image);

    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(getActivity(), Product_Detail_Men.class);
        // intent.putExtra("pid", pid); // put product id
        startActivity(intent);
    }

    //My Custom Adapter for GridView

    public void LoadDataFromFirebase() {
        onStart();
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ProductId.add(ds.child("pid").getValue().toString());
                    ProductTitle.add(ds.child("pname").getValue().toString());
                    ProductImageUrl.add(ds.child("image").getValue().toString());
                    ProductPrice.add(ds.child("price").getValue().toString());


                }
                myCustomAdapter.notifyDataSetChanged();  // THIS WILL NOTIFY YOUR ADAPTER WHEN DATA IS CHANGED
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public class MyCustomAdapter extends BaseAdapter {

        ArrayList<String> ProductImageUrl;
        ArrayList<String> ProductTitle;
        ArrayList<String> ProductPrice;
        ArrayList<String> ProductId;


        MyCustomAdapter(ArrayList<String> RecipeImageUrl, ArrayList<String> ProductTitle, ArrayList<String> ProductPrice, ArrayList<String> ProductId) {
            this.ProductImageUrl = RecipeImageUrl;
            this.ProductTitle = ProductTitle;
            this.ProductPrice = ProductPrice;
            this.ProductId = ProductId;

        }

        @Override
        public int getCount() {
            return this.ProductImageUrl.size();
        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.homeitem, null);

            ImageView imageView = view.findViewById(R.id.product_image);
            TextView textView = view.findViewById(R.id.product_name);
            TextView textView1 = view.findViewById(R.id.product_price);
            TextView textView2 = view.findViewById(R.id.product_id);
            textView2.setText(ProductId.get(position));
            textView1.setText(ProductPrice.get(position));
            textView.setText(ProductTitle.get(position));
            Picasso.get().load(ProductImageUrl.get(position)).into(imageView);

            pid = textView2.getText().toString();
            return view;
        }

    }
}
