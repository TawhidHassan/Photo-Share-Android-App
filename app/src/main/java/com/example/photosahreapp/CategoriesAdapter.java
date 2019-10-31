package com.example.photosahreapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    Context context;
    List<Categories> categorieslist;

    public CategoriesAdapter(Context context,List<Categories> categorieslist)
    {
       this.context=context;
       this.categorieslist=categorieslist;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_menu,null);

        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder categoriesViewHolder, int i) {

        Categories categories=categorieslist.get(i);
        categoriesViewHolder.itemTitle.setText(categories.getImagetitle());
        categoriesViewHolder.itemimage.setImageDrawable(context.getResources().getDrawable(categories.getImage()));
    }

    @Override
    public int getItemCount() {
        return categorieslist.size();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder{

        ImageView itemimage;
        TextView itemTitle;
        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);

            itemimage=(ImageView) itemView.findViewById(R.id.itemImageId);
            itemTitle=itemView.findViewById(R.id.imgeTitleId);
        }
    }
}
