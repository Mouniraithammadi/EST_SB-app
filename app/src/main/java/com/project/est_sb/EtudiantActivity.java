package com.project.est_sb;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class EtudiantActivity extends AppCompatActivity {
Toolbar toolbar ;
   private String groupName ;
    private String   sujetName ;
    private RecyclerView list ;
    private EtudiantAdapt adapt ;
    private RecyclerView.LayoutManager layoutMngr ;
    private ArrayList<EtudiantItem> etudiantItems = new ArrayList<>() ;
    private int position ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Intent intent = getIntent();
          groupName = intent.getStringExtra("GroupeName");
         sujetName =intent.getStringExtra("SujetName");
       position = intent.getIntExtra("position" , -1);
       setToolbar();
       list = findViewById(R.id.etudiant_list) ;
       list.setHasFixedSize(true);
       layoutMngr = new LinearLayoutManager(this);
       list.setLayoutManager(layoutMngr);
       adapt = new EtudiantAdapt(this , etudiantItems) ;
       list.setAdapter( adapt );
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
        TextView subTitl = toolbar.findViewById(R.id.subTitel_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton sav = toolbar.findViewById(R.id.save);
        titl.setText(groupName);
        subTitl.setText(sujetName);
        back.setOnClickListener(v-> onBackPressed());
        toolbar.inflateMenu(R.menu.etudiant_menu) ;
        toolbar.setOnMenuItemClickListener(menuItem->onMenuItemClick(menuItem));

    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        if(menuItem.getItemId()==R.id.ajouter_etudiant) showAjouterEtudiantDialog();
        return true ;
    }

    private void showAjouterEtudiantDialog() {
        MonDialog dialog = new MonDialog();
        dialog.show(getSupportFragmentManager() , MonDialog.ajouter_etud);
        dialog.setlistener(this::ajouterEtudiant);
    }

    private void ajouterEtudiant(String roll, String name) {
        etudiantItems.add(new EtudiantItem(roll , name));
        adapt.notifyItemChanged(etudiantItems.size() - 1);
    }


}