import java.util.*;

//CLASSE CONTROLE DA MEGA SENA
public class Megasena{

    // estrutura para referenciar cada numero da sorte com cada apostador que o escolheu
    private HashMap<Integer, ArrayList<Aposta>> apostas;

    // estrutura para guardar os numeros sorteados
    private ArrayList<Integer> sorteados;

    // DESCARTAVEL?
    // estrutura para guardar o(s) ganhador(es)
    private ArrayList<Aposta> ganhou;

    // construtor da mega sena
    public Megasena(){
        this.apostas = new HashMap<>();
        this.ganhou = new ArrayList<>();
    }

    // menu para coordenar as funcoes do jogo da mega sena
    // while !"avancar" > continua no loop das apostas
    public void iniciar(){
        System.out.println("========= Começando fase de apostas =========");
        System.out.println("0: Registrar nova aposta \n1: Listar apostas \n2: Finalizar periodo de apostas e executar sorteio");
        try (Scanner input = new Scanner(System.in)) {
            int ans = input.nextInt();
            switch(ans){
                case 0:
                    //registrar nova aposta
                case 1: 
                    //listar apostas
                case 2:
                    //finalizar periodo de apostas e iniciar sorteio
                    //chamada do metodo sorteio?
            }
        }
    }

    //com scanner
    //vai ter parametro?
    private void registrar_nova_aposta(){
        Aposta a = new Aposta();
        a.setNome("Nick");
    }

    //sem scanner
    //talvez receba so a lista de apostadores, onde ira mostrar todas as apostas de todos os jogadores
    private void lista_apostas(){

    }

    private void sorteio(){

    }
}