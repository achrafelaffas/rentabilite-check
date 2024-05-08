package rentabilite_check.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rentabilite_check.entities.AugmentationBFR;

import java.sql.Date;


public interface AugmentationBFRRepository extends JpaRepository<AugmentationBFR,Integer> {
    AugmentationBFR findAugmentationBFRByIdaUGbFR(int idaUGbFR);

    @Query("update AugmentationBFR a set a.nom=:nom, a.annee=:annee, a.montant=:montant where a.idaUGbFR=:idaUGbFR")
    void updateAugBFR(
            @Param("idaUGbFR") int idaUGbFR,
            @Param("nom") String nom,
            @Param("annee") Date annee,
            @Param("montant") int montant
    );
}
