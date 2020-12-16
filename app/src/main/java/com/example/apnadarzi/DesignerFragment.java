package com.example.apnadarzi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnadarzi.Model.Designer;
import com.example.apnadarzi.ViewHolder.DesignerViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DesignerFragment extends Fragment {

    private DatabaseReference ProductsRef;

    private RecyclerView rv;
   // private static String[] designername={"Amit","Atul","Akash","Nitin","Gulam"};
    //private static String[] rating={"4","3","3.5","4.2","3.4"};


    public static DesignerFragment newInstance()
    {
        return new DesignerFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      //  setHasOptionsMenu(true);
        View rootView=inflater.inflate(R.layout.fragment_designer,null);
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Designer");
       RatingBar simpleRatingBar = rootView.findViewById(R.id.d_rating); // initiate a rating bar
        //Float ratingNumber = simpleRatingBar.getRating();

        //REFERENCE
        rv = rootView.findViewById(R.id.recycler_menu);

        //LAYOUT MANAGER
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        //ADAPTER
        //rv.setAdapter(new MyAdapter(getActivity(), designername));
        // rv.setAdapter(new MyAdapter(getActivity(),rating));

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getLifecycle().addObserver(new TimberLogger(this));
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Designer> options =
                new FirebaseRecyclerOptions.Builder<Designer>()
                        .setQuery(ProductsRef, Designer.class)
                        .build();

        FirebaseRecyclerAdapter<Designer, DesignerViewHolder> adapter =
                new FirebaseRecyclerAdapter<Designer, DesignerViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull DesignerViewHolder holder, int position, @NonNull final Designer model)
                    {
                        holder.txtDesignerName.setText(model.getD_name());
                        holder.txtDesignerRating.setRating(model.getD_rating());
                    //    holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                       Picasso.get().load(model.getD_image()).into(holder.imageView);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                              //  Intent intent =new Intent(HomeActivity.this,ProductDetailsActivity.class);
                              //  intent.putExtra("pid",model.getPid());
                               // startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public DesignerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_design_layout, parent, false);
                        DesignerViewHolder holder = new DesignerViewHolder(view);
                        return holder;
                    }
                };
        rv.setAdapter(adapter);
        adapter.startListening();
    }

}