package com.gabri.crudandroid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Listar_itens extends AppCompatActivity {
    private DatabaseManager dataBaseManager = null;
    private SQLiteDatabase bancoDeDados = null;
    private ListView lista = null;
    private Cursor listaitem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_itens);

        dataBaseManager = new DatabaseManager(this, "aplicacaodb", 1);
        bancoDeDados = dataBaseManager.getWritableDatabase();

        lista = (ListView) findViewById(R.id.lv_lista);

        String[] campos_item = {"id", "descricao", "quantidade"};
        listaitem = bancoDeDados.query("item", campos_item, null, null, null, null, null);

        listaitem.moveToFirst();

        ArrayList list = new ArrayList();
        for (int i = 0; i < listaitem.getCount(); i++) {
            list.add("Item inserido: " + listaitem.getString(listaitem.getColumnIndexOrThrow("descricao")) +
                    " ( " + listaitem.getString(listaitem.getColumnIndexOrThrow("quantidade")) + " unidades )");
            listaitem.moveToNext();
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lista.setAdapter(arrayAdapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String codigo;
                listaitem.moveToPosition(position);
                codigo = listaitem.getString(listaitem.getColumnIndexOrThrow("id"));
                Intent intent = new Intent(Listar_itens.this, Editar_excluir.class);
                intent.putExtra("id", codigo);
                startActivity(intent);
                finish();
            }
        });
        bancoDeDados.close();
    }

}





