package com.example.apnadarzi;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnadarzi.Model.Cart;
import com.example.apnadarzi.Prevalent.CartViewHolder;
import com.example.apnadarzi.Prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button NextProcessBtn;
    TextView txtTotalAmount, txtMsg1;
    int overTotalPrice = 0;
    String pid, image;
    private static ArrayList<Cart> carts;
    Integer[] totalprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Cart Detail");

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        NextProcessBtn = findViewById(R.id.next_btn);
        txtTotalAmount = findViewById(R.id.total_price);
        txtMsg1 = findViewById(R.id.msg1);


        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTotalAmount.setText("Total Price = $" + overTotalPrice);
                Intent intent = new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
                intent.putExtra("Total Price", String.valueOf(overTotalPrice));
                intent.putExtra("pid", String.valueOf(pid));
                intent.putExtra("image", String.valueOf(image));

                startActivity(intent);
            }
        });

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
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child("User view")
                                .child(Prevalent.currentOnlineUser.getPhone()).child("Products"), Cart.class).build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, final Cart model) {
                holder.txtProductQuantity.setText("Quantity = " + model.getQuantity());
                holder.txtProductPrice.setText("Price = " + model.getPrice());
                holder.txtProductName.setText(model.getPname());
                pid = model.getPid();
                image = model.getImage();

                carts = new ArrayList<Cart>();
                for (int i = 0; i < getSnapshots().size(); i++) {
                    carts.add(new Cart(model.getPrice() * model.getQuantity())
                            // MyData.drawableArray[i]
                    );

                }
                totalprice = new Integer[carts.size()];
                for (int i = 0; i < totalprice.length; i++) {
                    overTotalPrice += totalprice[i];

                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence[] options = new CharSequence[]
                                {
                                        "Add",
                                        "Remove"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options: ");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
                                    final HashMap<String, Object> cartMap = new HashMap<>();

                                    cartMap.put("quantity", String.valueOf(Math.addExact(Integer.parseInt(String.valueOf(model.getQuantity())), 1)));

                                    cartListRef.child("User view").child(Prevalent.currentOnlineUser.getPhone()).child("Products").child(String.valueOf(model.getPid())).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                cartListRef.child("Admin view").child(Prevalent.currentOnlineUser.getPhone())
                                                        .child("Products").child(String.valueOf(model.getPid()))
                                                        .updateChildren(cartMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(CartActivity.this, "Added to cart List", Toast.LENGTH_SHORT).show();
                                                                    onStart();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                                }
                                if (i == 1) {
                                    if (Objects.equals(model.getQuantity(), 1)) {      //check quantity of product
                                        cartListRef.child("User view")
                                                .child(Prevalent.currentOnlineUser.getPhone())
                                                .child("Products")                                     //if 1 then delete
                                                .child(String.valueOf(model.getPid()))
                                                .removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(CartActivity.this, "Item removed Successfully.", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(CartActivity.this, MainActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                });
                                    } else {

                                        // if more than 1 then remove 1 quantity
                                        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
                                        final HashMap<String, Object> cartMap = new HashMap<>();

                                        cartMap.put("quantity", String.valueOf(Math.subtractExact(Integer.parseInt(String.valueOf(model.getQuantity())), 1)));

                                        cartListRef.child("User view").child(Prevalent.currentOnlineUser.getPhone()).child("Products").child(String.valueOf(model.getPid())).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    cartListRef.child("Admin view").child(Prevalent.currentOnlineUser.getPhone())
                                                            .child("Products").child(String.valueOf(model.getPid()))
                                                            .updateChildren(cartMap)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Toast.makeText(CartActivity.this, "Added to cart List", Toast.LENGTH_SHORT).show();
                                                                        onStart();
                                                                    }
                                                                }
                                                            });
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }


        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}
