package com.project.est_sb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Tableau_Activity2 extends AppCompatActivity {
private ListView listView;
public static  boolean checking = false ;
private ArrayAdapter adapt ;
    Toolbar toolbar ;
    private TextView subTitl ;
private ArrayList listIrems = new ArrayList ();
private long Gr_id ;
    String groupName ;
     String   sujetName ;
    int position ;
long grpId ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_tableau2 );
        Gr_id = EtudiantActivity.Groupe_id ;
        Intent intent = getIntent () ;
      grpId = intent.getLongExtra ("groupe_id" ,-1) ;
        System.out.println ( "the second id is " + grpId);

        setToolbar();
        listView = findViewById ( R.id.date_list );
        adapt = new ArrayAdapter ( this , R.layout.date_item , R.id.date_item,listIrems);
        listView.setAdapter ( adapt );
        listView.setOnItemClickListener((parent , view , position , id)->openabsentday(position , id));
        loaListItem();

    }

    private void openabsentday(int position, long id) {
        System.out.println ("\n\n\nposition " + position + "\n\n\nid" + listIrems.get ( position ));
        Intent intent2 = new Intent (this , EtudiantActivity.class);
        Intent intent = getIntent ();

         intent2.putExtra ( "GroupeName", intent.getStringExtra ( "group_name" ));
        intent2.putExtra("SujetName" , intent.getStringExtra ("group_sujet"));
        intent2.putExtra("position" , intent.getIntExtra ( "group_position" , -1));
        intent2.putExtra("group_id" , Gr_id);
        intent2.putExtra("check" , false);
        intent2.putExtra("date" ,(String) listIrems.get ( position ) );
//        EtudiantActivity.setdata ( (String) listIrems.get ( position ) , intent.getStringExtra ( "group_name" ) , intent.getStringExtra ("group_sujet") ,intent.getIntExtra ( "group_position" , -1));


        startActivity ( intent2 );
    }
    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        TextView titl = toolbar.findViewById(R.id.titel_toolbar);
        subTitl = toolbar.findViewById(R.id.subTitel_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton sav = toolbar.findViewById(R.id.save);
        sav.setVisibility(View.INVISIBLE);
        titl.setText("Historique");
        subTitl.setText(groupName );
        back.setOnClickListener(v-> onBackPressed());
//        toolbar.inflateMenu(R.menu.etudiant_menu) ;


    }

    private void loaListItem() {
        System.out.println ("now we are the getting " + Gr_id);
        Cursor cur = new DataBaseHLPR ( this ).getDistanceMonth ( Gr_id );
        while (cur.moveToNext ()){

          String date = cur.getString (0);

            System.out.println ( "loading items" + date.toString ()) ;
            if( ! listIrems.contains ( date.toString () )) listIrems.add( date.toString () );
        }
    }
}