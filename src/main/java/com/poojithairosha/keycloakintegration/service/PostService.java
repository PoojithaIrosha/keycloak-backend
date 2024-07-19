package com.poojithairosha.keycloakintegration.service;

import com.poojithairosha.keycloakintegration.model.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final RestTemplate restTemplate;

    public List<Post> getAll() {
        log.info("START: Fetching all posts");
        ResponseEntity<List<Post>> response =  restTemplate.exchange("https://jsonplaceholder.typicode.com/posts", HttpMethod.GET, null,  new ParameterizedTypeReference<List<Post>>() {});
        log.info("END: Fetched all posts");
        return response.getBody();
    }
}
