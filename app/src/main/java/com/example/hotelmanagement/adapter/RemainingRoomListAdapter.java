package com.example.hotelmanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmanagement.R;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.format.MyFormat;

import java.util.List;

public class RemainingRoomListAdapter extends RecyclerView.Adapter<RemainingRoomListAdapter.RemainingRoomListViewHolder>{

    public interface IClickMoreButton {
        void showBottomDialog(Room room);
    }
    private List<Room> roomList;
    private IClickMoreButton iClickMoreButton;

    public RemainingRoomListAdapter(IClickMoreButton iClickMoreButton) {
        this.iClickMoreButton = iClickMoreButton;
    }

    public void setData(List<Room> roomList) {
        this.roomList = roomList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RemainingRoomListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_remaining_room_list,parent,false);
        return new RemainingRoomListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemainingRoomListViewHolder holder, int position) {
        Room room = roomList.get(position);
        if (room == null) {
            return;
        }
        holder.tvRoomNumberRemaining.setText(room.getNumber());
        holder.tvRoomTypeRemaining.setText(room.getType() == 1 ? "Single" : "Double");
        holder.tvRoomRateRemaining.setText(MyFormat.VND(room.getRate()));
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

    public class RemainingRoomListViewHolder extends RecyclerView.ViewHolder {

        TextView tvRoomNumberRemaining;
        TextView tvRoomTypeRemaining;
        TextView tvRoomRateRemaining;
        ImageView ivMore;

        public RemainingRoomListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomNumberRemaining = itemView.findViewById(R.id.tv_room_number_remaining);
            tvRoomTypeRemaining = itemView.findViewById(R.id.tv_room_type_remaining);
            tvRoomRateRemaining = itemView.findViewById(R.id.tv_room_rate_remaining);
            ivMore = itemView.findViewById(R.id.iv_more);
        }
    }
}
