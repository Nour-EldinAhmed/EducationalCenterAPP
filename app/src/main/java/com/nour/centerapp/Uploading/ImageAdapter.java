package com.nour.centerapp.Uploading;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nour.centerapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {


    public Context context;
    public List<Uploading> listImages;
    private onItemClickListener listener;
    public ImageAdapter(Context context,List<Uploading> listImages)
    {
        this.context=context;
        this.listImages=listImages;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.images_items,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Uploading uploadimages=listImages.get(position);
        holder.txtViewUpload.setText(uploadimages.getNameimage());
        Picasso.with(context).
                load(uploadimages.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.image_upload);


    }

    @Override
    public int getItemCount() {
        return listImages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView txtViewUpload;
        public ImageView image_upload;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewUpload=itemView.findViewById(R.id.txt_image_view);
            image_upload=itemView.findViewById(R.id.img_view);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listener!=null) {
                int position = getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION)
                    listener.onItemClick(position);
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
           menu.setHeaderTitle("Select Action");
            MenuItem item_1 = menu.add(Menu.NONE,1,1,"Whet Ever");
            MenuItem item_2 = menu.add(Menu.NONE,2,2,"Delete");
            item_1.setOnMenuItemClickListener(this);
            item_2.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(listener!=null) {
                int position = getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION)
                switch(item.getItemId())
                {
                    case 1:
                        listener.onWheteeverClick(position);
                        return true;
                    case 2:
                        listener.onDeleteClick(position);
                        return true;
                }
            }
            return false;
        }
    }

    public interface onItemClickListener
    {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onWheteeverClick(int position);
    }
    public void setOnItemClickListener(onItemClickListener listener )
    {
       this.listener=listener;
    }

}
