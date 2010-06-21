/**
 * Clase que implementa una Cola genérica
 *
 * @author José A. Goncalves y Jennifer Dos Reis
 * @version 1.0
 * @since 1.6
**/
public class Cola<E>{

	private ElemCola primero=null;
	private ElemCola ultimo=null;
	private int tam = 0;

	/**
	 * Clase que representa a un elemento en la cola.
	 * Cada elemento tiene un apuntador al elemento
	 * que viene después en la cola.
	 */
	private class ElemCola<E> {
	    public E elemento;
	    public ElemCola siguiente;

	    public ElemCola(E elem){
		this.elemento= elem;
		this.siguiente= null;
	    }

	    public void setSiguiente(ElemCola sig){
		this.siguiente= sig;
	    }
	}

	/**
	 * Determina si la cola posee algún elemento
	 * @return True si no hay elementos, false en caso contrario
	 */
	public boolean vacia(){
	    return (this.tam==0);
	}

	/**
	 * Agrega un elemento al final de la cola
	 * @param elem Elemento a agregar
	 */
	public void encolar(E elem){
	    ElemCola caja= new ElemCola(elem);
	    if(this.tam==0){  
	    	this.primero= caja;
		this.ultimo= caja;
	    }else{
	    	this.ultimo.setSiguiente(caja);
	    	this.ultimo= caja;
	    }
	    this.tam++; 
	}

	/**
	 * Devuelve el primer elemento de la cola.
	 * Este elemento es borrado de la cola.
	 * @return El primer elemento de la cola
	 */
	public E desencolar(){

	    E primerElem= (E)this.primero.elemento;
	    if(this.tam==1){
	    	this.primero=null;
	    	this.ultimo=null;
	    } else {
	    	this.primero= this.primero.siguiente;
	    }
	    this.tam--;
	    return primerElem;
	} 
	 

}
