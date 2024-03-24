package Model;

/**
 * Etudiant
 */
public class Student {
    private int idStudent;
    private String name;
    private String firstname;
    private String classRank;

    // methode visant a recuperer l'id de l'etudiant
    public int getIdStudent() {
        return idStudent;
    }

    // methode visant a definir l'id de l'etudiant
    public int setIdStudent(int id) {
        return this.idStudent = id;
    }

    // methode visant a recuperer le nom de l'etudiant
    public String getName() {
        return name;
    }

    // methode visant a definir le nom de l'etudiant
    public String setName(String name) {
        return this.name = name;
    }

    // methode visant a recuperer le prenom de l'etudiant
    public String getFirstname() {
        return firstname;
    }

    // methode visant a definir le prenom de l'etudiant
    public String setFirstname(String firstname) {
        return this.firstname = firstname;
    }

    // methode visant a recuperer la classe de l'etudiant
    public String getClassRank() {
        return classRank;
    }

    // methode visant a definir la classe de l'etudiant
    public String setClassRank(String classRank) {
        return this.classRank = classRank;
    }

}
