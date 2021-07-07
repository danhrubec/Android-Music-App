package course.examples.Services.KeyService;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import course.examples.Services.KeyCommon.KeyGenerator;


//using the sample code from Blackboard to make a basis of the Music Central Client.


public class KeyGeneratorImpl extends Service {

	// Set of already assigned IDs
	// Note: These keys are not guaranteed to be unique if the Service is killed 
	// and restarted.
	
	private final static Set<UUID> mIDs = new HashSet<UUID>();

	List<String> sNames = Arrays.asList("Paisley Park", "Ripple", "Purple Haze", "Man in the Mirror", "Paradise City",
			"Enter Sandman", "Bohemian Rhapsody", "Norwegian Wood", "Do it Again", "Even Flow");

	List<String> artists = Arrays.asList("Prince", "Grateful Dead", "Jimi Hendrix", "Michael Jackson", "Guns N' Roses",
			"Metallica", "Queen", "The Beatles", "Steely Dan", "Pearl Jam");

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

	ArrayList<Bitmap> bmArray = new ArrayList<Bitmap>();



	private final KeyGenerator.Stub mBinder = new KeyGenerator.Stub() {


		public List<String> getSongNames() {

			UUID id;

			// Acquire lock to ensure exclusive access to mIDs
			// Then examine and modify mIDs

			checkCallingPermission("course.examples.Services.KeyService.GEN_ID") ;
			synchronized (mIDs) {

				do {

					id = UUID.randomUUID();

				} while (mIDs.contains(id));

				mIDs.add(id);
			}
			return sNames;
		}
		public List<String> getArtists() {

			UUID id;

			// Acquire lock to ensure exclusive access to mIDs
			// Then examine and modify mIDs

			checkCallingPermission("course.examples.Services.KeyService.GEN_ID") ;
			synchronized (mIDs) {

				do {

					id = UUID.randomUUID();

				} while (mIDs.contains(id));

				mIDs.add(id);
			}
			return artists;
		}

		public List<String> getSongUrls() {
			UUID id;

			// Acquire lock to ensure exclusive access to mIDs
			// Then examine and modify mIDs

			checkCallingPermission("course.examples.Services.KeyService.GEN_ID") ;
			synchronized (mIDs) {

				do {

					id = UUID.randomUUID();

				} while (mIDs.contains(id));

				mIDs.add(id);
			}
			return urls;
		}

		public Bitmap getBm(int id)
		{
			Bitmap b;

			synchronized (bmArray)
			{
				b = bmArray.get(id);
			}


			return b;
		}

		public void populateBitMapArray()
		{
			bmArray.add(BitmapFactory.decodeResource(getResources(),R.drawable.image0));
			bmArray.add(BitmapFactory.decodeResource(getResources(),R.drawable.image1));
			bmArray.add(BitmapFactory.decodeResource(getResources(),R.drawable.image2));
			bmArray.add(BitmapFactory.decodeResource(getResources(),R.drawable.image3));
			bmArray.add(BitmapFactory.decodeResource(getResources(),R.drawable.image4));
			bmArray.add(BitmapFactory.decodeResource(getResources(),R.drawable.image5));
			bmArray.add(BitmapFactory.decodeResource(getResources(),R.drawable.image6));
			bmArray.add(BitmapFactory.decodeResource(getResources(),R.drawable.image7));
			bmArray.add(BitmapFactory.decodeResource(getResources(),R.drawable.image8));
			bmArray.add(BitmapFactory.decodeResource(getResources(),R.drawable.image9));

		}
	};



	// Return the Stub defined above
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
}
