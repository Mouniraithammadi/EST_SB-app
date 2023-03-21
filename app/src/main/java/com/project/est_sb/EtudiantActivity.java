package com.project.est_sb;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EtudiantActivity extends AppCompatActivity {
    Toolbar toolbar ;
    MyCalendar calendar ;

    public static void setdata(String date , String name , String sujet , int posit) {
        DATE = date; groupName = name; sujetName = sujet ; position = posit;
    }

    public static String DATE = null  ;



    public static String groupName ;
    public static String   sujetName ;
    private RecyclerView list ;
    private EtudiantAdapt adapt ;
    private RecyclerView.LayoutManager layoutMngr ;
    private  ArrayList<EtudiantItem> etudiantItems = new ArrayList<>() ;
    public static int position ;
    private DataBaseHLPR BDD;
    public static int g_id ;
    public static   long Groupe_id ;
    private TextView subTitl ;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_student);
//        BDD = new DataBaseHLPR( this );
//        calendar = new MyCalendar();
//        Intent intent = getIntent();
//          groupName = intent.getStringExtra("GroupeName");
//         sujetName =intent.getStringExtra("SujetName");
//       position = intent.getIntExtra("position" , -1);
//
//
//        g_id = BDD.getGroupIdByGroupeNmAndSujet( groupName , sujetName );
//        setToolbar();
//        loadBDD();
//       list = findViewById(R.id.etudiant_list) ;
//       list.setHasFixedSize(true);
//       layoutMngr = new LinearLayoutManager(this);
//       list.setLayoutManager(layoutMngr);
//       adapt = new EtudiantAdapt(this , etudiantItems) ;
//       list.setAdapter( adapt );
//
//
//       adapt.setOnItemClickListenner(position -> changeStatus(position));
//        loadStatus();
//
//
//    }
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_student);
 Intent intent = getIntent () ;
    BDD = new DataBaseHLPR(this);
    calendar = new MyCalendar();
    boolean check ;
    try {
        check = getIntent ().getBooleanExtra ( "check" ,true);
    }catch (Exception e) {
        check = true;
    }
    if(check) {
        DATE = calendar.getDate ();

        groupName = intent.getStringExtra("GroupeName");
        sujetName = intent.getStringExtra("SujetName");
        position = intent.getIntExtra("position", -1);
        Groupe_id = intent.getLongExtra ("group_id", -1);
    }else {
        DATE = intent.getStringExtra("date");

        groupName = intent.getStringExtra("GroupeName");
        sujetName = intent.getStringExtra("SujetName");
        position = intent.getIntExtra("position", -1);
        Groupe_id = intent.getLongExtra ("group_id", -1);
    }

    g_id = BDD.getGroupIdByGroupeNmAndSujet(groupName, sujetName);
    setToolbar();
    loadBDD();
    list = findViewById(R.id.etudiant_list);
    list.setHasFixedSize(true);
    layoutMngr = new LinearLayoutManager(this);
    list.setLayoutManager(layoutMngr);
    adapt = new EtudiantAdapt(this, etudiantItems);
    list.setAdapter(adapt);
    adapt.setOnItemClickListenner(position -> changeStatus(position));
    loadStatus(); // add this line to load the status of each student
}


    private void changeStatus(int position) {
        String s = etudiantItems.get(position).getStatus();
        if (s.equals("P")) s = "A" ;
        else s = "P" ;
        etudiantItems.get(position).setStatus(s);
        adapt.notifyItemChanged(position);

    }


    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        TextView titl = toolbar.findViewById(R.id.titel_toolbar);
        subTitl = toolbar.findViewById(R.id.subTitel_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton sav = toolbar.findViewById(R.id.save);
        sav.setOnClickListener ( v -> saveStatus () );
        titl.setText(groupName);
        subTitl.setText(sujetName + " | "+DATE);
        back.setOnClickListener(v-> onBackPressed_());
        toolbar.inflateMenu(R.menu.etudiant_menu) ;
        toolbar.setOnMenuItemClickListener(menuItem->onMenuItemClick(menuItem));

    }

    private void onBackPressed_() {
    startActivity ( new Intent (this , MainActivity.class) );
    }

    private void saveStatus() {
     for (EtudiantItem etud :etudiantItems) {
     String st = etud.getStatus();
     if(!st.equals( "P" ) ) st = "A";

    long value =  BDD.addStatus( etud.getE_id() , g_id, DATE , st );
    if(value== -1) BDD.modifierStatus( etud.getE_id() ,DATE , st );

     }
        Toast.makeText( this, "L'absence est enregistre ", Toast.LENGTH_SHORT ).show();

    }


