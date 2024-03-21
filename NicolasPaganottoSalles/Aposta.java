//CADA APOSTA É UM OBJETO
import java.util.Arrays;

public class Aposta{
    private String nome;
    private String cpf;
    private int registro;
    private int numeros[];

    // construtor com parametros
    public Aposta(String nome, String cpf, int registro, int[] numeros){
        this.nome = nome;
        this.cpf = cpf;
        this.registro = registro;
        this.numeros = numeros;
    }

    // getters e setters
    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getCpf(){
        return cpf;
    }

    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    public int getRegistro(){
        return registro;
    }

    public void setRegistro(int registro){
        this.registro = registro;
    }

    public String getNumeros(){
        return Arrays.toString(numeros);
    }

    public int[] getNumerosRef(){
        return numeros;
    }


    @Override
    public String toString(){
        return "\nNome: "+getNome()+" - CPF: "+getCpf()+" - Registro: "+ getRegistro()+" - Ticket: "+getNumeros();
    }
}
