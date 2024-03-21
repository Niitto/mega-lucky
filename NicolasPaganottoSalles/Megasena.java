import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

//CLASSE CONTROLE DA MEGA SENA
public class Megasena{

    // estrutura para referenciar cada numero da sorte com cada apostador que o escolheu e calcular o vencedor
    private HashMap<Integer, ArrayList<Aposta>> apostasMap;

    // estrutura para guardar os numeros sorteados
    private ArrayList<Integer> sorteados;

    // estrutura para guardar apenas as apostas
    private ArrayList<Aposta> apostasLista;

    // estrutura para guardar o(s) ganhador(es)
    private ArrayList<Aposta> ganhadores;

    // variavel que controla o número de registro de cada apostador
    private int ctrl_registro;

    // grava quantas rodadas de sorteio foram executadas
    private int rodadas;

    // singleton para que seja usado o scanner instanciado na classe app
    private Scanner input = App.getScanner();

    // construtor da mega sena
    public Megasena(){
        this.apostasMap = new HashMap<Integer, ArrayList<Aposta>>();
        this.sorteados = new ArrayList<Integer>();
        this.apostasLista = new ArrayList<Aposta>();
        this.ganhadores = new ArrayList<Aposta>();
        this.ctrl_registro = 1000;
        this.rodadas = 0;
    }

    // getters e setters
    private int getCtrl_Registro(){
        return ctrl_registro;
    }
    //nota: como queremos aumentar de um em um os registros, deixo o set sem receber parametro e apenas somo um.
    private void setCtrl_Registro(){
        this.ctrl_registro = ctrl_registro + 1;
    }

    private int getRodadas(){
        return rodadas;
    }
    private void setRodadas(){
        this.rodadas = rodadas + 1;
    }

    // menu para coordenar as funcoes do jogo da mega sena
    public void iniciar(){

        boolean continuar = false;
        do{
            System.out.println("\nREGISTRO ESTA EM: "+getCtrl_Registro());
            System.out.println("\n========= Fase de apostas =========");
            System.out.println("1: Registrar nova aposta \n2: Listar apostas \n3: Finalizar periodo de apostas e executar sorteio");
            System.out.print(">> ");

            int ans = input.nextInt();
            switch(ans){
                case 1:
                    //registrar nova aposta
                    registrar_aposta();
                    break;

                case 2:                  
                    //listar apostas
                    apresenta_apostas();
                    break;

                case 3:
                    // passa para confirmacao
                    continuar = true;
                    finalizar_e_sortear();
                    break;

                default:
                    System.out.println("AVISO: ESCOLHA INVÁLIDA");
                    break;

                }
        }while(continuar != true);
    }

    // metodo para executar o sorteio e realizar a apuracao dos votos
    private void executar(){
        System.out.println("\n========= Iniciando sorteio =========");
        
        do{
            //em teste, inicializa arrays manualmente
            sorteio_teste();
            //sorteio();
            apura_votos();
            
        }while(verificaGanhadores() == true || rodadas < 25);
        
        terminar();
        
    }

    private void terminar(){
        System.out.println("\n========= FIM DA MEGA SENA! =========");
        if(verificaGanhadores() == true){
            System.out.println("Ganhador(es) desta edição: ");
            for(Aposta a : ganhadores) System.out.println(a.toString());
        }else{
            System.out.println("Não teve nenhum ganhador nesta edição!");
        }
    }

    private void apura_votos(){
        // para cada numero sorteado(filtra as opcoes impossiveis)
        for(Integer integer : sorteados){
            // para cada aposta desta chave do hashmap
            // e se a chave existir(numero sorteado) no hashmap
            if(apostasMap.get(integer) != null){
                for(Aposta ap : apostasMap.get(integer)){
                    //aux = ap.getNumerosRef();
                    if(contaAcertos(ap.getNumerosRef()) == 5) ganhadores.add(ap);
                    
                }
            }
        }
    }

    private int contaAcertos(int[] arr){
        int acerto = 0;
        for(int i : arr){
            for(Integer j : sorteados){
                if(Integer.valueOf(i) == j) acerto++;
            }
        }
        return acerto;
    }

    private boolean verificaGanhadores(){
        boolean ver = (ganhadores.isEmpty() == true) ? false : true;
        return ver;
    }

    private void sorteio_teste(){
        // +1 rodada
        setRodadas();

        System.out.println("\nRodada "+getRodadas()+": Sorteando...");

        if(sorteados.isEmpty()){
            sorteados.add(1);
            sorteados.add(2);
            sorteados.add(3);
            sorteados.add(4);
            sorteados.add(5);
        }
        else{
            int random;
            boolean feito = false;
            
            do{
                random = ThreadLocalRandom.current().nextInt(1, 51);
                if(sorteados.contains(random) != true){
                    sorteados.add(random);
                    feito = true;
                } 
            }while(feito != true);
             
        }
    }

