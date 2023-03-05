package com.project.est_sb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBaseHLPR extends SQLiteOpenHelper {


 Context context ;




    private static final int VERSION = 1;

    //    creation de les tables
//    1 table de GROUPE :
    private static final String GROUPE_TABLE_NM = "GROUPE_TABLEAU";
    public static final String GROUPES_ID = "GROUPE_ID";
    public static final String GROUPES_NM = "GROUPE_NAME";
    public static final String SUJETS_NM = "SUJET_NAME";
    // requetes  en SQLite

    private static final String CREATION_DE_GROUPE_requete =
            " CREATE TABLE " + GROUPE_TABLE_NM + " ( " +
            GROUPES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
            + GROUPES_NM + " TEXT NOT NULL  ,  "
            + SUJETS_NM + " TEXT NOT NULL , "
            + " UNIQUE ( " + GROUPES_NM +" , "+ SUJETS_NM + " ) ) ;";

    private static final String DROP_GROUPE_TABLEAU = " DROP TABLE IF EXISTS " + GROUPE_TABLE_NM;
    private static final String SELECT_GROUPE_TABLEAU = " SELECT * FROM " + GROUPE_TABLE_NM;


    //    2 table de ETUDIANTS :
    private static final String ETUDIANT_TABLE_NM = "ETUDIANT_TABLEAU";
    public static final String ETUDIANTS_ID = "ETUDIANT_ID";
    public static final String ETUDIANTS_NM = "ETUDIANT_NAME";
    public static final String ETUDIANT_ROLL = "ROLL";
    // requetes  en SQLite
    private static final String CREATION_DE_ETUDIANT_requete =
            "CREATE TABLE " + ETUDIANT_TABLE_NM + " ( " +
            ETUDIANTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
            + GROUPES_ID + " INTEGER NOT NULL , "
            + ETUDIANTS_NM + " TEXT NOT NULL , "
            + ETUDIANT_ROLL + " INTEGER   , "
            + " FOREIGN KEY  ( " + GROUPES_ID + " ) REFERENCES " + GROUPE_TABLE_NM + " ( " + GROUPES_ID + " ));";


    private static final String DROP_ETUDIANT_TABLEAU = "DROP TABLE IF EXISTS " + ETUDIANT_TABLE_NM;
    private static final String SELECT_ETUDIANT_TABLEAU = "SELECT * FROM " + ETUDIANT_TABLE_NM;


    //    3 table de ETUDIANTS :
    private static final String STATUS_TABLE_NM = " STATUS_TABLEAU ";
    public static final String STATUS_ID = " STATUS_ID ";
    public static final String STATUS = " STATUS ";
    public static final String DATE = " STATUS_DATE ";
    // requetes  en SQLite
    private static final String CREATION_DE_STATUS_requete = "CREATE TABLE " + STATUS_TABLE_NM + " ( " +
                                                             STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
                                                             + ETUDIANTS_ID + " INTEGER NOT NULL , "
                                                             + DATE + " DATE NOT NULL ,"
                                                             + STATUS + " TEXT NOT NULL , "
                                                             + " UNIQUE ( " + ETUDIANTS_ID + "," + DATE + " ),"
                                                             + " FOREIGN KEY  ( " + ETUDIANTS_ID + " ) REFERENCES " + ETUDIANT_TABLE_NM + "(" + ETUDIANTS_ID + "));";


    private static final String DROP_STATUS_TABLEAU = "DROP TABLE IF EXISTS " + STATUS_TABLE_NM;
    private static final String SELECT_STATUS_TABLEAU = "SELECT * FROM " + STATUS_TABLE_NM;


    public DataBaseHLPR(@Nullable Context context) {

        super(context, "ESTSB.db", null, VERSION);
    this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase SQLITE_DB) {
        SQLITE_DB.execSQL(CREATION_DE_GROUPE_requete);
        SQLITE_DB.execSQL(CREATION_DE_ETUDIANT_requete);
        SQLITE_DB.execSQL(CREATION_DE_STATUS_requete);
        Toast.makeText( context, "la base de données est créée ".toUpperCase(), Toast.LENGTH_SHORT ).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase SQLITE_DB, int i, int i1) {
        try {
            SQLITE_DB.execSQL(DROP_GROUPE_TABLEAU);
            SQLITE_DB.execSQL(DROP_ETUDIANT_TABLEAU);
            SQLITE_DB.execSQL(DROP_STATUS_TABLEAU);
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
    }
    long setGroupe( String GROUPE_NM , String SUJET_NM ){
        SQLiteDatabase bdd = this.getWritableDatabase();
        ContentValues grp_values = new ContentValues();
        grp_values.put(GROUPES_NM , GROUPE_NM);
        grp_values.put(SUJETS_NM, SUJET_NM );

        return bdd.insert(GROUPE_TABLE_NM , null , grp_values);
    }
    public Cursor getGroupe(){
        SQLiteDatabase bdd = this.getReadableDatabase();
        return bdd.rawQuery(SELECT_GROUPE_TABLEAU, null);
    }
     long DELET_groupe(long id){
        SQLiteDatabase bdd = this.getReadableDatabase();

        return bdd.delete( GROUPE_TABLE_NM , GROUPES_ID+"=?" , new String[]{String.valueOf( id )} );
    }

    long modifier_Groupe( long id  ,String GROUPE_NM , String SUJET_NM ){
        SQLiteDatabase bdd = this.getWritableDatabase();
        ContentValues grp_values = new ContentValues();
        grp_values.put(GROUPES_NM , GROUPE_NM);
        grp_values.put(SUJETS_NM, SUJET_NM );

        return bdd.update(GROUPE_TABLE_NM , grp_values,GROUPES_ID+"=?" ,new String[]{String.valueOf( id )});
    }
long setEtudiant(long grp_id , int roll , String name ){
        SQLiteDatabase bdd = this.getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put(GROUPES_ID , grp_id  );
        values.put( ETUDIANT_ROLL , roll );
        values.put( ETUDIANTS_NM , name );
        return  bdd.insert( ETUDIANT_TABLE_NM , null , values );
}
Cursor getEtudiant(long grp_id){
        SQLiteDatabase ndd = this.getReadableDatabase();
        return ndd.query( SELECT_ETUDIANT_TABLEAU , null , GROUPES_ID+"=?", new String[]{String.valueOf( grp_id )},null, null , ETUDIANTS_ID );
}
    long DELET_Etudiant(long id){
        SQLiteDatabase bdd = this.getReadableDatabase();

        return bdd.delete( ETUDIANT_TABLE_NM , ETUDIANTS_ID+"=?" , new String[]{String.valueOf( id )} );
    }
    long modifier_etudiant( long id  ,String etudiant_NM ){
        SQLiteDatabase bdd = this.getWritableDatabase();
        ContentValues grp_values = new ContentValues();
        grp_values.put(GROUPES_NM ,etudiant_NM);


        return bdd.update(ETUDIANT_TABLE_NM , grp_values,ETUDIANTS_ID+"=?" ,new String[]{String.valueOf( id )});
    }
}






















