package com.example.schen.camera.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.schen.camera.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class EasyAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<MyData> mDataList;


    public EasyAdapter(Context context, List<MyData> data) {

        this.mContext = context;
        this.mDataList = data;
        this.mInflater = LayoutInflater.from(context);

    }

    //Get the hex code for the cell reference, you dont need to care this part.
    @Override
    public long getItemId(int position) {
        return mDataList.get(position).hashCode();
    }

    // Get the total number of cells, which is the same as the data list's size. If you want to make
    // the last cell different like (add post), you can add 1 onto the mDataList.size(). Then you
    // will need to construct the +1 cell by yourself in getView() function.
    @Override
    public int getCount() {
        return mDataList.size();
    }

    //Return the data object for the target cell in selected position.
    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    /**
     * Construct the cell object. This is the place you update the TextView, ImageView, or whatever
     * widgets you placed on the item layout file.
     * @param position index for the cell
     * @param convertView old view to be reused, which is the cell object (item_layout)
     * @param parent Your father Tz. (ListView)
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        //Check if the reused convertView is null or not. if it's null, inflate and create a ViewHolder
        //to define the TextView, ImageView objects in the layout file.
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_listview, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //MyData object contained the information needed for this cell at index 'position'
        MyData targetData = mDataList.get(position);

        //Update the widgets.
        /*viewHolder.imageTitle.setText(targetData.getImageTitle());*/
        viewHolder.imageDes.setText(targetData.getImageDescription());
        Picasso.get().load(new File(targetData.getImageUrl())).into(viewHolder.image);
        return convertView;

    }

    /**
     * Update the data set. Once new data set is saved, call notifyDataSetChanged() to refresh
     * the UI.
     * @param newData
     */
    public void updateData(List<MyData> newData) {
        mDataList = newData;
        notifyDataSetChanged();
    }

    /**
     * Private class only used by the adapter class.
     * This is a helper class to define the exact widgets for the item layout files through using
     * findViewById.
     */
    public class ViewHolder {

        TextView imageTitle;
        TextView imageDes;
        ImageView image;

        /**
         * Pass the View object which is the view inflated from adapter class.
         * @param view
         */
        public ViewHolder(View view) {
            /*imageTitle = view.findViewById(R.id.item_title);*/
            imageDes = view.findViewById(R.id.item_description);
            image = view.findViewById(R.id.annotateimage);
        }
    }

}
