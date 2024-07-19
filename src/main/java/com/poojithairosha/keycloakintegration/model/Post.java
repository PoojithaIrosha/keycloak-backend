package com.poojithairosha.keycloakintegration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record Post(int userId, int id, String title, String body) { }
