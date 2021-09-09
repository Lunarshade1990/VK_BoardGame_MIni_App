package com.lunarshade.vkapp.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "user")
@Getter
@Setter
@RequiredArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String providerId;
    private String adapter;
    private String email;
    private String firstName;
    private String secondName;
    private String teseraProfile;
    private String avatarUrl;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Place> places;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<Event> events = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Roles> roles = new ArrayList<>();

    @JoinTable(name = "user_plays",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "plays_id"))
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Play> plays = new HashSet<>();

    @OneToMany(mappedBy = "appUser", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Collection> collections = new HashSet<>();


    private boolean active;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private City city;

    public Collection getCollectionByType(CollectionType type) {
        Collection collection;
        if (collections.size() == 0) {
            collection = new Collection(type);
            collections.add(collection);
            collection.setAppUser(this);
        } else {
            collection = this.getCollections()
                    .stream()
                    .filter(c -> c.getCollectionType().equals(type))
                    .findFirst()
                    .get();
        }
        return collection;
    }
}
