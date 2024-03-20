import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

//CLASSE CONTROLE DA MEGA SENA
public class Megasena{

    // estrutura para referenciar cada numero da sorte com cada apostador que o escolheu
    private HashMap<Integer, ArrayList<Aposta>> apostasMap;

    // estrutura para guardar os numeros sorteados
    private ArrayList<Integer> sorteados;

    // estrutura para guardar apenas as apostas
    private ArrayList<Aposta> apostasLista;

    // DESCARTAVEL?
    // estrutura para guardar o(s) ganhador(es)
    private ArrayList<Aposta> ganhou;

    // variavel que controla o número de registro de cada apostador
    private int ctrl_registro;

    // singleton para que seja usado o scanner instanciado na classe app
    private Scanner input = App.getScanner();

    // construtor da mega sena
    public Megasena(){
        this.apostasMap = new HashMap<>();
        this.sorteados = new ArrayList<>();
        this.apostasLista = new ArrayList<>();
        this.ganhou = new ArrayList<>();
        this.ctrl_registro = 1000;
        //iniciar(); //testar mais tarde
    }

    // getters e setters
    int getCtrl_Registro(){
        return ctrl_registro;
    }
    //nota: como queremos aumentar de um em um os registros, deixo o set sem receber parametro e apenas somo um.
    void setCtrl_Registro(){
        this.ctrl_registro = ctrl_registro+1;
    }

    // menu para coordenar as funcoes do jogo da mega sena
    // while !"avancar" > continua no loop das apostas
    public void iniciar(){

        boolean continuar = false;
        do{
            System.out.println("\nREGISTRO ESTA EM: "+getCtrl_Registro());
            System.out.println("\n========= Fase de apostas =========");
            System.out.println("0: Registrar nova aposta \n1: Listar apostas \n2: Finalizar periodo de apostas e executar sorteio");
            System.out.print(">> ");

            int ans = input.nextInt();
            switch(ans){
                case 0:
                    //registrar nova aposta
                    registrar_aposta();
                    break;

                case 1:                  
                    //listar apostas
                    apresenta_apostas();
                    break;

                case 2:
                    //TODO: finalizar periodo de apostas e iniciar sorteio
                    //chamada do metodo sorteio?
                    break;

                }
        }while(continuar != true);
    }

    //com scanner
    // metodo principal chamado no menu, monta e registra uma aposta
    private void registrar_aposta(){
        String reg_nome;
        String reg_cpf;

        // Nota: depois de passar um perrengue por conta de um bug nao resolvido com o scanner, descobri que a linha abaixo limpa o buffer do scanner e nao faz com que ele pule comandos
        input.nextLine();
        
        System.out.println("\nDigite o Nome: ");
        System.out.print(">> ");
        reg_nome = input.nextLine();

        System.out.println("\nDigite o CPF: ");
        System.out.print(">> ");
        reg_cpf = input.nextLine();

        boolean sair;
        do{
            //int [] array_sortudo = new int[5];
            
            // cria a aposta e aumenta o numero de registro para a proxima aposta
            Aposta ticket = new Aposta(reg_nome, reg_cpf, getCtrl_Registro(), gerar_numeros());
            setCtrl_Registro();

            System.out.println(ticket.getNumeros());

            // add a lista de apostas
            apostasLista.add(ticket);
            
            //TODO: add hashmap para sorteio

            // pergunta se sera gerado uma nova aposta
            System.out.println("Apostador, gostaria de realizar uma nova aposta? \n1: Sim \n2: Não");
            System.out.print(">> ");
            int escolha = input.nextInt();
            sair = (escolha == 1) ? false : true;

        }while(sair != true);

    }

    // metodo auxiliar, gera o conjunto de numeros da sorte de cada aposta de acordo com a escolha do apostador
    private int[] gerar_numeros(){

        int [] arr = new int[5]; 
        
        for(int n : arr) System.out.println("> "+ n);
        System.out.println("\n1: Escolher números \n2: Gerar aleatoriamente");
        System.out.print(">> ");
        int ans = input.nextInt();
        switch(ans){
            case 1: 
                escolhe_numeros(arr);
                break;
            case 2:
                surpresinha(arr);
                break;
            default:
                System.out.println("Opção inválida.");
        }
        System.out.println("\nARRAY FINAL: "+Arrays.toString(arr));
        return arr;
    }

    // metodo auxiliar em gerar_numeros(), opcao para escolher numeros a dedo
    private void escolhe_numeros(int [] arr){
        int my_int;
        int i = 0;
        do{
            System.out.print(">> ");
            my_int = input.nextInt();
            if(repetido(arr, my_int) == false){
                arr[i] = my_int;                
                i++;
            }
        }while(i < arr.length);
    }

    // metodo auxiliar em gerar_numeros(), opcao para escolher numeros aleatorios
    private void surpresinha(int [] arr){
        int new_int;
        int i = 0;
        do{
            // nao precisa passar por todo o array, apenas nos locais que foram instanciados
            new_int = ThreadLocalRandom.current().nextInt(1, 51);
            if(repetido(arr, new_int) == false){
                arr[i] = new_int;
                System.out.println("Int: "+ new_int);
                System.out.println("Arr: "+ Arrays.toString(arr));
                i++;
            }
            
        }while(i < arr.length);
    }

    // metodo auxiliar em surpresinha() e escolhe_numeros(), verifica se um numero gerado esta repetido
    private boolean repetido(int [] arr, int in){
        boolean repetido = false;
        for (int n = 0; n < arr.length; n++){
            if(in == arr[n]){ 
                repetido = true;
                break;
            }
        }
        return repetido;
    }

    
    // metodo principal, recebe a lista de apostadores, onde ira mostrar todas as apostas de todos os jogadores
    private void apresenta_apostas(){
        System.out.println("\nRegistro das apostas: ");
        for (Aposta a : apostasLista) System.out.println(a);
    }

    private void sorteio(){

    }
}