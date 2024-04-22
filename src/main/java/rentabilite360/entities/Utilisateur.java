package rentabilite360.entities;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "utilisateurs")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private int idUtilisateur;
    private String nom;
    private String email;
    @Column(name = "mot_de_passe")
    private String motDePasse;

    @OneToMany(mappedBy = "utilisateur")
    List<Projet> projets;
}
