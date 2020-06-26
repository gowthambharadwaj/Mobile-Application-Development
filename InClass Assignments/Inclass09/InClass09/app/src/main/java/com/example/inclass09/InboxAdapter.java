package com.example.inclass09;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {
    private final Context context;
    ArrayList<InboxData> mData;

    public InboxAdapter(ArrayList<InboxData> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.use_recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final InboxData inboxData = mData.get(position);
        holder.subject.setText(inboxData.subject);
        holder.date.setText(inboxData.dateTime);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DisplayActivity.class);
                intent.putExtra("inboxData",inboxData);
                view.getContext().startActivity(intent);
            }
        });
//        holder.displayMessage.setText(inboxData.message);
//        holder.displaySender.setText(inboxData.senderName);

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject,date;


        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            relativeLayout = itemView.findViewById(R.id.recycle);
            subject = itemView.findViewById(R.id.subject_mail);
            date = itemView.findViewById(R.id.date);


        }
    }
}
