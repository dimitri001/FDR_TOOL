
package view.modificar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;


import controller.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.UIManager;

import utils.MessageUtils;
import utils.NumericUtils;
import view.acierto.AciertoActualizar;
import view.error.ErrorActualizar;
import view.error.ErrorGenerico;
import view.error.ErrorGenericoCampo;
import view.error.ErrorValidacionCampo;

import libreria.*;

public class ModificarSerie extends JFrame implements PropertyChangeListener, ModificarInterface{
	
	private JTextField tfIdSerie;
	private JTextField tfIdModelo;
	private JTextField tfIdFabricante;
	private JTextPane tpDescripcion;
	private JTextField tfNumeroSerie;
	private JTextField tfFechaFabricacion;
	private JTextField tfAnyoLanzamiento;
	
	private Serie miObjetoSerie;
	private VerModificarController miVerModificarController;
	private ConsultaController miConsultaController;
	
	private UUID miIdSerie = UUID.randomUUID();;//Identificador unico de cada objeto
    private UUID idVerModificarSerie;
    private SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
	
    private int cbIdFabricante ;
    JComboBox cbFabricante;
    private ElementoComboBox elementoCbFabricante;
	
    private int cbIdModelo ;
    JComboBox cbModelo;
    private ElementoComboBox elementoCbModelo;
    private ListUniqueID listUniqueIdVerModificarSerie;
    
	public static final String PROP_ACTUALIZAR_SERIE = "actualizarSerie";
		
