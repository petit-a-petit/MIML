package com.petitapetit.miml.util;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class UriUtil {
    public static URI createUri(String defaultUrl, Long resourceId) {
        return UriComponentsBuilder.newInstance()
                .path(defaultUrl + "/{resource-id}")
                .buildAndExpand(resourceId)
                .toUri();
    }
}
