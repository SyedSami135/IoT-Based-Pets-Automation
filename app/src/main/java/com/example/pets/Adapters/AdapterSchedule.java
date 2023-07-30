package com.example.pets.Adapters;





import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pets.R;

import java.util.List;

public class AdapterSchedule extends RecyclerView.Adapter<AdapterSchedule.ViewHolder> {

    private static final String TAG = "RecyclerAdapter";
    List<String> list;

    public AdapterSchedule(List<String> list) {

        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.schedule, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvtime.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tvtime;
        ImageButton del;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvtime=itemView.findViewById(R.id.timeTextView);
            del=itemView.findViewById(R.id.deleteTime);
            itemView.setOnClickListener(this);
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });




        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), list.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    }
}
















