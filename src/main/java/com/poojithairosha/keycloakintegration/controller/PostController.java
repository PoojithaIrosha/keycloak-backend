package com.poojithairosha.keycloakintegration.controller;

import com.poojithairosha.keycloakintegration.model.Post;
import com.poojithairosha.keycloakintegration.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.poojithairosha.keycloakintegration.util.ApplicationConstant.UrlConstant.API_V1;

@Slf4j
@RestController
@RequestMapping(API_V1 + "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(postService.getAll());
    }

}
