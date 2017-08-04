package petermcneil.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Artist {
    private final String name;
    private final String type;
    private final Bio bio;

    private Artist(Builder toCopy) {
        this.name = toCopy.name;
        this.type = toCopy.type;
        this.bio = toCopy.bio;
    }

    public static Builder artistBuilder(){
        return new Builder();
    }

    public Builder copy(){
        return new Builder(this);
    }
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Bio getBio() {
        return bio;
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

    public static class Builder{
        private String name;
        private String type;
        private Bio bio;

        private Builder(){}

        private Builder(Artist toCopy){
            this.name = toCopy.name;
            this.type = toCopy.type;
            this.bio = toCopy.bio;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder type(String type){
            this.type = type;
            return this;
        }

        public Builder bio(Bio bio){
            this.bio = bio;
            return this;
        }

        public Artist build(){
            return new Artist(this);
        }
    }
}
