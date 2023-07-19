package com.example.hotelmanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.hotelmanagement.R;
import com.example.hotelmanagement.entity.ServiceItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private List<ServiceItem> serviceItems;
    public void setData(List<ServiceItem> serviceItems) {
        this.serviceItems = serviceItems;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        ServiceItem serviceItem = serviceItems.get(position);
        if (serviceItem == null) {
            return;
        }
        holder.circleImageView.setImageResource(serviceItem.getResourceId());
        holder.tvName.setText(serviceItem.getName());
        holder.tvDes.setText(serviceItem.getDes());
    }

    @Override
    public int getItemCount() {
        if (serviceItems != null) {
            return  serviceItems.size();
        }
        return 0;
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageView;
        private TextView tvName;
        private TextView tvDes;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_service_name);
            circleImageView = itemView.findViewById(R.id.img_avt);
            tvDes = itemView.findViewById(R.id.tv_service_des);
        }
    }
}
