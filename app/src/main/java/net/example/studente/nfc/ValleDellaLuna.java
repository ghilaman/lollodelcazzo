package net.example.studente.nfc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ValleDellaLuna extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valle_della_luna);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText editText = (EditText) findViewById(R.id.editText2);

        Button search = (Button) findViewById(R.id.button5);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQ = (String) editText.getText().toString();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?q=" +searchQ + "&sp=CANQFA%253D%253D")));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
