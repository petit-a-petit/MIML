package com.petitapetit.miml.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@UtilityClass
public class UriUtil {
    public static URI createUri(String defaultUrl, Long uriPathVariable) {
        return UriComponentsBuilder.newInstance()
                .path(defaultUrl + "/{uri-path-variable}")
                .buildAndExpand(uriPathVariable)
                .toUri();
    }
}
