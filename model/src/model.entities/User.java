import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(String name, String email, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return name;
    }

    public void setNome(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return password;
    }

    public void setSenha(String password) {
        this.password = password;
    }

    public String getDataCriacaoFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return  createdAt.format(formatter);
    }

    public void setDataCadastro(LocalDateTime createdAt) {
        this.createdAt = User.this.createdAt;
    }

    public LocalDateTime getDataAtualizacao() {

        return updatedAt;
    }

    public void setDataAtualizacao(LocalDateTime updatedAt) {
        this.updatedAt = User.this.updatedAt;
    }
    public void showInfo(){

        System.out.println("\nID do usuario: " + id + "\nNome do usuario: " + getNome() + "\nEmail: " + email + "\nSenha: " + password + "\nCriado em: " + getDataCriacaoFormatada());
    }
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + name + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + password + '\'' +
                ", data criação= " + createdAt +
                ", atualizado em=" + updatedAt +
                '}';
    }
}