	public ModificarSerie(Serie objetoSerie, VerModificarController verModificarcontroller, ConsultaController consultaController, 
			UUID idVMSerie, JComboBox cbFabricante, JComboBox cbModelo, ListUniqueID listUniqueIdVerModificarSerie) {
		
		miObjetoSerie = objetoSerie;
		miVerModificarController = verModificarcontroller;
		miConsultaController = consultaController;
		idVerModificarSerie = idVMSerie;
		this.cbFabricante = cbFabricante;
		this.cbModelo = cbModelo;
		this.listUniqueIdVerModificarSerie= listUniqueIdVerModificarSerie;
		setTitle("Modificar Serie");
		getContentPane().setLayout(null);
		
		
		
		JLabel lblIdserie = new JLabel("IdSerie");
		lblIdserie.setBounds(46, 26, 104, 14);
		getContentPane().add(lblIdserie);
		
		JLabel ldIdModelo = new JLabel("IdModelo");
		ldIdModelo.setBounds(46, 69, 104, 14);
		getContentPane().add(ldIdModelo);
		
		JLabel lbIdFabricante = new JLabel("IdFabricante");
		lbIdFabricante.setBounds(46, 113, 74, 14);
		getContentPane().add(lbIdFabricante);	
		
		JLabel lbNumeroDeSerie = new JLabel("N\u00FAmero de Serie");
		lbNumeroDeSerie.setBounds(46, 158, 116, 14);
		getContentPane().add(lbNumeroDeSerie);
		
		JLabel lbDescripcion = new JLabel("Descripci\u00F3n");
		lbDescripcion.setBounds(46, 202, 91, 14);
		getContentPane().add(lbDescripcion);
		
		JLabel lbFechaDeFabricacion = new JLabel("Fecha de Fabricaci\u00F3n");
		lbFechaDeFabricacion.setBounds(46, 279, 134, 14);
		getContentPane().add(lbFechaDeFabricacion);
		
		JLabel lbAnyoDeLanzamiento = new JLabel("A\u00F1o de lanzamiento");
		lbAnyoDeLanzamiento.setBounds(46, 323, 116, 14);
		getContentPane().add(lbAnyoDeLanzamiento);
		
		tfIdSerie = new JTextField();
		tfIdSerie.setBackground(UIManager.getColor("Button.background"));
		tfIdSerie.setEditable(false);
		tfIdSerie.setColumns(10);
		tfIdSerie.setBounds(235, 23, 235, 20);
		getContentPane().add(tfIdSerie);
		tfIdSerie.setText(Integer.toString(miObjetoSerie.getIdSerie()));
		
		tfIdModelo = new JTextField();
		tfIdModelo.setBackground(UIManager.getColor("Button.background"));
		tfIdModelo.setEditable(false);
		tfIdModelo.setColumns(10);
		tfIdModelo.setBounds(235, 66, 235, 20);
		getContentPane().add(tfIdModelo);
		tfIdModelo.setText(Integer.toString(miObjetoSerie.getIdModelo()));
		
		
		tfIdFabricante = new JTextField();
		tfIdFabricante.setEditable(false);
		tfIdFabricante.setBackground(UIManager.getColor("Button.background"));
		tfIdFabricante.setColumns(10);
		tfIdFabricante.setBounds(236, 110, 235, 20);
		getContentPane().add(tfIdFabricante);
		tfIdFabricante.setText(Integer.toString(miObjetoSerie.getIdFabricante()));
		
		tfNumeroSerie = new JTextField();
		tfNumeroSerie.setBounds(236, 158, 235, 20);
		getContentPane().add(tfNumeroSerie);
		tfNumeroSerie.setColumns(10);
		tfNumeroSerie.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));		
		tfNumeroSerie.setText(miObjetoSerie.getNumeroSerie());
		
		tpDescripcion = new JTextPane (new MyDefaultStyledDocument());		
		tpDescripcion.setText(miObjetoSerie.getDescripcion());
		JScrollPane scrDecripcion = new JScrollPane(tpDescripcion);
		scrDecripcion.setBounds(236, 197, 235, 63);
		getContentPane().add(scrDecripcion);

		
		tfFechaFabricacion = new JTextField();
		tfFechaFabricacion.setBounds(237, 282, 234, 20);
		getContentPane().add(tfFechaFabricacion);
		tfFechaFabricacion.setColumns(10);
		tfFechaFabricacion.setText(miObjetoSerie.getFechaFabricacionString());
				
		tfAnyoLanzamiento = new JTextField();
		tfAnyoLanzamiento.setBounds(238, 323, 233, 20);
		getContentPane().add(tfAnyoLanzamiento);
		tfAnyoLanzamiento.setColumns(10);
		tfAnyoLanzamiento.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));		
		tfAnyoLanzamiento.setText(miObjetoSerie.getAnyoLanzamiento());
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_btnGuardar_actionPerformed(e);
			}
		});
		btnGuardar.setBounds(100, 367, 89, 23);
		getContentPane().add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_btnCancelar_actionPerformed(e);
			}
		});
		btnCancelar.setBounds(328, 367, 89, 23);
		getContentPane().add(btnCancelar);
		
		
		
		
		setVisible(true);  
		setSize(586, 439);
		
		//Asi al cerrar en la X del JFrame, se invoca a do_btnCerrar_actionPerformed
		//y se cierra correctamente el objeto	
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
					   public void windowClosing(WindowEvent evt) {
						   do_btnCancelar_actionPerformed(null);
					   }
					  });
		
	}

	
	


	protected void do_btnGuardar_actionPerformed(ActionEvent e) {
		
		int errorFechaFabricacion = 0;
		String mensaje = null ;
		
		int intIdSerie = miObjetoSerie.getIdSerie();
		int intIdModelo = miObjetoSerie.getIdModelo();
		int intIdFabricante = miObjetoSerie.getIdFabricante();		
		String strNumeroSerie = tfNumeroSerie.getText();
		String strDescripcion = tpDescripcion.getText();		
		String strFechaFabricante = null ;
		String strAnyoLanzamiento = null;
		
		Date dtFechaFabricacion;
		
		//Comprobamos que el campo Fecha de Fabricacion es correcto
		//Al objeto Serie le pasamos el String y no el Date, porque este objeto pasa automaticamente el String a Date
		try{
			strFechaFabricante = tfFechaFabricacion.getText();
			dtFechaFabricacion = new Date(formatoDelTexto.parse(tfFechaFabricacion.getText()).getTime());// Obtiene util.Date y lo transforma a sql.Date con getTime()
			} catch (ParseException ex){
				ex.printStackTrace();
				errorFechaFabricacion = 1;
			}
		strAnyoLanzamiento = tfAnyoLanzamiento.getText().trim();
		mensaje = "La entrada Año de Lanzamiento";
		MutableBoolean blAnioLanzamientoValido = new MutableBoolean(false);
		int intAnioLanzamiento = NumericUtils.parseIntPositive(strAnyoLanzamiento, MyConstants.StringConstant.CAMPO.value(), mensaje, blAnioLanzamientoValido);
		if (errorFechaFabricacion == 1){
			mensaje = "La entrada Fecha de Fabricación";
			ErrorGenericoCampo errorGenerico = new ErrorGenericoCampo(MyConstants.StringConstant.CAMPO.value(), mensaje);
		}			
		if ((errorFechaFabricacion == 0)&& blAnioLanzamientoValido.valor){		
			miObjetoSerie = new Serie(intIdSerie, intIdModelo,intIdFabricante, strNumeroSerie, strDescripcion, strFechaFabricante, strAnyoLanzamiento);
			miVerModificarController.modificarSerie(miObjetoSerie, miIdSerie);
			
			//Se obtiene el objeto seleccionado dentro del modelo del combobox que se nos paso de VerModificarXXX
			elementoCbFabricante = (ElementoComboBox)cbFabricante.getModel().getSelectedItem();
			cbIdFabricante = elementoCbFabricante.getIdElemento();
			
			elementoCbModelo = (ElementoComboBox)cbModelo.getModel().getSelectedItem();
			cbIdModelo = elementoCbModelo.getIdElemento();
			miConsultaController.listaSerie(cbIdFabricante, cbIdModelo, idVerModificarSerie,true); //Actualizo la lista
						
			}
					
	}
	public void do_btnCancelar_actionPerformed(ActionEvent e) {
		dispose();	
		boolean uno = listUniqueIdVerModificarSerie.getUniqueListID().remove(miObjetoSerie.getIdSerie());
		miVerModificarController.getActualizarElemento().removePropertyChangeListener(PROP_ACTUALIZAR_SERIE, this);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		String propertyName = evt.getPropertyName();
		
		ParametrosActualizarElemento parametrosActualizarElemento;
	
		
		if (PROP_ACTUALIZAR_SERIE.equals(propertyName)){
			 
			parametrosActualizarElemento = (ParametrosActualizarElemento) evt.getNewValue();

			//Se comprueba el id del objeto Modelo
			if (parametrosActualizarElemento.getIdElemento().equals(miIdSerie)){				
				MessageUtils.mensajeActualizarElemento(parametrosActualizarElemento, MyConstants.StringConstant.SERIE.value(), this);
			}	
			
		}
		
		
	}
	
	/*
	 * Verifica si un string es un string numerico o no
	 * Devuelve true si cade es totalmente numerico y false en caso contrario 
	 * @param cade, es una String
	 */	
	public static boolean esNumero(String cade){
		
		int flag;	
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(cade, pos);//Parsea cade hasta que encuentra un caracter que no sea numerico
		
	  return cade.length() == pos.getIndex();//El index de pos apunta a la posicion siguiente al 
	   										 //ultimo caracter numerico
	}	
	
	/*
	 * Cambiar el id de este objeto.
	 * @param idModelo, es una UUID (identificador unico)
	 *  */
	
	public void setIdSerie(UUID idSerie){
		
		miIdSerie = idSerie;
	}
	
	
	/*
	 * Devuelve el id de este objeto.
	 * 
	 *  */
	public UUID getIdSerie(){
		
		return miIdSerie;
	}
}
