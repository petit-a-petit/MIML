package com.petitapetit.miml.domain.notification.entity;

import com.petitapetit.miml.domain.artist.domain.Artist;
import com.petitapetit.miml.domain.member.model.Member;
import com.petitapetit.miml.domain.track.entity.Track;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class TrackAddedNotification extends Notification {
    private String trackName;  // 추가된 음악 이름
    @ElementCollection
    private List<String> trackArtist; // 추가된 음악의 아티스트들 이름

    public static TrackAddedNotification of(Track track, Member user){
        List<String> songArtist = track.getArtists().stream().map(Artist::getName).collect(Collectors.toList());
        return new TrackAddedNotification(user.getEmail(), track.getName(), songArtist);
    }

    private TrackAddedNotification(String userEmail, String trackName, List<String> trackArtist){
        super(userEmail);
        this.trackName = trackName;
        this.trackArtist = trackArtist;
    }

    @Override
    public String makeSubject() {
        return "스트리밍 서비스 신곡 추가 알림";
    }

    @Override
    public String makeText() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTrackArtist());
        sb.append(getTrackName());
        return sb.toString();
    }
}