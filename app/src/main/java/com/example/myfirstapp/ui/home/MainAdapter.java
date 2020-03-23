package com.example.myfirstapp.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;

import java.util.ArrayList;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    ArrayList<Exam> Exams;

    public MainAdapter(ArrayList<Exam> examList) {
        this.Exams = examList;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        Log.d("MainAdapter", "onBindViewHolder: called.");
        holder.exam.setText(Exams.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return Exams.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView exam;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            exam = itemView.findViewById(R.id.row_exam);
        }
    }
}
