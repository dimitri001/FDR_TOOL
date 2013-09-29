package view.alta;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.text.*;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JButton;

import utils.MessageUtils;
import view.acierto.AciertoAlta;
import view.error.ErrorAlta;

import libreria.*;

import controller.AltaController;
import controller.ComboBoxController;


public class AltaManualReferencia extends JFrame implements PropertyChangeListener  {
	
    private final int maxNumeroCaracateresTextPanel = 300;
    
	private JTextPane tpDescripcion;	
	private JTextField tfRutaFichero;
	private int intIdModelo;
	private int intIdFabricante;
	private int intIdSerie;
	private String strDescripcion;
	private String strRutaFichero;
	
	private UUID miIdManual;//Identificador unico de cada objeto

	
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
	public static final String PROP_INSERTAR_MANUAL = "insertarManual";


	
	public AltaManualReferencia(AltaController altaController, ComboBoxController comboBoxController, UUID idManual) {
		
		miIdManual = idManual;
		
		miAltaController = altaController;
		miComboBoxController = comboBoxController;
		
		
		setTitle("Alta Manual de Referencia");
		getContentPane().setLayout(null);
		
		JLabel lbFabricante = new JLabel("Fabricante");
		lbFabricante.setBounds(43, 45, 102, 14);
		getContentPane().add(lbFabricante);
		
		JLabel lbNombreModelo = new JLabel("Nombre Modelo");
		lbNombreModelo.setBounds(43, 92, 102, 14);
		getContentPane().add(lbNombreModelo);
		
		JLabel lbNumeroDeSerie = new JLabel("N\u00FAmero de Serie");
		lbNumeroDeSerie.setBounds(43, 152, 102, 14);
		getContentPane().add(lbNumeroDeSerie);
		
		JLabel lbDescripcion = new JLabel("Descripci\u00F3n");
		lbDescripcion.setBounds(43, 199, 102, 14);
		getContentPane().add(lbDescripcion);
		
		JLabel lbRutaFicheroPdf = new JLabel("Ruta Fichero PDF");
		lbRutaFicheroPdf.setBounds(43, 354, 102, 14);
		getContentPane().add(lbRutaFicheroPdf);
		
		cbFabricante = new JComboBox();
		cbFabricante.setBounds(203, 39, 235, 20);
		getContentPane().add(cbFabricante);
		
		cbFabricante.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cbFabricante.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbFabricante.
				{
					elementoCbFabricante = (ElementoComboBox)cbFabricante.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdFabricante = elementoCbFabricante.getIdElemento();				
					miComboBoxController.comboBoxModelo(intIdFabricante, miIdManual);
				}
												
			}
		});

		
		cbModelo = new JComboBox();
		cbModelo.setBounds(203, 89, 235, 20);
		getContentPane().add(cbModelo);
		
		cbModelo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				
				if (cbModelo.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbModelo.
				{
					elementoCbModelo = (ElementoComboBox)cbModelo.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdModelo = elementoCbModelo.getIdElemento();				
					miComboBoxController.comboBoxSerie(intIdFabricante, intIdModelo, miIdManual);
					
				}
												
			}
		});
		
		cbSerie = new JComboBox();
		cbSerie.setBounds(203, 146, 235, 20);
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

		
//		Se comprueba que lo ingresado en el textPanel no excede lo especificado en maxNumeroCaracateresTextPanel 
		tpDescripcion = new JTextPane (new MyDefaultStyledDocument());
		JScrollPane scrDecripcion = new JScrollPane(tpDescripcion);
		scrDecripcion.setBounds(203, 199, 235, 123);
		getContentPane().add(scrDecripcion);
		
		tfRutaFichero = new JTextField();
		tfRutaFichero.setBounds(203, 354, 235, 20);
		getContentPane().add(tfRutaFichero);
		tfRutaFichero.setColumns(10);
		tfRutaFichero.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
				
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnGuardar_actionPerformed(arg0);
			}
		});
		btnGuardar.setBounds(72, 393, 141, 23);
		getContentPane().add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		btnCancelar.setBounds(285, 393, 141, 23);
		getContentPane().add(btnCancelar);
		
		setVisible(true);  
		setSize(514, 474);
		
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
		/*
        dataComboBoxFabricante =new Vector<ElementoComboBox>();  
        dataComboBoxModelo = new Vector<ElementoComboBox>();
        */

		if (PROP_INSERTAR_MANUAL.equals(propertyName)){			
			parametrosGuardarElemento = (ParametrosGuardarElemento) evt.getNewValue();			
			//Se comprueba el id del objeto Serie
			if (parametrosGuardarElemento.getIdElemento().equals(miIdManual)){
				MessageUtils.mensajeInsertarElemento(parametrosGuardarElemento, MyConstants.StringConstant.MANUAL.value());
			}	
		}else{ 
			parametrosConsultasCb = (ParametrosConsultasComboBoxBBDD) evt.getNewValue();

			//Se comprueba el id del objeto Serie
			if(parametrosConsultasCb.getIdElemento().equals(miIdManual)){
				
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
	 * 
	 * */
	protected void do_btnGuardar_actionPerformed(ActionEvent arg0) {
		
		strDescripcion = tpDescripcion.getText();
		strRutaFichero = tfRutaFichero.getText();
		
		miAltaController.guardarManual(intIdSerie, intIdModelo, intIdFabricante, strDescripcion, strRutaFichero, miIdManual);
	
	}
	
	/*
	 * Se encarga de cerrar la ventana y de quitar este objeto de la lista de listeners
	 * de ConsultasComboBoxBBDD y GuardarElemento
	 * */
	protected void do_btnCancelar_actionPerformed(ActionEvent arg0) {
		dispose();
		miComboBoxController.getConsultasComboBoxBBDD().removePropertyChangeListener(this);
		miAltaController.getGuardarElemento().removePropertyChangeListener(PROP_INSERTAR_MANUAL,this);//se remueve el objeto de los listeners

	}
	
	/*
	 * Cambiar el id de este objeto.
	 * @param idManual, es una UUID (identificador unico)
	 *  */
	
	public void setIdManual(UUID idManual){
		
		miIdManual = idManual;
	}
	
	
	/*
	 * Devuelve el id de este objeto.
	 * 
	 *  */
	public UUID getIdManual(){
		
		return miIdManual;
	}

}
