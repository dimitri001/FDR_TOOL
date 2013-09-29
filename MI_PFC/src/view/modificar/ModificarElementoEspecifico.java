
package view.modificar;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JSeparator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.UIManager;

import libreria.AtributoElemento;
import libreria.ContenedorVisualAtributoElemento;
import libreria.ElementoComboBox;
import libreria.ElementosSistemaEspecifico;
import libreria.JTextFieldLimit;
import libreria.ListUniqueID;
import libreria.MyConstants;
import libreria.ParametrosActualizarElemento;
import libreria.ParametrosListaBBDD;

import utils.MessageUtils;
import utils.ValidationUtils;
import view.acierto.AciertoActualizar;
import view.error.ErrorActualizar;
import view.error.ErrorModificacionCampo;

import controller.ConsultaController;
import controller.VerModificarController;


public class ModificarElementoEspecifico extends JFrame implements PropertyChangeListener, ModificarInterface{
	
		
	private JTextField tfSubframes;
	private JTextField tfPalabras;
	private JButton btnGuardar;
	private JButton btnCancelar;

	private JTextField tfNombreElemento;
	private JTextField tfBits;
	private JTextField tfNumeroDeAtributo;
	

	
	final JCheckBox chbInteresting;
	private int intNAtributos;
	private int oldIntNAtributos;

	List<JTextField> listTextField = new ArrayList<JTextField>();
	List<JComboBox> listJComboBox = new ArrayList<JComboBox>();
	List<ContenedorVisualAtributoElemento> listaContenedorVisualAtributoElemento = new ArrayList<ContenedorVisualAtributoElemento>();
	
	//String[] typeStrings = {"null","DATE","INT","VARCHAR(45)","VARCHAR(600)"};
	String[] typeStrings = MyConstants.ArrayConstant.comboBoxTipoElementoEspecifico.value();
	
	
	private JPanel panel;
	private JPanel panelAtributos;
	
	int j;
	int interesting;//Habilita el boton Añadir, para ser presionado
	boolean blInteresting;
	
	int nAtributos;
	int flag;
	
	private JTextField tfIdModelo;
	private JTextField tfIdSerie;
	private JTextField tfIdSistemaEspecifico;
	private JTextField tfIdElementoSistema;
	private JTextField tfIdFabricante;
	
	private ElementosSistemaEspecifico miObjetoElementosSistema;
	private VerModificarController miVerModificarController;
	private ConsultaController miConsultaController;
	
	private UUID miIdElementoEspecifico ;//Identificador unico de cada objeto
    private UUID idVerModificarSistemaEspecifico ;    
    
	private int cbIdFabricante ;
    JComboBox cbFabricante;
    private ElementoComboBox elementoCbFabricante;
	
    private int cbIdModelo ;
    JComboBox cbModelo;
    private ElementoComboBox elementoCbModelo;	
	
    private int cbIdSerie ;
    JComboBox cbSerie;
    private ElementoComboBox elementoCbSerie;
    
    private int cbIdSistemaEspecifico ;
    JComboBox cbSistemaEspecifico;
    private ElementoComboBox elementoCbSistemaEspecifico;
    private ListUniqueID listUniqueIdVerModificarElementoEspecifico;
    
    public static final String PROP_ACTUALIZAR_ELEMENTO_ESPECIFICO = "actualizarElementoEspecifico";
    public static final String PROP_LISTAR_ESTRUCTURA_ATRIBUTO = "listarEstructuraAtributo";
	
