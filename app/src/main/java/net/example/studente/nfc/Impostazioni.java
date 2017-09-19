package net.example.studente.nfc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Impostazioni extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList lista = new ArrayList();

        lista.add("Albero Tag");
        lista.add("Valle della Luna");
        lista.add("Aggiungi Tag");
        lista.add("Modifica Tag");
        lista.add("Impostazioni avanzate");

        listView = (ListView) findViewById(R.id.lista);

        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        listView.setAdapter(itemsAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    Intent i = new Intent(Impostazioni.this,MainActivity.class);
                    startActivity(i);
                    itemsAdapter.notifyDataSetChanged();
                }
                if(position==1) {
                    Intent i = new Intent(Impostazioni.this,ValleDellaLuna.class);
                    startActivity(i);
                    itemsAdapter.notifyDataSetChanged();
                }
                if(position==2) {
                    Intent i = new Intent(Impostazioni.this,Tag.class);
                    startActivity(i);
                    itemsAdapter.notifyDataSetChanged();
                }
            }
        });


    }

}
