import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayerClient {
    private List<ConcretePlaylist> playlists;
    private List<Song> allSongs;
    private Scanner scanner;
    private ConcretePlaylist currentPlaylist;
    private SongIterator currentIterator;
    private Song currentlyPlaying;
    private boolean isPlaying;

    public MusicPlayerClient() {
        this.playlists = new ArrayList<>();
        this.allSongs = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.currentlyPlaying = null;
        this.isPlaying = false;
    }

    public void play(ConcretePlaylist p) {
        if (p.getSongs().isEmpty()) {
            System.out.println("La playlist esta vacia. No se puede reproducir.");
            return;
        }

        currentPlaylist = p;
        currentIterator = p.createIterator();

        if (currentIterator.hasNext()) {
            currentlyPlaying = currentIterator.next();
            isPlaying = true;
            System.out.println("Reproduciendo: " + currentlyPlaying);
        }
    }

    public void start() {
        boolean running = true;

        while (running) {
            displayMenu();

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createPlaylist();
                    break;
                case "2":
                    createSong();
                    break;
                case "3":
                    addSongToPlaylist();
                    break;
                case "4":
                    changePlaybackMode();
                    break;
                case "5":
                    playPlaylist();
                    break;
                case "6":
                    nextSong();
                    break;
                case "7":
                    running = false;
                    System.out.println("Saliendo del reproductor...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intenta de nuevo.");
            }

            System.out.println();
        }
    }

    private void displayMenu() {
        System.out.println("=".repeat(50));
        if (isPlaying && currentlyPlaying != null) {
            System.out.println("REPRODUCIENDO: " + currentlyPlaying);
            System.out.println("Modo: " + (currentPlaylist != null ? currentPlaylist.getIteratorType() : "N/A"));
        } else {
            System.out.println("Estado: Detenido");
        }
        System.out.println("=".repeat(50));
        System.out.println("\nMENU PRINCIPAL:");
        System.out.println("1. Crear playlist");
        System.out.println("2. Crear cancion");
        System.out.println("3. Anadir cancion a playlist");
        System.out.println("4. Cambiar modo de reproduccion");
        System.out.println("5. Reproducir playlist");
        System.out.println("6. Siguiente cancion");
        System.out.println("7. Salir");
        System.out.print("\nSelecciona una opcion: ");
    }

    private void createPlaylist() {
        System.out.print("Nombre de la playlist: ");
        String name = scanner.nextLine();

        for (ConcretePlaylist pl : playlists) {
            if (pl.getName().equalsIgnoreCase(name)) {
                System.out.println("Error: Ya existe una playlist con ese nombre.");
                return;
            }
        }

        ConcretePlaylist newPlaylist = new ConcretePlaylist(name);
        playlists.add(newPlaylist);
        System.out.println("Playlist '" + name + "' creada exitosamente.");
    }

    private void createSong() {
        System.out.print("Titulo de la cancion: ");
        String title = scanner.nextLine();

        for (Song s : allSongs) {
            if (s.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Error: Ya existe una cancion con ese titulo.");
                return;
            }
        }

        System.out.print("Artista: ");
        String artist = scanner.nextLine();

        Song newSong = new Song(title, artist);
        allSongs.add(newSong);
        System.out.println("Cancion '" + title + "' creada exitosamente.");
    }

    private void addSongToPlaylist() {
        if (playlists.isEmpty()) {
            System.out.println("No hay playlists disponibles. Crea una primero.");
            return;
        }

        if (allSongs.isEmpty()) {
            System.out.println("No hay canciones disponibles. Crea una primero.");
            return;
        }

        System.out.println("\nPlaylists disponibles:");
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println((i + 1) + ". " + playlists.get(i).getName());
        }
        System.out.print("Selecciona una playlist: ");

        int playlistIndex;
        try {
            playlistIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (playlistIndex < 0 || playlistIndex >= playlists.size()) {
                System.out.println("Seleccion invalida.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida.");
            return;
        }

        ConcretePlaylist selectedPlaylist = playlists.get(playlistIndex);

        System.out.println("\nCanciones disponibles:");
        for (int i = 0; i < allSongs.size(); i++) {
            System.out.println((i + 1) + ". " + allSongs.get(i));
        }
        System.out.print("Selecciona una cancion: ");

        int songIndex;
        try {
            songIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (songIndex < 0 || songIndex >= allSongs.size()) {
                System.out.println("Seleccion invalida.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida.");
            return;
        }

        Song selectedSong = allSongs.get(songIndex);

        if (selectedPlaylist.containsSong(selectedSong.getTitle())) {
            System.out.println("Error: La cancion ya esta en la playlist.");
            return;
        }

        selectedPlaylist.addSong(selectedSong);
        System.out.println("Cancion '" + selectedSong.getTitle() + "' anadida a la playlist '" + selectedPlaylist.getName() + "'.");
    }

    private void changePlaybackMode() {
        if (playlists.isEmpty()) {
            System.out.println("No hay playlists disponibles. Crea una primero.");
            return;
        }

        System.out.println("\nPlaylists disponibles:");
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println((i + 1) + ". " + playlists.get(i).getName() + " [Modo actual: " + playlists.get(i).getIteratorType() + "]");
        }
        System.out.print("Selecciona una playlist: ");

        int playlistIndex;
        try {
            playlistIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (playlistIndex < 0 || playlistIndex >= playlists.size()) {
                System.out.println("Seleccion invalida.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida.");
            return;
        }

        ConcretePlaylist selectedPlaylist = playlists.get(playlistIndex);

        System.out.println("\nModos de reproduccion:");
        System.out.println("1. Sequential");
        System.out.println("2. Random");
        System.out.println("3. Loop");
        System.out.print("Selecciona un modo: ");

        String modeChoice = scanner.nextLine();
        String newMode;

        switch (modeChoice) {
            case "1":
                newMode = "sequential";
                break;
            case "2":
                newMode = "random";
                break;
            case "3":
                newMode = "loop";
                break;
            default:
                System.out.println("Opcion invalida.");
                return;
        }

        selectedPlaylist.setIteratorType(newMode);
        System.out.println("Modo de reproduccion cambiado a: " + newMode);

        if (currentPlaylist == selectedPlaylist && isPlaying) {
            currentIterator = selectedPlaylist.createIterator();
            System.out.println("El iterador de la playlist actual ha sido reiniciado.");
        }
    }

    private void playPlaylist() {
        if (playlists.isEmpty()) {
            System.out.println("No hay playlists disponibles. Crea una primero.");
            return;
        }

        System.out.println("\nPlaylists disponibles:");
        for (int i = 0; i < playlists.size(); i++) {
            ConcretePlaylist pl = playlists.get(i);
            System.out.println((i + 1) + ". " + pl.getName() + " (" + pl.getSongs().size() + " canciones) [Modo: " + pl.getIteratorType() + "]");
        }
        System.out.print("Selecciona una playlist para reproducir: ");

        int playlistIndex;
        try {
            playlistIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (playlistIndex < 0 || playlistIndex >= playlists.size()) {
                System.out.println("Seleccion invalida.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida.");
            return;
        }

        play(playlists.get(playlistIndex));
    }

    private void nextSong() {
        if (!isPlaying || currentIterator == null) {
            System.out.println("No hay reproduccion activa. Selecciona 'Reproducir playlist' primero.");
            return;
        }

        if (currentIterator.hasNext()) {
            currentlyPlaying = currentIterator.next();
            System.out.println("Reproduciendo: " + currentlyPlaying);
        } else {
            System.out.println("No hay mas canciones en la playlist.");
            isPlaying = false;
            currentlyPlaying = null;
        }
    }


}
