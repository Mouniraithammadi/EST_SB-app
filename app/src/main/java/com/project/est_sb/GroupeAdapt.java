package com.project.est_sb;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroupeAdapt extends RecyclerView.Adapter<GroupeAdapt.ClassVwHolder> {
    ArrayList<Groupe_item> clsItems ;
    Context cntxt ;
    private OnItemClickListenner onItemClickListenner;
public interface OnItemClickListenner{
    void onClick(int position);
}

    public void setOnItemClickListenner(OnItemClickListenner onItemClickListenner) {
        this.onItemClickListenner = onItemClickListenner;
    }

    public GroupeAdapt(Context c, ArrayList<Groupe_item> clsItems) {
        this.clsItems = clsItems;
        cntxt = c ;
    }

    @NonNull
    @Override
    public ClassVwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cls_item , parent , false);

        return new ClassVwHolder(itemView , onItemClickListenner);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassVwHolder hldr, int p) {
        hldr.grp.setText(clsItems.get(p).getGroup());
        hldr.sjt.setText(clsItems.get(p).getSujet());
    }

    @Override
    public int getItemCount() {
        return clsItems.size();
    }

    public static class ClassVwHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView grp ; TextView sjt ;


        public ClassVwHolder(@NonNull View view , OnItemClickListenner clickonItyem) {

            super(view);
            grp = view.findViewById(R.id.grp_item);
            sjt = view.findViewById(R.id.sjt_item) ;
            view.setOnClickListener(v-> clickonItyem.onClick(getAdapterPosition()));
            view.setOnCreateContextMenuListener( this );
        }

        @Override
        public void onCreateContextMenu(ContextMenu con_menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            con_menu.add( getAdapterPosition() , 0 ,0 ,"modifier".toUpperCase() );
            con_menu.add( getAdapterPosition(), 1 , 0 , "supprimer".toUpperCase() );
        }
    }
}
