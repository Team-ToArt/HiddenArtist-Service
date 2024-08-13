package com.pop.backend.domain.artist.persistence.repository;

import com.pop.backend.domain.artist.persistence.FollowArtist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowArtistRepository extends JpaRepository<FollowArtist, Long> {

}