package rentabilite_check.entities;

public class admin {
    private String login;
    private String motDePasse;

    public admin(String login, String motDePasse) {
        this.login = login;
        this.motDePasse = motDePasse;
    }

    public admin() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