//    private void loadStatus(){
//
////        for (EtudiantItem etud :etudiantItems) {
////            String st = BDD.getStatus( etud.getE_id() ,calendar.getDate() );
////            if (st == null ) etud.setStatus( "" );
////             else  etud.setStatus( st );
////
////        }
//        for(int i = 0 ; i<etudiantItems.size() ;i++){
//            String st = BDD.getStatus( etudiantItems.get( i ).getE_id() ,calendar.getDate() );
//            if (st == null ) {
//                etudiantItems.get( i ).setStatus( "" );
//                adapt.notifyItemChanged ( i );
//            } else  etudiantItems.get( i ).setStatus( st );
//        }
//
//        Toast.makeText( this, "loading status", Toast.LENGTH_SHORT ).show();
//        }

    private void loadStatus() {
        for (int i = 0; i < etudiantItems.size(); i++) {
            EtudiantItem etud = etudiantItems.get(i);
            String st = BDD.getStatus(etud.getE_id(), DATE);

            if (st == null) {
                etud.setStatus("");
            } else {
                etud.setStatus(st);
            }
            adapt.notifyItemChanged(i);
        }
//        Toast.makeText(this, "loading status", Toast.LENGTH_SHORT).show();
    }



    private boolean onMenuItemClick(MenuItem menuItem) {
        if(menuItem.getItemId()==R.id.ajouter_etudiant) showAjouterEtudiantDialog();
        else if(menuItem.getItemId()==R.id.show_calendar) showCalendar();
        else if(menuItem.getItemId()==R.id.show_date_list) OpenDateList();
        return true ;
    }

    private void OpenDateList() {
    int size = etudiantItems.size ();
    long[] ids =new long[size];
    int[] rolls = new int[size];
    String[] names = new String[size] ;
    for (int i = 0 ; i < size ; i++){ids[i] =etudiantItems.get(i).getE_id (); rolls[i] =etudiantItems.get(i).getRoll (); names[i] =etudiantItems.get(i).getName ();  }
        Intent intent = new Intent (this , Tableau_Activity2.class);

        intent.putExtra ( "groupe_id" , Groupe_id );
        intent.putExtra ( "group_name" , groupName );
        intent.putExtra ( "group_sujet" , sujetName );
        intent.putExtra ( "group_position" , position );
        startActivity ( intent );
    }


    private void showCalendar() {

        calendar.show( getSupportFragmentManager() , "");
        calendar.setOnCalendarOkClickListener( this::onCalendarOkClicked );
    }


    private void onCalendarOkClicked(int year, int month, int day) {
        calendar.setDate( year , month , day);
        DATE = calendar.getDate() ;
        subTitl.setText(sujetName + " | "+ calendar.getDate());
        loadStatus();
    }

    private void showAjouterEtudiantDialog() {
        MonDialog dialog = new MonDialog(0,"");
        dialog.show(getSupportFragmentManager() , MonDialog.ajouter_etud);
        dialog.setlistener( this::ajouterEtudiant );
    }

    private void ajouterEtudiant(String rol, String name) {
         int roll = Integer.parseInt( rol ) ;
        long e_id = BDD.addEtudiant( g_id , roll, name );
        etudiantItems.add(new EtudiantItem( e_id , roll , name));
        adapt.notifyItemChanged(etudiantItems.size() - 1);
    }
    @SuppressLint("Range")
    private void loadBDD(){
        Cursor curs = BDD.getEtudiantTable(g_id);
        etudiantItems.clear();
        while ( curs.moveToNext() ){
             int  e_id = curs.getInt( curs.getColumnIndex( DataBaseHLPR.ETUDIANTS_ID ) );
          String e_name =  curs.getString( curs.getColumnIndex( DataBaseHLPR.ETUDIANTS_NM ) );
            int e_roll =  curs.getInt( curs.getColumnIndex( DataBaseHLPR.ETUDIANT_ROLL ) );

            if (BDD.getGroupIdOfStudent(e_id ,g_id )) etudiantItems.add( new EtudiantItem( e_id, e_roll , e_name));
        }
        MonDialog.index = etudiantItems.size () ;
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