package Iterator;

import Playlist.Song;

public interface SongIterator {
    boolean hasNext();
    Song next();
}
