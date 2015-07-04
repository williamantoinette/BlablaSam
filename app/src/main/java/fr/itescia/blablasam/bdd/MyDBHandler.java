package fr.itescia.blablasam.bdd;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

/**
 * Classe permettant la gestion de la base de données
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "blablaSam.db";
    private static final String TABLE_UTILISATEUR = "utilisateur";
    private static final String TABLE_ADRESSE = "adresse";
    private static final String TABLE_TRAJET = "trajet";

    public static final String COLUMN_ID = "_id";

    /**
     * Permet de définir les critères de la base de données (nom, version, etc)
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * Permet d'écrire dans la base de données. Cette méthode est la même pour tous les insert
     * @param table
     * @param values
     */
    public void insertDB(String table, ContentValues values){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(table, null, values);
            db.close();
            System.out.println("insert finished");
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Ajoute un utilisateur dans la base de données
     * @param utilisateur
     */
    public void addUtilisateur(Utilisateur utilisateur){
        try{
            System.out.println("insert user");
            ContentValues values = new ContentValues();
            values.put("nom", utilisateur.getNom());
            values.put("prenom", utilisateur.getPrenom());
            values.put("dateDeNaissance", utilisateur.getDateDeNaissance());
            values.put("dateInscription", utilisateur.getDateInscription());

            //TODO Ajouter l'id de l'adresse
//            values.put("adresse", utilisateur.getAdresse().get_id());
            values.put("adresse", utilisateur.getAdresseBis());

            values.put("statut", utilisateur.isStatut());
            values.put("login", utilisateur.getLogin());
            values.put("password", utilisateur.getPassword());
            this.insertDB(TABLE_UTILISATEUR, values);
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Ajoute une adresse dans la base de données
     * @param adresse
     */
    public void addAdresse(Adresse adresse) {
        try{
            System.out.println("insert adresse");
            ContentValues values = new ContentValues();
            values.put("numero", adresse.getNumero());
            values.put("rue", adresse.getRue());
            values.put("ville", adresse.getVille());
            values.put("codePostal", adresse.getCodePostal());
            values.put("pays", adresse.getPays());
            this.insertDB(TABLE_ADRESSE, values);
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Recherche un utilisateur
     * @param nom
     * @return l'utilisateur recherché
     */
    public Utilisateur findUtilisateur(String nom) {
        String query = "SELECT * FROM " + TABLE_UTILISATEUR + " WHERE " + "nom" + " =  \"" + nom + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Utilisateur utilisateur = new Utilisateur();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            utilisateur.set_id(Integer.parseInt(cursor.getString(0)));
            utilisateur.setNom(cursor.getString(1));
            utilisateur.setPrenom(cursor.getString(2));
            utilisateur.setDateDeNaissance(cursor.getString(3));
            utilisateur.setDateInscription(cursor.getString(4));
//            utilisateur.setAdresse(cursor.getString(5));
//            utilisateur.setStatut(cursor.getString(5));
            cursor.close();
        } else {
            utilisateur = null;
        }
        db.close();
        return utilisateur;
    }

    /**
     * Supprime un utilisateur
     * @param nom
     * @return true si supprimé, false si non supprimé
     */
    public boolean deleteUtilisateur(String nom) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_UTILISATEUR + " WHERE " + "nom" + " =  \"" + nom + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Utilisateur utilisateur = new Utilisateur();
        if (cursor.moveToFirst()) {
            utilisateur.set_id(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_UTILISATEUR, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(utilisateur.get_id()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    /**
     * Création des tables
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_ADRESSE = "CREATE TABLE " + TABLE_ADRESSE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "numero INTEGER, " +
                "rue TEXT, " +
                "ville TEXT, " +
                "codePostal TEXT, " +
                "pays TEXT)";

        String CREATE_TABLE_UTILISATEUR = "CREATE TABLE " + TABLE_UTILISATEUR + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nom TEXT," +
                "prenom TEXT, " +
                "dateDeNaissance TEXT, " +
                "dateInscription TEXT, " +
                "adresse TEXT, " +
                "statut TEXT, " +
                "login TEXT NOT NULL, " +
                "password TEXT NOT NULL)";

        String CREATE_TABLE_TRAJET = "CREATE TABLE " + TABLE_TRAJET + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                "depart INTEGER," +
                "destination INTEGER, " +
                "conducteur INTEGER, " +
                "passagers INTEGER)";

        db.execSQL(CREATE_TABLE_ADRESSE);
        db.execSQL(CREATE_TABLE_UTILISATEUR);
        db.execSQL(CREATE_TABLE_TRAJET);
    }

    /**
     * Suppression puis recréation des tables
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADRESSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAJET);
        onCreate(db);
    }

} 
