package view.alta;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.UUID;
import java.util.Vector;

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

import controller.AltaController;
import controller.ComboBoxController;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import libreria.ElementoComboBox;
import libreria.JTextFieldLimit;
import libreria.MutableBoolean;
import libreria.MyConstants;
import libreria.MyDefaultStyledDocument;
import libreria.ParametrosConsultasComboBoxBBDD;
import libreria.ParametrosGuardarElemento;

public class AltaEstructuraFrame extends JFrame implements PropertyChangeListener {
	
    private final int maxNumeroCaracateresTextPanel = 300;

	private JTextField tfNumeroSubframes;
	private JTextField tfTiempoSubframe;
	private JTextField tfPalabrasSegundo;
	private JTextField tfBitsPalabra;
	private JTextPane tpDescripcion;
	
	private int intIdModelo;
	private int intIdFabricante;
	private int intIdSerie;
	
	private int intNumeroSubframes;
	private int intTiempoSubframe;//se podra cambiar por un long 
	private double dblTiempoSubframe;
	private String strTiempoSubframe;//es una var auxiliar para verificar la entrada de tfTiempoSubframe
	private int intPalabrasSegundo;
	private int intBitsPalabra;
	private String strDescripcion;

	private UUID miIdEstructuraFrame;//Identificador unico de cada objeto

	
	private JComboBox cbFabricante;
	private Vector<ElementoComboBox> dataComboBoxFabricante;
	private ElementoComboBox elementoCbFabricante;

	
	private JComboBox cbModelo;
	private Vector<ElementoComboBox> dataComboBoxModelo;
	private ElementoComboBox elementoCbModelo;
		

	private JComboBox cbSerie;
	private Vector<ElementoComboBox> dataComboBoxSerie;
	private ElementoComboBox elementoCbSerie;

	
	private AltaController miAltaController;
	private ComboBoxController miComboBoxController;


	public static final String PROP_CONSULTAR_FABRICANTE = "consultarFabricante";
	public static final String PROP_CONSULTAR_MODELO = "consultarModelo";
	public static final String PROP_CONSULTAR_SERIE = "consultarSerie";
	public static final String PROP_INSERTAR_ESTRUCTURA_FRAME = "insertarEstructuraFrame";

	
	
