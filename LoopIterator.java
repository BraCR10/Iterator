public class LoopIterator implements SongIterator {
    private ConcretePlaylist playList;
    private int cIndex;

    public LoopIterator(ConcretePlaylist playList) {
        this.playList = playList;
        this.cIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return playList.getSongs().size() > 0;
    }

    @Override
    public Song next() {
        if (playList.getSongs().size() == 0) {
            return null;
        }

        Song song = playList.getSongs().get(cIndex);
        cIndex = (cIndex + 1) % playList.getSongs().size();
        return song;
    }
}
