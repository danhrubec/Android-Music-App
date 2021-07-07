package course.examples.Services.KeyClient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<String> songList; //data: the names displayed
    private ArrayList<String> artistList;
    private ArrayList<ImageView> coverList;
    private ArrayList<Bitmap> coverart;
    private RVClickListener RVlistener; //listener defined in main activity
    private Context context;

    private static int songcoverindex = 0;

    /*
    passing in the data and the listener defined in the main activity.
    also include the context so that the context menu can open up the links properly, other wise it will crash
     */
    public MyAdapter(ArrayList<String> songs, ArrayList<String> artists, ArrayList<Bitmap> img, RVClickListener listener, Context ctx){
        songList = songs;
        artistList = artists;
        this.RVlistener = listener;
        coverart = img;
        context = ctx;

    }

    //on create menthod. Pretty much identically the same as the code from BB. No need to have changed it in the
    //context of this prokject/
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //defining the parent context, menu inflaters and the view holders.
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listView = inflater.inflate(R.layout.rv_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listView, RVlistener);

        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        //displays the text on the text view. Displays the song and artist for the song
        holder.song.setText(songList.get(position));
        holder.artist.setText(artistList.get(position));
        holder.cover.setImageBitmap(coverart.get(position));



    }

    //simply returns the song list for the song
    @Override
    public int getItemCount()
    {
        return songList.size();
    }




   //view holder class being the main driving force for the recycler view.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView song;
        public TextView artist;
        public ImageView cover;
        private RVClickListener listener;
        private View itemView;


        public ViewHolder(@NonNull View itemView, RVClickListener passedListener) {
            super(itemView);
            song = (TextView) itemView.findViewById(R.id.textView);
            artist = (TextView) itemView.findViewById(R.id.textView2);
            cover = (ImageView) itemView.findViewById(R.id.imageView);
            //now we need to add in the images for the cover arts


            cover.setMaxWidth(200);
            cover.setMaxHeight(200);




            this.itemView = itemView;

            this.listener = passedListener;


            itemView.setOnClickListener(this); //set short click listener
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        //  Toast.makeText(context, "ack",Toast.LENGTH_SHORT).show();
            Log.i("ON_CLICK", "in the onclick in view holder");
        }


        };



    }





