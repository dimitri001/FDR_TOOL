package view.alta;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JSeparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.MessageUtils;
import utils.ValidationUtils;
import view.acierto.AciertoAlta;
import view.error.ErrorAlta;
import view.error.ErrorValidacionCampo;

import controller.AltaController;
import controller.ComboBoxController;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import libreria.ContenedorVisualAtributoElemento;
import libreria.ElementoComboBox;
import libreria.JTextFieldLimit;
import libreria.MyConstants;
import libreria.ParametrosConsultasComboBoxBBDD;
import libreria.ParametrosGuardarElemento;

public class AltaElementoEspecifico extends JFrame implements PropertyChangeListener {
	
	private JTextField tfSubframes;
	private JTextField tfPalabras;
	private JButton btnGuardar;
	private JButton btnCancelar;

	private JLabel lblAtributosFijos;
	private JTextField tfNombreElemento;
	private JTextField tfBits;
	private JTextField tfNumeroDeAtributo;

	private JPanel panel;
	private JPanel panelAtributos;
	
	int j;
	int interesting;//Habilita el boton Añadir, para ser presionado
	private int intIdFabricante;
	private int intIdModelo;
	private int intIdSerie;
	private int intIdSistema;

	
	private String strNombreElemento;
	private boolean blInteresting;
	private String strSubFrames;
	private String strPalabras;
	private String strBits;
	
	final JCheckBox chbInteresting;
	private int intNAtributos;

	List<JTextField> listTextField = new ArrayList<JTextField>();
	List<JComboBox> listJComboBox = new ArrayList<JComboBox>();
	List<ContenedorVisualAtributoElemento> listAtributoEspecifico = new ArrayList<ContenedorVisualAtributoElemento>();
	String[] typeStrings = MyConstants.ArrayConstant.comboBoxTipoElementoEspecifico.value();
	
	private UUID miIdElementoEspecifico;//Identificador unico de cada objeto
	
	
	private JComboBox cbFabricante;
	private Vector<ElementoComboBox> dataComboBoxFabricante;
	private ElementoComboBox elementoCbFabricante;

	
	private JComboBox cbModelo;
	private Vector<ElementoComboBox> dataComboBoxModelo;
	private ElementoComboBox elementoCbModelo;
		
	
	private JComboBox cbSerie;
	private Vector<ElementoComboBox> dataComboBoxSerie;
	private ElementoComboBox elementoCbSerie;

	private JComboBox cbSistema;
	private Vector<ElementoComboBox> dataComboBoxSistema;
	private ElementoComboBox elementoCbSistema;
	
	private AltaController miAltaController;
	private ComboBoxController miComboBoxController;

	public static final String PROP_CONSULTAR_FABRICANTE = "consultarFabricante";
	public static final String PROP_CONSULTAR_MODELO = "consultarModelo";
	public static final String PROP_CONSULTAR_SERIE = "consultarSerie";
	public static final String PROP_CONSULTAR_SISTEMA = "consultarSistema";	
	
	public static final String PROP_INSERTAR_ELEMENTO_ESPECIFICO = "insertarElementoEspecifico";
	Logger LOG = LoggerFactory.getLogger(AltaElementoEspecifico.class);
	
	public AltaElementoEspecifico(AltaController altaController, ComboBoxController comboBoxController, UUID idElementoEspecifico) {
		
		
		miIdElementoEspecifico = idElementoEspecifico;		
		miAltaController = altaController;
		miComboBoxController = comboBoxController;		
		setTitle("Alta Elemento del Sistema Especifico");		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(500,1600));//Indica el tamaño preferido del panel, asi podemos usar el scrollpanel, fijando este valor.
		
		JLabel lblNombreModelo = new JLabel("Nombre Modelo");
		lblNombreModelo.setBounds(41, 76, 130, 14);
		panel.add(lblNombreModelo);
		
		JLabel lblNumeroDeSerie = new JLabel("N\u00FAmero de Serie");
		lblNumeroDeSerie.setBounds(41, 119, 130, 14);
		panel.add(lblNumeroDeSerie);
		
