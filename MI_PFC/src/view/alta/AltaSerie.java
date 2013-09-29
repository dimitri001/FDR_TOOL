package view.alta;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
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

import libreria.ElementoComboBox;
import libreria.JTextFieldLimit;
import libreria.MutableBoolean;
import libreria.MyConstants;
import libreria.MyDefaultStyledDocument;
import libreria.ParametrosConsultasComboBoxBBDD;
import libreria.ParametrosGuardarElemento;


public class AltaSerie extends JFrame implements PropertyChangeListener {
	
    private final int maxNumeroCaracateresTextPanel = 300;
	
	private JTextField tfNumeroSerie;
	private JTextField tfFechaFabricacion;
	private JTextField tfAnyoLanzamiento;
	private JTextPane tpDescripcion;	
	private int intIdModelo;
	private int intIdFabricante;
	private String strNumeroSerie;
	private String strDescripcion;
	private Date dtFechaFabricacion = null;
	private String strAnyoLanzamiento;	
	private UUID miIdSerie;//Identificador unico de cada objeto	
	private SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");	
	private JComboBox cbFabricante;
	private Vector<ElementoComboBox> dataComboBoxFabricante;
	private ElementoComboBox elementoCbFabricante;	
	private JComboBox cbModelo;
	private Vector<ElementoComboBox> dataComboBoxModelo;
	private ElementoComboBox elementoCbModelo;
	private AltaController miAltaController;
	private ComboBoxController miComboBoxController;
	public static final String PROP_INSERTAR_SERIE = "insertarSerie";
	public static final String PROP_CONSULTAR_FABRICANTE = "consultarFabricante";
	public static final String PROP_CONSULTAR_MODELO = "consultarModelo";

