package ui;

import java.util.Scanner;
import structures.PilaGenerica;
import structures.TablasHash;

public class Main {

    private Scanner sc;

    public Main() {
        sc = new Scanner(System.in);

    }

    public void ejecutar() throws Exception {
        while (true) {
            System.out.println("\nSeleccione la opcion:");
            System.out.println("1. Punto 1, Verificar balanceo de expresión");
            System.out.println("2. Punto 2, Encontrar pares con suma objetivo");
            System.out.println("3. Salir del programa");
            System.out.print("Opcion: ");

            int opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese expresion a verificar:");
                    String expresion = sc.nextLine();
                    boolean resultado = verificarBalanceo(expresion);
                    System.out.println("Resultado: " + (resultado ? "TRUE" : "FALSE"));
                    break;

                case 2:
                    System.out.println("Ingrese numeros separados por espacio: ");
                    String lineaNumeros = sc.nextLine();
                    System.out.println("Ingrese suma objetivo: ");
                    int objetivo = Integer.parseInt(sc.nextLine());

                    String[] partes = lineaNumeros.trim().split("\\s+");
                    int[] numeros = new int[partes.length];
                    for (int i = 0; i < partes.length; i++) {
                        numeros[i] = Integer.parseInt(partes[i]);
                    }

                    encontrarParesConSuma(numeros, objetivo);
                    break;

                case 3:
                    System.out.println("Chao");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opcion no permitida");
            }
        }
    }

    /**
     * Verifica si la expresion esta balanceada usando PilaGenerica
     * @param s expresion a verificar
     * @return true si esta balanceada, false si no
     */
    public boolean verificarBalanceo(String s) {
        PilaGenerica<Character> pila = new PilaGenerica<>(s.length());
        int contador = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(' || c == '[' || c =='{'){
                pila.Push(c);
                contador ++;
            } else if(c == ')' ||c == ']'||c == '}'){
                if (pila.getSize() == 0){
                        return false;
                }
    
                char top = pila.Pop();
                contador --;
    
                if((c == ')' && top != '(') || (c == ']' && top != '[') || (c == '}' && top != '{')){
                    return false;
                }
            }

        }
        return contador == 0;
    }

    /**
     * Encuentra y muestra todos los pares unicos de numeros que sumen objetivo usando TablasHash.
     * @param numeros arreglo de numeros enteros
     * @param objetivo suma objetivo
     */
    public void encontrarParesConSuma(int[] numeros, int objetivo) {
        try {
            TablasHash tabla = new TablasHash(numeros.length * 2);
            for (int i = 0; i < numeros.length; i++) {
                int num = numeros[i];
                int complemento = objetivo - num;
                
                int claveNum = (num % tabla.getSize() + tabla.getSize()) % tabla.getSize();
                int claveComplemento = (complemento % tabla.getSize() + tabla.getSize()) % tabla.getSize();

                if(tabla.search(claveComplemento, complemento)){
                    if(num < complemento){
                        System.out.println("(" + num + "," + complemento+")");
                    } else if(num > complemento){
                        System.out.println("(" + complemento + "," + num+")");
                    }
                }

                tabla.insert(claveNum, num);
            }    
        } catch (Exception e) {
            System.out.println("Ocurrió un error.");

        }
    }

    public static void main(String[] args) throws Exception {
        Main app = new Main();
        app.ejecutar();
    }
}
