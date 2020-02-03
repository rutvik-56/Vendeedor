package com.realestate.vendeedor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder> {
    private ArrayList<SingleVertical> data = new ArrayList<>();

    public VerticalAdapter(ArrayList<SingleVertical> data) {
        this.data=data ;
    }

    @Override
    public VerticalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_vertical, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(VerticalAdapter.MyViewHolder holder, int position) {
        //holder.image.setImageResource(data.get(position).getImage());
        Glide.with(holder.image.getContext()).load(data.get(position).getImage()).into(holder.image);
        holder.title.setText(data.get(position).getName());
        holder.description.setText(data.get(position).getPrize());
        holder.sqft.setText(data.get(position).getSqft());
        holder.ago.setText(data.get(position).getAgo());
        holder.sr.setText(data.get(position).getSr());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, description,sqft,ago,sr;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(view.getContext(),details.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("i",MainActivity.fori.get(getAdapterPosition())+"");
                    i.putExtra("j",MainActivity.forj.get(getAdapterPosition())+"");
                    view.getContext().startActivity(i);

                }});
            image = (ImageView) itemView.findViewById(R.id.imo);
            title = (TextView) itemView.findViewById(R.id.des1);
            description = (TextView) itemView.findViewById(R.id.des2);
            sqft = (TextView) itemView.findViewById(R.id.des4);
            ago = (TextView) itemView.findViewById(R.id.des3);
            sr=(TextView)itemView.findViewById(R.id.sr);
        }
    }
}