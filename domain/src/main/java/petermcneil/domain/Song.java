package petermcneil.domain;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import java.util.Set;

@JsonDeserialize(builder = Song.Builder.class)
public class Song {
    private final String title;
    private final int length;
    private final Artist leadArtist;
    private final Set<Artist> featuredArtists;
    private final String genre;
    private final String artwork;
    private final String lyrics;
    private final int playcount;

    private Song(Builder from){
        this.title = from.title;
        this.length = from.length;
        this.leadArtist = from.leadArtist;
        this.genre = from.genre;
        this.artwork = from.artwork;
        this.lyrics = from.lyrics;
        this.playcount = from.playcount;

        if(from.featuredArtists != null){
            this.featuredArtists = ImmutableSet.copyOf(from.featuredArtists);
        }else{
            this.featuredArtists = ImmutableSet.of();
        }
    }

    public static Builder songBuilder(){
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

    public int getLength() {
        return length;
    }

    public Artist getLeadArtist() {
        return leadArtist;
    }

    public Set<Artist> getFeaturedArtists() {
        return featuredArtists;
    }

    public String getGenre() {
        return genre;
    }

    public String getArtwork() {
        return artwork;
    }

    public String getLyrics() {
        return lyrics;
    }

    public int getPlaycount() {
        return playcount;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder{
        private String title;
        private int length;
        private Artist leadArtist;
        private Set<Artist> featuredArtists;
        private String genre;
        private String artwork;
        private String lyrics;
        private int playcount;


        private Builder(){}

        private Builder (Song toCopy){
            this.title = toCopy.title;
            this.length = toCopy.length;
            this.leadArtist = toCopy.leadArtist;
            this.featuredArtists = toCopy.featuredArtists;
            this.genre = toCopy.genre;
            this.artwork = toCopy.artwork;
            this.lyrics = toCopy.lyrics;
            this.playcount = toCopy.playcount;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder length(int length){
            this.length = length;
            return this;
        }

        public Builder leadArtist(Artist leadArtist){
            this.leadArtist = leadArtist;
            return this;
        }

        public Builder genre(String genre){
            this.genre = genre;
            return this;
        }

        public Builder artwork(String artwork){
            this.artwork = artwork;
            return this;
        }

        public Builder lyrics(String lyrics){
            this.lyrics = lyrics;
            return this;
        }

        public Builder playcount(int playcount){
            this.playcount = playcount;
            return this;
        }

        public Builder featuredArtists(Set<Artist> featuredArtists){
            this.featuredArtists = featuredArtists;
            return this;
        }

        public Song build(){
            return new Song(this);
        }

    }
}
