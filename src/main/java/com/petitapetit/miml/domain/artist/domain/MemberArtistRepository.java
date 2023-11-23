package com.petitapetit.miml.domain.artist.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberArtistRepository extends JpaRepository<MemberArtist, Long> {
    @EntityGraph(attributePaths = {"member", "artist"})
    Optional<MemberArtist> findByMember_MemberIdAndArtist_Id(Long memberId, Long artistId);
}
