package com.project.est_sb;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class EtudiantActivity extends AppCompatActivity {
    Toolbar toolbar ;
    MyCalendar calendar ;
    private String groupName ;
    private String   sujetName ;
    private RecyclerView list ;
    private EtudiantAdapt adapt ;
    private RecyclerView.LayoutManager layoutMngr ;
    private ArrayList<EtudiantItem> etudiantItems = new ArrayList<>() ;
    private int position ;
    private DataBaseHLPR BDD;
    private int g_id ;
    private TextView subTitl ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        BDD = new DataBaseHLPR( this );
        calendar = new MyCalendar();
        Intent intent = getIntent();
          groupName = intent.getStringExtra("GroupeName");
         sujetName =intent.getStringExtra("SujetName");
       position = intent.getIntExtra("position" , -1);
       g_id = BDD.getGroupIdByGroupeNmAndSujet( groupName , sujetName );

       setToolbar();

       list = findViewById(R.id.etudiant_list) ;
       list.setHasFixedSize(true);
       layoutMngr = new LinearLayoutManager(this);
       list.setLayoutManager(layoutMngr);
       adapt = new EtudiantAdapt(this , etudiantItems) ;
       list.setAdapter( adapt );
        loadBDD();
       adapt.setOnItemClickListenner(position -> changeStatus(position));
    }

    private void changeStatus(int position) {
        String s = etudiantItems.get(position).getStatus();
        if (s.equals("Presente")) s = "Absent" ;
        else s = "Presente" ;
        etudiantItems.get(position).setStatus(s);
        adapt.notifyItemChanged(position);
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        TextView titl = toolbar.findViewById(R.id.titel_toolbar);
        subTitl = toolbar.findViewById(R.id.subTitel_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton sav = toolbar.findViewById(R.id.save);
        titl.setText(groupName);
        subTitl.setText(sujetName + " | "+ calendar.getDate());
        back.setOnClickListener(v-> onBackPressed());
        toolbar.inflateMenu(R.menu.etudiant_menu) ;
        toolbar.setOnMenuItemClickListener(menuItem->onMenuItemClick(menuItem));

    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        if(menuItem.getItemId()==R.id.ajouter_etudiant) showAjouterEtudiantDialog();
        else if(menuItem.getItemId()==R.id.show_calendar) showCalendar();

        return true ;
    }

    private void showCalendar() {

        calendar.show( getSupportFragmentManager() , "");
        calendar.setOnCalendarOkClickListener( this::onCalendarOkClicked );
    }

    private void onCalendarOkClicked(int year, int month, int day) {
        calendar.setDate( year , month , day);
        subTitl.setText(sujetName + " | "+ calendar.getDate());
    }

    private void showAjouterEtudiantDialog() {
        MonDialog dialog = new MonDialog(0,"");
        dialog.show(getSupportFragmentManager() , MonDialog.ajouter_etud);
        dialog.setlistener((rol , name)->ajouterEtudiant( rol , name));
    }

    private void ajouterEtudiant(String rol, String name) {
         int roll = Integer.parseInt( rol ) ;
        long e_id = BDD.addEtudiant( g_id , roll, name );
        etudiantItems.add(new EtudiantItem( e_id , roll , name));
        adapt.notifyItemChanged(etudiantItems.size() - 1);
    }
    private void loadBDD(){
        Cursor curs = BDD.getEtudiantTable(g_id);
        etudiantItems.clear();
        while ( curs.moveToNext() ){
             int  e_id = curs.getInt( curs.getColumnIndex(DataBaseHLPR.ETUDIANTS_ID ) -0);
          String e_name =  curs.getString(curs.getColumnIndex(DataBaseHLPR.ETUDIANTS_NM) -0 );
            int e_roll =  curs.getInt(curs.getColumnIndex(DataBaseHLPR.ETUDIANT_ROLL)-0);

            if (BDD.getGroupIdOfStudent(e_id ,g_id )) etudiantItems.add( new EtudiantItem( e_id, e_roll , e_name));
        }
    curs.close();}

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case 0:
                showModiferEtudiantDialog(item.getGroupId());
                break;
            case 1 :suppEtudiant(item.getGroupId());
        }
        return super.onContextItemSelected( item );
    }

    private void showModiferEtudiantDialog(int position) {
        MonDialog dlg = new MonDialog(etudiantItems.get( position ).getRoll() ,etudiantItems.get( position ).getName());
        dlg.show( getSupportFragmentManager() ,MonDialog.modifier_etud );
        dlg.setlistener( ( groupI_,name)->modifierEtudiant(position  , name) );
    }

    private void modifierEtudiant(int position,  String name) {
        BDD.modifier_etudiant( etudiantItems.get( position ).getE_id(),name );
        etudiantItems.get( position).setName( name );
        adapt.notifyItemChanged( position );
    }

    private void suppEtudiant(int groupId) {
        BDD.DELET_Etudiant( etudiantItems.get( groupId ).getE_id() );
        etudiantItems.remove( groupId );
        adapt.notifyItemRemoved( groupId );
    }
}