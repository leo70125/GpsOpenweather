package com.example.gpsopenweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OpenweatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String TAG = getClass().getSimpleName();
    private ArrayList<Detail> details;
    private Context mcontext ;
    private OnItemClickListener mlistener;


    public OpenweatherAdapter(Context context,ArrayList<Detail> details,OnItemClickListener listener ){
        this.mcontext = context;
        this.details = details ;
        this.mlistener = listener;

    }
    public interface OnItemClickListener{
        void onClick(int pos);
    }

    class weatherViewHolder extends RecyclerView.ViewHolder{
        private TextView ondate;
        private TextView ondescription;
        private TextView ontemp;
        private ImageView icon;
        public weatherViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.imv);
            ondate = itemView.findViewById(R.id.TV_ondate);
            ondescription = itemView.findViewById(R.id.TV_description);
            ontemp = itemView.findViewById(R.id.TV_temp);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
    {return new weatherViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.layout_linear_item,parent,false)); }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position)
    {
    {((weatherViewHolder)holder).ondate.setText(details.get(position).getDt());
        ((weatherViewHolder)holder).ondescription.setText(details.get(position).getDescription());
        ((weatherViewHolder)holder).ontemp.setText(details.get(position).getTemp());
        Detail  currentItem = details.get(position);
        String im = currentItem.getimurl();
        Picasso.with(mcontext).load(im).fit().centerInside().into(((weatherViewHolder) holder).icon);
    }


    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mlistener.onClick(position);
        }
    });

    }



    @Override
    public int getItemCount() {
        return details.size();
    }




}
