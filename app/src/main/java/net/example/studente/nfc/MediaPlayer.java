//package net.example.studente.nfc;
//MediaPlayer mediaPlayer;
//        Button buttonPlayPause;
//        ImageView Image;
//        SeekBar seekBar;
//        Handler handler;
//
//private int stateMediaPlayer;
//private final int stateMP_NotStarter = 0;
//private final int stateMP_Playing = 1;
//private final int stateMP_Pausing = 2;
//private int mediaPos;
//private int mediaMax;
//
///** Called when the activity is first created. */
//@Override
//public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.playerwere);
//
//        Image = (ImageView) findViewById(R.id.pdfview);
//
//        Image.setImageResource(R.drawable.wereim);
//
//        seekBar = (SeekBar) findViewById(R.id.seekBar);
//
//        buttonPlayPause = (Button) findViewById(R.id.playpause);
//
//        buttonPlayPause.setOnClickListener(buttonPlayPauseOnClickListener);
//
//        seekBar.setOnSeekBarChangeListener(seekBarOnSeekChangeListener);
//
//        initMediaPlayer();
//
//        }
//
//private void initMediaPlayer() {
//        handler = new Handler();
//        mediaPlayer = new MediaPlayer();
//        mediaPlayer = MediaPlayer.create(were.this, R.raw.were);
//        stateMediaPlayer = stateMP_NotStarter;
//        mediaPos = mediaPlayer.getCurrentPosition();
//
//        mediaMax = mediaPlayer.getDuration();
//
//        seekBar.setMax(mediaMax); // Set the Maximum range of the
//        // seekBar.setProgress(mediaPos);// set
//        // current progress to song's
//
//        handler.removeCallbacks(moveSeekBarThread);
//        handler.postDelayed(moveSeekBarThread, 100);
//        }
//
//private Runnable moveSeekBarThread = new Runnable() {
//
//public void run() {
//        if (mediaPlayer.isPlaying()) {
//
//        int mediaPos_new = mediaPlayer.getCurrentPosition();
//        int mediaMax_new = mediaPlayer.getDuration();
//        seekBar.setMax(mediaMax_new);
//        seekBar.setProgress(mediaPos_new);
//
//        handler.postDelayed(this, 100); // Looping the thread after 0.1
//        // second
//        // seconds
//        }
//        }
//        };
//
//        Button.OnClickListener buttonPlayPauseOnClickListener = new Button.OnClickListener() {
//
//@Override
//public void onClick(View v) {
//        // TODO Auto-generated method stub
//        switch (stateMediaPlayer) {
//        case stateMP_NotStarter:
//        mediaPlayer.start();
//        buttonPlayPause
//        .setBackgroundResource(android.R.drawable.ic_media_pause);
//        stateMediaPlayer = stateMP_Playing;
//        break;
//        case stateMP_Playing:
//        mediaPlayer.pause();
//        buttonPlayPause
//        .setBackgroundResource(android.R.drawable.ic_media_play);
//        stateMediaPlayer = stateMP_Pausing;
//        break;
//        case stateMP_Pausing:
//        mediaPlayer.start();
//        buttonPlayPause
//        .setBackgroundResource(android.R.drawable.ic_media_pause);
//        stateMediaPlayer = stateMP_Playing;
//        break;
//        }
//
//        }
//        };
//
//        SeekBar.OnSeekBarChangeListener seekBarOnSeekChangeListener = new SeekBar.OnSeekBarChangeListener() {
//
//@Override
//public void onStopTrackingTouch(SeekBar seekBar) {
//        // TODO Auto-generated method stub
//
//        }
//
//@Override
//public void onStartTrackingTouch(SeekBar seekBar) {
//        // TODO Auto-generated method stub
//
//        }
//
//@Override
//public void onProgressChanged(SeekBar seekBar, int progress,
//        boolean fromUser) {
//        // TODO Auto-generated method stub
//
//        if (fromUser) {
//        mediaPlayer.seekTo(progress);
//        seekBar.setProgress(progress);
//        }
//
//        }
//        };qec