package net.example.studente.nfc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Tag extends AppCompatActivity {

    ImageButton imBut;
    ImageButton player;
    ImageButton audioChooser;
    SeekBar musicBar;
    private final int SELECT_PHOTO = 1;
    private final int SELECT_AUDIO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aggiungi_nfc);
        musicBar = (SeekBar) findViewById(R.id.musicBar);
        imBut = (ImageButton) findViewById(R.id.imBut);
        player = (ImageButton) findViewById(R.id.player);
        audioChooser = (ImageButton) findViewById(R.id.audioChooser);
        imBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, SELECT_PHOTO);//one can be replaced with any action code

            }

        });

        audioChooser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent pickAudio = new Intent(Intent.ACTION_PICK,
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickAudio, SELECT_AUDIO);
            }
        });

        player.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
    }

    public void seekbar() {
        musicBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    int progress_value;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imBut.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

}
