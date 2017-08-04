package petermcneil.domain;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class Playlist {
    private final String title;
    private final List<Song> tracks;

    private Playlist(Builder from) {
        this.title = from.title;

        if (from.tracks != null) {
            this.tracks = ImmutableList.copyOf(from.tracks);
        }else
        {
            this.tracks = ImmutableList.of();
        }
    }

    public static Builder playlistBuilder(){
        return new Builder();
    }

    public Builder copy(){
        return new Builder(this);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public String getTitle() {
        return title;
    }

    public List<Song> getTracks() {
        return tracks;
    }

    public static class Builder{
        private String title;
        private List<Song> tracks;

        private Builder(){}

        private Builder(Playlist toCopy){
            this.title = toCopy.title;
            this.tracks = toCopy.tracks;
        }

        public Playlist build(){
            return new Playlist(this);
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder tracks(List<Song> tracks){
            this.tracks = tracks;
            return this;
        }
    }
}
