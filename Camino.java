public class Camino {
    private Camino pred;
    private int costo;
    private int nodoTerm;

    public Camino (Camino pred, int costo, int nodoTerm) {
	this.pred = pred;
	this.costo = costo;
	this.nodoTerm = nodoTerm;
    }

    public int getNodoTerm() {
	return nodoTerm;
    }

    public int getCosto() {
	return costo;
    }


}