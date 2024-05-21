package rentabilite_check.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rentabilite_check.entities.Projet;

import java.util.List;


public interface ProjetRepository extends JpaRepository<Projet, Integer> {
    Projet findByIdProjet(int idProjet);

    @Modifying
    @Transactional
    @Query("update Projet p set p.nom=:nom, p.description=:description, p.impotSociete=:impotSociete, p.duree=:duree, p.captial=:captial where p.idProjet=:idProjet")
    void updateProjet(@Param("idProjet") int idProjet, @Param("nom") String nom, @Param("description") String description, @Param("impotSociete") float impotSociete, @Param("duree") int duree,@Param("captial") float captial);


    @Query("SELECT DISTINCT YEAR(ca.annee) FROM ChiffreAffaire ca WHERE ca.projet.idProjet = ?1")
    List<Integer> findYearsByProjetId(int projetId);

}