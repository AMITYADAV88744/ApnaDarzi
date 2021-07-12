package com.example.apnadarzi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnadarzi.Model.MyOrder;
import com.example.apnadarzi.Prevalent.Prevalent;
import com.example.apnadarzi.ViewHolder.MyOrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class My_Order extends AppCompatActivity {

    private DatabaseReference OrderRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__order);
        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("My Order");


        OrderRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        // Recycler View
        recyclerView = findViewById(R.id.recycler_my_order);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<MyOrder> options =
                new FirebaseRecyclerOptions.Builder<MyOrder>()
                        .setQuery(OrderRef, MyOrder.class)
                        .build();

        FirebaseRecyclerAdapter<MyOrder, MyOrderViewHolder> adapter =
                new FirebaseRecyclerAdapter<MyOrder, MyOrderViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MyOrderViewHolder holder, int position, @NonNull final MyOrder model)
                    {
                        holder.txtOrderName.setText(model.getOrder_no());
                        holder.txtOrderStatus.setText(model.getState());
                        holder.txtOrderPrice.setText("Price = " + model.getTotalAmount() + "$");
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_layout, parent, false);
                        MyOrderViewHolder holder = new MyOrderViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}