    private List <Object> listaAtributoElementoActualizar = new ArrayList<Object>();
    private List <Object> listaVacia = new ArrayList<Object>();
	
	
	public ModificarElementoEspecifico(ElementosSistemaEspecifico objetoElementosSistema, VerModificarController verModificarcontroller, 
									   ConsultaController consultaController, UUID idVMSistemaEspecifico, UUID idModificarElementoEspecifico, 
									   JComboBox cbFabricante, JComboBox cbModelo, JComboBox cbSerie, 
									   JComboBox cbSistemaEspecifico, ListUniqueID listUniqueIdVerModificarElementoEspecifico) {
    	
		miObjetoElementosSistema = objetoElementosSistema;
		miVerModificarController = verModificarcontroller;
		miConsultaController = consultaController;
		idVerModificarSistemaEspecifico = idVMSistemaEspecifico;
		miIdElementoEspecifico = idModificarElementoEspecifico; 
		this.cbFabricante = cbFabricante;
		this.cbModelo = cbModelo;
		this.cbSerie = cbSerie;
		this.cbSistemaEspecifico = cbSistemaEspecifico;
		this.listUniqueIdVerModificarElementoEspecifico = listUniqueIdVerModificarElementoEspecifico;
		setTitle("Modificar Elemento del Sistema Espec\u00EDfico");		

		panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(500,1500));//Indica el tamaño preferido del panel, asi podemos usar el scrollpanel, fijando este valor.
															
		
		tfIdElementoSistema = new JTextField();
		tfIdElementoSistema.setEditable(false);
		tfIdElementoSistema.setBackground(UIManager.getColor("Button.background"));
		tfIdElementoSistema.setBounds(245, 35, 235, 20);
		panel.add(tfIdElementoSistema);
		tfIdElementoSistema.setColumns(10);
		tfIdElementoSistema.setText(Integer.toString(miObjetoElementosSistema.getIdElementosSistemaEspecifico()));
		
		tfIdSistemaEspecifico = new JTextField();
		tfIdSistemaEspecifico.setEditable(false);
		tfIdSistemaEspecifico.setBackground(UIManager.getColor("Button.background"));
		tfIdSistemaEspecifico.setBounds(245, 73, 235, 20);
		panel.add(tfIdSistemaEspecifico);
		tfIdSistemaEspecifico.setColumns(10);
		tfIdSistemaEspecifico.setText(Integer.toString(miObjetoElementosSistema.getIdSistemaEspecifico()));
		
		tfIdSerie = new JTextField();
		tfIdSerie.setEditable(false);
		tfIdSerie.setBackground(UIManager.getColor("Button.background"));
		tfIdSerie.setBounds(245, 116, 235, 20);
		panel.add(tfIdSerie);
		tfIdSerie.setColumns(10);
		tfIdSerie.setText(Integer.toString(miObjetoElementosSistema.getIdSerie()));
		
		tfIdModelo = new JTextField();
		tfIdModelo.setEditable(false);
		tfIdModelo.setBackground(UIManager.getColor("Button.background"));
		tfIdModelo.setBounds(245, 156, 235, 20);
		panel.add(tfIdModelo);
		tfIdModelo.setColumns(10);
		tfIdModelo.setText(Integer.toString(miObjetoElementosSistema.getIdModelo()));
		
		tfIdFabricante = new JTextField();
		tfIdFabricante.setEditable(false);
		tfIdFabricante.setBackground(UIManager.getColor("Button.background"));
		tfIdFabricante.setColumns(10);
		tfIdFabricante.setBounds(245, 193, 235, 20);
		panel.add(tfIdFabricante);
		tfIdFabricante.setText(Integer.toString(miObjetoElementosSistema.getIdFabricante()));
		
		JLabel lblIdElementoSistema = new JLabel("IdElementoSistema");
		lblIdElementoSistema.setBounds(41, 38, 130, 14);
		panel.add(lblIdElementoSistema);
		
		JLabel lbIdSistemaEspecifico = new JLabel("IdSistemaEspecifico");
		lbIdSistemaEspecifico.setBounds(41, 76, 130, 14);
		panel.add(lbIdSistemaEspecifico);
		
		JLabel lbIdSerie = new JLabel("IdSerie");
		lbIdSerie.setBounds(41, 119, 130, 14);
		panel.add(lbIdSerie);
		
