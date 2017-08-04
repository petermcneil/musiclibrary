package petermcneil.domain;

import com.google.common.collect.ImmutableSet;
import oracle.jrockit.jfr.openmbean.RecordingType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

public class Recording {
    private final RecordType type;
    private final String title;
    private final Set<Song> tracks;
    private final String label;
    private final String artwork;
//    private final Artist artist;

    private Recording(Builder from) {
        this.type = from.type;
        this.title = from.title;
        this.label = from.label;
        this.artwork = from.artwork;
//        this.artist = from.artist;

        if(from.tracks != null){
            this.tracks = ImmutableSet.copyOf(from.tracks);
        }else{
            this.tracks = ImmutableSet.of();
        }
    }

    public static Builder recordingBuilder(){
        return new Builder();
    }

    public Builder copy(){
        return new Builder(this);
    }

/*
    public Artist getArtist() {
        return artist;
    }
*/

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

    public RecordType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public Set<Song> getTracks() {
        return tracks;
    }

    public String getLabel() {
        return label;
    }

    public String getArtwork() {
        return artwork;
    }

    public enum RecordType {
        SINGLE, LP, EP
    }

    public static class Builder{
        private RecordType type;
        private String title;
        private Set<Song> tracks;
        private String label;
        private String artwork;
//        private Artist artist;

        private Builder(){}

        private Builder (Recording toCopy){
            this.type = toCopy.type;
            this.title = toCopy.title;
            this.tracks = toCopy.tracks;
            this.label = toCopy.label;
            this.artwork = toCopy.artwork;
//            this.artist = toCopy.artist;
        }

        public Recording build(){
            return new Recording(this);
        }

        public Builder type(RecordType type){
            this.type = type;
            return this;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder label(String label){
            this.label = label;
            return this;
        }

        public Builder artwork(String artwork){
            this.artwork = artwork;
            return this;
        }
/*

        public Builder artist(Artist artist){
            this.artist = artist;
            return this;
        }
*/

        public Builder tracks(Set<Song> tracks){
            this.tracks = tracks;
            return this;
        }
    }
}
