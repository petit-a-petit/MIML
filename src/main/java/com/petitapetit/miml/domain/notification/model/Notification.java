package com.petitapetit.miml.domain.notification.model;

import com.petitapetit.miml.domain.notification.TempSong;
import com.petitapetit.miml.domain.notification.TempUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private String songName;
    private String songArtist;
    @CreatedDate
    private LocalDateTime createdAt;

    public Notification(TempUser user, TempSong song){
        this.userEmail = user.getEmail();
        this.songArtist = song.getArtist().getName();
        this.songName = song.getName();
    }
}