	public AltaEstructuraFrame(AltaController altaController, ComboBoxController comboBoxController, UUID idEstructuraFrame) {
		
		
		
		miIdEstructuraFrame = idEstructuraFrame;
		
		miAltaController = altaController;
		miComboBoxController = comboBoxController;
	
		
		setTitle("Alta Estructura de Frame ");
		getContentPane().setLayout(null);
		
		JLabel lbFabricante = new JLabel("Fabricante ");
		lbFabricante.setBounds(43, 31, 128, 14);
		getContentPane().add(lbFabricante);
		
		JLabel lbNombreModelo = new JLabel("Nombre Modelo");
		lbNombreModelo.setBounds(43, 76, 128, 14);
		getContentPane().add(lbNombreModelo);
		
		JLabel lbNumeroDeSerie = new JLabel("N\u00FAmero de Serie");
		lbNumeroDeSerie.setBounds(43, 121, 128, 14);
		getContentPane().add(lbNumeroDeSerie);
		
		JLabel lbNumeroDeSubframes = new JLabel("N\u00FAmero de Subframes");
		lbNumeroDeSubframes.setBounds(43, 166, 128, 14);
		getContentPane().add(lbNumeroDeSubframes);
		
		JLabel lbEstructuraDeSubframes = new JLabel("Estructura de Subframes");
		lbEstructuraDeSubframes.setBounds(43, 211, 152, 14);
		getContentPane().add(lbEstructuraDeSubframes);
		
		JLabel lbTiempoDeCada = new JLabel("Tiempo de cada Subframe");
		lbTiempoDeCada.setBounds(53, 236, 167, 14);
		getContentPane().add(lbTiempoDeCada);
		
		JLabel lbPalabrasPorSegundo = new JLabel("Palabras por Segundo");
		lbPalabrasPorSegundo.setBounds(53, 281, 167, 14);
		getContentPane().add(lbPalabrasPorSegundo);
		
		JLabel lbBitsPorPalabra = new JLabel("Bits por Palabra");
		lbBitsPorPalabra.setBounds(53, 326, 95, 14);
		getContentPane().add(lbBitsPorPalabra);
		
		JLabel lbDescripcion = new JLabel("Descripci\u00F3n");
		lbDescripcion.setBounds(43, 371, 81, 14);
		getContentPane().add(lbDescripcion);
		
		cbFabricante = new JComboBox();
		cbFabricante.setBounds(218, 25, 235, 20);
		getContentPane().add(cbFabricante);

		cbFabricante.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				
				if (cbFabricante.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbFabricante.
				{
					elementoCbFabricante = (ElementoComboBox)cbFabricante.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdFabricante = elementoCbFabricante.getIdElemento();
					miComboBoxController.comboBoxModelo(intIdFabricante, miIdEstructuraFrame);
				}
												
			}
		});

		
		cbModelo = new JComboBox();
		cbModelo.setBounds(218, 73, 235, 20);
		getContentPane().add(cbModelo);
		
		cbModelo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				
				if (cbModelo.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbModelo.
				{
					elementoCbModelo = (ElementoComboBox)cbModelo.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdModelo = elementoCbModelo.getIdElemento();
					miComboBoxController.comboBoxSerie(intIdFabricante, intIdModelo, miIdEstructuraFrame);
					
				}
												
			}
		});

		
		cbSerie = new JComboBox();
		cbSerie.setBounds(218, 118, 235, 20);
		getContentPane().add(cbSerie);
		
		cbSerie.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (cbSerie.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbSerie.
				{
					elementoCbSerie = (ElementoComboBox)cbSerie.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro de la serie del combobox
					intIdSerie = elementoCbSerie.getIdElemento();
					
				}
												
			}
		});

		
		tfNumeroSubframes = new JTextField();
		tfNumeroSubframes.setBounds(218, 163, 235, 20);
		getContentPane().add(tfNumeroSubframes);
		tfNumeroSubframes.setColumns(10);
		tfNumeroSubframes.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
				
		tfTiempoSubframe = new JTextField();
		tfTiempoSubframe.setBounds(218, 233, 235, 20);
		getContentPane().add(tfTiempoSubframe);
		tfTiempoSubframe.setColumns(10);
		tfTiempoSubframe.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		tfTiempoSubframe.setText("n ó n.m");
		//Se borra el contenido del textField al hacer click sobre el
		tfTiempoSubframe.addMouseListener(new MouseAdapter(){
	 		@Override
            public void mouseClicked(MouseEvent e){
	 			tfTiempoSubframe.setText(null);
            }
	 	});
			
		tfPalabrasSegundo = new JTextField();
		tfPalabrasSegundo.setBounds(218, 278, 235, 20);
		getContentPane().add(tfPalabrasSegundo);
		tfPalabrasSegundo.setColumns(10);
		tfPalabrasSegundo.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
				
		tfBitsPalabra = new JTextField();
		tfBitsPalabra.setBounds(218, 323, 235, 20);
		getContentPane().add(tfBitsPalabra);
		tfBitsPalabra.setColumns(10);
		tfBitsPalabra.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
				
