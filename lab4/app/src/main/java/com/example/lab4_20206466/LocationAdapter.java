package com.example.lab4_20206466;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private Context context;
    private List<Location> locationList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Location location);
    }
    public LocationAdapter(Context context, List<Location> locationList, OnItemClickListener listener) {
        this.context = context;
        this.locationList = locationList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location location = locationList.get(position);
        holder.iDTextView.setText(location.getIdLocation());
        holder.nameTextView.setText(location.getName());
        holder.regionTextView.setText(location.getRegion());
        holder.countryTextView.setText(location.getCountry());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(location);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public void setData(List<Location> newList) {
        locationList.clear();
        locationList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView iDTextView, nameTextView, regionTextView, countryTextView;

        public LocationViewHolder(View itemView) {
            super(itemView);
            iDTextView = itemView.findViewById(R.id.tvId);
            nameTextView = itemView.findViewById(R.id.tvName);
            regionTextView = itemView.findViewById(R.id.tvRegion);
            countryTextView = itemView.findViewById(R.id.tvCountry);
        }
    }
}

