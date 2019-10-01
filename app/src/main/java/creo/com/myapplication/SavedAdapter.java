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

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.MyViewHolder> {
    private Context context;
    //SessionManager sessionManager=new SessionManager(context);


    // private int lastSelectedPosition = -1;

    private LayoutInflater inflater;

    private ArrayList<SavedPojo>savedPojos;


    // private RecyclerPojo[] recyclerPojos;

    // RecyclerView recyclerView;
    public SavedAdapter(Context ctx, ArrayList<SavedPojo> savedPojos){

        inflater = LayoutInflater.from(ctx);
        this.savedPojos = savedPojos;
    }

//     public RecyclerAdapter(Context ctx, ArrayList<RecyclerPojo> recyclerPojos){
//       this.ctx=ctx;
//
//     inflater = LayoutInflater.from(ctx);
//     this.recyclerPojos = recyclerPojos;
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.saved_row, parent, false);
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

        holder.name.setText(savedPojos.get(position).getName());
        // holder.rat.setText(recyclerPojos.get(position).getRat());
      //  holder.lat.setText(savedPojos.get(position).getLatitude());
      //  holder.lat.setText(savedPojos.get(position).getLongitude());


    }

    @Override
    public int getItemCount() {
        return savedPojos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;

        TextView lat;
        TextView lon;

        public MyViewHolder(View itemView) {
            super(itemView);

            name=(TextView)itemView.findViewById(R.id.name);











        }
    }
}
