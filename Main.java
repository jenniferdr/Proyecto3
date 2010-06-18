import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.Integer;

/**
 * Programa que recibe como entrada un archivo que
 * contiene un calabozo medieval.
 * Produce un arhivo de salida en el que indica "Atrapado!" 
 * se encuentra atrapado o "Escape en x minutos" si logro 
 * escapar en x minutos.
 * Sintaxis: java Main <archivo_entrada> <archivo_salida>
 *
 * @author José A. Goncalves y Jennifer Dos Reis
 * @version 1.0
 * @since 1.6
**/


public class Main {

    public static Object[] cargarArchivo(String nombre) 
		throws NumberFormatException, FileNotFoundException, IOException {
	BufferedReader in = new BufferedReader(new FileReader(nombre));
	String datos = in.readLine();
	String[] tokens = datos.split(" ");
	Object[] elementos= new Object[3];

	int numPisos = Integer.parseInt(tokens[0]);
	int numFilas = Integer.parseInt(tokens[1]);
	int numColumnas = Integer.parseInt(tokens[2]);

	DiGraph salida = new DiGraphList(numPisos*numColumnas*numFilas);
	boolean[] tipoNodos = new boolean[numPisos*numColumnas*numFilas];
	int numNodo = -1;

	for (int i=0; i<numPisos; i++) {
	    for (int k=0; k<numFilas; k++) {
		String linea = in.readLine();
		//Verificar que linea.length==numColumnas
		for (int j=0; j<numColumnas; j++) {
		    numNodo++;
		    char caracter = linea.charAt(j);
		    if (caracter=='#') {
			tipoNodos[numNodo] = false;
		    } else {
			tipoNodos[numNodo] = true;
			if (caracter == 'S' ) {
			    elementos[1]= new Integer(numNodo);
			}
			if (caracter == 'E' ) {
			    elementos[2]= new Integer(numNodo);
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
	// Devuelve un arreglo con un Digraph, numero de nodo S y numero de nodo E
	elementos[0]= salida;
	return elementos;

    }

    public static int escapar(DiGraph grafo, Integer S, Integer E) {
	int nodoS = S.intValue();
	int nodoE = E.intValue();

    	boolean[] visitados = new boolean[grafo.getNumberOfNodes()];
	for (int i=0; i<visitados.length; i++) {
	    visitados[i] = false;
	}
	
	Cola<Camino> caminos = new Cola<Camino>();
	//abrir S
	visitados[nodoS] = true;
	caminos.encolar(new Camino(null, 0, nodoS));

	while (!caminos.vacia()) {
	    Camino caminoTmp = caminos.desencolar();
	    int nodoPred = caminoTmp.getNodoTerm();
	    int costoPred = caminoTmp.getCosto();

	    List<Integer> sucesores = grafo.getSucesors(nodoPred);
	    for (int i=0; i<sucesores.size(); i++) {
		int nodoSuc = sucesores.get(i).intValue();
		if (nodoSuc == nodoE) {
		    return costoPred+1;
		}
		if (!visitados[nodoSuc]) {
		    visitados[nodoSuc] = true;
		    caminos.encolar(new Camino(caminoTmp, costoPred+1, nodoSuc));
		}
	    }
	}
	return -1;
    }

    public static void main(String[] args){
	
	if (args.length != 2) {
	    System.err.println("Sintaxis: java Main <fileName_entrada> <fileName_salida>");
	    return;
	}

	Integer S;
	Integer E;
	Object[] elementos= null;
	DiGraph grafo = null;
	try {
	    elementos = cargarArchivo(args[0]);
	    grafo= (DiGraph)elementos[0];
	    S = (Integer) elementos[1];
	    E = (Integer) elementos[2];
	} catch(NumberFormatException e) {
	    System.err.println("Error de formato en el archivo especificado");
	    return;
	} catch (FileNotFoundException fnfe) {
	    System.err.println("Error al cargar archivo, verifique el nombre");
	    return;
	} catch(IOException ioe) {
	    System.err.println("Error: " + ioe);
	    return;
	}

	int tiempo = escapar(grafo,S,E);

	String salida = "";
	if (tiempo==-1) {
	    salida = "Atrapado!";
	} else {
	    salida = "Escape en "+tiempo+" minutos(s).";
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
