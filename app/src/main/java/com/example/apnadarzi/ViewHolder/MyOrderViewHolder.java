package com.example.apnadarzi.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.apnadarzi.Interface.ItemClickListner;
import com.example.apnadarzi.R;


public class MyOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtOrderStatus, txtOrderName, txtSellerName,txtOrderPrice;
    public ImageView imageView2;
    public ItemClickListner listner;
    public MyOrderViewHolder(View itemView)
    {
        super(itemView);


        imageView2 = (ImageView) itemView.findViewById(R.id.product_image);
        txtOrderName = (TextView) itemView.findViewById(R.id.order_name);
        txtSellerName = (TextView) itemView.findViewById(R.id.seller_name);
        txtOrderPrice = (TextView) itemView.findViewById(R.id.order_price);
        txtOrderStatus = (TextView) itemView.findViewById(R.id.order_status);

    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
