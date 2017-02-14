package com.ramu.simpletodo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ramu on 2/12/2017.
 */

public class RecylerItemAdapter extends RecyclerView.Adapter<RecylerItemAdapter.MyViewHolder> {


    DatabaseHandler mdatabaseHandler;
    List<TodoItemModel> mtodoItemModelList = new ArrayList();
    Context mContext;
    MainActivity mainActivity;

    RecylerItemAdapter(Context context, DatabaseHandler t_handler){
        this.mContext = context;
        this.mdatabaseHandler = t_handler;
        this.mtodoItemModelList = mdatabaseHandler.getAllTodoItems();
        mainActivity = (MainActivity) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TodoItemModel todoItem = mtodoItemModelList.get(position);
        holder.titleTv.setText(todoItem.getTitle());

        // formatting the date
        String date_str = formateDateFromstring("MM/dd/yyyy", "MMM dd, yyyy", todoItem.getDate());
        holder.dateTv.setText(date_str);
        holder.timeTv.setText(todoItem.getTime());

        //changing the text view color as per the priority.
        holder.priorityTv.setText("\u2022"+" "+todoItem.getPriority());

        if(todoItem.getPriority().equals("Low"))
            holder.priorityTv.setTextColor(Color.parseColor("#00FF00"));
        else if(todoItem.getPriority().equals("Medium"))
            holder.priorityTv.setTextColor(Color.parseColor("#FFA500"));
        else if(todoItem.getPriority().equals("High"))
            holder.priorityTv.setTextColor(Color.parseColor("#FF0000"));

        holder.statusTv.setText("\u2022"+" "+todoItem.getStatus());
    }

    @Override
    public int getItemCount() {
        return mtodoItemModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleTv, dateTv, statusTv, priorityTv, timeTv;

        public MyViewHolder(View itemView) {
            super(itemView);

            titleTv = (TextView) itemView.findViewById(R.id.rcv_title_tv);


            dateTv = (TextView) itemView.findViewById(R.id.rcv_date_tv);
            statusTv = (TextView) itemView.findViewById(R.id.rcv_statu_tv);
            priorityTv = (TextView) itemView.findViewById(R.id.rcv_priority_tv);
            timeTv = (TextView) itemView.findViewById(R.id.rcv_time_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.callFragmentCreateTodo(getAdapterPosition());
                }
            });
        }
    }

    public void addToAdapter(TodoItemModel todoItem){
        mtodoItemModelList.add(0,todoItem);
        notifyItemInserted(0);
    }

    public void refreshItems(){
        int length = mtodoItemModelList.size();
    //     for(int i=0;i<length;i++)
    //     mtodoItemModelList.remove(i);

        mtodoItemModelList = mdatabaseHandler.getAllTodoItems();
        notifyDataSetChanged();

    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            Log.e("error: ", "ParseException - dateFormat");
        }
        return outputDate;
    }
}
