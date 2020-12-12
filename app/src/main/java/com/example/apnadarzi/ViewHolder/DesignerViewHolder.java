package com.example.apnadarzi.ViewHolder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apnadarzi.Interface.ItemClickListner;
import com.example.apnadarzi.My_Order;
import com.example.apnadarzi.R;


public class DesignerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtDesignerName;
    public RatingBar txtDesignerRating;
    public ImageView imageView;
    public ItemClickListner listner;
    public DesignerViewHolder(View itemView)
    {
        super(itemView);


        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtDesignerName = (TextView) itemView.findViewById(R.id.designer_name);
        txtDesignerRating = (RatingBar) itemView.findViewById(R.id.d_rating);
       // txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
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
