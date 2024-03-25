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

    // variavel que controla o número de registro de cada apostador, aumentando em um a cada criacao de aposta
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

    // metodo para executar o sorteio e realizar a apuracao do sorteio
    private void executar(){
        System.out.println("\n========= Iniciando sorteio =========");
        
        // controle para sair do loop
        boolean ctrl = false;
        do{
            
            //em teste, inicializa arrays manualmente
            //sorteio_teste();
            sorteio();
            apura_sorteio();

            ctrl = (verificaGanhador() == false && verificaRodadaLimite() == false) ? false : true;
            
        }while(ctrl != true);
        
        terminar();
    }

    // fim da apuracao e apresentacao dos resultados
    private void terminar(){

        int[] ints = new int[apostasMap.keySet().size()];

        System.out.println("\n========= FIM DA MEGA SENA! =========");
        System.out.println("\nNumeros sorteados: "+sorteados.toString());
        System.out.println("Foram executadas "+rodadas+" rodadas no total.");
        
        //metodo pra printar numeros mais escolhidos
        prepara_sort(ints);
        mais_sorteados(ints);

        if(verificaGanhador() == true){
            
            ganhadores.sort(null);
            System.out.println("\nGanhador(es) desta edição: "+ganhadores.size());
            for(Aposta a : ganhadores) System.out.println(a.toString());
        
        }else{
            System.out.println("\nNão teve nenhum ganhador nesta edição!");
        }
    }

    // metodo auxiliar em apura_sorteio() focado na apuracao do sorteio, adiciona um ganhador a lista de ganhadores caso exista algum ticket da sorte com 5 acertos
    private void apura_sorteio(){
        // para cada numero sorteado(filtra as opcoes impossiveis)
        for(Integer integer : sorteados){
            // para cada aposta desta chave do hashmap
            // e se a chave existir(numero sorteado) no hashmap
            if(apostasMap.get(integer) != null){
                for(Aposta ap : apostasMap.get(integer)){
                    //aux = ap.getNumerosRef();
                    if(contaAcertos(ap.getNumerosRef()) == 5 && ganhadores.contains(ap) == false) ganhadores.add(ap);
                    
                }
            }
        }
    }

    // metodo auxiliar em apura_sorteio(), verifica os acertos do sorteio
    private int contaAcertos(int[] arr){
        int acerto = 0;
        for(int i : arr){
            for(Integer j : sorteados){
                if(Integer.valueOf(i) == j) acerto++;
            }
        }
        return acerto;
    }

    // metodo auxiliar em executar() e terminar(), retorna true ou false para checar se ha ganhadores na lista
    private boolean verificaGanhador(){
        boolean ver = (ganhadores.isEmpty() == true) ? false : true;
        return ver;
    }

    // metodo auxiliar em executar(), retorna true ou false de acordo com o limite de rodadas
    private boolean verificaRodadaLimite(){
        boolean limite = (rodadas < 25) ? false : true;
        return limite;
    }

    //metodo auxiliar em terminar() que mostra os numeros mais sorteados
    private void mais_sorteados(int [] ints){
        System.out.println("\nEstes foram os 5 numeros mais sorteados! [numero - quantidade]");
        for(int i = 0; i < 5; i++){
            System.out.println(">>"+ints[i]+" - "+apostasMap.get(ints[i]).size());    
        }
    }

    //metodo para preparar uma lista de numeros mais escolhidos do sorteio para ser ordenada, para ser usado dentro da chamada do quick sort
    private void prepara_sort(int [] ints){
        
        int index = 0;
        for(Integer in : apostasMap.keySet()){
            ints[index] = in.intValue(); 
            index++;
        }
        qs(ints, 0, 0);
    }

    //metodo que realiza ordenacao
    private void qs(int[] arr, int l, int r){
        if(r == 0){
            r = arr.length-1;
        }else{
            int p = partition(arr, l, r);
            qs(arr, l, p-1);
            qs(arr, p+1, r);
        }
    }

    private int partition(int[] arr, int l, int r) {
        int aux;
        int pivot = arr[r];
        int i = l;
        for(int j = l; j < r; j++){
            if(arr[j] <= pivot){
                aux = arr[j];
                arr[j] = arr[i];
                arr[i] = aux;
                i = i+1;
            }
        }    
        aux = arr[i];
        arr[i] = arr[r];
        arr[r] = aux;

        return i;
    }

    // metodo auxiliar para testes, igual ao teste(), porem com prints a mais e lista de numeros sorteados criada "manualmente"
    @SuppressWarnings("unused")
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
            sorteados.add(6);
        }
        else{
            int random;
            boolean feito = false;
            
            do{
                random = ThreadLocalRandom.current().nextInt(1, 51);
                if(sorteados.contains(random) != true){
                    System.out.println("Novo numero sorteado: "+random);
                    sorteados.add(random);
                    feito = true;
                } 
            }while(feito != true);
             
        }
    }

    // metodo principal em executar(), executa o sorteio
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
                    System.out.println("Novo numero sorteado!");
                    sorteados.add(random);
                    feito = true;
                } 
            }while(feito != true);
        }
    }

    // metodo auxiliar em sorteio(), completa a lista de numeros sorteados
    private void popula_sorteados(int [] arr){
        
        surpresinha(arr);
        for (int n : arr) sorteados.add(n);
        System.out.println(sorteados.toString());
    }

    // metodo usado para confirmar a passagem da fase inicial para a fase final do jogo
    private void finalizar_e_sortear(){
        // variavel responsavel por confirmar a passagem de um estagio do sorteio para outro
        int confirmar_token;
        
        //iniciar sorteio e apuração dos resultados
        System.out.println("\nATENÇÃO: Uma vez que iniciado o sorteio, não será mais possível registrar novas apostas, deseja continuar? \n1: Confirmar \n2: Voltar ");
        System.out.print(">> ");
        confirmar_token = input.nextInt();
        
        if(confirmar_token == 1){ 
            executar(); 
        }else{ 
            iniciar();
        }
    }

    // metodo principal chamado no menu, monta e registra uma aposta
    private void registrar_aposta(){
        String reg_nome;
        String reg_cpf;

        // Nota: depois de passar um perrengue por conta de um bug nao resolvido com o scanner, descobri que a linha abaixo limpa o buffer do scanner e nao faz com que ele pule comandos
        input.nextLine();
        
        // recebe o nome
        System.out.println("\nDigite o Nome: ");
        System.out.print(">> ");
        reg_nome = input.nextLine();

        // recebe o cpf
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
        //teste: System.out.println("Array inicializado: ");
        //teste: for(int n : arr) System.out.println("> "+ n);
        
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

    // metodo auxiliar em gerar_numeros() e popula_sorteados(), opcao para escolher numeros aleatorios
    private void surpresinha(int [] arr){
        int new_int;
        int i = 0;
        do{
            new_int = ThreadLocalRandom.current().nextInt(1, 51);
            if(repetido(arr, new_int) == false){
                arr[i] = new_int;
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
        for(int i : aux){
            if (apostasMap.containsKey(i) != true) apostasMap.put(i, new ArrayList<Aposta>());
            apostasMap.get(Integer.valueOf(i)).add(aposta);
        }
    }
}