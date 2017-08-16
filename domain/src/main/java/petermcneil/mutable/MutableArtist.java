package petermcneil.mutable;

import petermcneil.domain.Bio;

public class MutableArtist {
    private String name;
    private String type;
    private Bio bio;

    public MutableArtist(String name, String type, Bio bio) {
        this.name = name;
        this.type = type;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Bio getBio() {
        return bio;
    }

    public void setBio(Bio bio) {
        this.bio = bio;
    }
}
