package org.example.clickearn.service.interfaces;

import org.example.clickearn.entity.PostSponsorise;
import org.example.clickearn.enums.StatutPost;

import java.util.List;

public interface IPostSponsoriseService {
    // Création
    PostSponsorise createPost(PostSponsorise post, Long campagneId, Long entrepriseId);
    
    // Lecture
    PostSponsorise getPostByIdAndEntreprise(Long postId, Long entrepriseId);
    List<PostSponsorise> getPostsByCampagne(Long campagneId, Long entrepriseId);
    List<PostSponsorise> getAllPostsByEntreprise(Long entrepriseId);
    List<PostSponsorise> getPostsByStatut(Long entrepriseId, StatutPost statut);
    
    // Modification
    PostSponsorise updatePost(Long postId, Long entrepriseId, PostSponsorise postDetails);
    PostSponsorise updatePostStatut(Long postId, Long entrepriseId, StatutPost nouveauStatut);
    PostSponsorise publierPost(Long postId, Long entrepriseId);
    PostSponsorise desactiverPost(Long postId, Long entrepriseId);
    
    // Suppression
    void deletePost(Long postId, Long entrepriseId);
    
    // Statistiques
    Integer getTotalClicsByCampagne(Long campagneId);
    Double getTotalDepenseByCampagne(Long campagneId);
}

