package com.lunarshade.vkapp.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "play")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AttributeOverrides({
        @AttributeOverride(
                name = "plannedTime.timeStart",
                column = @Column(name = "planned_time_time_start")
        ),
        @AttributeOverride(
                name = "plannedTime.timeEnd",
                column = @Column(name = "planned_time_time_end")
        ),
        @AttributeOverride(
                name = "realTime.timeStart",
                column = @Column(name = "real_time_time_start")
        ),
        @AttributeOverride(
                name = "realTime.timeEnd",
                column = @Column(name = "real_time_time_end")
        )
})
public class Play {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private BoardGame boardGame;
    @ManyToOne
    private Event event;
    @ManyToOne
    private Desk table;
    @ManyToMany(mappedBy = "plays")
    private Set<AppUser> appUser;
    @Embedded
    private PlayTime plannedTime;
    @Embedded
    private PlayTime realTime;
}