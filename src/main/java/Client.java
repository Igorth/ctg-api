public class Client {

    private String nome;
    private int idade;
    private int id;
    private int risco;

    public Client() {
        nome = "N/D";
        idade = 0;
        id = 0;
        risco = 0;
    }

    public Client(String nome, int idade, int id, int risco) {
        this.nome = nome;
        this.idade = idade;
        this.id = id;
        this.risco = risco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRisco() {
        return risco;
    }

    public void setRisco(int risco) {
        this.risco = risco;
    }
}
