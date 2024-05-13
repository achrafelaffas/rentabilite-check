package rentabilite_check.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rentabilite_check.entities.Investissement;
import rentabilite_check.entities.Projet;

import java.util.List;

public interface InvestissementRepository extends JpaRepository<Investissement, Integer> {
    Investissement findInvestissementById(int id);

    List<Investissement> findInvestissementByProjet(Projet projet);

    @Transactional
    @Modifying
    @Query("update Investissement i set i.montantINV=:montantINV, i.dureeINV=:dureeINV, i.intitule=:intitule, i.tauxActualisation=:tauxActualisation, i.projet=:projet where i.id =:id")
    void updateInvestissement(@Param("id") int id, @Param("montantINV") float montantINV, @Param("dureeINV") int dureeINV, @Param("tauxActualisation") float tauxActualisation, @Param("intitule") String intitule, @Param("projet") Projet projet);

    @Query("SELECT i.projet, sum(i.montantINV) FROM Projet p, Investissement i GROUP BY i.projet")
    List<Object[]> getProjectInvestmentSum();
}
