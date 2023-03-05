package com.project.est_sb;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MonDialog extends DialogFragment {
    private static String grp_modifier ="";
    private static String sjt_modifier ="";
    public static final String ajouter_grp = "Ajouter Groupe";
    public static final String modifier_grp = "Modifier Groupe";
    public static final String ajouter_etud = "Ajouter Etudiant";
    public static final String modifier_etud= "Modifier Etudiant";
    private static Integer index = 1 ;

    OnClickListener listener;

    public void setGRP_SJT(String g , String s){
        grp_modifier = g ; sjt_modifier = s ;
    }
public interface OnClickListener{
    void onClick(String txt1 , String txt2 );
}

    public void setlistener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        android.app.Dialog dlg = null ;
            if (getTag().equals(modifier_etud)) dlg = modifierEtudiantDialouge();
            if (getTag().equals(modifier_grp)) dlg = modifierGroupeDialouge();
            if (getTag().equals(ajouter_grp)) dlg = ajouterGroupeDialouge();
            if (getTag().equals(ajouter_etud)) dlg = ajouterEtudiantDialouge();

            dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            return  dlg ;
    }



    private Dialog modifierGroupeDialouge() {

        AlertDialog.Builder build = new AlertDialog.Builder(getActivity()) ;
        View vw = LayoutInflater.from(getActivity()).inflate(R.layout.dlg, null);

        build.setView(vw);


        EditText grp_edit = vw.findViewById(R.id.edt1); grp_edit.setHint("groupe");
        EditText sjt_edit = vw.findViewById(R.id.edt2); sjt_edit.setHint("sujet");
        TextView titr = vw.findViewById(R.id.title_dlg); titr.setText("ajouter un groupe");
        grp_edit.setText( grp_modifier  ); sjt_edit.setText(sjt_modifier  );

        Button cncl = vw.findViewById(R.id.annuler_button);
        Button add = vw.findViewById(R.id.ajouter_button) ;
        cncl.setOnClickListener(v ->  dismiss());
        add.setOnClickListener(v-> {
            String grpNm , sjtNm ; grpNm = grp_edit.getText().toString().trim(); sjtNm = sjt_edit.getText().toString().trim() ;

            if (grpNm.isEmpty() ){
            grp_edit.setHint( "entrez le nouveau nom du groupe" );
                grp_edit.setBackground( impty_background() );
            } else if ( sjtNm.isEmpty()) {
                sjt_edit.setHint( "entrez le nouveau nom du sujet" );
                grp_edit.setBackground( null );

                sjt_edit.setBackground(impty_background() );

            } else{listener.onClick( grpNm, sjtNm );
                dismiss();}


        });

        return build.create() ;
    }



    private Dialog ajouterGroupeDialouge() {
        AlertDialog.Builder build = new AlertDialog.Builder(getActivity()) ;
        View vw = LayoutInflater.from(getActivity()).inflate(R.layout.dlg, null);

        build.setView(vw);


         EditText grp_edit = vw.findViewById(R.id.edt1); grp_edit.setHint("groupe");
        EditText sjt_edit = vw.findViewById(R.id.edt2); sjt_edit.setHint("sujet");
        TextView titr = vw.findViewById(R.id.title_dlg); titr.setText("ajouter un groupe");


        Button cncl = vw.findViewById(R.id.annuler_button);
        Button add = vw.findViewById(R.id.ajouter_button) ;
        cncl.setOnClickListener(v ->  dismiss());
        add.setOnClickListener(v-> {
            String grpNm , sjtNm ; grpNm = grp_edit.getText().toString().trim(); sjtNm = sjt_edit.getText().toString().trim() ;

                if (grpNm.isEmpty() ){
                    grp_edit.setHint( "entrez le nom du groupe" );
                   grp_edit.setBackground( impty_background() );
                } else if ( sjtNm.isEmpty()) {
                    grp_edit.setBackground( null );
                    sjt_edit.setHint( "entrez le nom du sujet" );
                    sjt_edit.setBackground(impty_background() );

                } else{listener.onClick( grpNm, sjtNm );
            dismiss();}


        });

        return build.create() ;
    }
    private Dialog modifierEtudiantDialouge() {
        return null ;
    }
    private Dialog ajouterEtudiantDialouge() {

        AlertDialog.Builder build = new AlertDialog.Builder(getActivity()) ;
        View vw = LayoutInflater.from(getActivity()).inflate(R.layout.dlg, null);
        build.setView(vw);

        EditText name_edit = vw.findViewById(R.id.edt2); name_edit.setHint("Name");
        EditText roll_edit = vw.findViewById(R.id.edt1); roll_edit.setHint("Numero"); roll_edit.setText(index.toString());
        TextView titr = vw.findViewById(R.id.title_dlg); titr.setText("ajouter un etudiant");
        Button cncl = vw.findViewById(R.id.annuler_button);
        Button add = vw.findViewById(R.id.ajouter_button) ;
        cncl.setOnClickListener(v ->  dismiss());
        add.setOnClickListener(v-> {
            String roll , Nm ; Nm = name_edit.getText().toString(); roll = roll_edit.getText().toString() ;
            roll_edit.setText((++index).toString());
            listener.onClick( roll,Nm);
            name_edit.setText("");
            name_edit.setHint("ajouter l'etudiant suivant ");
        });

        return build.create() ;
    }
    public  GradientDrawable impty_background(){
        int color = Color.parseColor("#FE8484");
        GradientDrawable background = new GradientDrawable();
        background.setShape( GradientDrawable.RECTANGLE);
        background.setColor(color);
        background.setCornerRadius(20);
        return background ;
    }
}
