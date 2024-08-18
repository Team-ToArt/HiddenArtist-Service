package com.hiddenartist.backend.domain.artist.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artist.persistence.ArtistContact;
import com.hiddenartist.backend.domain.artist.persistence.type.ContactType;
import com.hiddenartist.backend.domain.genre.persistence.Genre;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record ArtistGetDetailResponse(
    String name,
    String image,
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate birth,
    String summary,
    List<String> genres,
    Map<ContactType, List<ContactInfo>> contacts,
    String description
) {

  public static ArtistGetDetailResponse create(Artist artist, List<ArtistContact> artistContacts, List<Genre> genres) {
    List<String> genreNames = genres.stream().map(Genre::getName).toList();
    Map<ContactType, List<ContactInfo>> contacts = createContactInfos(artistContacts);
    return new ArtistGetDetailResponse(artist.getName(), artist.getProfileImage(), artist.getBirth(),
        artist.getSummary(), genreNames, contacts, artist.getDescription());
  }

  private static Map<ContactType, List<ContactInfo>> createContactInfos(List<ArtistContact> artistContacts) {
    Map<ContactType, List<ContactInfo>> contacts =
        Arrays.stream(ContactType.values())
              .collect(Collectors.toMap(Function.identity(), contactType -> new ArrayList<>()));
    artistContacts.forEach(
        contact -> contacts.get(contact.getType()).add(new ContactInfo(contact.getLabel(), contact.getContactValue())));
    return contacts;
  }

  public record ContactInfo(
      String label,

      String value
  ) {

  }
}