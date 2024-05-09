package rentabilite_check.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rentabilite_check.entities.ChargesFixes;
import rentabilite_check.entities.Projet;

import java.util.Date;
import java.util.List;


public interface ChargesFixesRepository extends JpaRepository<ChargesFixes,Integer> {
    List<ChargesFixes> findByProjet(Projet projet);
    ChargesFixes findByIdCF(int idCF);






}
