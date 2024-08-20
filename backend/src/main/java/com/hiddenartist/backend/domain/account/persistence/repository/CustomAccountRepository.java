package com.hiddenartist.backend.domain.account.persistence.repository;

import com.hiddenartist.backend.domain.account.persistence.type.Role;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import java.util.List;

public interface CustomAccountRepository {

  boolean existsAdminByEmailAndRole(String email, Role role);

  void removeFollowArtists(String email, List<String> token);

  List<Artist> findFollowArtistListByEmail(String email);

}