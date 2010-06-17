import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.Integer;

/**
 * Programa que recibe como entrada un archivo (.input) con
 * un conjunto de nombres de cursos junto con sus
 * prerequisitos.
 * Computa los prerequisitos inmediatos  para cada
 * curso y las escribe en el archivo de salida. 
 * Sintaxis: java Main <archivo.input> <archivo.output>
 *
 * @author José A. Goncalves y Jennifer Dos Reis
 * @version 1.0
 * @since 1.6
**/

public class Main {

    public DiGraph cargarArchivo(String nombre, Integer S, Integer E) {
	BufferedReader in = new BufferedReader(nombre);
	String datos = in.readLine();
	String[] tokens = datos.split(" ");

	int numPisos = Integer.parseInt(tokens[0]);
	int numColumnas = Integer.parseInt(tokens[1]);
	int numFilas = Integer.parseInt(tokens[2]);

	DiGraph salida = new DiGraphList(numPisos*numColumnas*numFilas);
	boolean[] tipoNodos = new boolean[numPisos*numColumnas*numFilas];
	int numNodo = 0;

	for (int i=0; i<numPisos; i++) {
	    for (int k=0; k<numFilas; k++) {
		String linea = in.readLine();
		//Verificar que linea.length==numColumnas
		for (int j=0; j<numColumnas; j++) {
		    numNodo++;
		    char caracter = linea.charAt(j);
		    if (caracter=="#") {
			tipoNodos[numNodo] = false;
		    } else {
			tipoNodos[numNodo] = true;
			if (caracter == "S" ) {
			    S = new Integer(numNodo);
			}
			if (caracter == "E" ) {
			    E = new Integer(numNodo);
		        }
			//Si no es el 1º caracter
			// Evaluar nodo 'oeste'
			if (j!=0 && tipoNodos[numNodo-1]) {
			    Arc tmp = salida.addArc(numNodo, numNodo-1);
			    tmp = salida.addArc(numNodo-1, numNodo);
			}

			//Si no es la primera linea
			// Evaluar nodo 'norte'
			if (k!=0 && tipoNodos[numNodo-numColumnas]) {
			    Arc tmp = salida.addArc(numNodo, numNodo-numColumnas);
			    tmp = salida.addArc(numNodo-numColumnas, numNodo);
			}

			//Si no es el primer piso
			// Evaluar nodo 'piso arriba'
			if (i!=0 && tipoNodos[numNodo-numColumnas*numFilas]) {
			    Arc tmp = salida.addArc(numNodo, numNodo-numColumnas*numFilas);
			    tmp = salida.addArc(numNodo-numColumnas*numFilas, numNodo);
			}
		    }
		    
		}
	    }
	    //Esto lee la linea en blanco que separa cada piso
	    String tmp = in.readLine();
	}

	return salida;

    }

    public int BFS(DiGraph grafo, Integer S, Integer E) {

    }

    public static void main(String[] args){
	
	if (args.length != 2 && args.length !=1) {
	    System.err.println("Sintaxis: java Main <fileName_entrada> <fileName_salida>");
	    return;
	}

	Integer S = null;
	Integer E = null;
	DiGraph grafo = null;
	try {
	    grafo = cargarArchivo(args[0],S,E);
	} catch ...

	int tiempo = BFS(grafo,S,E);

	String salida = "";
	if (tiempo==0) {
	    salida = "Atrapado!";
	} else {
	    salida = "bla";
	}

	//escribir salida al archivo
	try {
	    PrintStream out = new PrintStream(args[1]);
	    out.print(salida);
	} catch (IOException ioe) {
	    System.err.println("No se puede escribir el archivo");
	}

    }

}





