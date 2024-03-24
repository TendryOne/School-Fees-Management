package Model;

/**
 * Etudiant
 */
public class User {
    private int idUser;
    private String username;
    private String password;

    // methode visant a recuperer l'id de l'etudiant
    public int GetId() {
        return idUser;
    }

    // methode visant a definir l'id de l'etudiant
    public int setId(int id) {
        return this.idUser = id;
    }

    // methode visant a recuperer le nom de l'etudiant
    public String Getusername() {
        return username;
    }

    // methode visant a definir le nom de l'etudiant
    public String setusername(String username) {
        return this.username = username;
    }

    // methode visant a recuperer le prenom de l'etudiant
    public String Getpassword() {
        return password;
    }

    // methode visant a definir le prenom de l'etudiant
    public String setpassword(String password) {
        return this.password = password;
    }

}
