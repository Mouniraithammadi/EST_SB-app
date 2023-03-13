package com.project.est_sb;

import android.annotation.SuppressLint;
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
//    SELECT * FROM ETUDIANT_TABLEAU WHERE GROUPE_ID=? AND ETUDIANT_ID IN (SELECT ETUDIANT_ID FROM ETUDIANT_TABLEAU WHERE GROUPE_ID=? ORDER BY ETUDIANT_ID)


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
        SQLITE_DB.execSQL(CREATE_TABLE);
        Toast.makeText( context, "la base de données est créée ".toUpperCase(), Toast.LENGTH_SHORT ).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase SQLITE_DB, int i, int i1) {
        try {
            SQLITE_DB.execSQL(DROP_GROUPE_TABLEAU);
            SQLITE_DB.execSQL(DROP_ETUDIANT_TABLEAU);
            SQLITE_DB.execSQL(DROP_STATUS_TABLEAU);
            SQLITE_DB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(SQLITE_DB);
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
long addEtudiant(int grp_id , int roll , String name ){
        SQLiteDatabase bdd = this.getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put(GROUPES_ID , grp_id  );
        values.put( ETUDIANT_ROLL , roll );
        values.put( ETUDIANTS_NM , name );
        return  bdd.insert( ETUDIANT_TABLE_NM , null , values );
}

Cursor getEtudiantTable(long groupeId){
        SQLiteDatabase ndd = this.getReadableDatabase();
      Cursor cur =  ndd.query(ETUDIANT_TABLE_NM , null , GROUPES_ID+"=?", new String[]{String.valueOf( groupeId )},null, null , ETUDIANT_ROLL );
    return cur ;
    //    SQLiteDatabase db = this.getReadableDatabase();
//    String[] selectionArgs = { String.valueOf(groupeId) };
//    String query = "SELECT * FROM ETUDIANT_TABLEAU WHERE GROUPE_ID=? ORDER BY ETUDIANT_ID;";
//    return db.rawQuery(query, selectionArgs);
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
    @SuppressLint("Range")
    public  boolean getGroupIdOfStudent(int studentId , int groupe_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + GROUPES_ID + " FROM " + ETUDIANT_TABLE_NM + " WHERE " + ETUDIANTS_ID + " = ?", new String[]{String.valueOf(studentId)});
        int groupId = -1; // default value in case no group is found
        if (cursor.moveToFirst()) {
            groupId = cursor.getInt(cursor.getColumnIndex(GROUPES_ID) );
        }
        cursor.close();
        if(groupId == groupe_id) return true;
        return false;
    }
    public int getGroupIdByGroupeNmAndSujet(String groupeNm, String groupeSujet) {
        SQLiteDatabase db = this.getReadableDatabase();
        int groupId = -1; // default value if no matching group is found

        String selectQuery = "SELECT " + GROUPES_ID + " FROM " + GROUPE_TABLE_NM +
                             " WHERE " + GROUPES_NM + " = ? AND " + SUJETS_NM + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{groupeNm, groupeSujet});

        if (cursor.moveToFirst()) {
            groupId = cursor.getInt(cursor.getColumnIndex(GROUPES_ID)-0);
        }

        cursor.close();
        db.close();

        return groupId;
    }
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT"
            + ")";
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = { COLUMN_ID };
        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean insertData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}






















