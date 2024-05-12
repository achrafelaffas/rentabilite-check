package rentabilite_check.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rentabilite_check.entities.ChargesVariables;
import rentabilite_check.entities.Projet;


import java.sql.Date;
import java.util.List;

@Repository
public interface ChargesVariablesRepository extends JpaRepository<ChargesVariables, Integer> {
    List<ChargesVariables> findByProjet(Projet projet);
    ChargesVariables findByIdCV(int idCV);

    @Modifying
    @Transactional
    @Query("update ChargesVariables a set a.nom=:nom, a.annee=:annee, a.montant=:montant, a.projet=:projet where a.idCV=:idCV")
    void updateChargeVariable(
            @Param("idCV") int idCV,
            @Param("nom") String nom,
            @Param("annee") Date annee,
            @Param("montant") double montant,
            @Param("projet") Projet projet
    );

}
