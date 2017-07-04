package org.ciencialabart.profilesbrowser.facebook;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.ciencialabart.profilesbrowser.city.City;
import org.ciencialabart.profilesbrowser.person.gender.Gender;
import org.ciencialabart.profilesbrowser.person.gender.GenderFromStringDeserializer;
import org.ciencialabart.profilesbrowser.person.relationship.Relationship;
import org.ciencialabart.profilesbrowser.person.relationship.RelationshipFromStringDeserializer;
import org.ciencialabart.profilesbrowser.post.Post;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Facebook {
    
    private String id;
    
    @JsonDeserialize(using = BirthdateFromEpochMillisecondsDeserializer.class)
    @JsonSerialize(using = BirthdateToEpochMillisecondsSerializer.class)
    private LocalDate birthday;
    
    @JsonProperty("firstname")
    private String firstName;
    
    @JsonProperty("lastname")
    private String lastName;
    private String occupation;
    
    @JsonDeserialize(using = GenderFromStringDeserializer.class)
    private Gender gender;
    
    private City city;
    private String work;
    private Set<Long> friends;
    private String school;
    private String location;
    
    @JsonDeserialize(using = RelationshipFromStringDeserializer.class)
    private Relationship relationship;
    
    private List<Post> posts;

    public String getId() {
        return id;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getOccupation() {
        return occupation;
    }

    public Gender getGender() {
        return gender;
    }

    public City getCity() {
        return city;
    }

    public String getWork() {
        return work;
    }

    public Set<Long> getFriends() {
        return friends;
    }

    public String getSchool() {
        return school;
    }

    public String getLocation() {
        return location;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public List<Post> getPosts() {
        return posts;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Facebook) {
            Facebook otherFacebook = (Facebook) other;
            
            return id.equals(otherFacebook.id);
        }
        
        return false;
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
}
