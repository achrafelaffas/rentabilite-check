package rentabilite_check;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import rentabilite_check.entities.User;
import rentabilite_check.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UtilisateurRepoTest {
    @Autowired
    UserRepository utilisateurRepository;
    @Autowired
    TestEntityManager entityManager;

    @Test
    public void createUtilisateur() {
        User utilisateur = new User("achraf", "elaffas", "achraf@elaffas.com", "1341");
        User saved = utilisateurRepository.save(utilisateur);
        User exists = entityManager.find(User.class, saved.getId());
        assertThat(exists.getEmail()).isEqualTo(saved.getEmail());
    }

    @Test
    public void getUtilisateurByEmail() {
        User exists = utilisateurRepository.getUserByEmail("achraf@elaffas.com");
        assertThat(exists).isNotNull();
    }
}