    // se método for chamado novamente/se array de numeros ja estiver cheio, adiciona apenas um
    // nota para self: poderia usar tokens/variáveis de verificação na própria classe
    private void sorteio(){
        // +1 rodada
        setRodadas();

        System.out.println("\nRodada "+getRodadas()+": Sorteando...");

        if(sorteados.isEmpty()){
            int [] aux = new int[5];
            popula_sorteados(aux);
        }
        else{
            int random;
            boolean feito = false;
            
            do{
                random = ThreadLocalRandom.current().nextInt(1, 51);
                if(sorteados.contains(random) != true){
                    sorteados.add(random);
                    feito = true;
                } 
            }while(feito != true);
             
        }
    }

    // completa a lista de numeros sorteados
    private void popula_sorteados(int [] arr){
        
        surpresinha(arr);
        //System.out.println("\nFOI, HORA DE PASSAR PRO ARRAYLIST");
        //System.out.println(Arrays.toString(arr));
        for (int n : arr) sorteados.add(n);
        //System.out.println("FOI, ARRAYLIST CRIADO!");
        //System.out.println(sorteados.toString());
    }

    // metodo usado para confirmar a passagem da fase inicial para a fase final do jogo
    private void finalizar_e_sortear(){
        int confirmar_token;
        
        // Nota: depois de passar um perrengue por conta de um bug nao resolvido com o scanner, descobri que a linha abaixo limpa o buffer do scanner e nao faz com que ele pule comandos
        //input.nextLine();
        
        //iniciar sorteio e apuração dos resultados
        System.out.println("\nATENÇÃO: Uma vez que iniciado o sorteio, não será mais possível registrar novas apostas, deseja continuar? \n1: Confirmar \n2: Voltar ");
        System.out.print(">> ");
        confirmar_token = input.nextInt();
        //confirmar_token.replaceAll("\\s", "");
        
        if(confirmar_token == 1){ 
            executar(); 
        }else{ 
            iniciar();}
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
            
            // cria a aposta e aumenta o numero de registro para a proxima aposta
            Aposta ticket = new Aposta(reg_nome, reg_cpf, getCtrl_Registro(), gerar_numeros());
            setCtrl_Registro();

            // add a lista de apostas
            apostasLista.add(ticket);
            
            // add um ticket no hashmap para sorteio
            addApostasEmHash(ticket);

            // pergunta se sera gerado uma nova aposta
            System.out.println("\nApostador, gostaria de realizar uma nova aposta? \n1: Sim \n2: Não");
            System.out.print(">> ");
            int escolha = input.nextInt();
            sair = (escolha == 1) ? false : true;

        }while(sair != true);

    }

    // metodo auxiliar, gera o conjunto de numeros da sorte de cada aposta de acordo com a escolha do apostador
    private int[] gerar_numeros(){

        int [] arr = new int[5]; 
        //System.out.println("Array inicializado: ");
        //for(int n : arr) System.out.println("> "+ n);
        
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

        //System.out.println("\nARRAY FINAL: "+Arrays.toString(arr));
        return arr;
    }

    // metodo auxiliar em gerar_numeros(), opcao para escolher numeros a dedo
    private void escolhe_numeros(int [] arr){
                
        int my_int;
        int i = 1;
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
            new_int = ThreadLocalRandom.current().nextInt(1, 51);
            if(repetido(arr, new_int) == false){
                arr[i] = new_int;
                //System.out.println("Int: "+ new_int);
                //System.out.println("Arr: "+ Arrays.toString(arr));
                i++;
            }
            
        }while(i < arr.length);
    }

    // metodo auxiliar em surpresinha() e escolhe_numeros(), verifica se um numero gerado esta repetido
    // param: um array, um numero
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

    // adiciona um conjunto <integer(numero sorteado), integer(registro)> em apostasMap
    private void addApostasEmHash(Aposta aposta){
        int[] aux = aposta.getNumerosRef();
        //System.out.println("Este array acabou de ser adicionado ao hash: "+Arrays.toString(aux));
        for(int i : aux){
            if (apostasMap.containsKey(i) != true) apostasMap.put(i, new ArrayList<Aposta>());
            apostasMap.get(Integer.valueOf(i)).add(aposta);
        }
        //System.out.println("\nKeySet: "+apostasMap.keySet());
        //System.out.println("Values: "+apostasMap.values());
        //System.out.println("EntrySet: "+apostasMap.entrySet());

    }

}