/**
 * Camino es una clase que representa un camino en un grafo
 * como una terna compuesta del nodo terminal del camino,
 * el costo de llegar hasta el nodo terminal, y una referencia
 * al camino que llega hasta el nodo predecesor del nodo terminal de este camino.
 * @author José A. Goncalves y Jennifer Dos Reis
 * @version 1.0
 * @since 1.6
**/
public class Camino {
    private Camino pred;
    private int costo;
    private int nodoTerm;

    /**
     * Crea un camino
     * @param pred Camino que precede a este camino
     * @param costo Costo de este camino (entero)
     * @param nodoTerm Nodo donde termina este camino (entero)
     */
    public Camino (Camino pred, int costo, int nodoTerm) {
	this.pred = pred;
	this.costo = costo;
	this.nodoTerm = nodoTerm;
    }

    /**
     * Permite obtener el nodo terminal de este camino
     * @return El nodo terminal de este camino
     */    
    public int getNodoTerm() {
	return nodoTerm;
    }

    /**
     * Permite obtener el costo de este camino
     * @return El costo de este camino
     */    
    public int getCosto() {
	return costo;
    }


}