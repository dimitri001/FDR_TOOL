package view.alta;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

import utils.MessageUtils;
import utils.NumericUtils;
import view.acierto.AciertoAlta;
import view.error.ErrorAlta;
import view.error.ErrorGenericoCampo;
import view.error.ErrorValidacionCampo;

import controller.AltaController;
import controller.ComboBoxController;


import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

//Si se usa libreria.Modelo se podria borrar
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.UUID;
import java.util.Vector;

import libreria.ElementoComboBox;
import libreria.JTextFieldLimit;
import libreria.MutableBoolean;
import libreria.MyConstants;
import libreria.MyDefaultStyledDocument;
import libreria.ParametrosConsultasComboBoxBBDD;
import libreria.ParametrosGuardarElemento;

public class AltaModelo extends JFrame implements PropertyChangeListener{	
    private final int maxNumeroCaracateresTextPanel = 300;    
	private JTextField tfNombreModelo;
	private JTextPane tpDescripcion;
	private JTextField tfFechaFabricacion;
	private JTextField tfAnyoLanzamiento;	
	private int intIdFabricante;
	private String strNombreModelo;
	private String strDescripcion;
	private Date dtFechaFabricacion = null;
	private String strAnyoLanzamiento;	
	private UUID miIdModelo;//Identificador unico de cada objeto	
	private SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");	
	private JComboBox cbFabricante;
	private Vector<ElementoComboBox> dataComboBoxFabricante;
	private ElementoComboBox elementoCbFabricante;	
	private AltaController miAltaController;
	private ComboBoxController miComboBoxController;
	public static final String PROP_INSERTAR_MODELO = "insertarModelo";
	public static final String PROP_CONSULTAR_FABRICANTE = "consultarFabricante";
	
	public AltaModelo(AltaController altaController, ComboBoxController comboBoxController, UUID idModelo) {
		
		miIdModelo= idModelo;		
		miAltaController = altaController;
		miComboBoxController = comboBoxController;		
		setTitle("Alta Modelo");
		getContentPane().setLayout(null);
		
		JLabel lbFabricante = new JLabel("Fabricante");
		lbFabricante.setBounds(39, 18, 94, 14);
		getContentPane().add(lbFabricante);
		
		cbFabricante = new JComboBox();
		cbFabricante.setBounds(193, 15, 235, 20);
		getContentPane().add(cbFabricante);
		
		cbFabricante.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cbFabricante.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbFabricante.
				{
					elementoCbFabricante = (ElementoComboBox)cbFabricante.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdFabricante = elementoCbFabricante.getIdElemento();					
				}												
			}
		});
		
		JLabel lbNombreModelo = new JLabel("Nombre Modelo");
		lbNombreModelo.setBounds(39, 78, 112, 14);
		getContentPane().add(lbNombreModelo);
		
		JLabel lbDescripcion = new JLabel("Descripci\u00F3n");
		lbDescripcion.setBounds(39, 131, 74, 14);
		getContentPane().add(lbDescripcion);
		
		JLabel lbFechaFabricacion = new JLabel("Fecha de Fabricaci\u00F3n");
		lbFechaFabricacion.setBounds(39, 288, 144, 14);
		getContentPane().add(lbFechaFabricacion);
		
		JLabel lbAnyoLanzamiento = new JLabel("A\u00F1o de Lanzamiento");
		lbAnyoLanzamiento.setBounds(39, 335, 144, 14);
		getContentPane().add(lbAnyoLanzamiento);
				
		tfNombreModelo = new JTextField();
		tfNombreModelo.setBounds(193, 72, 235, 20);
		getContentPane().add(tfNombreModelo);
		tfNombreModelo.setColumns(10);
		tfNombreModelo.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));		
		
		tfFechaFabricacion = new JTextField();
		tfFechaFabricacion.setBounds(193, 285, 235, 20);
		getContentPane().add(tfFechaFabricacion);
		tfFechaFabricacion.setColumns(10);
		tfFechaFabricacion.setText("yyyy-MM-dd");
		//Se borra el contenido del textField al hacer clic sobre el
		tfFechaFabricacion.addMouseListener(new MouseAdapter(){
	 		@Override
            public void mouseClicked(MouseEvent e){
	 			tfFechaFabricacion.setText(null);
            }
	 	});		
	
		tfAnyoLanzamiento = new JTextField();
		tfAnyoLanzamiento.setBounds(193, 335, 235, 20);
		getContentPane().add(tfAnyoLanzamiento);
		tfAnyoLanzamiento.setColumns(10);		
		//Se borra el contenido del textField al hacer clic sobre el
		tfAnyoLanzamiento.addMouseListener(new MouseAdapter(){
	 		@Override
            public void mouseReleased(MouseEvent e){
	 			tfAnyoLanzamiento.setText(null);
            }
	 	});
		tfAnyoLanzamiento.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_PANE.value()));
		tfAnyoLanzamiento.setText("yyyy");
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnGuardar_actionPerformed(arg0);
			}
		});
		btnGuardar.setBounds(57, 386, 141, 23);
		getContentPane().add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		btnCancelar.setBounds(255, 386, 141, 23);
		getContentPane().add(btnCancelar);
		
