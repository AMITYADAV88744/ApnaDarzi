package com.example.apnadarzi.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apnadarzi.R;


public class ViewHolder extends RecyclerView.ViewHolder {

    View mView;
    private ViewHolder.ClickListener mClickListener;

    public ViewHolder(View itemView) {
        super(itemView);

        mView = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
        /*/item long click
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
        });
*/
    }

    //set details to recycler view row
    public void setDetails(Context ctx, String ag_name, String ag_address, String ag_image, String ag_phone) {
        //Views
        TextView mAgNameTv = mView.findViewById(R.id.order_name);
        TextView mAgAddressTv = mView.findViewById(R.id.order_price);
        ImageView mImageIv = mView.findViewById(R.id.product_image);
       // ImageView mImageIv2 = mView.findViewById(R.id.locationmark);
        TextView mPhoneTv = mView.findViewById(R.id.seller_name);


        //set data to views
        mAgNameTv.setText(ag_name);
        mAgAddressTv.setText(ag_address);
        mPhoneTv.setText(ag_phone);

        //  mPhoneTv.setText(Math.toIntExact((ag_phone)));
        //  Picasso.get().load(image).into(mImageIv);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }

    //interface to send callbacks
    public interface ClickListener {
        void onItemClick(View view, int position);

    }
}