		JLabel lbIdModelo = new JLabel("IdModelo");
		lbIdModelo.setBounds(41, 159, 177, 14);
		panel.add(lbIdModelo);
		
		JLabel lbIdFabricante = new JLabel("IdFabricante");
		lbIdFabricante.setBounds(41, 195, 177, 14);
		panel.add(lbIdFabricante);

		
		JLabel lbNombreElemento = new JLabel("Nombre Elemento");
		lbNombreElemento.setBounds(43, 231, 111, 14);
		panel.add(lbNombreElemento);
		
		JLabel lblInteresting = new JLabel("Interesting");
		lblInteresting.setBounds(41, 261, 88, 14);
		panel.add(lblInteresting);
	
		JLabel lblAtributosFijos = new JLabel("Atributos Fijos");
		lblAtributosFijos.setBounds(41, 297, 147, 14);
		panel.add(lblAtributosFijos);
		
		JLabel lblSubframes = new JLabel("Subframes");
		lblSubframes.setBounds(41, 322, 119, 14);
		panel.add(lblSubframes);
		
		JLabel lfPalabras = new JLabel("Palabras");
		lfPalabras.setBounds(41, 347, 119, 14);
		panel.add(lfPalabras);
		
		JLabel lblBits = new JLabel("Bits");
		lblBits.setBounds(41, 378, 119, 14);
		panel.add(lblBits);
		
		JLabel lblNumeroDeAtributos = new JLabel("N\u00FAmero de Atributos");
		lblNumeroDeAtributos.setBounds(41, 425, 119, 14);
		panel.add(lblNumeroDeAtributos);
				
		tfNombreElemento = new JTextField();
		tfNombreElemento.setBounds(245, 228, 235, 20);
		panel.add(tfNombreElemento);
		tfNombreElemento.setColumns(10);
		tfNombreElemento.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		tfNombreElemento.setText(miObjetoElementosSistema.getNombreElemento());
				
		tfSubframes = new JTextField();
		tfSubframes.setBounds(186, 316, 154, 20);
		panel.add(tfSubframes);
		tfSubframes.setColumns(10);
		tfSubframes.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		tfSubframes.setText(miObjetoElementosSistema.getSubFrames());
		
		tfPalabras = new JTextField();
		tfPalabras.setBounds(186, 347, 154, 20);
		tfPalabras.setColumns(10);
		panel.add(tfPalabras);
		tfPalabras.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		tfPalabras.setText(miObjetoElementosSistema.getPalabras());
		
		tfBits = new JTextField();
		tfBits.setBounds(186, 378, 154, 20);
		tfBits.setColumns(10);
		panel.add(tfBits);
		tfBits.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		tfBits.setText(miObjetoElementosSistema.getBits());
				
