package rentabilite_check.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rentabilite_check.entities.ChargesFixes;
import rentabilite_check.entities.ChiffreAffaire;
import rentabilite_check.entities.Projet;

import java.sql.Date;
import java.util.List;

public interface ChiffreAffaireRepository extends JpaRepository<ChiffreAffaire,Integer> {
    List<ChiffreAffaire> findByProjet(Projet projet);
    ChiffreAffaire findByIdChiffreAffaire(int idChiffreAffaire );

    @Modifying
    @Transactional
    @Query("update ChiffreAffaire a set a.prixUnitaire=:prixUnitaire, a.quantite=:quantite, a.valeur=:valeur, a.annee=:annee ,a.projet=:projet where a.idChiffreAffaire=:idChiffreAffaire")
    void updateChiffreAffaire(
            @Param("idChiffreAffaire") int idChiffreAffaire,
            @Param("prixUnitaire") double prixUnitaire,
            @Param("quantite") int quantite,
            @Param("valeur") double valeur,
            @Param("annee") Date annee,
            @Param("projet") Projet projet
    );

    //@Transactional
    //@Query("select year(cf.annee) as annee, sum(cf.montant) as total from ChargesFixes cf where cf.projet = :projet group by year(cf.annee)")
    //List<Object[]> getTotalByYearForProjet(@Param("projet") Projet projet);

    //@Query("SELECT cf FROM ChargesFixes cf WHERE cf.projet.idProjet = :projetId AND YEAR(cf.annee) = YEAR(:year)")
    //List<ChargesFixes> getTotalByYearForProjet(@Param("projetId") int projetId, @Param("year") Integer year);

    @Query("SELECT ca FROM ChiffreAffaire ca WHERE ca.projet.idProjet = ?1 AND YEAR(ca.annee) = ?2")
    List<ChiffreAffaire> findByProjetIdAndDateYear(int projetId, int year);
}
