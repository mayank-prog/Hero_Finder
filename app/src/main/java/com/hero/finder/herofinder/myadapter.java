package com.hero.finder.herofinder;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.History;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder>
{
    FirebaseAuth fAuth;
    FirebaseUser CurrentUser;

    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {
      holder.tv_High.setText(model.getHigh());
      holder.tv_low.setText(model.getLow());
      holder.tv_Open.setText(model.getOpen());
      holder.tv_close.setText(model.getClose());
      holder.c_name.setText(model.getC_name());

                  holder.c_name.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          Intent i = new Intent(holder.c_name.getContext(), History.class);
                          i.putExtra("Lowhis",model.getHigh());
                          i.putExtra("Highhis",model.getLow());
                          i.putExtra("Closehis",model.close);
                          i.setFlags(i.FLAG_ACTIVITY_NEW_TASK);
                          holder.c_name.getContext().startActivity(i);
                      }
                  });
                        holder.tv_Open.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(holder.c_name.getContext(), History.class);
                                i.putExtra("Lowhis",model.getHigh());
                                i.putExtra("Highhis",model.getLow());
                                i.putExtra("Closehis",model.close);
                                i.setFlags(i.FLAG_ACTIVITY_NEW_TASK);
                                holder.c_name.getContext().startActivity(i);
                            }
                        });
                            holder.tv_High.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(holder.c_name.getContext(), History.class);
                                    i.putExtra("Lowhis",model.getHigh());
                                    i.putExtra("Highhis",model.getLow());
                                    i.putExtra("Closehis",model.close);
                                    i.setFlags(i.FLAG_ACTIVITY_NEW_TASK);
                                    holder.c_name.getContext().startActivity(i);
                                }
                            });
                  holder.delete_history.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          fAuth = FirebaseAuth.getInstance();
                          CurrentUser = fAuth.getCurrentUser();
                          String userID = fAuth.getCurrentUser().getUid();
                          try {
                              FirebaseDatabase.getInstance().getReference().child("History").child(userID)
                                      .child(getRef(position).getKey()).removeValue();
                                       notifyDataSetChanged();
                          }
                          catch (Exception e){

                              Toast.makeText(v.getContext(), "Wait for responding", Toast.LENGTH_SHORT).show();
                          }
                      }
                  });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{

        TextView c_name;
        View delete_history;
        TextView tv_Open,tv_High,tv_low,tv_close;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            tv_Open = itemView.findViewById(R.id.tv_Open);
            tv_High = itemView.findViewById(R.id.tv_High);
            tv_low = itemView.findViewById(R.id.tv_low);
            tv_close = itemView.findViewById(R.id.tv_close);
            c_name = itemView.findViewById(R.id.c_name);
            delete_history = itemView.findViewById(R.id.delete_history);

        }
    }
}
