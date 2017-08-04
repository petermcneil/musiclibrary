package petermcneil.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

public class Library {
    private final Set<Recording> library;
    private final Set<Playlist> playlists;

    public Library(Set<Recording> library, Set<Playlist> playlists) {
        this.library = library;
        this.playlists = playlists;
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

}