	public AltaSerie(AltaController altaController, ComboBoxController comboBoxController, UUID idSerie) {		
		
		miIdSerie = idSerie;		
		miAltaController = altaController;
		miComboBoxController = comboBoxController;		
		
		setTitle("Alta Serie");
		getContentPane().setLayout(null);
		
		JLabel lbFabricante = new JLabel("Fabricante");
		lbFabricante.setBounds(75, 40, 74, 14);
		getContentPane().add(lbFabricante);
		
		JLabel lbNombreModelo = new JLabel("Nombre Modelo");
		lbNombreModelo.setBounds(75, 84, 104, 14);
		getContentPane().add(lbNombreModelo);
		
		JLabel lbNumeroDeSerie = new JLabel("N\u00FAmero de Serie");
		lbNumeroDeSerie.setBounds(75, 128, 116, 14);
		getContentPane().add(lbNumeroDeSerie);
		
		JLabel lbDescripcion = new JLabel("Descripci\u00F3n");
		lbDescripcion.setBounds(75, 172, 91, 14);
		getContentPane().add(lbDescripcion);
		
		JLabel lbFechaDeFabricacion = new JLabel("Fecha de Fabricaci\u00F3n");
		lbFechaDeFabricacion.setBounds(75, 309, 134, 14);
		getContentPane().add(lbFechaDeFabricacion);
		
		JLabel lbAnyoDeLanzamiento = new JLabel("A\u00F1o de lanzamiento");
		lbAnyoDeLanzamiento.setBounds(75, 353, 116, 14);
		getContentPane().add(lbAnyoDeLanzamiento);		
		
		cbFabricante = new JComboBox();
		cbFabricante.setBounds(265, 37, 235, 20);
		getContentPane().add(cbFabricante);
		
		cbFabricante.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cbFabricante.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbFabricante.
				{
					elementoCbFabricante = (ElementoComboBox)cbFabricante.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdFabricante = elementoCbFabricante.getIdElemento();				
					miComboBoxController.comboBoxModelo(intIdFabricante, miIdSerie);
				}												
			}
		});
		
		cbModelo = new JComboBox();
		cbModelo.setBounds(265, 84, 235, 20);
		getContentPane().add(cbModelo);
		cbModelo.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				if (cbModelo.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbFabricante.
				{
					elementoCbModelo = (ElementoComboBox)cbModelo.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdModelo = elementoCbModelo.getIdElemento();		
				}
												
			}
		});

			
		tfNumeroSerie = new JTextField();
		tfNumeroSerie.setBounds(265, 128, 235, 20);
		getContentPane().add(tfNumeroSerie);
		tfNumeroSerie.setColumns(10);
		tfNumeroSerie.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));		
			
		//Se comprueba que lo ingresado en el textPanel no excede lo especificado en maxNumeroCaracateresTextPanel 	
		tpDescripcion = new JTextPane (new MyDefaultStyledDocument());		
		JScrollPane scrDecripcion = new JScrollPane(tpDescripcion);
		scrDecripcion.setBounds(265, 167, 235, 123);
		getContentPane().add(scrDecripcion);

		tfFechaFabricacion = new JTextField();
		tfFechaFabricacion.setBounds(266, 312, 234, 20);
		getContentPane().add(tfFechaFabricacion);
		tfFechaFabricacion.setColumns(10);
		tfFechaFabricacion.setText("yyyy-MM-dd");
		//Se borra el contenido del textField al hacer clicl sobre el
		tfFechaFabricacion.addMouseListener(new MouseAdapter(){
	 		@Override
            public void mouseClicked(MouseEvent e){
	 			tfFechaFabricacion.setText(null);
            }
	 	});
		
		tfAnyoLanzamiento = new JTextField();
		tfAnyoLanzamiento.setBounds(267, 353, 233, 20);
		getContentPane().add(tfAnyoLanzamiento);
		tfAnyoLanzamiento.setColumns(10);
		//Se borra el contenido del textField al hacer clicl sobre el
		tfAnyoLanzamiento.addMouseListener(new MouseAdapter(){
	 		@Override
            public void mouseReleased(MouseEvent e){
	 			tfAnyoLanzamiento.setText(null);
            }
	 	});
		tfAnyoLanzamiento.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));		
		tfAnyoLanzamiento.setText("yyyy");
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnGuardar_actionPerformed(arg0);
			}
		});
		btnGuardar.setBounds(101, 406, 141, 23);
		getContentPane().add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		btnCancelar.setBounds(343, 406, 141, 23);
		getContentPane().add(btnCancelar);
		
		setVisible(true);  
		setSize(603, 484);
		
		//Asi al cerrar en la X del JFrame, se invoca a do_btnCerrar_actionPerformed
		//y se cierra correctamente el objeto	
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
					   public void windowClosing(WindowEvent evt) {
						   do_btnCancelar_actionPerformed(null);
					   }
					  });

	}
	
	/*
	 * Se invoca cada vez que algo cambia en los objetos de los que este objeto es listener 
	 * Se debe disintinguir entre una insercion de un elemento en la bbdd (PROP_INSERTAR_XXXX) y una consulta de un 
	 * ComboBox (PROP_CONSULTAR_XXXX)
	 * Si este objeto es el que pidio algo al Modelo, esto se comprobara por medio del Id, que esta en 
	 * los parametrosGuardarElemento y parametrosConsultasCb.
	 * @param evt, es el parametro que nos envia el objeto que es escuchado (el modelo del MVC)
	 * */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
		String propertyName = evt.getPropertyName();
		ParametrosGuardarElemento parametrosGuardarElemento;
		ParametrosConsultasComboBoxBBDD parametrosConsultasCb ;		
        dataComboBoxFabricante =new Vector<ElementoComboBox>();  
        dataComboBoxModelo = new Vector<ElementoComboBox>();        

		if (PROP_INSERTAR_SERIE.equals(propertyName)){			
			parametrosGuardarElemento = (ParametrosGuardarElemento) evt.getNewValue();
			//Se comprueba el id del objeto Serie
			if (parametrosGuardarElemento.getIdElemento().equals(miIdSerie)){
				MessageUtils.mensajeInsertarElemento(parametrosGuardarElemento, MyConstants.StringConstant.SERIE.value());
			}	
		}else{ 		
			parametrosConsultasCb = (ParametrosConsultasComboBoxBBDD) evt.getNewValue();
			//Se comprueba el id del objeto Serie
			if(parametrosConsultasCb.getIdElemento().equals(miIdSerie)){				
				if (PROP_CONSULTAR_FABRICANTE.equals(propertyName)){
					intIdFabricante = 0;//Asi si no se actualiza el comboBox, el id =0
					dataComboBoxFabricante = parametrosConsultasCb.getDataElemento();// DataElemento es un Vector<ElementoComboBox>
					actualizarComboBox(cbFabricante,dataComboBoxFabricante );
				}else if (PROP_CONSULTAR_MODELO.equals(propertyName)){
					intIdModelo =0;//Asi si no se actualiza el comboBox, el id =0
					dataComboBoxModelo = parametrosConsultasCb.getDataElemento();//(Vector<ElementoComboBox>)
					actualizarComboBox(cbModelo,dataComboBoxModelo );						
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
        for(int i=0;i<dataComboBox.size();i=i+1){
        	elementoComboBox = new ElementoComboBox(dataComboBox.get(i).getIdElemento(), dataComboBox.get(i).getNombreElemento());
        	comboBox.addItem(elementoComboBox);        	
       }		
	}

	

	/*
	 * Es llamado por el listener del boton guardar. 
	 * 
	 * */	
	protected void do_btnGuardar_actionPerformed(ActionEvent arg0) {		
		int errorFechaFabricacion = 0;
		String mensaje = null ;
		strNumeroSerie = tfNumeroSerie.getText();
		strDescripcion = tpDescripcion.getText();
		//Se verifica si la dtFechaFabricacion es correcta, debe ser del tipo yyyy-MM-dd
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
			miAltaController.guardarSerie(intIdModelo, intIdFabricante, strNumeroSerie, strDescripcion, dtFechaFabricacion, strAnyoLanzamiento, miIdSerie);

	}
	
	
	
	/*
	 * Se encarga de cerrar la ventana y de quitar este objeto de la lista de listeners
	 * de ConsultasComboBoxBBDD y GuardarElemento
	 * */	
	protected void do_btnCancelar_actionPerformed(ActionEvent arg0) {		
		dispose();
		miComboBoxController.getConsultasComboBoxBBDD().removePropertyChangeListener(this);
		miAltaController.getGuardarElemento().removePropertyChangeListener(PROP_INSERTAR_SERIE,this);//se remueve el objeto de los listeners
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
	 * Cambiar el idSerie de este objeto.
	 * @param idSerie, es una UUID (identificador unico)
	 *  */
	
	public void setIdSerie(UUID idSerie){		
		miIdSerie= idSerie;
	}
	
	
	/*
	 * Devuelve el idSerie de este objeto.
	 * 
	 *  */
	public UUID getIdSerie(){		
		return miIdSerie;
	}
	
	
}
