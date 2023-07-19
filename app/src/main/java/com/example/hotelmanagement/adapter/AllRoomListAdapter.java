package com.example.hotelmanagement.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmanagement.format.MyFormat;
import com.example.hotelmanagement.R;
import com.example.hotelmanagement.entity.Room;

import java.util.List;

public class AllRoomListAdapter extends RecyclerView.Adapter<AllRoomListAdapter.AllRoomListViewHolder> {

    private List<Room> roomList;
    private IClickMoreButton iClickMoreButton;

    public AllRoomListAdapter(IClickMoreButton iClickMoreButton) {
        this.iClickMoreButton = iClickMoreButton;
    }

    public interface IClickMoreButton {
        void showBottomDialog(Room room);
    }

    public void setData(List<Room> roomList) {
        this.roomList = roomList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AllRoomListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_room_list,parent,false);
        return new AllRoomListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllRoomListViewHolder holder, int position) {
        final Room room = roomList.get(position);
        if (room == null) {
            return;
        }
        holder.tvNumber.setText(room.getNumber());
        holder.tvType.setText(room.getType() == 1 ? "Single" : "Double");
        holder.tvRate.setText(MyFormat.VND(room.getRate()));
        if (room.getStatus() == 0) {
            holder.tvStatus.setTextColor(Color.parseColor("#c02626"));
            holder.tvStatus.setText("Not booked");
            holder.ivStatus.setColorFilter(Color.parseColor("#c02626"));
            holder.ivStatus.setImageResource(R.drawable.ic_not_booked);
        }
        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickMoreButton.showBottomDialog(room);
            }
        });
    }
    @Override
    public int getItemCount() {
        if (roomList != null) {
            return roomList.size();
        }
        return 0;
    }

    public static class AllRoomListViewHolder extends RecyclerView.ViewHolder {

        TextView tvNumber;
        TextView tvType;
        TextView tvRate;
        TextView tvStatus;
        ImageView ivStatus;
        ImageView ivMore;

        public AllRoomListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_room_number);
            tvType = itemView.findViewById(R.id.tv_room_type);
            tvRate = itemView.findViewById(R.id.tv_room_rate);
            tvStatus = itemView.findViewById(R.id.tv_room_status);
            ivStatus = itemView.findViewById(R.id.iv_r_status);
            ivMore = itemView.findViewById(R.id.iv_all_room_more);
        }
    }
}
