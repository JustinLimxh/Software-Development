package com.allcodingtutorials.softwaredev;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.NotificationViewHolder> {

    private List<NotificationsClass> notificationList;

    public notificationAdapter(List<NotificationsClass> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notificationitem, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationsClass notification = notificationList.get(position);
        holder.bind(notification);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    // Add this method to update the data in the adapter
    public void setNotificationList(List<NotificationsClass> notificationList) {
        this.notificationList = notificationList;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView orderIdTextView;
        private TextView orderDescTextView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            orderDescTextView = itemView.findViewById(R.id.orderDescTextView);
        }

        public void bind(NotificationsClass notification) {
            orderIdTextView.setText("Order ID: " + notification.getOrderId());
            orderDescTextView.setText("Order Description: " + notification.getOrderDescription());
        }
    }
}

