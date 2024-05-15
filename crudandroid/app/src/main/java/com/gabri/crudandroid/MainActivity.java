package com.gabri.crudandroid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText etItem = null;
    private EditText etQuantidade = null;
    private DatabaseManager dataBaseManager = null;
    private SQLiteDatabase bancoDeDados = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseManager = new DatabaseManager(this, "aplicacaodb", 1);
        bancoDeDados = dataBaseManager.getWritableDatabase();

        etItem = (EditText) findViewById(R.id.et_item);
        etQuantidade = (EditText) findViewById(R.id.et_quantidade);

        Button btBotaoEnviar = (Button) findViewById(R.id.bt_enviar);
        btBotaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues valores = new ContentValues();
                valores.put("descricao", etItem.getText().toString());
                valores.put("quantidade", etQuantidade.getText().toString());

                bancoDeDados = dataBaseManager.getWritableDatabase();
                long resultado = bancoDeDados.insert("item", null, valores);
                bancoDeDados.close();

                if (resultado == -1)
                    Toast.makeText(MainActivity.this, "Não foi possível inserir este item", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(MainActivity.this, "Item inserido: " + etItem.getText() + " n: " + etQuantidade.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btBotaoListar = (Button) findViewById(R.id.bt_listar);
        btBotaoListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            proximaTela();
            }
        });


        }
    public void proximaTela(){

        Intent lista = new Intent(this, Listar_itens.class);
        startActivity(lista);

    }
}