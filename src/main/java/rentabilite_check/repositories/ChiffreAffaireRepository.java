package rentabilite_check.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rentabilite_check.entities.ChiffreAffaire;

public interface ChiffreAffaireRepository extends JpaRepository<ChiffreAffaire,Integer> {
}
