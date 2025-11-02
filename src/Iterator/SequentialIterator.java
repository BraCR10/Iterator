package Iterator;

import Playlist.ConcretePlaylist;
import Playlist.Song;

public class SequentialIterator implements SongIterator {
    private ConcretePlaylist playList;
    private int cIndex;

    public SequentialIterator(ConcretePlaylist playList) {
        this.playList = playList;
        this.cIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return cIndex < playList.getSongs().size();
    }

    @Override
    public Song next() {
        if (hasNext()) {
            Song song = playList.getSongs().get(cIndex);
            cIndex++;
            return song;
        }
        return null;
    }
}
