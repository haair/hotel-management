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

public class BookedRoomListAdapter extends RecyclerView.Adapter<BookedRoomListAdapter.BookedRoomListViewHolder>{

    public interface IClickMoreButton {
        void showBottomDialog(Room room);
    }
    private List<Room> roomList;
    private IClickMoreButton iClickMoreButton;

    public BookedRoomListAdapter(IClickMoreButton iClickMoreButton) {
        this.iClickMoreButton = iClickMoreButton;
    }

    public void setData(List<Room> roomList) {
        this.roomList = roomList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookedRoomListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booked_room_list,parent,false);
        return new BookedRoomListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookedRoomListAdapter.BookedRoomListViewHolder holder, int position) {
        Room room = roomList.get(position);
        if (room == null) {
            return;
        }
        holder.tvRoomNumberBooked.setText(room.getNumber());
        holder.tvRoomTypeBooked.setText(room.getType() == 1 ? "Single" : "Double");
        holder.tvRoomRateBooked.setText(MyFormat.VND(room.getRate()));
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

    public class BookedRoomListViewHolder extends RecyclerView.ViewHolder {

        TextView tvRoomNumberBooked;
        TextView tvRoomTypeBooked;
        TextView tvRoomRateBooked;
        ImageView ivMore;

        public BookedRoomListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomNumberBooked = itemView.findViewById(R.id.tv_room_number_booked);
            tvRoomTypeBooked = itemView.findViewById(R.id.tv_room_type_booked);
            tvRoomRateBooked = itemView.findViewById(R.id.tv_room_rate_booked);
            ivMore = itemView.findViewById(R.id.iv_more);
        }
    }
}
