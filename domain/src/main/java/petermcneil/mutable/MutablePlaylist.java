package petermcneil.mutable;

public class MutablePlaylist {
    private String title;
    private String tracks;

    public MutablePlaylist() {

    }

    public String getTracks() {
        return tracks;
    }

    public void setTracks(String tracks) {
        this.tracks = tracks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
