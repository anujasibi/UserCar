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

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.MyViewHolder> {
    private Context context;
    //SessionManager sessionManager=new SessionManager(context);


    // private int lastSelectedPosition = -1;

    private LayoutInflater inflater;

    private ArrayList<RecyclerPojo>recyclerPojos;


    // private RecyclerPojo[] recyclerPojos;

    // RecyclerView recyclerView;
    public SourceAdapter(Context ctx, ArrayList<RecyclerPojo> recyclerPojos){

        inflater = LayoutInflater.from(ctx);
        this.recyclerPojos = recyclerPojos;
    }

//     public RecyclerAdapter(Context ctx, ArrayList<RecyclerPojo> recyclerPojos){
//       this.ctx=ctx;
//
//     inflater = LayoutInflater.from(ctx);
//     this.recyclerPojos = recyclerPojos;
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.car_row, parent, false);
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
        Picasso.with(context).load(recyclerPojos.get(position).getImage()).into(holder.image);
        holder.name.setText(recyclerPojos.get(position).getName());
        // holder.rat.setText(recyclerPojos.get(position).getRat());
        holder.distance.setText(recyclerPojos.get(position).getDistance());



        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,choosedestination.class);
                intent.putExtra("Car",holder.name.getText());
                intent.putExtra("Name",holder.distance.getText());
                intent.putExtra("image",recyclerPojos.get(position).getImage());
                context.startActivity(intent);
                //   context.startActivity(new Intent(context,cardetails.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return recyclerPojos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView image;
        TextView distance;
        TextView rat;
        TextView book;

        public MyViewHolder(View itemView) {
            super(itemView);

            rat=(TextView)itemView.findViewById(R.id.rat);
            name=(TextView)itemView.findViewById(R.id.name);
            image=(ImageView)itemView.findViewById(R.id.imk);
            distance=(TextView)itemView.findViewById(R.id.phone);
            book=itemView.findViewById(R.id.bo);








        }
    }
}
