package com.waracle.androidtest.list;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waracle.androidtest.model.cache.ImageCache;
import com.waracle.androidtest.R;
import com.waracle.androidtest.model.Cake;

import java.util.List;

/**
 * Created by Ruairi on 12/02/2017.
 */

public class CakeAdapter extends RecyclerView.Adapter<CakeViewHolder> {
    public static final String TAG = CakeAdapter.class.getSimpleName();
    private List<Cake> cakeList;
    private ImageCache imageCache;

    public CakeAdapter(List<Cake> cakeList) {
        this.cakeList = cakeList;
//        imageCache = ImageCache.getInstance();
    }

    public void populate (List<Cake> cakes){
        if (cakes == null){
            return;
        }
        cakeList.clear();
        cakeList.addAll(cakes);
        notifyDataSetChanged();
        Log.d(TAG, "populate: Adapter List contains: " + cakeList.toString());
    }

    // Called when RCV first created - this creates an instance of the viewholder
    @Override
    public CakeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cake_list_item, viewGroup, false);
        return new CakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CakeViewHolder holder, int position) {
        // Need to get current position from the data list that was passed to the view populate method
        Cake cake = cakeList.get(position);
        holder.cakeName.setText(cake.getTitle());
        holder.cakeDescription.setText(cake.getDesc());
        Bitmap bitmap = ImageCache.getInstance().getCache().get(cake.getTitle());
        if(bitmap != null){
            holder.cakePhoto.setImageBitmap(bitmap);
        }
        holder.cakePhoto.setImageBitmap(cake.getBitmap());

    }

    @Override
    public int getItemCount() {
        return cakeList.size();
    }
}
