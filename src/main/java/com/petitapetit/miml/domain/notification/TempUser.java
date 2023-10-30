package com.petitapetit.miml.domain.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TempUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_artist_mapping",
            joinColumns = {@JoinColumn(name = "temp_user_id")},
            inverseJoinColumns = {@JoinColumn(name = "temp_artist_id")})

    private Set<TempArtist> likeArtistsSet;

    public TempUser(String email) {
        this.email = email;
        this.likeArtistsSet= new HashSet<>();
    }

    public void likeArtist(TempArtist artist) {
        this.likeArtistsSet.add(artist);
    }
}
