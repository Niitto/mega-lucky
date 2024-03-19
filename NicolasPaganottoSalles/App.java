// @author: Nicolas Salles
import java.util.InputMismatchException;
import java.util.Scanner;

// ESTA CLASSE APENAS INICIALIZA O PROGRAMA
public class App{
    public static void main(String [] args){
        
        System.out.println("========= Bem-vindo ao sistema da Mega Sena! =========");

        try(Scanner input = new Scanner(System.in)){
            boolean sair = false;
            while(sair != true){
                System.out.println("0: Iniciar nova Mega Sena \n1: Sair \n2: TESTE");
                //int ans = input.nextInt();
                switch(input.nextInt()){
                    case 0:
                        //chama metodo para executar este processo
                        //cria objeto megasena e da inicio ao jogo?
                        Megasena megasena = new Megasena();
                        megasena.iniciar();
                        //System.out.println("0: Registrar nova aposta \n1: Listar apostas \n2: Finalizar periodo de apostas e executar sorteio");
                    case 1:
                        sair = true;
                        System.out.println("\nPrograma finalizado");
                        //break;
                    case 2:
                        System.out.println("\nTESTE");
                        //break;
                    default:
                        System.out.println("\nESCOLHA INVALIDA");
                        //throw new InputMismatchException("Escolha invalida");    
                        
                }
            }
        }
        //catch(InputMismatchException e){
        //    System.out.println("ASJEHFLAKJDLAW");
        //}
        
    }

    
}