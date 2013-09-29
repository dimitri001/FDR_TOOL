package view.modificar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

import utils.MessageUtils;
import utils.NumericUtils;
import view.acierto.AciertoActualizar;
import view.error.ErrorActualizar;
import view.error.ErrorGenericoCampo;
import view.error.ErrorValidacionCampo;

import controller.ConsultaController;
import controller.VerModificarController;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.UUID;

import libreria.ElementoComboBox;
import libreria.JTextFieldLimit;
import libreria.ListUniqueID;
import libreria.Modelo;
import libreria.MutableBoolean;
import libreria.MyConstants;
import libreria.MyDefaultStyledDocument;
import libreria.ParametrosActualizarElemento;

public class ModificarModelo extends JFrame implements PropertyChangeListener, ModificarInterface{
	
		
	private JTextField tfIdModelo;
	private JTextField tfIdFabricante;
	private JTextField tfNombre;
	private JTextPane tpDescripcion;
	private JTextField tfFechaFabricacion;
	private JTextField tfAnyoLanzamiento;
		
	private Modelo miObjetoModelo;
	private VerModificarController miVerModificarController;
	private ConsultaController miConsultaController;
	
	private UUID miIdModelo = UUID.randomUUID();;//Identificador unico de cada objeto
    private UUID idVerModificarModelo;
    private SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
	
    private int cbIdFabricante ;
    JComboBox cbFabricante;
    private ElementoComboBox elementoCbFabricante;
	private ListUniqueID listUniqueIdVerModificarModelo;
    
    public static final String PROP_ACTUALIZAR_MODELO = "actualizarModelo";
    
