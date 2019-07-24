package com.example.agendo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.agendo.BDHelper.ContatosBD;
import com.example.agendo.model.Contatos;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    ContatosBD bdHelper;

    Contatos contato;
    ArrayList<Contatos> listaContatos;

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.listView_Contatos);
        registerForContextMenu(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                Contatos contatoEscolhido = (Contatos) adapter.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, FormularioContatos.class);
                intent.putExtra("contato-escolhido",contatoEscolhido);
                startActivity(intent);
            }
        });

        Button btnAdicionar = (Button) findViewById(R.id.btnAdicionar);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormularioContatos.class);
                startActivity(intent);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int i, long l) {
                contato = (Contatos) adapter.getItemAtPosition(i);
                return false;
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Deletar Contato");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                bdHelper = new ContatosBD(MainActivity.this);
                bdHelper.deletarContato(contato);
                bdHelper.close();
                carregarContato();
                return true;
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        carregarContato();
    }

    public void carregarContato(){
        bdHelper = new ContatosBD(MainActivity.this);
        listaContatos = bdHelper.getListaContatos();
        bdHelper.close();

        if (listaContatos != null) {
            adapter = new ArrayAdapter<Contatos>(MainActivity.this, android.R.layout.simple_list_item_1, listaContatos);
            lista.setAdapter(adapter);
        }
    }
}
