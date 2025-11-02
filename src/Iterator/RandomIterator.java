package Iterator;

import Playlist.ConcretePlaylist;
import Playlist.Song;

import java.util.Random;

public class RandomIterator implements SongIterator {
    private ConcretePlaylist playList;
    private int cIndex;
    private final Random random;

    public RandomIterator(ConcretePlaylist playList) {
        this.playList = playList;
        this.cIndex = 0;
        this.random = new Random();
    }

    @Override
    public boolean hasNext() {
        return cIndex < playList.getSongs().size();
    }

    @Override
    public Song next() {
        if (hasNext()) {
            int randomIndex = random.nextInt(playList.getSongs().size());
            Song song = playList.getSongs().get(randomIndex);
            cIndex++;
            return song;
        }
        return null;
    }
}
