package com.dime.wadiag.diag.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
public class GenericResponseHandler<T extends GenericEntity> {
    private boolean success;
    private List<T> data;
    private String message;
    private Map<String, String> links;

    public static <T extends GenericEntity> GenericResponseHandler<T> success(T data) {
        return success(Collections.singletonList(data));
    }

    public static <T extends GenericEntity> GenericResponseHandler<T> success(List<T> data) {
        return success(data, "Resource retrieved successfully");
    }

    public static <T extends GenericEntity> GenericResponseHandler<T> success(List<T> data, String message) {
        return GenericResponseHandler.<T>builder()
                .message(Optional.ofNullable(message).orElse(""))
                .data(data)
                .links(generateLinks(data))
                .success(true)
                .build();
    }

    public ResponseEntity<GenericResponseHandler<T>> entityOk() {
        return ResponseEntity.ok(this);
    }

    public ResponseEntity<GenericResponseHandler<T>> entityCreated() {
        return ResponseEntity.created(generateIdUri(data.get(0))).body(this);
    }

    private static <T extends GenericEntity> Map<String, String> generateLinks(List<T> data) {
        Objects.requireNonNull(data, "Data cannot be null");
        Map<String, String> map = new HashMap<>();
        data.forEach(d -> map.put("link_" + map.size(), generateIdUri(d).toString()));
        return map;
    }

    public static URI generateIdUri(GenericEntity data) {
        Objects.requireNonNull(data, "Data cannot be null");
        return UriComponentsBuilder.fromPath(generateIdPath(data))
                .buildAndExpand(data.getId())
                .toUri();
    }

    private static String generateIdPath(GenericEntity data) {
        return "/rest/" + data.getClass().getSimpleName().toLowerCase()
                + "/{id}";
    }

}
