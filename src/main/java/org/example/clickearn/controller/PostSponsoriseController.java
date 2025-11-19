package org.example.clickearn.controller;

import lombok.RequiredArgsConstructor;
import org.example.clickearn.entity.PostSponsorise;
import org.example.clickearn.enums.StatutPost;
import org.example.clickearn.service.interfaces.IPostSponsoriseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entreprises/{entrepriseId}/campagnes/{campagneId}/posts")
@RequiredArgsConstructor
public class PostSponsoriseController {

    private final IPostSponsoriseService postSponsoriseService;

    // Obtenir tous les posts d'une campagne
    @GetMapping
    public ResponseEntity<List<PostSponsorise>> getPostsByCampagne(
            @PathVariable Long entrepriseId,
            @PathVariable Long campagneId) {
        List<PostSponsorise> posts = postSponsoriseService.getPostsByCampagne(campagneId, entrepriseId);
        return ResponseEntity.ok(posts);
    }

    // Obtenir un post spécifique
    @GetMapping("/{postId}")
    public ResponseEntity<PostSponsorise> getPost(
            @PathVariable Long entrepriseId,
            @PathVariable Long campagneId,
            @PathVariable Long postId) {
        PostSponsorise post = postSponsoriseService.getPostByIdAndEntreprise(postId, entrepriseId);
        return ResponseEntity.ok(post);
    }

    // Créer un post
    @PostMapping
    public ResponseEntity<PostSponsorise> createPost(
            @PathVariable Long entrepriseId,
            @PathVariable Long campagneId,
            @RequestBody PostSponsorise post) {
        PostSponsorise nouveauPost = postSponsoriseService.createPost(post, campagneId, entrepriseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveauPost);
    }

    // Modifier un post
    @PutMapping("/{postId}")
    public ResponseEntity<PostSponsorise> updatePost(
            @PathVariable Long entrepriseId,
            @PathVariable Long campagneId,
            @PathVariable Long postId,
            @RequestBody PostSponsorise postDetails) {
        PostSponsorise post = postSponsoriseService.updatePost(postId, entrepriseId, postDetails);
        return ResponseEntity.ok(post);
    }

    // Changer le statut d'un post
    @PatchMapping("/{postId}/statut")
    public ResponseEntity<PostSponsorise> updatePostStatut(
            @PathVariable Long entrepriseId,
            @PathVariable Long campagneId,
            @PathVariable Long postId,
            @RequestParam StatutPost statut) {
        PostSponsorise post = postSponsoriseService.updatePostStatut(postId, entrepriseId, statut);
        return ResponseEntity.ok(post);
    }

    // Publier un post
    @PatchMapping("/{postId}/publier")
    public ResponseEntity<PostSponsorise> publierPost(
            @PathVariable Long entrepriseId,
            @PathVariable Long campagneId,
            @PathVariable Long postId) {
        PostSponsorise post = postSponsoriseService.publierPost(postId, entrepriseId);
        return ResponseEntity.ok(post);
    }

    // Supprimer un post
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long entrepriseId,
            @PathVariable Long campagneId,
            @PathVariable Long postId) {
        postSponsoriseService.deletePost(postId, entrepriseId);
        return ResponseEntity.noContent().build();
    }
}

