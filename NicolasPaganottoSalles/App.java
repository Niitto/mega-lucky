// @author: Nicolas Salles
import java.util.InputMismatchException;
import java.util.Scanner;

// ESTA CLASSE APENAS INICIALIZA O PROGRAMA
public class App{
    
    private static final Scanner input = new Scanner(System.in);

    protected static Scanner getScanner(){
        return input;
    }

    protected static void closeScanner(){
        input.close();
    }

    // metodo auxiliar, escaneia um inteiro
    protected static int scan_int() throws InputMismatchException{

        System.out.print(">> ");
        int in = input.nextInt();

        System.out.println(in);
        return in;
    }

    // metodo auxiliar, escaneia uma string
    protected static String scan_string() throws InputMismatchException{
                
        System.out.print(">> ");
        String str = input.nextLine();
        
        System.out.println(str);
        return str;
    }

    public static void main(String [] args){

        //final Scanner input= new Scanner(System.in);

        System.out.println("Bem-vindo ao sistema da Mega Sena!");

        //try(Scanner input = new Scanner(System.in)){
        boolean sair = false;
        do{
            System.out.println("\n========= Menu principal =========");
            System.out.println("1: Iniciar nova Mega Sena \n2: Sair ");
            System.out.print(">> ");
            
            int ans = input.nextInt();
            switch(ans){
                case 1:
                    //chama metodo para executar este processo
                    //cria objeto megasena e da inicio ao jogo?
                    Megasena megasena = new Megasena();
                    megasena.iniciar();
                    break;
                    //System.out.println("0: Registrar nova aposta \n1: Listar apostas \n2: Finalizar periodo de apostas e executar sorteio");
                case 2:
                    sair = true;
                    System.out.println("\nPrograma finalizado");
                    break;

                default:
                    System.out.println("\nAVISO: ESCOLHA INVÁLIDA");
                    break;
                    //throw new InputMismatchException("Escolha invalida");    
            }
        }while(sair != true);
        //}
        //catch(InputMismatchException e){
        //    System.out.println("ASJEHFLAKJDLAW");
        //}
        closeScanner();
    }

    
}