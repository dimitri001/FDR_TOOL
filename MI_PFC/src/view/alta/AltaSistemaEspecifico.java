package view.alta;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.AttributeSet;

import utils.MessageUtils;
import view.acierto.AciertoAlta;
import view.error.ErrorAlta;

import libreria.ElementoComboBox;
import libreria.JTextFieldLimit;
import libreria.MyConstants;
import libreria.MyDefaultStyledDocument;
import libreria.ParametrosConsultasComboBoxBBDD;
import libreria.ParametrosGuardarElemento;

import controller.AltaController;
import controller.ComboBoxController;

public class AltaSistemaEspecifico extends JFrame implements PropertyChangeListener {
	
    private final int maxNumeroCaracateresTextPanel = 300;    
    
	private JTextField tfNombreSistemaEspecifico;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JTextPane tpDescripcion;
	
	private int intIdModelo;
	private int intIdFabricante;
	private int intIdSerie;
	private String strNombreSistemaEspecifico;	
	private String strDescripcion;
	
	private UUID miIdSistemaEspecifico;//Identificador unico de cada objeto
	
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
	public static final String PROP_INSERTAR_SISTEMA_ESPECIFICO = "insertarSistemaEspecifico";
	
	public AltaSistemaEspecifico(AltaController altaController, ComboBoxController comboBoxController, UUID idSistemaEspecifico) {
		
		miIdSistemaEspecifico = idSistemaEspecifico;		
		miAltaController = altaController;
		miComboBoxController = comboBoxController;
		setTitle("Alta Sistema Especifico");
		getContentPane().setLayout(null);
		
		JLabel lblFabricante = new JLabel("Fabricante");
		lblFabricante.setBounds(41, 38, 88, 14);
		getContentPane().add(lblFabricante);
		
		JLabel lblNombreModelo = new JLabel("Nombre Modelo");
		lblNombreModelo.setBounds(41, 76, 113, 14);
		getContentPane().add(lblNombreModelo);
		
		JLabel lblNumeroDeSerie = new JLabel("N\u00FAmero de Serie");
		lblNumeroDeSerie.setBounds(41, 119, 129, 14);
		getContentPane().add(lblNumeroDeSerie);
		
		JLabel lbNombreSistemaEspecif = new JLabel("Nombre Sistema Espec\u00EDfico ");
		lbNombreSistemaEspecif.setBounds(41, 159, 178, 14);
		getContentPane().add(lbNombreSistemaEspecif);
		
		JLabel lblNewLabel = new JLabel("Descripci\u00F3n");
		lblNewLabel.setBounds(41, 193, 113, 14);
		getContentPane().add(lblNewLabel);
		
	    cbFabricante = new JComboBox();
		cbFabricante.setBounds(245, 35, 235, 20);
		getContentPane().add(cbFabricante);
		
		cbFabricante.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cbFabricante.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbFabricante.
				{
					elementoCbFabricante = (ElementoComboBox)cbFabricante.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdFabricante = elementoCbFabricante.getIdElemento();					
					miComboBoxController.comboBoxModelo(intIdFabricante, miIdSistemaEspecifico);
				}
												
			}
		});

		
		cbModelo = new JComboBox();
		cbModelo.setBounds(245, 73, 235, 20);
		getContentPane().add(cbModelo);

		cbModelo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cbModelo.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbModelo.
				{
					elementoCbModelo = (ElementoComboBox)cbModelo.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdModelo = elementoCbModelo.getIdElemento();				
					miComboBoxController.comboBoxSerie(intIdFabricante, intIdModelo, miIdSistemaEspecifico);
					
				}
												
			}
		});

		cbSerie = new JComboBox();
		cbSerie.setBounds(245, 116, 235, 20);
		getContentPane().add(cbSerie);
		

		cbSerie.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cbSerie.getItemCount() > 0){ //Evitamos una exepcion si no hay objetos en el cbSerie.
					elementoCbSerie = (ElementoComboBox)cbSerie.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro de la serie del combobox
					intIdSerie = elementoCbSerie.getIdElemento();	
				}
												
			}
		});

		
		tfNombreSistemaEspecifico = new JTextField();
		tfNombreSistemaEspecifico.setBounds(245, 156, 235, 20);
		getContentPane().add(tfNombreSistemaEspecifico);
		tfNombreSistemaEspecifico.setColumns(10);
		tfNombreSistemaEspecifico.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
			
