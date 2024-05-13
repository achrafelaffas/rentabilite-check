package rentabilite_check.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rentabilite_check.entities.AugmentationBFR;
import rentabilite_check.entities.Projet;

import java.sql.Date;
import java.util.List;


public interface AugmentationBFRRepository extends JpaRepository<AugmentationBFR, Integer> {
    AugmentationBFR findAugmentationBFRByIdaUGbFR(int idaUGbFR);

    List<AugmentationBFR> findAugmentationBFRByProjet(Projet projet);

    @Transactional
    @Modifying
    @Query("update AugmentationBFR a set a.nom=:nom, a.annee=:annee, a.montant=:montant, a.projet=:projet where a.idaUGbFR=:idaUGbFR")
    void updateAugBFR(
            @Param("idaUGbFR") int idaUGbFR,
            @Param("nom") String nom,
            @Param("annee") Date annee,
            @Param("montant") double montant,
            @Param("projet") Projet projet
    );
}
