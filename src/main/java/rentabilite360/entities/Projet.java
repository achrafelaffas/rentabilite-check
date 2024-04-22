package rentabilite360.entities;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "projets")
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_projet")
    private int idProjet;
    private String nom;
    private String description;
    @Column(name = "taux_actualisation")
    private float tauxActualisation;
    @Column(name = "impot_societe")
    private float impotSociete;
    private int duree;
    private float VAN;
    private float IP;
    private float DRCI;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;
    @OneToMany(mappedBy = "projet")
    List<Investissement> investissements;
    @OneToMany(mappedBy = "projet")
    List<Parametre> parametres;
}
