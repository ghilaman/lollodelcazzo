package net.example.studente.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    private NFCManager nfcMger;
    private View v;
    private NdefMessage message = null;
    private ProgressDialog dialog;
    Tag currentTag;
    private TextView mTextView;
    private NfcAdapter mNfcAdapter;
    public ImageView image;
    private ImageButton imBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nfcMger = new NFCManager(this);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            Toast.makeText(this, "Il dispositivo non supporta gli NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        handleIntent(getIntent());

        Button buttonEmo = (Button) findViewById(R.id.buttonEmo);
        Button buttonCibo = (Button) findViewById(R.id.buttonCibo);
        Button buttonBis = (Button) findViewById(R.id.buttonBis);
        Button buttonTag = (Button) findViewById(R.id.buttonTag);

        buttonEmo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Emozioni.class);
                startActivity(i);
            }
        });

        buttonCibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Cibo.class);
                startActivity(i);
            }
        });

        buttonBis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Bisogni.class);
                startActivity(i);
            }
        });

        buttonTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), net.example.studente.nfc.Tag.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupForegroundDispatch(this, mNfcAdapter);
    }

    @Override
    protected void onPause() {
        stopForegroundDispatch(this, mNfcAdapter);
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }



    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);
            } else {
                Log.d(TAG, "Mime type errato: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();
            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }

    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);
        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e){
            throw new RuntimeException("Check your mime type.");
        }
        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }


    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            image.setImageResource(R.drawable.source4);
            mTextView.setText("APPOGGIA LA CARTA");
            return true;
        }
        if(id==R.id.action){
            Intent i=new Intent(MainActivity.this, Impostazioni.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {
        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];
            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                return null;
            }
            NdefMessage ndefMessage = ndef.getCachedNdefMessage();
            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e(TAG, "Encoding non supportato", e);
                    }
                }
            }
            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {
            byte[] payload = record.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageCodeLength = payload[0] & 0063;
            // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");

            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }


        @Override
        protected void onPostExecute(String result) {
            String risultato=result;
            if (risultato != null) {
                 if (risultato.equals("Acqua"))
                {
                    image.setImageResource(R.drawable.acqua);
                    mTextView.setText("");
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.acqua);
                    mp.start();
                }
                else if (risultato.equals("SuccoPera")){
                    image.setImageResource(R.drawable.succo_pera);
                     mTextView.setText("");

                     MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.succo_pera);
                    mp.start();
                }
                else if (risultato.equals("MalDiPancia")){
                    image.setImageResource(R.drawable.pancia);
                     mTextView.setText("");

                     MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.mal_di_pancia);
                    mp.start();
                }
                else if (risultato.equals("Stanco")){
                    image.setImageResource(R.drawable.emozioni_stanco);
                     mTextView.setText("");

                     MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.stanco);
                    mp.start();
                }
                else
                    mTextView.setText("non ci sono immagini per il tag: "+ risultato);
            }
        }

    }
}