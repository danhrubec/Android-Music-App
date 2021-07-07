package course.examples.Services.KeyClient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondActivity extends Activity {
    ArrayList<String> songs;
    ArrayList<String> artistList;
    ArrayList<Bitmap> albumArt;
    RecyclerView listSongView;
    MyAdapter adapter;
    RVClickListener listener;
    private MediaPlayer player;
    int passableArrPos = 0;
    private final int MAX_SONGS = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        listSongView =(RecyclerView)findViewById(R.id.recycleView);

        List<String> sNames = Arrays.asList("0.0", "0.1", "0.2", "0.3", "0.4",
                "0.5", "0.6", "0.7", "0.8", "0.9");

        List<String> artists = Arrays.asList("1.0", "1.1", "1.2", "1.3", "1.4",
                "1.5", "1.6", "1.7", "1.8", "1.9");

        List<String> urls = Arrays.asList(
                "https://www.youtube.com/watch?v=1mhL5ynXzB0&ab_channel=Prince",
                "https://www.youtube.com/watch?v=sFBQSx_xc2o&ab_channel=GratefulDead-Topic",
                "https://www.youtube.com/watch?v=WGoDaYjdfSg&ab_channel=JimiHendrixVEVO",
                "https://www.youtube.com/watch?v=Z9NYDgbKsBE&ab_channel=MichaelJackson-Topic",
                "https://www.youtube.com/watch?v=T0ZmErXkbxE&ab_channel=GunsN%27Roses-Topic",
                "https://www.youtube.com/watch?v=YxRxd8aNd6I&ab_channel=Metallica-Topic",
                "https://www.youtube.com/watch?v=fJ9rUzIMcZQ&ab_channel=QueenOfficial",
                "https://www.youtube.com/watch?v=Y_V6y1ZCg_8&ab_channel=TheBeatles-Topic",
                "https://www.youtube.com/watch?v=jmdiKePVUy8&ab_channel=SteelyDan-Topic",
                "https://www.youtube.com/watch?v=u7T2OR-O2Vk&ab_channel=PearlJam-Topic"
        );




        songs = new ArrayList<>();
        songs.addAll(sNames);

        artistList = new ArrayList<>();
        artistList.addAll(artists);

        albumArt = new ArrayList<Bitmap>();
        //------------------------------getting the proper info from the other activity -------------------------


        Intent i2 = getIntent();
        Bundle args = i2.getBundleExtra("BUNDLE");

        songs = (ArrayList<String>) args.getSerializable("SONGS");
        artistList = (ArrayList<String>) args.getSerializable("ARTISTS");

        ArrayList<byte[]> compressedImgs =  ( ArrayList<byte[]>) args.getSerializable("COVERART");

        for(int j = 0; j < MAX_SONGS; j++)
        {
            Bitmap tempbm = BitmapFactory.decodeByteArray(compressedImgs.get(j),0,compressedImgs.get(j).length);
            albumArt.add(tempbm);

        }



        listener = (view, position)->{

            passableArrPos = position;
            play(view);

        };


        adapter = new MyAdapter(songs, artistList, albumArt,listener,this);
        listSongView.setHasFixedSize(true);
        listSongView.setAdapter(adapter);
        listSongView.setLayoutManager(new LinearLayoutManager(this));
    }



    public void play(View v)
    {
        if(player == null)
        {
            if(passableArrPos == 0)
            {
                player = MediaPlayer.create(this,R.raw.song0);
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopPlayer();
                    }
                });
            }
            else if(passableArrPos == 1)
            {
                player = MediaPlayer.create(this,R.raw.song1);
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopPlayer();
                    }
                });
            }
            else if(passableArrPos == 2)
            {
                player = MediaPlayer.create(this,R.raw.song2);
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopPlayer();
                    }
                });
            }
            else if(passableArrPos == 3)
            {
                player = MediaPlayer.create(this,R.raw.song3);
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopPlayer();
                    }
                });
            }
            else if(passableArrPos == 4)
            {
                player = MediaPlayer.create(this,R.raw.song4);
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopPlayer();
                    }
                });
            }
            else if(passableArrPos == 5)
            {
                player = MediaPlayer.create(this,R.raw.song5);
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopPlayer();
                    }
                });
            }
            else if(passableArrPos == 6)
            {
                player = MediaPlayer.create(this,R.raw.song6);
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopPlayer();
                    }
                });
            }
            else if(passableArrPos == 7)
            {
                player = MediaPlayer.create(this,R.raw.song7);
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopPlayer();
                    }
                });
            }
            else if(passableArrPos == 8)
            {
                player = MediaPlayer.create(this,R.raw.song8);
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopPlayer();
                    }
                });
            }
            else if(passableArrPos == 9)
            {
                player = MediaPlayer.create(this,R.raw.song9);
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopPlayer();
                    }
                });
            }



        }
        else
        {
            System.out.println("If player is active");
            stop(v);
            play(v);
        }

        player.start();

    }

    public void pause(View v)
    {
        if(player != null)
        {
            player.pause();
        }

    }

    public void stop(View v)
    {
        stopPlayer();

    }

    private void stopPlayer()
    {
        if(player != null)
        {
            player.release();
            player = null;
            System.out.println("Releasing media player.");
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
        stopPlayer();
    }
}