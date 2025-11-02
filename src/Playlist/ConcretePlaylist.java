package Playlist;

import Iterator.LoopIterator;
import Iterator.RandomIterator;
import Iterator.SequentialIterator;
import Iterator.SongIterator;

import java.util.List;
import java.util.ArrayList;

public class ConcretePlaylist implements PlayList {
    private List<Song> songs;
    private String name;
    private String iteratorType;

    public ConcretePlaylist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
        this.iteratorType = "sequential";
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public boolean containsSong(String title) {
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                return true;
            }
        }
        return false;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public String getName() {
        return name;
    }

    public void setIteratorType(String type) {
        this.iteratorType = type;
    }

    public String getIteratorType() {
        return iteratorType;
    }

    @Override
    public SongIterator createIterator() {
        switch (iteratorType.toLowerCase()) {
            case "sequential":
                return new SequentialIterator(this);
            case "random":
                return new RandomIterator(this);
            case "loop":
                return new LoopIterator(this);
            default:
                return new SequentialIterator(this);
        }
    }
}
