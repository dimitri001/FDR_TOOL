package libreria;

import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ContenedorVisualAtributoElemento {
	

	private JTextField nombreAtributo;
	private JComboBox cbTipo;
	private String strNombreAtributo;
	private String strTipo;
	private boolean visible;
	
	public ContenedorVisualAtributoElemento (JTextField nombreAtributo, JComboBox cbTipo, boolean visible ){
		
		this.nombreAtributo = nombreAtributo;		
		this.cbTipo = cbTipo;		
		this.visible = visible;
	}
	
	public void setNombreAtributo(JTextField nombreAtributo){
		this.nombreAtributo = nombreAtributo;
	}
	
	public void setCbTipo(JComboBox cbTipo){
		this.cbTipo = cbTipo;
	}
	
	public void setStrNombreAtributo(String strNombreAtributo){
		this.strNombreAtributo = strNombreAtributo;
	}
	
	public void setStrCbTipo(String strCbTipo ){
		
		this.strTipo = strCbTipo;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public JTextField getNombreAtributo() {
		return nombreAtributo;
			
	}


	public JComboBox getCbTipo() {
	
		return cbTipo;
	
		
	}

	public String getStrNombreAtributo() {
		return strNombreAtributo;
	}
	
	
	public String getStrCbTipo() {
		return strTipo;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
}
