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
 * Produce un archivo de salida en el que indica "Atrapado!" 
 * si se encuentra atrapado o "Escape en x minutos" si logró  
 * escapar (donde x es la cantidad de minutos que tarda en salir).
 * Sintaxis: java Main <archivo_entrada> <archivo_salida>
 *
 * @author José A. Goncalves y Jennifer Dos Reis
 * @version 1.0
 * @since 1.6
**/


public class Main2 {
    /**
     * A partir del archivo de calabozo lo carga
     * y devuelve un arreglo de tamaño 3 cuyo primera posición es un DiGraph
     * correspondiente a la representación del calabozo; la segunda
     * y tercera posición son Integer que contienen cuál es el nodo S y el
     * nodo E, respectivamente.
     * @param nombre Nombre del archivo del calabozo
     * @return Un arreglo de Object de tamaño 3, cuyo contenido ya fue descrito.
     */
    public static Object[] cargarArchivo(String nombre) 
		throws NumberFormatException, FileNotFoundException, IOException {
	BufferedReader in = new BufferedReader(new FileReader(nombre));
	String datos = in.readLine();
	String[] tokens = datos.split(" ");
	Object[] elementos= new Object[3];
	DiGraph salida= null;

	int numArcos=0;
	int numPisos = Integer.parseInt(tokens[0]);
	int numFilas = Integer.parseInt(tokens[1]);
	int numColumnas = Integer.parseInt(tokens[2]);

	int totalNodos = numPisos*numColumnas*numFilas;
	boolean[] tipoNodos = new boolean[totalNodos];
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
			    numArcos++;
			}

			//Si no es la primera linea
			// Evaluar nodo 'norte'
			if (k!=0 && tipoNodos[numNodo-numColumnas]) {
			    numArcos++;
			}

			//Si no es el último piso
			// Evaluar nodo 'piso abajo'
			if (i!=0 && tipoNodos[numNodo-numColumnas*numFilas]) {
			    numArcos++;
			}
		    }
		    
		}
	    }
	    //Esto lee la linea en blanco que separa cada piso
	    String tmp = in.readLine();
	}

	if(numArcos>=totalNodos){
	    salida = new DiGraphMatrix(totalNodos);
	}else{
	    salida = new DiGraphList(totalNodos);
	}
	numNodo = -1;
	for (int i=0; i<numPisos; i++) {
	    for (int k=0; k<numFilas; k++) {
		for (int j=0; j<numColumnas; j++) {
		    numNodo++;
		    if(tipoNodos[numNodo]){
			//Si no es el 1º caracter
			// Evaluar nodo 'oeste'
			if (j!=0 && tipoNodos[numNodo-1]) {
			    int nodoOeste = numNodo-1;
			    Arc tmp = salida.addArc(numNodo, nodoOeste);
			    tmp = salida.addArc(nodoOeste, numNodo );
			}

			//Si no es la primera linea
			// Evaluar nodo 'norte'
			if (k!=0 && tipoNodos[numNodo-numColumnas]) {
			    int nodoNorte = numNodo-numColumnas;
			    Arc tmp = salida.addArc(numNodo, nodoNorte);
			    tmp = salida.addArc(nodoNorte, numNodo);
			}

			//Si no es el último piso
			// Evaluar nodo 'piso abajo'
			if (i!=0 && tipoNodos[numNodo-numColumnas*numFilas]) {
			    int nodoAbajo = numNodo - numColumnas*numFilas;
			    Arc tmp = salida.addArc(numNodo, nodoAbajo);
			    tmp = salida.addArc(nodoAbajo, numNodo);
			}
		    }
		}
	    }
	}

	// Devuelve un arreglo con un Digraph, numero de nodo S y numero de nodo E
	elementos[0] = salida;
	return elementos;

    }

    /**
     * Dado un grafo (que representa el calabozo), un nodo inicial y un nodo
     * final aplica un algoritmo de BFS modificado para conseguir y devolver 
     * el camino de longitud mínima entre los nodos dados. 
     * Si no hay camino o los nodos no existen, devuelve -1.
     * @param grafo La representación del calabozo.
     * @param S Start, el nodo inicial donde comienza la búsqueda.
     * @param E End, el nodo donde termina la búsqueda.
     * @return La distancia mínima entre el nodo S y el E.
     *         -1 si no hay camino entre ellos.
     *         -2 si alguno de los nodos no existe
     */
    public static int escapar(DiGraph grafo, Integer S, Integer E) {
	int nodoS = S.intValue();
	int nodoE = E.intValue();
	int totalNodos = grafo.getNumberOfNodes();

	//Si el inciio y el fin son los mismos, entonces no hay nada que hacer
	if (nodoS==nodoE) {
	    return 0;
	}

	//Chequeamos a ver si los nodos son válidos
	if (nodoS<0 || nodoE<0 || nodoS > totalNodos || nodoE>totalNodos) {
	    return -2;
	}

	//Utilizamos un arreglo de booleanos para determinar fácilmente
	//si ya abrimos o cerramos un camino a un nodo específico
	//true-> Ya abrimos/cerramos a ese nodo, false->Lo contrario
    	boolean[] visitados = new boolean[totalNodos];
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
		//Cuando conseguimos a E por 1º vez ya sabemos 
		//la longitud mínima, entonces terminamos
		if (nodoSuc == nodoE) {
		    return costoPred+1;
		}
		if (!visitados[nodoSuc]) {
		    visitados[nodoSuc] = true;
		    caminos.encolar(new Camino(caminoTmp, costoPred+1, nodoSuc));
		}
	    }
	}
	//Si llegamos hast aquí quiere decir que ya expandimos todos los caminos
	//posibles y que nunca encontramos a E, entonces no existía camino C(S,E)
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
	    salida = "Atrapado!\n";
	} else {
	    salida = "Escape en "+tiempo+" minuto(s).\n";
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
