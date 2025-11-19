package org.example.clickearn.service;

import org.example.clickearn.entity.Campagne;
import org.example.clickearn.entity.PostSponsorise;
import org.example.clickearn.enums.StatutPost;
import org.example.clickearn.exception.BadRequestException;
import org.example.clickearn.exception.ResourceNotFoundException;
import org.example.clickearn.repository.CampagneRepository;
import org.example.clickearn.repository.PostSponsoriseRepository;
import org.example.clickearn.service.interfaces.IPostSponsoriseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostSponsoriseeService implements IPostSponsoriseService {

    private final PostSponsoriseRepository postSponsoriseRepository;
    private final CampagneRepository campagneRepository;

    // Créer un nouveau post sponsorisé
    @Override
    @Transactional
    public PostSponsorise createPost(PostSponsorise post, Long campagneId, Long entrepriseId) {
        Campagne campagne = campagneRepository.findByIdAndEntrepriseId(campagneId, entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Campagne", campagneId));

        post.setCampagne(campagne);
        post.setStatut(StatutPost.EN_ATTENTE);

        return postSponsoriseRepository.save(post);
    }

    // Modifier un post sponsorisé
    @Override
    @Transactional
    public PostSponsorise updatePost(Long postId, Long entrepriseId, PostSponsorise postDetails) {
        PostSponsorise post = getPostByIdAndEntreprise(postId, entrepriseId);

        post.setTitre(postDetails.getTitre());
        post.setContenu(postDetails.getContenu());
        post.setImageUrl(postDetails.getImageUrl());
        post.setLienPublicitaire(postDetails.getLienPublicitaire());
        post.setPlateforme(postDetails.getPlateforme());
        post.setBudgetAlloue(postDetails.getBudgetAlloue());

        return postSponsoriseRepository.save(post);
    }

    // Publier un post (changer statut à ACTIF)
    @Override
    @Transactional
    public PostSponsorise publierPost(Long postId, Long entrepriseId) {
        PostSponsorise post = getPostByIdAndEntreprise(postId, entrepriseId);
        post.setStatut(StatutPost.ACTIF);
        post.setDatePublication(LocalDateTime.now());
        post.setActif(true);

        return postSponsoriseRepository.save(post);
    }

    // Obtenir tous les posts d'une campagne
    @Override
    public List<PostSponsorise> getPostsByCampagne(Long campagneId, Long entrepriseId) {
        // Vérifier que la campagne appartient à l'entreprise
        campagneRepository.findByIdAndEntrepriseId(campagneId, entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Campagne", campagneId));

        return postSponsoriseRepository.findByCampagneId(campagneId);
    }

    // Obtenir un post spécifique
    @Override
    public PostSponsorise getPostByIdAndEntreprise(Long postId, Long entrepriseId) {
        PostSponsorise post = postSponsoriseRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", postId));

        // Vérifier que le post appartient à une campagne de l'entreprise
        if (!post.getCampagne().getEntreprise().getId().equals(entrepriseId)) {
            throw new ResourceNotFoundException("Post", postId);
        }

        return post;
    }

    @Override
    public List<PostSponsorise> getAllPostsByEntreprise(Long entrepriseId) {
        return postSponsoriseRepository.findByEntrepriseId(entrepriseId);
    }

    @Override
    public List<PostSponsorise> getPostsByStatut(Long entrepriseId, StatutPost statut) {
        return postSponsoriseRepository.findByEntrepriseIdAndStatut(entrepriseId, statut);
    }

    @Override
    @Transactional
    public PostSponsorise updatePostStatut(Long postId, Long entrepriseId, StatutPost nouveauStatut) {
        PostSponsorise post = getPostByIdAndEntreprise(postId, entrepriseId);
        post.setStatut(nouveauStatut);
        if (nouveauStatut == StatutPost.ACTIF) {
            post.setDatePublication(LocalDateTime.now());
            post.setActif(true);
        } else {
            post.setActif(false);
        }
        return postSponsoriseRepository.save(post);
    }

    @Override
    @Transactional
    public PostSponsorise desactiverPost(Long postId, Long entrepriseId) {
        return updatePostStatut(postId, entrepriseId, StatutPost.PAUSE);
    }

    @Override
    @Transactional
    public void deletePost(Long postId, Long entrepriseId) {
        PostSponsorise post = getPostByIdAndEntreprise(postId, entrepriseId);
        
        // Vérifier si le post est actif
        if (post.getStatut() == StatutPost.ACTIF) {
            throw new BadRequestException("Impossible de supprimer un post actif. Désactivez-le d'abord.");
        }
        
        postSponsoriseRepository.delete(post);
    }

    @Override
    public Integer getTotalClicsByCampagne(Long campagneId) {
        Integer total = postSponsoriseRepository.getTotalClicsByCampagne(campagneId);
        return total != null ? total : 0;
    }

    @Override
    public Double getTotalDepenseByCampagne(Long campagneId) {
        Double total = postSponsoriseRepository.getTotalDepenseByCampagne(campagneId);
        return total != null ? total : 0.0;
    }
}