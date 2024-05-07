package rentabilite_check.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rentabilite_check.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("SELECT u from User u where u.email =:email")
    User getUserByEmail(@Param("email") String email);
}
