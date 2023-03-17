package com.project.est_sb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EtudiantAdapt extends RecyclerView.Adapter<EtudiantAdapt.EtudiantVwHolder> {
    ArrayList<EtudiantItem> etudiantItems;
    Context cntxt ;
    private OnItemClickListenner onItemClickListenner;
public interface OnItemClickListenner{
    void onClick(int position);
}

    public void setOnItemClickListenner(OnItemClickListenner onItemClickListenner) {
        this.onItemClickListenner = onItemClickListenner;
    }

    public EtudiantAdapt(Context c, ArrayList<EtudiantItem> etdItems) {
        this.etudiantItems = etdItems;
        cntxt = c ;
    }

    @NonNull
    @Override
    public EtudiantVwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.etud_item , parent , false);

        return new EtudiantVwHolder(itemView , onItemClickListenner);
    }


    @Override
    public void onBindViewHolder(@NonNull EtudiantVwHolder hldr, int p) {
        hldr.name.setText(etudiantItems.get(p).getName());
        hldr.status.setText(etudiantItems.get(p).getStatus());
        hldr.roll.setText(etudiantItems.get(p).getRoll()+"");
        hldr.card.setCardBackgroundColor(getColor(p));
    }
    private int getColor( int p ){
    String s = etudiantItems.get(p).getStatus();
        if (s.equals("Presente")) return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(cntxt , R.color.present)));
        else if (s.equals("Absent")) return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(cntxt , R.color.absent)));
        return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(cntxt , R.color.normal)));

    }

    @Override
    public int getItemCount() {
        return etudiantItems.size();
    }

    public static class EtudiantVwHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView name ; TextView status ; TextView roll ; CardView card ;


        public EtudiantVwHolder(@NonNull View itemView , OnItemClickListenner clickonItyem) {

            super(itemView);
            name = itemView.findViewById(R.id.name);
            status = itemView.findViewById(R.id.status) ;
            roll = itemView.findViewById(R.id.roll);
            card = itemView.findViewById(R.id.cardvw);
            itemView.setOnClickListener(v-> clickonItyem.onClick(getAdapterPosition()));
            itemView.setOnCreateContextMenuListener( this );
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add( getAdapterPosition() , 0 ,0 , "modifier" );
            contextMenu.add( getAdapterPosition() ,1,0,"supprimer" ) ;
        }
    }
}
