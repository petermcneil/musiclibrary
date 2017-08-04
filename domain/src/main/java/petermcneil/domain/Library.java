package petermcneil.domain;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

public class Library {
    private final Set<Recording> library;
    private final Set<Playlist> playlists;

    private Library(Builder from) {
        if(from != null) {
            this.library = ImmutableSet.copyOf(from.recordings);
        }else{
            this.library = ImmutableSet.of();
        }

        if(from != null) {
            this.playlists = ImmutableSet.copyOf(from.playlists);
        }else{
            this.playlists = ImmutableSet.of();
        }
    }

    public static Builder libraryBuilder(){
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
    public Set<Recording> getLibrary() {
        return library;
    }

    public Set<Playlist> getPlaylists() {
        return playlists;
    }

    public static class Builder{
        private Set<Recording> recordings;
        private Set<Playlist> playlists;

        private Builder(){}

        private Builder(Library toCopy){
            this.recordings = toCopy.library;
            this.playlists = toCopy.playlists;
        }

        public Builder recordings(Set<Recording> library){
            this.recordings = library;
            return this;
        }

        public Builder playlists(Set<Playlist> playlists){
            this.playlists = playlists;
            return this;
        }

        public Library build(){
            return new Library(this);
        }
    }

}
