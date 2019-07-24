package com.example.agendo.BDHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.agendo.model.Contatos;

import java.util.ArrayList;

public class ContatosBD extends SQLiteOpenHelper {

    private static final String DATABASE= "BDContatos";
    private static final int VERSION = 1;

    public ContatosBD (Context context){
        super(context, DATABASE,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        String contato ="CREATE TABLE contatos(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nomeContato TEXT NOT NULL, telefoneContato TEXT NOT NULL,emailContato TEXT NOT NULL);";
        bd.execSQL(contato);

    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int i, int i1) {
        String contato ="DROP TABLE IF EXISTS contatos;";
        bd.execSQL(contato);
    }

    public void editarContato(Contatos contato){
        ContentValues values = new ContentValues();

        values.put("nomeContato",contato.getNome());
        values.put("telefoneContato",contato.getTelefone());
        values.put("emailContato",contato.getEmail());

        String[] args = {String.valueOf(contato.getId())};
        getWritableDatabase().update("contatos",values,"id=?",args);
    }

    public void deletarContato(Contatos contato){
        String[] args = {String.valueOf(contato.getId())};
        getWritableDatabase().delete("contatos","id=?",args);

    }
    public void salvarContato(Contatos contato){
        ContentValues values = new ContentValues();

        values.put("nomeContato",contato.getNome());
        values.put("telefoneContato",contato.getTelefone());
        values.put("emailContato",contato.getEmail());

        getWritableDatabase().insert("contatos",null, values);
    }

    public ArrayList<Contatos> getListaContatos(){
        String[] columns = {"id","nomeContato","telefoneContato","emailContato"};
        Cursor cursor = getWritableDatabase().query("contatos", columns, null, null,
                null, null, null,null);

        ArrayList<Contatos> contatos = new ArrayList<Contatos>();

        while (cursor.moveToNext()){
            Contatos contato = new Contatos();
            contato.setId(cursor.getLong(0));
            contato.setNome(cursor.getString(1));
            contato.setTelefone(cursor.getString(3));
            contato.setEmail(cursor.getString(2));

            contatos.add(contato);
        }
        return contatos;
    }
}
