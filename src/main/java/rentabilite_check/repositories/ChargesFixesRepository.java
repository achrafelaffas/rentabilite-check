package rentabilite_check.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rentabilite_check.entities.ChargesFixes;


public interface ChargesFixesRepository extends JpaRepository<ChargesFixes,Integer> {
}
