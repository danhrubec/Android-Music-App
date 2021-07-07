package course.examples.Services.KeyClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import course.examples.Services.KeyCommon.KeyGenerator;

public class KeyServiceUser extends Activity {

	protected static final String TAG = "KeyServiceUser";
	protected static final int PERMISSION_REQUEST = 0;
	private KeyGenerator mKeyGeneratorService;
	private boolean mIsBound = false;
	private List<String>  songNames;
	private List<String>  artistList;
	private List<String>  urlList;
	private ArrayList<Bitmap> bmList;
	private Bitmap testbm;
	private ImageView songCoverArt;
	private EditText songInput;
	boolean infoGot = false;
	boolean songFound = false;
	private final int MAX_SONGS = 10;
	private int passableArrPos = -1;

	private MediaPlayer player;

	private TextView songUI;
	private TextView artistUI;



	@SuppressLint("WrongViewCast")
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);




		setContentView(R.layout.main);
		bmList = new ArrayList<Bitmap>();
		final Button goButton = (Button) findViewById(R.id.go_button);

		final Button binding = (Button) findViewById(R.id.bindbutton);
		final Button unbinding = (Button) findViewById(R.id.unbindservice);

		songInput = (EditText) findViewById(R.id.songInput);

		final Button songFinder = (Button) findViewById(R.id.findSong);
		final Button playClip = (Button) findViewById(R.id.playClipButton);
		final Button load2ndAct = (Button) findViewById(R.id.goToActivity);



		final TextView bindStat = (TextView) findViewById(R.id.bindStatus);
		songUI = (TextView) findViewById(R.id.songNameUI);
		artistUI = (TextView) findViewById(R.id.artistUI);

		songCoverArt = (ImageView) findViewById(R.id.coverArt);

		goButton.setEnabled(false);
		unbinding.setEnabled(false);
		binding.setEnabled(true);
		load2ndAct.setEnabled(false);
		playClip.setEnabled(false);
		songFinder.setEnabled(false);


		binding.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				System.out.println("Binding.");
				if (checkSelfPermission("course.examples.Services.KeyService.GEN_ID")
						!= PackageManager.PERMISSION_GRANTED) {
					ActivityCompat.requestPermissions(KeyServiceUser.this,
							new String[]{"course.examples.Services.KeyService.GEN_ID"},
							PERMISSION_REQUEST);
				}
				else {
					System.out.println("Going into checkbinding and bind.");
					checkBindingAndBind();
					bindStat.setText("Current Bind Status: BOUND");
				}

				if(mIsBound)
				{
					System.out.println("Service is bound.");
					bindStat.setText("Current Bind Status: BOUND");
				}
				goButton.setEnabled(true);
				binding.setEnabled(false);

				unbinding.setEnabled(true);
				if(infoGot)
				{
					songFinder.setEnabled(true);
				}
				else
				{
					songFinder.setEnabled(false);
				}

				if(songFound)
				{
					playClip.setEnabled(true);
				}




			}
		});

		unbinding.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mIsBound) {
					try{
						unbindService(KeyServiceUser.this.mConnection);
						System.out.println("completed unbind service.");
						bindStat.setText("Current Bind Status: NOT BOUND");
					}
					catch(Exception e)
					{
						bindStat.setText("Current Bind Status: NOT BOUND");
						Log.i(TAG, "SERVICE ALREADY UNBOUND!");
					}

				}

				stop(v);
				goButton.setEnabled(false);
				unbinding.setEnabled(false);
				binding.setEnabled(true);
				load2ndAct.setEnabled(false);
				playClip.setEnabled(false);
				songFinder.setEnabled(false);

			}

		});

		goButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				try {

					// Call KeyGenerator and get a new ID
					if (mIsBound)
					{
						songNames = mKeyGeneratorService.getSongNames();
						artistList = mKeyGeneratorService.getArtists();
						urlList = mKeyGeneratorService.getSongUrls();
						mKeyGeneratorService.populateBitMapArray();
						//add all of the bitmaps into an array
						for(int i = 0; i < MAX_SONGS; i++)
						{
							bmList.add(mKeyGeneratorService.getBm(i));
						}



						System.out.println("Printing all info");
						System.out.println(songNames);
						System.out.println(artistList);
						System.out.println(urlList);

					} else {
						Log.i(TAG, "Ugo says that the service was not bound!");
					}

				} catch (RemoteException e) {

					Log.e(TAG, e.toString());

				}

				infoGot = true;
				if(infoGot)
				{
					load2ndAct.setEnabled(true);
					songFinder.setEnabled(true);
				}
				else
				{
					load2ndAct.setEnabled(true);
					songFinder.setEnabled(false);
				}
			}

		});

		songFinder.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String attemptedQuery = songInput.getText().toString();
				int arrPos = -1;
				System.out.println("Starting Search");
				for(int i = 0; i < songNames.size();i++)
				{
					if(attemptedQuery.equals(songNames.get(i)))
					{
						arrPos = i;
						songFound = true;
					}
				}
				System.out.println(arrPos);

				if(songFound)
				{
					playClip.setEnabled(true);
				}

				if(arrPos != -1)
				{
					songUI.setText(songNames.get(arrPos));
					artistUI.setText(artistList.get(arrPos));
					songCoverArt.setImageBitmap(bmList.get(arrPos));
					passableArrPos = arrPos;
				}



			}

		});


		playClip.setOnClickListener(new OnClickListener() {

			public void onClick(View v)
			{
				if(passableArrPos != -1)
				{
					play(v);
				}
			}

		});


		load2ndAct.setOnClickListener(new OnClickListener() {

			public void onClick(View v)
			{
				goNext(v);
			}

		});
	}



	protected void checkBindingAndBind()
	{
		if (!mIsBound) {

			boolean b = false;
			Intent i = new Intent(KeyGenerator.class.getName());

			ResolveInfo info = getPackageManager().resolveService(i, 0);
			i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));

			b = bindService(i, this.mConnection, Context.BIND_AUTO_CREATE);




			if (b) {
				Log.i(TAG, "Ugo says bindService() succeeded!");
			} else {
				Log.i(TAG, "Ugo says bindService() failed!");
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case PERMISSION_REQUEST: {

				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

					// Permission granted, go ahead and display map

					checkBindingAndBind();
				}
				else {
					Toast.makeText(this, "BUMMER: No Permission :-(", Toast.LENGTH_LONG).show() ;
				}
			}
			default: {
				// do nothing
			}
		}
	}

	private final ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder iservice) {

			mKeyGeneratorService = KeyGenerator.Stub.asInterface(iservice);

			mIsBound = true;

		}

		public void onServiceDisconnected(ComponentName className) {

			mKeyGeneratorService = null;

			mIsBound = false;

		}
	};

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
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


	public void goNext(View v)
	{
		//create the intent to go to the next class
		Intent i = new Intent(this,SecondActivity.class);
		//create a bundle to be able to send our junk over
		Bundle args = new Bundle();
		args.putSerializable("SONGS", (Serializable) songNames);
		args.putSerializable("ARTISTS", (Serializable) artistList);



		ArrayList<byte[]> compressedImgs = new ArrayList<byte[]>();
		for(int j = 0; j < MAX_SONGS;j++)
		{
			//System.out.println(j);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bmList.get(j).compress(Bitmap.CompressFormat.JPEG,5, stream);
			byte[] bArray = stream.toByteArray();
			compressedImgs.add(bArray);
		}


		args.putSerializable("COVERART", (Serializable) compressedImgs);

		i.putExtra("BUNDLE", args);
		startActivity(i);
	}


}
