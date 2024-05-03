package rentabilite_check.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rentabilite_check.entities.Investissement;

public interface InvestissementRepository extends JpaRepository<Investissement,Integer> {
}
