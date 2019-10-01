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

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyViewHolder> {
    private Context ctx;


    private LayoutInflater inflater;

    //   private ArrayList<RecyclerPojo>recyclerPojos;

    private TripPojo[] tripPojos;

    // RecyclerView recyclerView;
    public TripAdapter(TripPojo[]tripPojos,Context context) {
        this.tripPojos=tripPojos;
        this.ctx=context;
    }


//     public RecyclerAdapter(Context ctx, ArrayList<RecyclerPojo> recyclerPojos){
//       this.ctx=ctx;
//
//     inflater = LayoutInflater.from(ctx);
//     this.recyclerPojos = recyclerPojos;
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //  View view = inflater.inflate(R.layout.choose_address_row, parent, false);
        //  MyViewHolder holder = new MyViewHolder(view);

        // return holder;

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.trip_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final TripPojo tripPojo = tripPojos[position];
        holder.price.setText(tripPojos[position].getPrice());
        holder.date.setText(tripPojos[position].getDate());
        holder.dest.setText(tripPojos[position].getDest());
        holder.source.setText(tripPojos[position].getSource());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctx.startActivity(new Intent(ctx,Tripdetails.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return tripPojos.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

       TextView source,dest,price,date;

       CardView cardView;


        public MyViewHolder(View itemView) {
            super(itemView);


           source=itemView.findViewById(R.id.source);
           dest=itemView.findViewById(R.id.dest);
           price=itemView.findViewById(R.id.name);
           date=itemView.findViewById(R.id.phone);
           cardView=itemView.findViewById(R.id.cardq);






        }
    }
}