		tfNumeroDeAtributo = new JTextField();		
		tfNumeroDeAtributo.setBounds(186, 425, 64, 20);
		tfNumeroDeAtributo.setColumns(10);
		panel.add(tfNumeroDeAtributo);
		intNAtributos = miObjetoElementosSistema.getNAtributos();
		tfNumeroDeAtributo.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		tfNumeroDeAtributo.setText(Integer.toString(miObjetoElementosSistema.getNAtributos()));
		nAtributos = miObjetoElementosSistema.getNAtributos();	
			
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 286, 588, 4);
		panel.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 410, 588, 4);
		panel.add(separator_1);
		
		JLabel lblAtributosEspecificos = new JLabel("Estructura de los Atributos Espec\u00EDficos del Elemento");
		lblAtributosEspecificos.setBounds(41, 474, 313, 14);
		panel.add(lblAtributosEspecificos);
		
		JScrollPane scrollPane = new JScrollPane(panel);		
		panelAtributos = new JPanel();
		panelAtributos.setBounds(0, 539, 500, 3430);
		panel.add(panelAtributos);
		panelAtributos.setLayout(null);		
		getContentPane().add(scrollPane,BorderLayout.CENTER);
		JLabel lblNombreDeAtributo = new JLabel("Nombre de Atributo");
		lblNombreDeAtributo.setBounds(41, 514, 119, 14);
		panel.add(lblNombreDeAtributo);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(307, 514, 119, 14);
		panel.add(lblTipo);	
		
		JButton btnAniadir = new JButton("A\u00F1adir");
		btnAniadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (interesting == 1) {
				do_btnAniadir_actionPerformed(arg0);
				
				}
			}
		});
		btnAniadir.setBounds(291, 424, 89, 23);
		panel.add(btnAniadir);	
		
		chbInteresting = new JCheckBox("");
		//Revisar este codigo
		chbInteresting.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(chbInteresting.isSelected()) {
					interesting =1;
	                intNAtributos = miObjetoElementosSistema.getNAtributos();
	                tfNumeroDeAtributo.setEnabled(true);
	                if(intNAtributos>0){
	                	tfNumeroDeAtributo.setText(Integer.toString(intNAtributos)); 
	                    pintarAtributos(intNAtributos,listaAtributoElementoActualizar);
	                  } 
                } else {
                    interesting = 0;
                    intNAtributos =0; 
                    tfNumeroDeAtributo.setText("0");  
                    tfNumeroDeAtributo.setEnabled(false);
                    pintarAtributos(intNAtributos,listaVacia);          
                }
			}
		});
		
		chbInteresting.setBounds(245, 261, 95, 22);
		panel.add(chbInteresting);		
		blInteresting = miObjetoElementosSistema.getInteresting();		
		if (blInteresting)			
			chbInteresting.doClick();
		
		setVisible(true);  
		pack();
		setSize(600, 700);
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
				pintarAtributos( intNAtributos, listaAtributoElementoActualizar);					
			}else {
				mensaje = "La entrada Número de Atributos debe ser mayor que 0 y menor que 31";
				ErrorModificacionCampo errorModificarNAtributos= new ErrorModificacionCampo(mensaje);				
			}			
		} 
		catch (NumberFormatException e) {
		   System.err.println("No se puede convertir a número");
		   e.printStackTrace();		   
		   mensaje = "La entrada Número de Atributos es errónea";
		   ErrorModificacionCampo errorAltaNAtributos= new ErrorModificacionCampo(mensaje);		   
		}		
	}
	
	/* En las listas guardo los objetos asi cuando los necesite los obtengo por medio
	 * de las listas  */
	private void pintarAtributos(int intNAtributos, List<Object> listaAtributoElementoActualizar){
	
		Iterator it = listaAtributoElementoActualizar.iterator();			
		panelAtributos.removeAll();
		listaContenedorVisualAtributoElemento = new ArrayList<ContenedorVisualAtributoElemento>();
		j=0;//Es cero porque se toman las coordenadas del panelAtributos 		

		for (int i=0 ;i < intNAtributos;i++ ){			
			j=j+31;			
			JTextField tfNombreAtributo = new JTextField();
			tfNombreAtributo.setBounds(41,j , 247, 20);//64, 20);
			tfNombreAtributo.setColumns(10);
			panelAtributos.add(tfNombreAtributo);
			tfNombreAtributo.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
			final JComboBox cbTipo = new JComboBox(typeStrings);
			cbTipo.setBounds (314,j, 111, 20);			
			panelAtributos.add(cbTipo);	
			
			//copio los atributos a la listaAtributoElementoActualizar
			if (it.hasNext()){
				AtributoElemento atributoElemento = (AtributoElemento) it.next();	
				tfNombreAtributo.setText(atributoElemento.getNombreAtributo());
				cbTipo.setSelectedItem(atributoElemento.getTipoAtributo());	
			}
			boolean visible = true;
			//Se añade a esta lista el nombre y el cbTipo de cada atributo
			final ContenedorVisualAtributoElemento  contenedorAtributoEspecifico = new ContenedorVisualAtributoElemento(tfNombreAtributo, cbTipo, visible);
			listaContenedorVisualAtributoElemento.add(contenedorAtributoEspecifico);
			
			cbTipo.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent arg0) {		
					//Evitamos una exepcion si no hay objetos en el cbTipo.	
					//çSe puede borrar????????????????'
					if (cbTipo.getItemCount() > 0) {	
				    	//Asi añadimos el strCbTipo directamente en el contenedorAtributoEspecifico
						String aux = (String)cbTipo.getModel().getSelectedItem();						
						contenedorAtributoEspecifico.setStrCbTipo(aux);
					}													
				}
			});
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
		
		int idElementosSistema = Integer.parseInt(tfIdElementoSistema.getText());
		int idSistemaEspecifico = Integer.parseInt(tfIdSistemaEspecifico.getText());
		int idSerie = Integer.parseInt(tfIdSerie.getText());
		int idModelo = Integer.parseInt(tfIdModelo.getText());
		int idFabricante = Integer.parseInt(tfIdFabricante.getText());
		String strNombreElemento = tfNombreElemento.getText();
		boolean blInteresting = chbInteresting.isSelected();
		String strSubFrames = tfSubframes.getText();
		String strPalabras = tfPalabras.getText();
		String strBits = tfBits.getText();		
		Iterator it = listaAtributoElementoActualizar.iterator();
		Iterator it1 = listaContenedorVisualAtributoElemento.iterator();
		List <Object> nuevaListaAtributoElementoActualizar = new ArrayList<Object>();
		List <Object> nuevaListaAtributoElementoInsertar = new ArrayList<Object>();
		
		if (ValidationUtils.validationInterestingNAtributos(blInteresting,intNAtributos,MyConstants.StringConstant.VER_MODIFICAR.value())){
			AtributoElemento atributoElemento;			
			while (it1.hasNext()){				
				ContenedorVisualAtributoElemento contenedorVisualAtributoElemento =  (ContenedorVisualAtributoElemento) it1.next();				
				if (it.hasNext()){
					atributoElemento = (AtributoElemento) it.next();
					
					atributoElemento.setNombreAtributo(contenedorVisualAtributoElemento.getNombreAtributo().getText());
					atributoElemento.setTipoAtributo((String)contenedorVisualAtributoElemento.getCbTipo().getSelectedItem());
					atributoElemento.setVisible(contenedorVisualAtributoElemento.isVisible());
					nuevaListaAtributoElementoActualizar.add(atributoElemento);
				}else{				
					atributoElemento = new AtributoElemento(0, idElementosSistema, idSistemaEspecifico, idSerie, idModelo, idFabricante, " ", " ", true);
					
					atributoElemento.setNombreAtributo(contenedorVisualAtributoElemento.getNombreAtributo().getText());
					atributoElemento.setTipoAtributo((String)contenedorVisualAtributoElemento.getCbTipo().getSelectedItem());
					atributoElemento.setVisible(contenedorVisualAtributoElemento.isVisible());
					nuevaListaAtributoElementoInsertar.add(atributoElemento);
				}
			}
			//Atributos que no serán visibles para este elemento
			while (it.hasNext()){
				atributoElemento = (AtributoElemento) it.next();				
				atributoElemento.setVisible(false);
				nuevaListaAtributoElementoActualizar.add(atributoElemento);				
			}
			
			miObjetoElementosSistema = new ElementosSistemaEspecifico(idElementosSistema, idSistemaEspecifico, idSerie, idModelo, idFabricante, strNombreElemento, blInteresting, strSubFrames, strPalabras, strBits, intNAtributos);
			miVerModificarController.modificarElementoSistema(miObjetoElementosSistema,  miIdElementoEspecifico, nuevaListaAtributoElementoActualizar, nuevaListaAtributoElementoInsertar);
			
			//Para actualizar la tabla de VerModificarElementoEspecifico
			//Se obtiene el objeto seleccionado dentro del modelo del combobox que se nos paso de VerModificarXXX
			elementoCbFabricante = (ElementoComboBox)cbFabricante.getModel().getSelectedItem();
			cbIdFabricante = elementoCbFabricante.getIdElemento();		
			elementoCbModelo = (ElementoComboBox)cbModelo.getModel().getSelectedItem();
			cbIdModelo = elementoCbModelo.getIdElemento();		
			elementoCbSerie = (ElementoComboBox)cbSerie.getModel().getSelectedItem();
			cbIdSerie = elementoCbSerie.getIdElemento();		
			elementoCbSistemaEspecifico = (ElementoComboBox)cbSistemaEspecifico.getModel().getSelectedItem();
			cbIdSistemaEspecifico = elementoCbSistemaEspecifico.getIdElemento();		
			//Actualizo la lista en VerModificarManual, listando todos los fabricantes
			miConsultaController.listaElementoEspecifico(cbIdFabricante, cbIdModelo, cbIdSerie, cbIdSistemaEspecifico, idVerModificarSistemaEspecifico, true); 
			
		}
				
	}
	
	
	public void do_btnCancelar_actionPerformed(ActionEvent e) {
		dispose();
		boolean uno = listUniqueIdVerModificarElementoEspecifico.getUniqueListID().remove(miObjetoElementosSistema.getIdElementosSistemaEspecifico());
        miVerModificarController.getActualizarElemento().removePropertyChangeListener(PROP_ACTUALIZAR_ELEMENTO_ESPECIFICO, this);
	}
	
	public void setAtributoElemento(AtributoElemento atributoElemento, ContenedorVisualAtributoElemento contenedorVisualAtributoElemento, List <Object> listaAtributoElemento){
		
		//ContenedorVisualAtributoElemento contenedorVisualAtributoElemento =  (ContenedorVisualAtributoElemento) it.next();
		atributoElemento.setNombreAtributo(contenedorVisualAtributoElemento.getNombreAtributo().getText());
		atributoElemento.setTipoAtributo((String)contenedorVisualAtributoElemento.getCbTipo().getSelectedItem());
		atributoElemento.setVisible(contenedorVisualAtributoElemento.isVisible());
		listaAtributoElemento.add(atributoElemento);
	}	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();		
		ParametrosActualizarElemento parametrosActualizarElemento;
		ParametrosListaBBDD parametrosListaBBDD;
		if (PROP_ACTUALIZAR_ELEMENTO_ESPECIFICO.equals(propertyName)){			 
			parametrosActualizarElemento = (ParametrosActualizarElemento) evt.getNewValue();
			//Se comprueba el id del objeto Modelo
			if (parametrosActualizarElemento.getIdElemento().equals(miIdElementoEspecifico)){
				MessageUtils.mensajeActualizarElemento(parametrosActualizarElemento, MyConstants.StringConstant.ELEMENTOS.value(), this);
			}				
		}else if (PROP_LISTAR_ESTRUCTURA_ATRIBUTO.equals(propertyName)){		 
			parametrosListaBBDD = (ParametrosListaBBDD) evt.getNewValue();			
			//List<Object> dataEstructuraAtributosBBDD ;
			//Se comprueba el id del objeto Estructura Frame
			if (parametrosListaBBDD.getIdElemento().equals(miIdElementoEspecifico)){
				// hace lo necesario para refrescar la ventana con los atributos de este elemento
				listaAtributoElementoActualizar  = (ArrayList<Object>) parametrosListaBBDD.getDataListaBBDD();
				pintarAtributos(intNAtributos,listaAtributoElementoActualizar);				
			}				
		}
	}
	
	
	/*
	 * Cambiar el id de este objeto.
	 * @param idEstructuraFrame, es una UUID (identificador unico)
	 *  */

	public void setIdElementoEspecifico(UUID idElementoEspecifico){
		
		miIdElementoEspecifico = idElementoEspecifico;
	}


	/*
	 * Devuelve el id de este objeto.
	 * 
	 *  */
	public UUID getIdElementoEspecifico(){
		
		return miIdElementoEspecifico;
	}
	
}
