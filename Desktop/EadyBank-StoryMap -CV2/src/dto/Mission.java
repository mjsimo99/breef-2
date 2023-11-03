package dto;

import java.util.List;

public class Mission {
    private String code ;
    private String nome;
    private String description;
    List<Affectation> affectations;

    public Mission(String code, String nome, String description,List<Affectation> affectations) {
        setCode(code);
        setNome(nome);
        setDescription(description);
        setAffectations(affectations);

    }


    public List<Affectation> getAffectations() {
        return affectations;
    }

    public void setAffectations(List<Affectation> affectations) {
        this.affectations = affectations;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "code='" + code + '\'' +
                ", nome='" + nome + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
