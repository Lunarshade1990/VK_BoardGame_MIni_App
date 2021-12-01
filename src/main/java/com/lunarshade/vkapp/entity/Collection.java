package com.lunarshade.vkapp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
public class Collection {

    public Collection() {
    }

    public Collection(CollectionType collectionType) {
        this.collectionType = collectionType;
    }

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private AppUser appUser;

    @OneToMany(mappedBy = "collection", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Set<BoardGameCollection> boardGameCollection = new HashSet<>();

    @Enumerated
    private CollectionType collectionType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collection that = (Collection) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