	public ModificarModelo(Modelo objetoModelo, VerModificarController verModificarcontroller, 
			ConsultaController consultaController, UUID idVMModelo, JComboBox cbFabricante, ListUniqueID listUniqueIdVerModificarModelo) {
		
		miObjetoModelo = objetoModelo;
		miVerModificarController = verModificarcontroller;
		miConsultaController = consultaController;
		idVerModificarModelo = idVMModelo;
		this.cbFabricante = cbFabricante;
		this.listUniqueIdVerModificarModelo = listUniqueIdVerModificarModelo;
		
		setTitle("Modificar Modelo ");
		getContentPane().setLayout(null);
		
		JLabel lbIdModelo = new JLabel("IdModelo");
		lbIdModelo.setBounds(29, 33, 74, 14);
		getContentPane().add(lbIdModelo);
		
		JLabel lbIdFabricante = new JLabel("IdFabricante");
		lbIdFabricante.setBounds(29, 81, 74, 14);
		getContentPane().add(lbIdFabricante);
		
		JLabel lbNombre = new JLabel("Nombre");
		lbNombre.setBounds(29, 131, 117, 14);
		getContentPane().add(lbNombre);
		
		JLabel lbDescripcion = new JLabel("Descripci\u00F3n");
		lbDescripcion.setBounds(29, 184, 74, 14);
		getContentPane().add(lbDescripcion);
		
		JLabel lbFechaFabricacion = new JLabel("Fecha de Fabricaci\u00F3n");
		lbFechaFabricacion.setBounds(29, 266, 132, 14);
		getContentPane().add(lbFechaFabricacion);
		
		JLabel lbAnyoLanzamiento = new JLabel("A\u00F1o de Lanzamiento");
		lbAnyoLanzamiento.setBounds(29, 318, 132, 14);
		getContentPane().add(lbAnyoLanzamiento);
		
		tfIdModelo = new JTextField();
		tfIdModelo.setEditable(false);
		tfIdModelo.setColumns(10);
		tfIdModelo.setBounds(183, 27, 235, 20);
		getContentPane().add(tfIdModelo);
		tfIdModelo.setText(Integer.toString(miObjetoModelo.getIdModelo()));
		
		
		tfIdFabricante = new JTextField();
		tfIdFabricante.setEditable(false);
		tfIdFabricante.setColumns(10);
		tfIdFabricante.setBounds(183, 75, 235, 20);
		getContentPane().add(tfIdFabricante);
		tfIdFabricante.setText(Integer.toString(miObjetoModelo.getIdFabricante()));
				
		tfNombre = new JTextField();
		tfNombre.setBounds(183, 125, 235, 20);
		getContentPane().add(tfNombre);
		tfNombre.setColumns(10);
		tfNombre.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));		
		tfNombre.setText(miObjetoModelo.getNombre());
		
		tpDescripcion = new JTextPane (new MyDefaultStyledDocument());
		tpDescripcion.setText(miObjetoModelo.getDescripcion());
		JScrollPane scrDescripcion = new JScrollPane(tpDescripcion);
		scrDescripcion.setBounds(183, 175, 235, 63);
		getContentPane().add(scrDescripcion);
		
		tfFechaFabricacion = new JTextField();
		tfFechaFabricacion.setBounds(183, 263, 235, 20);
		getContentPane().add(tfFechaFabricacion);
		tfFechaFabricacion.setColumns(10);
		tfFechaFabricacion.setText(miObjetoModelo.getFechaFabricacionString());
		
		tfAnyoLanzamiento = new JTextField();
		tfAnyoLanzamiento.setBounds(183, 312, 235, 20);
		getContentPane().add(tfAnyoLanzamiento);
		tfAnyoLanzamiento.setColumns(10);
		tfAnyoLanzamiento.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_PANE.value()));
		tfAnyoLanzamiento.setText(miObjetoModelo.getAnyoLanzamiento());
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnGuardar_actionPerformed(arg0);
			}
		});
		
		btnGuardar.setBounds(87, 363, 89, 23);
		getContentPane().add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		btnCancelar.setBounds(263, 363, 89, 23);
		getContentPane().add(btnCancelar);
		
		
		
		setVisible(true);  
		setSize(457, 437);
		
		//Asi al cerrar en la X del JFrame, se invoca a do_btnCerrar_actionPerformed
		//y se cierra correctamente el objeto	
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
					   public void windowClosing(WindowEvent evt) {
						   do_btnCancelar_actionPerformed(null);
					   }
					  });
		
	}
	
	
	
	protected void do_btnGuardar_actionPerformed(ActionEvent arg0) {
		
		int errorFechaFabricacion = 0;
		String mensaje = null ;
		
		int intIdModelo = miObjetoModelo.getIdModelo();
		int intIdFabricante = miObjetoModelo.getIdFabricante();		
		String strNombre = tfNombre.getText();
		String strDescripcion = tpDescripcion.getText();
		String strFechaFabricante = null ;
		String strAnyoLanzamiento = null;
		
		Date dtFechaFabricacion;
		
		//Comprobamos que el campo Fecha de Fabricacion es correcto
		//Al objeto Modelo le pasamos el String y no el Date, porque este objeto pasa automaticamente el String a Date
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
			miObjetoModelo = new Modelo(intIdModelo,intIdFabricante, strNombre, strDescripcion, strFechaFabricante, strAnyoLanzamiento);
			miVerModificarController.modificarModelo(miObjetoModelo, miIdModelo);			
			//Se obtiene el objeto seleccionado dentro del modelo del combobox que se nos paso de VerModificarXXX
			elementoCbFabricante = (ElementoComboBox)cbFabricante.getModel().getSelectedItem();
			cbIdFabricante = elementoCbFabricante.getIdElemento();			
			//Actualizo la lista en VerModificarModelo, listando todos los fabricantes
			miConsultaController.listaModelo(cbIdFabricante, idVerModificarModelo, true); 
		}
		
	}
	
	
	public void do_btnCancelar_actionPerformed(ActionEvent arg0) {
		dispose();
		boolean uno = listUniqueIdVerModificarModelo.getUniqueListID().remove(miObjetoModelo.getIdModelo());
		miVerModificarController.getActualizarElemento().removePropertyChangeListener(PROP_ACTUALIZAR_MODELO, this);
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		
		ParametrosActualizarElemento parametrosActualizarElemento;
	
		
		if (PROP_ACTUALIZAR_MODELO.equals(propertyName)){
			 
		
			parametrosActualizarElemento = (ParametrosActualizarElemento) evt.getNewValue();

			//Se comprueba el id del objeto Modelo
			if (parametrosActualizarElemento.getIdElemento().equals(miIdModelo)){
				MessageUtils.mensajeActualizarElemento(parametrosActualizarElemento, MyConstants.StringConstant.MODELO.value(), this);
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
	
	public void setIdModelo(UUID idModelo){
		
		miIdModelo = idModelo;
	}
	
	
	/*
	 * Devuelve el id de este objeto.
	 * 
	 *  */
	public UUID getIdModelo(){
		
		return miIdModelo;
	}
}
