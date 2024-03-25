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
    protected static String scan_str() throws InputMismatchException{
                
        System.out.print(">> ");
        String str = input.nextLine();
        
        System.out.println(str);
        return str;
    }

    public static void main(String [] args){

        System.out.println("Bem-vindo ao sistema da Mega Sena!");

        boolean sair = false;
        do{
            System.out.println("\n========= Menu principal =========");
            System.out.println("1: Iniciar nova Mega Sena \n2: Sair ");
            System.out.print(">> ");
            
            int ans = input.nextInt();
            switch(ans){
                case 1:
                    Megasena megasena = new Megasena();
                    megasena.iniciar();
                    break;
                    
                case 2:
                    sair = true;
                    System.out.println("\nPrograma finalizado");
                    break;

                default:
                    System.out.println("\nAVISO: ESCOLHA INVÁLIDA");
                    break;
                        
            }
        }while(sair != true);
        
        closeScanner();
    }

    
}