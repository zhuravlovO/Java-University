package Lab5.src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Головний клас.
 * Реалізує ієрархію композицій та роботу з альбомом.
 */
public class Main {
    public static void main(String[] args) {
        try {
            Album myAlbum = new Album("Legendary Mix");

            myAlbum.addTrack(new RockMusic("It's my life", 226));
            myAlbum.addTrack(new PopMusic("Flowers", 200));
            myAlbum.addTrack(new ClassicalMusic("Symphony No. 5", 420));
            myAlbum.addTrack(new RockMusic("Numb", 187));
            myAlbum.addTrack(new PopMusic("Bad Guy", 194));

            System.out.println("--- Original Album ---");
            System.out.println(myAlbum);

            System.out.println("Total Duration: " + myAlbum.getTotalDuration() + " sec");

            System.out.println("\n--- Sorted by Style ---");
            myAlbum.sortByStyle();
            System.out.println(myAlbum);

            int minSec = 180;
            int maxSec = 210;
            System.out.println("\n--- Finding tracks between " + minSec + " and " + maxSec + " sec ---");
            List<MusicComposition> found = myAlbum.findByDurationRange(minSec, maxSec);
            
            if (found.isEmpty()) {
                System.out.println("No tracks found.");
            } else {
                for (MusicComposition track : found) {
                    System.out.println("Found: " + track);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * Базовий клас. Трек.
 */
abstract class MusicComposition {
    private String title;
    private int durationInSeconds;
    private String style;

    public MusicComposition(String title, int durationInSeconds, String style) {
        this.title = title;
        this.durationInSeconds = durationInSeconds;
        this.style = style;
    }

    public int getDuration() {
        return durationInSeconds;
    }

    public String getStyle() {
        return style;
    }

    @Override
    public String toString() {
        return String.format("Title: %-15s | Style: %-10s | Duration: %3d sec", title, style, durationInSeconds);
    }
}

/**
 * Рок.
 */
class RockMusic extends MusicComposition {
    public RockMusic(String title, int duration) {
        super(title, duration, "Rock");
    }
}

/**
 * Поп.
 */
class PopMusic extends MusicComposition {
    public PopMusic(String title, int duration) {
        super(title, duration, "Pop");
    }
}

/**
 * Класика.
 */
class ClassicalMusic extends MusicComposition {
    public ClassicalMusic(String title, int duration) {
        super(title, duration, "Classical");
    }
}

/**
 * Альбом. Список треків.
 */
class Album {
    private String name;
    private List<MusicComposition> tracks;

    public Album(String name) {
        this.name = name;
        this.tracks = new ArrayList<>();
    }

    /**
     * Додати трек.
     */
    public void addTrack(MusicComposition track) {
        tracks.add(track);
    }

    /**
     * Порахувати час.
     */
    public int getTotalDuration() {
        int total = 0;
        for (MusicComposition track : tracks) {
            total += track.getDuration();
        }
        return total;
    }

    /**
     * Сортувати за стилем.
     */
    public void sortByStyle() {
        Collections.sort(tracks, Comparator.comparing(MusicComposition::getStyle));
    }

    /**
     * Знайти за діапазоном.
     */
    public List<MusicComposition> findByDurationRange(int min, int max) {
        List<MusicComposition> result = new ArrayList<>();
        for (MusicComposition track : tracks) {
            if (track.getDuration() >= min && track.getDuration() <= max) {
                result.add(track);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Album: " + name + "\n");
        for (MusicComposition track : tracks) {
            sb.append(" - ").append(track.toString()).append("\n");
        }
        return sb.toString();
    }
}