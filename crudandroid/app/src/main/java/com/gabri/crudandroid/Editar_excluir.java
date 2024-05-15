package com.gabri.crudandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

public class Editar_excluir extends AppCompatActivity {

    private EditText et_nome = null;
    private EditText et_quantidade = null;
    private DatabaseManager dataBaseManager = null;
    private SQLiteDatabase bancoDeDados = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_excluir);

        dataBaseManager = new DatabaseManager(this, "aplicacaodb", 1);

        et_nome = (EditText) findViewById(R.id.et_nome);
        et_quantidade = (EditText) findViewById(R.id.et_quantidade);

        String codigo = this.getIntent().getStringExtra("id");

        Cursor cursor = carregaDadoById(Integer.parseInt(codigo));

        et_nome.setText(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
        et_quantidade.setText(cursor.getString(cursor.getColumnIndexOrThrow("quantidade")));


        Button btAlterar = (Button) findViewById(R.id.bt_alterar);
        btAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bancoDeDados = dataBaseManager.getWritableDatabase();
                ContentValues valores;
                String where;
                String novaDescricao;
                String novaQuantidade;

                valores = new ContentValues();
                novaDescricao = et_nome.getText().toString();
                novaQuantidade = et_quantidade.getText().toString();
                valores.put("descricao", novaDescricao);
                valores.put("quantidade", novaQuantidade);

                where = "id =" + cursor.getString(cursor.getColumnIndexOrThrow("id"));

                bancoDeDados.update("item", valores, where, null);
                abrirTelaListagem();
            }
        }); bancoDeDados.close();

        Button btExcluir = (Button) findViewById(R.id.bt_excluir);
        btExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String where;
                bancoDeDados = dataBaseManager.getWritableDatabase();
                where = "id =" + cursor.getString(cursor.getColumnIndexOrThrow("id"));
                bancoDeDados.delete("item", where, null);
                abrirTelaListagem();

            }
        }); bancoDeDados.close();

    }

    public Cursor carregaDadoById(int id){
        String[] campos_item = {"id", "descricao", "quantidade"};
        String where =  "id =" + id;
        bancoDeDados = dataBaseManager.getReadableDatabase();
        Cursor cursor = bancoDeDados.query("item", campos_item,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        bancoDeDados.close();
        return cursor;
    }
    public void abrirTelaListagem(){

        Intent lista = new Intent(this, Listar_itens.class);
        startActivity(lista);

    }

}

