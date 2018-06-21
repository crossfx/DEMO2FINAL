package com.example.schen.camera.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.schen.camera.R;
import com.squareup.picasso.Picasso;


public class ViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public ViewHolder(View itemView){
        super(itemView);

        mView = itemView;

    }
public void setDetails(Context ctx, String title,String description, String image){

    TextView mTitleView=mView.findViewById(R.id.rtitle);
    TextView mDescpView=mView.findViewById(R.id.rdescp);
    ImageView mImageView=mView.findViewById(R.id.rIV);

    mTitleView.setText(title);
    mDescpView.setText(description);
    Picasso.get().load(image).into(mImageView);


}


}
