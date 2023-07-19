package com.example.hotelmanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmanagement.R;
import com.example.hotelmanagement.entity.Guest;

import java.util.List;

public class GuestBookingListAdapter extends RecyclerView.Adapter<GuestBookingListAdapter.GuestBookingListViewHolder> {

    private IClickMoreButton iClickMoreButton;

    private List<Guest> guestList;

    public GuestBookingListAdapter(IClickMoreButton iClickMoreButton) {
        this.iClickMoreButton = iClickMoreButton;
    }

    public void setData(List<Guest> guestList) {
        this.guestList = guestList;
        notifyDataSetChanged();
    }

    public interface IClickMoreButton {
        void showBottomDialog(Guest guest);
    }

    @NonNull
    @Override
    public GuestBookingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest_booking_list,parent,false);
        return new GuestBookingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestBookingListViewHolder holder, int position) {
        Guest guest = guestList.get(position);
        if (guest == null) {
            return;
        }
//        String[] time = guest.getDateIn().split("\\s");
//        String newTime = time[1];
        holder.tvTime.setText(guest.getDateIn());
        holder.tvGuestRoom.setText(guest.getRoom());
        holder.tvGuestName.setText(guest.getName());
        holder.tvGuestPhone.setText(guest.getPhone());
        holder.tvGuestIdentify.setText(guest.getIdentify());
        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickMoreButton.showBottomDialog(guest);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (guestList != null) {
            return guestList.size();
        }
        return 0;
    }

    public class GuestBookingListViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime;
        TextView tvGuestRoom;
        TextView tvGuestName;
        TextView tvGuestPhone;
        TextView tvGuestIdentify;
        ImageView ivMore;

        public GuestBookingListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvGuestRoom = itemView.findViewById(R.id.tv_guest_room);
            tvGuestName = itemView.findViewById(R.id.tv_guest_name);
            tvGuestPhone = itemView.findViewById(R.id.tv_guest_phone);
            tvGuestIdentify = itemView.findViewById(R.id.tv_guest_identify);
            ivMore = itemView.findViewById(R.id.iv_more);
        }
    }

}
