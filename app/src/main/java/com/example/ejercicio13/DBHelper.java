package com.example.ejercicio13;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "personas.db";
    private static final String TABLA_PERSONAS = "personas";

    private static final String COL_ID = "id";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_APELLIDO = "apellido";
    private static final String COL_EDAD = "edad";
    private static final String COL_CORREO = "correo";
    private static final String COL_DIRECCION = "direccion";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String crearTabla = "CREATE TABLE " + TABLA_PERSONAS + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NOMBRE + " TEXT,"
                + COL_APELLIDO + " TEXT,"
                + COL_EDAD + " INTEGER,"
                + COL_CORREO + " TEXT,"
                + COL_DIRECCION + " TEXT" + ")";
        db.execSQL(crearTabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_PERSONAS);
        onCreate(db);
    }

    public long agregarPersona(Persona persona) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOMBRE, persona.getNombre());
        values.put(COL_APELLIDO, persona.getApellido());
        values.put(COL_EDAD, persona.getEdad());
        values.put(COL_CORREO, persona.getCorreo());
        values.put(COL_DIRECCION, persona.getDireccion());
        long id = db.insert(TABLA_PERSONAS, null, values);
        db.close();
        return id;
    }

    public Persona obtenerPersona(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLA_PERSONAS, new String[] { COL_ID, COL_NOMBRE, COL_APELLIDO, COL_EDAD, COL_CORREO, COL_DIRECCION },
                COL_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Persona persona = new Persona(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5));
        cursor.close();
        db.close();
        return persona;
    }

    public List<Persona> obtenerTodasPersonas() {
        List<Persona> listaPersonas = new ArrayList<Persona>();
        String selectQuery = "SELECT  * FROM " + TABLA_PERSONAS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Persona persona = new Persona();
                persona.setId(cursor.getInt(0));
                persona.setNombre(cursor.getString(1));
                persona.setApellido(cursor.getString(2));
                persona.setEdad(cursor.getInt(3));
                persona.setCorreo(cursor.getString(4));
                persona.setDireccion(cursor.getString(5));
                listaPersonas.add(persona);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaPersonas;
    }

    public int actualizarPersona(Persona persona) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOMBRE, persona.getNombre());
        values.put(COL_APELLIDO, persona.getApellido());
        values.put(COL_EDAD, persona.getEdad());
        values.put(COL_CORREO, persona.getCorreo());
        values.put(COL_DIRECCION, persona.getDireccion());
        return db.update(TABLA_PERSONAS, values, COL_ID + "=?", new String[] { String.valueOf(persona.getId()) });
    }

    public void borrarPersona(Persona persona) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLA_PERSONAS, COL_ID + "=?", new String[] { String.valueOf(persona.getId()) });
        db.close();
    }

    public int contarPersonas() {
        String contarQuery = "SELECT * FROM " + TABLA_PERSONAS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(contarQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
}

