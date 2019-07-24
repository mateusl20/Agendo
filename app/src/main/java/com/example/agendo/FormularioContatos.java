package com.example.agendo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.agendo.BDHelper.ContatosBD;
import com.example.agendo.model.Contatos;

public class FormularioContatos extends AppCompatActivity {
    EditText txtNome, txtFone, txtEmail;
    Button btnSalvar;
    Contatos editarContato, novoContato;
    ContatosBD bdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        novoContato = new Contatos();
        setContentView(R.layout.activity_formulario_contatos);

        Intent intent = getIntent();
        editarContato = (Contatos) intent.getSerializableExtra("contato-escolhido");

        bdHelper = new ContatosBD(FormularioContatos.this);

        txtNome = (EditText) findViewById(R.id.txtNome);
        txtFone = (EditText) findViewById(R.id.txtFone);
        txtEmail = (EditText) findViewById(R.id.txtEmail);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        if (editarContato != null){
            btnSalvar.setText("EDITAR");

            txtNome.setText(editarContato.getNome());
            txtFone.setText(editarContato.getTelefone());
            txtEmail.setText(editarContato.getEmail());

            editarContato.setId(editarContato.getId());

        }else{
            btnSalvar.setText("ADICIONAR");
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novoContato.setNome(txtNome.getText().toString());
                novoContato.setTelefone(txtFone.getText().toString());
                novoContato.setEmail(txtEmail.getText().toString());

               if (btnSalvar.getText().toString().equals("ADICIONAR")) {
                   bdHelper.salvarContato(novoContato);
                   bdHelper.close();
               }else{
                   bdHelper.editarContato(novoContato);
                   bdHelper.close();
               }
               finish();
            }
        });
    }
}
