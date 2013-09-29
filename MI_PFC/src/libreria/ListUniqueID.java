package libreria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import view.error.ErrorGenerico;

public class ListUniqueID{
	private LinkedHashSet<Integer> uniqueListID = new LinkedHashSet<Integer>();
	private String mensaje;
	private String modulo;
	
	public ListUniqueID(String mensaje,String modulo){
		this.mensaje = mensaje;
		this.modulo = modulo;		
	}
	
	public LinkedHashSet<Integer> getUniqueListID() {
		return uniqueListID;
	}

	public void setUniqueListID(LinkedHashSet<Integer> uniqueListID) {
		this.uniqueListID = uniqueListID;
	}

	public boolean add(int a){		
		boolean bl1= uniqueListID.add(a);
		if (!bl1){
			ErrorGenerico errorGenerico = new ErrorGenerico(modulo, mensaje);
		}
		return bl1;
	}
	
	public boolean add(int a, LinkedHashSet<Integer> myUniqueListID ){
		
		boolean bl2 = add(a);
		
		if (bl2)
			myUniqueListID.add(a);
		
		return bl2;
		
	}
	

	public void removeContentOfList(LinkedHashSet<Integer> myUniqueListID ){
		Iterator it = myUniqueListID.iterator();		
		while(it.hasNext()){
			int i = (Integer) it.next();
			uniqueListID.remove(i);
		}
		
	}
	
	public boolean containsElementListBoolean(List <Object> listaIdElementos){
		Iterator it = listaIdElementos.iterator();
		boolean found = false;
		while(it.hasNext() && !found){
			int id = (Integer) it.next();
			found = this.uniqueListID.contains(id);
		}
		
		return found;
	}
	
	public List <Integer> containsElementList(List <Object> listaIdElementos){
		Iterator it = listaIdElementos.iterator();
		List <Integer> lista = new ArrayList <Integer>() ;
		while(it.hasNext()){
			int id = (Integer) it.next();
			if (this.uniqueListID.contains(id))
				lista.add(id);
		}
		return lista;
	}
}