		JLabel lblNombreSistema = new JLabel("Nombre Sistema Especifico ");
		lblNombreSistema.setBounds(41, 159, 177, 14);
		panel.add(lblNombreSistema);
		
		JLabel lblNombreElemento = new JLabel("Nombre Elemento");
		lblNombreElemento.setBounds(43, 199, 111, 14);
		panel.add(lblNombreElemento);
		
		JLabel lblInteresting = new JLabel("Interesting");
		lblInteresting.setBounds(41, 229, 88, 14);
		panel.add(lblInteresting);
		
		cbFabricante = new JComboBox();
		cbFabricante.setBounds(245, 35, 235, 20);
		panel.add(cbFabricante);
		
		cbFabricante.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				if (cbFabricante.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbFabricante.
				{
					elementoCbFabricante = (ElementoComboBox)cbFabricante.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdFabricante = elementoCbFabricante.getIdElemento();					
					miComboBoxController.comboBoxModelo(intIdFabricante, miIdElementoEspecifico);
				}												
			}
		});
	
		cbModelo = new JComboBox();
		cbModelo.setBounds(245, 73, 235, 20);
		panel.add(cbModelo);
		
		cbModelo.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				if (cbModelo.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbModelo.
				{
					elementoCbModelo = (ElementoComboBox)cbModelo.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro del modelo del combobox
					intIdModelo = elementoCbModelo.getIdElemento();					
					miComboBoxController.comboBoxSerie(intIdFabricante, intIdModelo, miIdElementoEspecifico);
					
				}
												
			}
		});		
		cbSerie = new JComboBox();
		cbSerie.setBounds(245, 116, 235, 20);
		panel.add(cbSerie);	
		cbSerie.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cbSerie.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbSerie.
				{
					elementoCbSerie = (ElementoComboBox)cbSerie.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro de la serie del combobox
					intIdSerie = elementoCbSerie.getIdElemento();					
					miComboBoxController.comboBoxSistema(intIdFabricante, intIdModelo, intIdSerie, miIdElementoEspecifico);
				}							
			}
		});
		cbSistema = new JComboBox();
		cbSistema.setBounds(245, 156, 235, 20);
		panel.add(cbSistema);
		cbSistema.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cbSistema.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbSerie.
				{
					elementoCbSistema = (ElementoComboBox)cbSistema.getModel().getSelectedItem();//Se obtiene el objeto seleccionado dentro de la serie del combobox
					intIdSistema = elementoCbSistema.getIdElemento();					
				}							
			}
		});
		tfNombreElemento = new JTextField();
		tfNombreElemento.setBounds(245, 196, 235, 20);
		panel.add(tfNombreElemento);
		tfNombreElemento.setColumns(10);
		tfNombreElemento.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
				
		lblAtributosFijos = new JLabel("Atributos Fijos");
		lblAtributosFijos.setBounds(41, 265, 147, 14);
		panel.add(lblAtributosFijos);
		
		JLabel lblSubframes = new JLabel("Subframes");
		lblSubframes.setBounds(41, 290, 119, 14);
		panel.add(lblSubframes);
		
		JLabel lblPalabras = new JLabel("Palabras");
		lblPalabras.setBounds(41, 315, 119, 14);
		panel.add(lblPalabras);
		
		tfSubframes = new JTextField();
		tfSubframes.setBounds(186, 284, 64, 20);
		panel.add(tfSubframes);
		tfSubframes.setColumns(10);
		tfSubframes.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		
				
		tfPalabras = new JTextField();
		tfPalabras.setBounds(186, 315, 64, 20);
		tfPalabras.setColumns(10);
		panel.add(tfPalabras);
		tfPalabras.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		
		
		JLabel lblBits = new JLabel("Bits");
		lblBits.setBounds(41, 346, 119, 14);
		panel.add(lblBits);
		
		tfBits = new JTextField();
		tfBits.setBounds(186, 346, 64, 20);
		tfBits.setColumns(10);
		panel.add(tfBits);
		tfBits.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
				
		JLabel lblFabricante = new JLabel("Fabricante");
		lblFabricante.setBounds(41, 38, 88, 14);
		panel.add(lblFabricante);
		
		chbInteresting = new JCheckBox("");
		chbInteresting.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(chbInteresting.isSelected()) {
					tfNumeroDeAtributo.setEnabled(true);
					interesting =1;                    
                } else {
                	interesting = 0;
                    intNAtributos =0;
                    tfNumeroDeAtributo.setText("0");
                    tfNumeroDeAtributo.setEnabled(false);
                    pintarAtributos(intNAtributos);
                }
			}
		});
		chbInteresting.setBounds(245, 229, 95, 22);
		panel.add(chbInteresting);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 254, 588, 4);
		panel.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 378, 588, 4);
		panel.add(separator_1);
		
		JLabel lblAtributosEspecificos = new JLabel("Estructura de los Atributos Espec\u00EDficos del Elemento");
		lblAtributosEspecificos.setBounds(41, 442, 313, 14);
		panel.add(lblAtributosEspecificos);

		JScrollPane scrollPane = new JScrollPane(panel);
		
		JLabel lblNumeroDeAtributos = new JLabel("N\u00FAmero de Atributos");
		lblNumeroDeAtributos.setBounds(41, 393, 119, 14);
		panel.add(lblNumeroDeAtributos);
		
		tfNumeroDeAtributo = new JTextField();
		tfNumeroDeAtributo.setBounds(186, 393, 64, 20);
		tfNumeroDeAtributo.setColumns(10);
		panel.add(tfNumeroDeAtributo);
		tfNumeroDeAtributo.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
				
		JLabel lblNombreDeAtributo = new JLabel("Nombre de Atributo");
		lblNombreDeAtributo.setBounds(41, 482, 119, 14);
		panel.add(lblNombreDeAtributo);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(323, 482, 119, 14);
		panel.add(lblTipo);
		
		JButton btnAniadir = new JButton("A\u00F1adir");
		btnAniadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (interesting == 1) {
				do_btnAniadir_actionPerformed(arg0);
				
				}
			}
		});
		btnAniadir.setBounds(307, 393, 89, 23);
		panel.add(btnAniadir);		
		panelAtributos = new JPanel();
		panelAtributos.setBounds(0, 507, 500, 3462);
		panel.add(panelAtributos);
		panelAtributos.setLayout(null);		
		getContentPane().add(scrollPane,BorderLayout.CENTER);		
		setVisible(true);  
		pack();
		setSize(524, 680);		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
					   public void windowClosing(WindowEvent evt) {
						   do_btnCancelar_actionPerformed(null);
					   }
					  });
		//Se usa esto para que nos pinte los botones guardar y cancelar al crear la ventana.
		chbInteresting.doClick();//sets the checkBox
		chbInteresting.doClick();//unset the checkBox

	}
	

	protected void do_btnAniadir_actionPerformed(ActionEvent arg0) {
		
		String mensaje = null ;		
		String texto = tfNumeroDeAtributo.getText();		
		//Verificamos si intNAtributos es un valor entero
		try {			
			intNAtributos = Integer.parseInt(texto);	
			//Verificamos si intNAtributos es un numero positivo y menor que 31
			if ((intNAtributos > 0)&& (intNAtributos <31)){
				pintarAtributos( intNAtributos );					
			}else {
				mensaje = "La entrada Número de Atributos debe ser mayor que 0 y menor que 31, esta";
				ErrorValidacionCampo errorAltaNAtributos= new ErrorValidacionCampo(mensaje);				
			}			
		} 
		catch (NumberFormatException e) {
		   System.err.println("No se puede convertir a número");
		   e.printStackTrace();		   
		   mensaje = "La entrada Número de Atributos";
		   ErrorValidacionCampo errorAltaNAtributos= new ErrorValidacionCampo(mensaje);		   
		}		
	}
	
	/* En las listas guardo los objetos asi cuando los necesite los obtengo por medio
	 * de las listas  */	
	private void pintarAtributos(int intNAtributos ){
		
		panelAtributos.removeAll();
		listAtributoEspecifico = new ArrayList<ContenedorVisualAtributoElemento>();
		j=0;//Es cero porque se toman las coordenadas del panelAtributos 
		for (int i=0 ;i < intNAtributos;i++ ){		
			j=j+31;		
			JTextField tfNombreAtributo = new JTextField();
			tfNombreAtributo.setBounds(41,j , 247, 20);
			tfNombreAtributo.setColumns(10);
			panelAtributos.add(tfNombreAtributo);
			listTextField.add(tfNombreAtributo);
			tfNombreAtributo.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
			final JComboBox cbTipo = new JComboBox(typeStrings);
			cbTipo.setBounds (314,j, 111, 20);
			panelAtributos.add(cbTipo);		
			listJComboBox.add(cbTipo);	
			//Se añade a esta lista el nombre y el cbTipo de cada atributo
			listAtributoEspecifico.add(new ContenedorVisualAtributoElemento(tfNombreAtributo, cbTipo, true));
			cbTipo.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {		
					if (cbTipo.getItemCount() > 0) //Evitamos una exepcion si no hay objetos en el cbTipo.
					{	
						//Posible mejora
						//Se podria omitir el siguiente cacho de codigo si en lugar de buscar el cbTipo en la listaContenedorVisualAtributoElemento
						//Se añade el listener en el cbTipo asi
						//contenedorAtributoEspecifico.getCbTipo.addListener(new ....
						//   public void actionPerformed....
						//     if (contenedorAtributoEspecifico.getCbTipo.getItemCount() > 0){
						//		  String aux = (String)contenedorAtributoEspecifico.getCbTipo.getModel().getSelectedItem();
						// 		  contenedorAtributoEspecifico.setStrCbTipo(aux);
						//        }...
						//Asi añadimos el strCbTipo directamente en el contenedorAtributoEspecifico
						
						//Procedemos a buscar el cbTipo dentro de la listaContenedorVisualAtributoElemento
						int ind = -1; //Indice de la lista
						Iterator it = listAtributoEspecifico.iterator(); 
						boolean encontrado = false; // flag que nos indica si se encontro o no el cbTipo dentro de la listaContenedorVisualAtributoElemento
						String strCbTipo = (String)cbTipo.getModel().getSelectedItem();
						while (!encontrado && it.hasNext()){						
							ind++;
							ContenedorVisualAtributoElemento contenedorAtributo = (ContenedorVisualAtributoElemento) it.next();
							encontrado = contenedorAtributo.getCbTipo().equals(cbTipo);
						}
						//Si se encontro el cbTipo, el strCbTipo se añade al elemento de listaContenedorVisualAtributoElemento correspondiente
						if (encontrado){						
							listAtributoEspecifico.get(ind).setStrCbTipo(strCbTipo);
						}					
						LOG.info("Alta Elemento Especifico indice de tabla ind= "+ ind );			
						LOG.info("Alta Elemento Especifico listaContenedorVisualAtributoElemento.get(ind).getStrCbTipo() = "+ listAtributoEspecifico.get(ind).getStrCbTipo());			
					}
													
				}
			});
			cbTipo.setSelectedIndex(0);
		}
		j=j+68;
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(90, j, 88, 23);
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnGuardar_actionPerformed(arg0);
			}
		});	
		panelAtributos.add(btnGuardar);	
		btnCancelar= new JButton("Cancelar");
		btnCancelar.setBounds(284, j, 88, 23);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		panelAtributos.add(btnCancelar);	
		panelAtributos.revalidate();	
		panelAtributos.repaint();
		}

	/*
	 * Es llamado por el listener del boton guardar. 
	 * 
	 * */
	protected void do_btnGuardar_actionPerformed(ActionEvent arg0) {
		
	 	strNombreElemento = tfNombreElemento.getText();		
	 	blInteresting = chbInteresting.isSelected(); 	
		strSubFrames = tfSubframes.getText();
		strPalabras = tfPalabras.getText();
		strBits = tfBits.getText();
		if (ValidationUtils.validationInterestingNAtributos(blInteresting,intNAtributos,MyConstants.StringConstant.ALTA.value())){
			//Se copia en listAuxiliar la listaContenedorVisualAtributoElemento
		    List<ContenedorVisualAtributoElemento> listAuxiliar = new ArrayList<ContenedorVisualAtributoElemento>(listAtributoEspecifico);
		    Iterator it = listAuxiliar.iterator(); 
			String nombreAux;
			//Se obtienen los strings de Jtexfields	y se rellena toda la informacion dentro de listaContenedorVisualAtributoElemento
			while (it.hasNext()){		
				ContenedorVisualAtributoElemento contenedorAtributo = (ContenedorVisualAtributoElemento) it.next();
				nombreAux = contenedorAtributo.getNombreAtributo().getText();
				contenedorAtributo.setStrNombreAtributo(nombreAux);		
			}
			miAltaController.guardarElementoEspecifico(intIdSistema, intIdSerie, intIdModelo, intIdFabricante,strNombreElemento, blInteresting, strSubFrames,strPalabras, strBits, intNAtributos, listAuxiliar, miIdElementoEspecifico);
			listAuxiliar.removeAll(listAuxiliar);
		}
			

	}




	/*
	 * Se encarga de cerrar la ventana y de quitar este objeto de la lista de listeners
	 * de ConsultasComboBoxBBDD y GuardarElemento
	 * */	
	protected void do_btnCancelar_actionPerformed(ActionEvent arg0) {	
		dispose();
		miComboBoxController.getConsultasComboBoxBBDD().removePropertyChangeListener(this);
		miAltaController.getGuardarElemento().removePropertyChangeListener(PROP_INSERTAR_ELEMENTO_ESPECIFICO,this);//se remueve el objeto de los listeners
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
		ParametrosConsultasComboBoxBBDD parametrosConsultasCb ;
		if (PROP_INSERTAR_ELEMENTO_ESPECIFICO.equals(propertyName)){		
			parametrosGuardarElemento = (ParametrosGuardarElemento) evt.getNewValue();
			//Se comprueba el id del objeto Serie
			if (parametrosGuardarElemento.getIdElemento().equals(miIdElementoEspecifico)){
				MessageUtils.mensajeInsertarElemento(parametrosGuardarElemento, MyConstants.StringConstant.ELEMENTOS.value());
			}							
		}else{
			parametrosConsultasCb = (ParametrosConsultasComboBoxBBDD) evt.getNewValue();
			//Se comprueba el id del objeto Serie
			if(parametrosConsultasCb.getIdElemento().equals(miIdElementoEspecifico)){
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
						//Añadir lo de cbSistemaESpecifico
						cbSistema.removeAllItems();
						intIdSistema =0;	
					}
						
				}else if (PROP_CONSULTAR_SERIE.equals(propertyName)){
					intIdSerie =0;//Asi si no se actualiza el comboBox, el id =0
					dataComboBoxSerie = parametrosConsultasCb.getDataElemento();//Es un Vector<ElementoComboBox>
					actualizarComboBox(cbSerie,dataComboBoxSerie );
					//Asi si no hay datos en modelo, los ComboBox que dependen de el se quedan tambien vacios
					if (cbSerie.getModel().getSize() == 0){
						cbSistema.removeAllItems();
						intIdSistema =0;
					}

				}else if (PROP_CONSULTAR_SISTEMA.equals(propertyName)){
					intIdSistema =0;//Asi si no se actualiza el comboBox, el id =0
					dataComboBoxSistema = parametrosConsultasCb.getDataElemento();//Es un Vector<ElementoComboBox>
					actualizarComboBox(cbSistema,dataComboBoxSistema );
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
	 * Cambiar el id de este objeto.
	 * @param idEstructuraFrame, es una UUID (identificador unico)
	 *  */

	public void setIdElementoEspecifico(UUID idElementoEspecifico){	
		miIdElementoEspecifico = idElementoEspecifico;}


	/*
	 * Devuelve el id de este objeto.
	 * 
	 *  */
	public UUID getIdElementoEspecifico(){	
		return miIdElementoEspecifico;
	}
}