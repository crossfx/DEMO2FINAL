package com.example.schen.camera.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.schen.camera.R;
import com.squareup.picasso.Callback;
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
    final ProgressBar progressBar2 = mView.findViewById(R.id.progress);

    mTitleView.setText(title);
    mDescpView.setText(description);
    Picasso.get().load(image).into(mImageView, new Callback() {
        @Override
        public void onSuccess() {

            progressBar2.setVisibility(View.GONE);
        }

        @Override
        public void onError(Exception e) {

        }
    });


}


}
