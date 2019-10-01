package creo.com.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SlidingAdapter extends RecyclerView.Adapter<SlidingAdapter.MyViewHolder> {
    private Context ctx;

    private int lastSelectedPosition = -1;

    private LayoutInflater inflater;

    //   private ArrayList<RecyclerPojo>recyclerPojos;

    private ArrayList<SlidingPojo> slidingPojos;

    // RecyclerView recyclerView;
    public SlidingAdapter(ArrayList<SlidingPojo> slidingPojos, Context context) {
        this.slidingPojos = slidingPojos;
        this.ctx = context;
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
        View listItem = layoutInflater.inflate(R.layout.item_slide, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.name.setText(slidingPojos.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return slidingPojos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;


        public MyViewHolder(View itemView) {
            super(itemView);


            name = (TextView) itemView.findViewById(R.id.tr);


        }
    }
}