//		Se comprueba que lo ingresado en el textPanel no excede lo especificado en maxNumeroCaracateresTextPanel 
		tpDescripcion = new JTextPane (new MyDefaultStyledDocument());
		JScrollPane scrDescripcion = new JScrollPane(tpDescripcion);
		scrDescripcion.setBounds(193, 131, 233, 121);
		getContentPane().add(scrDescripcion);
		
		setVisible(true);  
		setSize(470, 470);
		
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
		strNombreModelo = tfNombreModelo.getText();
		strDescripcion = tpDescripcion.getText();
		try{
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
		if ((errorFechaFabricacion == 0)&& blAnioLanzamientoValido.valor)
			miAltaController.guardarModelo(intIdFabricante, strNombreModelo, strDescripcion, dtFechaFabricacion, strAnyoLanzamiento, miIdModelo);
		
	}
	
	protected void do_btnCancelar_actionPerformed(ActionEvent arg0) {
		dispose();
		miComboBoxController.getConsultasComboBoxBBDD().removePropertyChangeListener(this);
		miAltaController.getGuardarElemento().removePropertyChangeListener( PROP_INSERTAR_MODELO,this);//se remueve el objeto de los listeners
		
	}
	
	
	/*
	 * Se invoca cada vez que algo cambia en los objetos de los que este objeto es listener 
	 * Se debe disintinguir entre una insercion de un elemento en la bbdd (PROP_INSERTAR_XXXX) y una consulta de un 
	 * ComboBox (PROP_CONSULTAR_XXXX)
	 * Si este objeto es el que pidio algo al Modelo, esto se comprobara por medio del Id, que esta en 
	 * los parametrosGuardarElemento y parametrosConsultasCb.
	 * @param evt, es el parametro que nos envia el objeto que es escuchado (el modelo del MVC)
	 * */

	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		String propertyName = evt.getPropertyName();
		ParametrosGuardarElemento parametrosGuardarElemento;
		ParametrosConsultasComboBoxBBDD parametrosConsultasCb ;
        dataComboBoxFabricante =new Vector<ElementoComboBox>(); 
		if (PROP_INSERTAR_MODELO.equals(propertyName)){
			parametrosGuardarElemento = (ParametrosGuardarElemento) evt.getNewValue();			
			//Se comprueba el id del objeto Modelo
			if (parametrosGuardarElemento.getIdElemento().equals(miIdModelo)){
				MessageUtils.mensajeInsertarElemento(parametrosGuardarElemento, MyConstants.StringConstant.MODELO.value());
			}
		}else{
			parametrosConsultasCb = (ParametrosConsultasComboBoxBBDD) evt.getNewValue();
			//Se comprueba el id del objeto Serie
			if(parametrosConsultasCb.getIdElemento().equals(miIdModelo)){				
				if (PROP_CONSULTAR_FABRICANTE.equals(propertyName)){
					intIdFabricante = 0;//Asi si no se actualiza el comboBox, el id =0
					dataComboBoxFabricante = parametrosConsultasCb.getDataElemento();// DataElemento es un Vector<ElementoComboBox>
					actualizarComboBox(cbFabricante,dataComboBoxFabricante );				
				}		
			}
		}
	}
	
	
	/*
	 * Se encarga de actualizar el comboBox con los datos pasados en dataComboBox
	 * @param comboBox, es el comboBox en el cual se quiere hacer la actualizacion
	 * @param dataComboBox, es el vector que contiene los elementos con los que se actualizará el comboBox  
	 * */
	private void actualizarComboBox(JComboBox comboBox,Vector<ElementoComboBox> dataComboBox ){
		comboBox.removeAllItems();
		ElementoComboBox elementoComboBox; 
		
        for(int i=0;i<dataComboBox.size();i=i+1)
        {
        	elementoComboBox = new ElementoComboBox(dataComboBox.get(i).getIdElemento(), dataComboBox.get(i).getNombreElemento());
           	comboBox.addItem(elementoComboBox);        	
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
	 * @param idFabricante, es una UUID (identificador unico)
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
