package com.project.est_sb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fltAb ;
    RecyclerView listView ;
    GroupeAdapt groupe_adapter;
    RecyclerView.LayoutManager lyt_manager ;
    ArrayList<Groupe_item> groupeItems = new ArrayList<>();

    Toolbar toolbar ;
     Context context = this ;

     public Context getContext() {
        return context;
    }

    private FloatingActionButton fabMain, fabLogout;
    DataBaseHLPR BDD ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
BDD = new DataBaseHLPR( this );
        setContentView(R.layout.activity_main);
        loadBDD();
        fltAb = findViewById(R.id.fltAb_main);
        fltAb.setOnClickListener(v-> showDialog());

        listView = findViewById(R.id.listView);
        listView.setHasFixedSize(true);
        lyt_manager = new LinearLayoutManager(this);
        listView.setLayoutManager(lyt_manager);
        groupe_adapter = new GroupeAdapt(this , groupeItems);
        listView.setAdapter(groupe_adapter);
        groupe_adapter.setOnItemClickListenner(position -> gotoItemActivity(position));
       addToolbar();
        fabMain = findViewById(R.id.fltAb_main);
        fabLogout = findViewById(R.id.fltAb_logout);

        // Set click listener for logout button
        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform logout action
                showExitDialog();
            }
        });




    }
    void logout(){
        // Exit the app
        // Clear user session data
        SharedPreferences prefs = getSharedPreferences("UserSessionData", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();

        // Redirect to login screen

        Intent intent = new Intent(this, LoginActivity.class );
        startActivity(intent);
        finish();
    }
    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.exit_dialog_message)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      logout ();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Dismiss the dialog
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void loadBDD(){
        Cursor curs = BDD.getGroupe();
        groupeItems.clear();
        while ( curs.moveToNext() ){
              int  g_id = curs.getInt( curs.getColumnIndex(DataBaseHLPR.GROUPES_ID ) -0 );
           String g_name =  curs.getString(curs.getColumnIndex(DataBaseHLPR.GROUPES_NM) -0);
            String g_sujet =  curs.getString(curs.getColumnIndex(DataBaseHLPR.SUJETS_NM)-0);
                 groupeItems.add(new Groupe_item(g_id , g_name , g_sujet));
        }}





    private void addToolbar() {
        toolbar = findViewById(R.id.toolbar);
        TextView titl = toolbar.findViewById(R.id.titel_toolbar);
        TextView subTitl = toolbar.findViewById(R.id.subTitel_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton sav = toolbar.findViewById(R.id.save);
        titl.setText("groupes");
        subTitl.setVisibility(View.GONE);
        back.setVisibility(View.INVISIBLE);
        sav.setVisibility(View.INVISIBLE);

    }

    private void gotoItemActivity(int position) {
        Intent intent = new Intent(MainActivity.this, EtudiantActivity.class);
        intent.putExtra("GroupeName" , groupeItems.get(position).getGroup());
        intent.putExtra("SujetName" , groupeItems.get(position).getSujet());
        intent.putExtra("position" , position);
        intent.putExtra("group_id" , groupeItems.get( position ).getId());
        startActivity(intent);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 0 :
                showUpdatDialog(item.getGroupId());
                break;
            case 1:
                suppGroupe(item.getGroupId());
        }
        return super.onContextItemSelected( item );
    }

    private void showUpdatDialog(int position) {
        MonDialog dlg = new MonDialog(0,"");
        dlg.setGRP_SJT( groupeItems.get( position ).getGroup() , groupeItems.get( position ).getSujet());
        dlg.show( getSupportFragmentManager() , MonDialog.modifier_grp );
        dlg.setlistener( (grp_name , sjt_name)->modifierGroupe(position , grp_name , sjt_name) );
    }

    private void modifierGroupe(int position, String grp_name, String sjt_name) {
        BDD.modifier_Groupe( groupeItems.get( position ).getId() , grp_name ,sjt_name );
        groupeItems.get( position ).setGroup( grp_name );
        groupeItems.get( position ).setSujet( sjt_name );

        groupe_adapter.notifyItemChanged( position );


    }

    private void suppGroupe(int position) {
        BDD.DELET_groupe(groupeItems.remove( position ).getId());
        groupe_adapter.notifyItemRemoved( position );

    }


    private void showDialog(){
MonDialog dlg = new MonDialog(0,"");
dlg.show(getSupportFragmentManager(), MonDialog.ajouter_grp);
dlg.setlistener((grpNm , sjtNm)-> {
    addgrp(grpNm, sjtNm);
});
    }

    private void addgrp(String groupe_name , String sujet_name) {


            long g_id = BDD.setGroupe(groupe_name , sujet_name);

            Groupe_item gr = new Groupe_item( g_id, groupe_name, sujet_name );
            groupeItems.add( gr );
            Toast.makeText( this, "ajouter " + groupe_name + " avec le sujet " + sujet_name, Toast.LENGTH_SHORT ).show();

            groupe_adapter.notifyDataSetChanged();


    }
//    public static boolean checking ;
}