//		Se comprueba que lo ingresado en el textPanel no excede lo especificado en maxNumeroCaracateresTextPanel 
		tpDescripcion = new JTextPane (new MyDefaultStyledDocument());		
		JScrollPane scrDecripcion = new JScrollPane(tpDescripcion);
		scrDecripcion.setBounds(245, 193, 235, 123);
		getContentPane().add(scrDecripcion);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnGuardar_actionPerformed(arg0);
			}
		});
		btnGuardar.setBounds(91, 346, 141, 23);
		getContentPane().add(btnGuardar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		btnCancelar.setBounds(323, 346, 141, 23);
		getContentPane().add(btnCancelar);
		
		setVisible(true);  
		setSize(573, 429);
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
		String propertyName = evt.getPropertyName();
		ParametrosGuardarElemento parametrosGuardarElemento;
		ParametrosConsultasComboBoxBBDD parametrosConsultasCb;

		if (PROP_INSERTAR_SISTEMA_ESPECIFICO.equals(propertyName)){			
			parametrosGuardarElemento = (ParametrosGuardarElemento) evt.getNewValue();			
			//Se comprueba el id del objeto Serie
			if (parametrosGuardarElemento.getIdElemento().equals(miIdSistemaEspecifico)){							
				MessageUtils.mensajeInsertarElemento(parametrosGuardarElemento, MyConstants.StringConstant.SISTEMA.value());
			}
		}else{ 
			parametrosConsultasCb = (ParametrosConsultasComboBoxBBDD) evt.getNewValue();
			//Se comprueba el id del objeto Serie
			if(parametrosConsultasCb.getIdElemento().equals(miIdSistemaEspecifico)){				
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
		
        for(int i=0;i<dataComboBox.size();i=i+1){
        	elementoComboBox = new ElementoComboBox(dataComboBox.get(i).getIdElemento(), dataComboBox.get(i).getNombreElemento());
        	comboBox.addItem(elementoComboBox);
       }
	}

	
	/*
	 * Es llamado por el listener del boton guardar. 
	 * */
	protected void do_btnGuardar_actionPerformed(ActionEvent arg0) {	
		strNombreSistemaEspecifico = tfNombreSistemaEspecifico.getText();
		strDescripcion = tpDescripcion.getText();
		miAltaController.guardarSistemaEspecifico(intIdSerie, intIdModelo, intIdFabricante, strNombreSistemaEspecifico, strDescripcion, miIdSistemaEspecifico);
	}
	
	/*
	 * Se encarga de cerrar la ventana y de quitar este objeto de la lista de listeners
	 * de ConsultasComboBoxBBDD y GuardarElemento
	 * */
	protected void do_btnCancelar_actionPerformed(ActionEvent arg0) {
		dispose();
		miComboBoxController.getConsultasComboBoxBBDD().removePropertyChangeListener(this);
		miAltaController.getGuardarElemento().removePropertyChangeListener(PROP_INSERTAR_SISTEMA_ESPECIFICO,this);//se remueve el objeto de los listeners
	
	}
	
	/*
	 * Cambiar el id de este objeto.
	 * @param idSistemaEspecifico, es una UUID (identificador unico)
	 *  */	
	public void setIdSistemaEspecifico(UUID idSistemaEspecifico){		
		miIdSistemaEspecifico = idSistemaEspecifico;
	}
	
	
	/*
	 * Devuelve el id de este objeto.
	 * 
	 *  */
	public UUID getIdSistemaEspecifico(){		
		return miIdSistemaEspecifico;
	}

}
