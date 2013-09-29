package libreria;

public class ElementoComboBox {
	
	  private String nombreElemento;
	  private int idElemento;

	    public ElementoComboBox(int idElemento, String nombreElemento)
	      {
	        this.idElemento=idElemento;
	        this.nombreElemento=nombreElemento;
	      }
	    public String getNombreElemento() {
	        return nombreElemento;
	    }

	    public void setNombreElemento(String nombreElemento) {
	        this.nombreElemento = nombreElemento;
	    }

	    public int getIdElemento() {
	        return idElemento;
	    }

	    public void setIdElemento(int idElemento) {
	        this.idElemento = idElemento;
	    }
	    
	    public String toString()
	    {
	    	String strAux= idElemento +" "+ nombreElemento;
	    	return strAux;
	    	//return nombreElemento;//()	     
	    }

}
