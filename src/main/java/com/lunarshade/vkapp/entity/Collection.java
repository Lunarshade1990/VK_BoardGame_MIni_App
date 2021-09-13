package com.lunarshade.vkapp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
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

    @OneToMany(mappedBy = "collection")
    private Set<BoardGameCollection> boardGameCollection = new HashSet<>();

    @Enumerated
    private CollectionType collectionType;

}
