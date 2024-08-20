package com.hiddenartist.backend.domain.artist.controller.response;

import com.hiddenartist.backend.global.type.SimpleArtistResponse;
import org.springframework.data.domain.Page;

public record ArtistGetListResponse(
    Page<SimpleArtistResponse> artists
) {

}