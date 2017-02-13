package com.waracle.androidtest.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.waracle.androidtest.R;

/**
 * Created by Ruairi on 12/02/2017.
 */
public class CakeViewHolder extends RecyclerView.ViewHolder{

    public TextView cakeName, cakeDescription;
    public ImageView cakePhoto;

    public CakeViewHolder(View itemView) {
        super(itemView);
        cakeName =(TextView)itemView.findViewById(R.id.cake_name);
        cakeDescription = (TextView)itemView.findViewById(R.id.cake_description);
        cakePhoto = (ImageView)itemView.findViewById(R.id.cake_image);
    }


}