//		Se comprueba que lo ingresado en el textPanel no excede lo especificado en maxNumeroCaracateresTextPanel 
		tpDescripcion = new JTextPane (new MyDefaultStyledDocument());		
		JScrollPane scrDescripcion = new JScrollPane(tpDescripcion);
		scrDescripcion.setBounds(218, 371, 235, 123);
		getContentPane().add(scrDescripcion);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnGuardar_actionPerformed(arg0);
			}
		});
		btnGuardar.setBounds(70, 520, 141, 23);
		getContentPane().add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		btnCancelar.setBounds(281, 520, 141, 23);
		getContentPane().add(btnCancelar);
		
		setVisible(true);  
		setSize(509, 599);
		
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
		
       

		if (PROP_INSERTAR_ESTRUCTURA_FRAME.equals(propertyName)){
			
			parametrosGuardarElemento = (ParametrosGuardarElemento) evt.getNewValue();
			
			//Se comprueba el id del objeto Serie
			if (parametrosGuardarElemento.getIdElemento().equals(miIdEstructuraFrame)){
				MessageUtils.mensajeInsertarElemento(parametrosGuardarElemento, MyConstants.StringConstant.ESTRUCTURA_FRAME.value());
			}			
				
		}else{ 
			
			parametrosConsultasCb = (ParametrosConsultasComboBoxBBDD) evt.getNewValue();

			//Se comprueba el id del objeto Serie
			if(parametrosConsultasCb.getIdElemento().equals(miIdEstructuraFrame)){
				
				if (PROP_CONSULTAR_FABRICANTE.equals(propertyName)){
					intIdFabricante = 0;//Asi si no se actualiza el comboBox, el id =0
					dataComboBoxFabricante = parametrosConsultasCb.getDataElemento();// DataElemento es un Vector<ElementoComboBox>
					actualizarComboBox(cbFabricante,dataComboBoxFabricante );

				}else if (PROP_CONSULTAR_MODELO.equals(propertyName)){
					intIdModelo =0;//Asi si no se actualiza el comboBox, el id =0
					dataComboBoxModelo = parametrosConsultasCb.getDataElemento();// Es un Vector<ElementoComboBox>
					actualizarComboBox(cbModelo,dataComboBoxModelo );
					
					//Asi si no hay datos en modelo, los ComboBox que dependen de el se quedan tambien vacios
					if (cbModelo.getModel().getSize() == 0){
						cbSerie.removeAllItems();
						intIdSerie =0;
					}
						
				}else if (PROP_CONSULTAR_SERIE.equals(propertyName)){
					intIdSerie =0;//Asi si no se actualiza el comboBox, el id =0
					dataComboBoxSerie = parametrosConsultasCb.getDataElemento();//Es un Vector<ElementoComboBox>
					actualizarComboBox(cbSerie,dataComboBoxSerie );
	
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
	 * Es llamado por el listener del boton guardar. 
	 * 
	 * */
	protected void do_btnGuardar_actionPerformed(ActionEvent arg0) {

		boolean errorTiempoSubframe;
		String mensaje = null ;		
		mensaje = "La entrada Número de Subframes";
		MutableBoolean blNumeroSubframes = new MutableBoolean(false);
		intNumeroSubframes = NumericUtils.parseIntPositive(tfNumeroSubframes.getText(), MyConstants.StringConstant.ALTA.value(), mensaje, blNumeroSubframes);
		mensaje = "La entrada Tiempo de Subframes";
		MutableBoolean blTiempoSubframe = new MutableBoolean(false);
		dblTiempoSubframe = NumericUtils.parseDoublePositive(tfTiempoSubframe.getText(), MyConstants.StringConstant.ALTA.value(), mensaje, blTiempoSubframe);
		mensaje = "La entrada Palabras por Subframes";
		MutableBoolean blPalabrasSegundo = new MutableBoolean(false);
		intPalabrasSegundo = NumericUtils.parseIntPositive(tfPalabrasSegundo.getText(), MyConstants.StringConstant.ALTA.value(), mensaje, blPalabrasSegundo);
		mensaje = "La entrada Bits por Palabra";
		MutableBoolean blBitsPalabra = new MutableBoolean(false);
		intBitsPalabra = NumericUtils.parseIntPositive(tfBitsPalabra.getText(), MyConstants.StringConstant.ALTA.value(), mensaje, blBitsPalabra);
		strDescripcion = tpDescripcion.getText();		
		if (blNumeroSubframes.valor && blTiempoSubframe.valor && blPalabrasSegundo.valor && blBitsPalabra.valor){			
			miAltaController.guardarEstructuraFrame(intIdSerie, intIdModelo, intIdFabricante, intNumeroSubframes, dblTiempoSubframe, intPalabrasSegundo, intBitsPalabra, strDescripcion, miIdEstructuraFrame);
		}					
	}
	
	/*
	 * Se encarga de cerrar la ventana y de quitar este objeto de la lista de listeners
	 * de ConsultasComboBoxBBDD y GuardarElemento
	 * */
	protected void do_btnCancelar_actionPerformed(ActionEvent arg0) {
		dispose();
		miComboBoxController.getConsultasComboBoxBBDD().removePropertyChangeListener(this);
		miAltaController.getGuardarElemento().removePropertyChangeListener(PROP_INSERTAR_ESTRUCTURA_FRAME,this);//se remueve el objeto de los listeners
	
	}

	/*
	 * Cambiar el id de este objeto.
	 * @param idEstructuraFrame, es una UUID (identificador unico)
	 *  */
	
	public void setIdEstructuraFrame(UUID idEstructuraFrame){		
		miIdEstructuraFrame = idEstructuraFrame;		
	}
	
	
	/*
	 * Devuelve el id de este objeto.
	 * 
	 *  */
	public UUID getIdEstructuraFrame(){
		
		return miIdEstructuraFrame;
	}
}
