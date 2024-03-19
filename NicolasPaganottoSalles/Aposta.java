//CADA APOSTA É UM OBJETO
public class Aposta{
    private String nome;
    private long cpf;
    private int registro;
    private int numeros[];
    //Registro 

    // construtor com parametros
    public Aposta(String nome, long cpf, int registro, int[] numeros){
        this.nome = nome;
        this.cpf = cpf;
        this.registro = registro;
        this.numeros = new int[5];
    }

    // construtor vazio/default
    public Aposta(){};

    // getters e setters
    String getNome(){
        return nome;
    }

    void setNome(String nome){
        this.nome = nome;
    }

    long getCpf(){
        return cpf;
    }

    void setCpf(int cpf){
        this.cpf = cpf;
    }

    int getRegistro(){
        return registro;
    }

    void setRegistro(int registro){
        this.registro = registro;
    }

    int[] getNumeros(){
        return numeros;
    }

    void setNumeros(int[] numeros){
        this.numeros = numeros;
    }

}
