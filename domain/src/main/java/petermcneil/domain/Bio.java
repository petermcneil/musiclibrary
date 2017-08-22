package petermcneil.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

public class Bio {
    private final String biography;
    private final String image;
    private final String website;
    private final Integer bioId;

    private Bio(Builder from) {
        this.biography = from.biography;
        this.image = from.image;
        this.website = from.website;
        this.bioId = from.bioId;
    }

    public static Builder bioBuilder(){
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

    public String getBiography() {
        return biography;
    }

    public String getImage() {
        return image;
    }

    public String getWebsite() {
        return website;
    }

    public Integer getBioId() {
        return bioId;
    }

    public static class Builder{
        private String biography;
        private String image;
        private String website;
        private Integer bioId;

        private Builder(){

        }

        private Builder(Bio toCopy){
            this.website = toCopy.website;
            this.biography = toCopy.biography;
            this.image = toCopy.image;
            this.bioId = toCopy.bioId;
        }

        public Builder biography(String biography){
            this.biography = biography;
            return this;
        }

        public Builder image(String image){
            this.image = image;
            return this;
        }

        public Builder website(String website){
            this.website = website;
            return this;
        }

        public Builder bioId(Integer bioId){
            this.bioId = bioId;
            return this;
        }
        public Bio build(){
            return new Bio(this);
        }
    }
}
