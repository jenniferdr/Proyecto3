/**
 * Clase que implementa una Cola
 *
 * @author José A. Goncalves y Jennifer Dos Reis
 * @version 1.0
 * @since 1.6
**/
public class Cola<E>{

	private ElemCola primero=null;
	private ElemCola ultimo=null;
	private int tam = 0;

	private class ElemCola<E> {
	    public E elemento;
	    public ElemCola siguiente;

	    public ElemCola(E elem){
		this.elemento= elem;
		this.siguiente= null;
	    }

	    public void setSiguiente(ElemCola sig){
		
	    }
	}

	public boolean vacia(){
	    return (this.tam==0);
	}

	public void encolar(E elem){
	    ElemCola caja= new ElemCola(elem);
	    this.ultimo.siguiente.setSiguiente(caja);
	    this.ultimo= caja; 
	}

	public E desencolar(){
	    E primerElem= (E)this.primero.elemento;
	    this.primero= this.primero.siguiente;
	    return primerElem;
	} 

}
