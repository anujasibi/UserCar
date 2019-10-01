package creo.com.myapplication;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import creo.com.myapplication.utils.SessionManager;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {
    private Context context;
    //SessionManager sessionManager=new SessionManager(context);


    // private int lastSelectedPosition = -1;

    private LayoutInflater inflater;

    private ArrayList<SchedulePojo>schedulePojos;


    // private RecyclerPojo[] recyclerPojos;

    // RecyclerView recyclerView;
    public ScheduleAdapter(Context ctx, ArrayList<SchedulePojo> schedulePojos){

        inflater = LayoutInflater.from(ctx);
        this.schedulePojos = schedulePojos;
    }

//     public RecyclerAdapter(Context ctx, ArrayList<RecyclerPojo> recyclerPojos){
//       this.ctx=ctx;
//
//     inflater = LayoutInflater.from(ctx);
//     this.recyclerPojos = recyclerPojos;
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.scheduled_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        context=parent.getContext();
        return holder;

        /*LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.car_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listItem);
        return viewHolder;*/
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // final RecyclerPojo recyclerPojo = recyclerPojos.get(position);

        holder.date.setText(schedulePojos.get(position).getDate());
        holder.time.setText(schedulePojos.get(position).getTimes());
        holder.source.setText(schedulePojos.get(position).getSources());
        holder.dest.setText(schedulePojos.get(position).getDestination());


    }

    @Override
    public int getItemCount() {
        return schedulePojos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date;

        TextView time;

        TextView source;
        TextView dest;

        public MyViewHolder(View itemView) {
            super(itemView);

            date=(TextView)itemView.findViewById(R.id.date);
            time=(TextView)itemView.findViewById(R.id.time);
            source=(TextView)itemView.findViewById(R.id.source);
            dest=(TextView)itemView.findViewById(R.id.dest);











        }
    }
}
