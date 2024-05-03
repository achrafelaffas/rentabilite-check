package rentabilite_check.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rentabilite_check.entities.Projet;


public interface ProjetRepository extends JpaRepository<Projet,Integer> {
}
