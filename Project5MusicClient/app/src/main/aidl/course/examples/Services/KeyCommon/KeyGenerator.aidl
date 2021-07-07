package course.examples.Services.KeyCommon;

   interface KeyGenerator {
       List<String> getSongNames();
       List<String> getSongUrls();
       List<String> getArtists();
       Bitmap getBm(int id);
       void populateBitMapArray();

   }