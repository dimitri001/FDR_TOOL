package view.modificar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

import utils.MessageUtils;
import view.acierto.AciertoActualizar;
import view.error.ErrorActualizar;

import controller.ConsultaController;
import controller.VerModificarController;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.UUID;

import libreria.ElementoComboBox;
import libreria.JTextFieldLimit;
import libreria.ListUniqueID;
import libreria.MyConstants;
import libreria.MyDefaultStyledDocument;
import libreria.ParametrosActualizarElemento;
import libreria.SistemaEspecifico;
import javax.swing.UIManager;

public class ModificarSistemaEspecifico extends JFrame implements PropertyChangeListener, ModificarInterface{
		
	
	private JTextField tfIdSistemaEspecifico;
	private JTextField tfIdSerie;
	private JTextField tfIdModelo;
	private JTextField tfIdFabricante;
	
	private JTextField tfNombreSistemaEspecifico;
	private JTextPane tpDescripcion ;
	
	private JButton btnGuardar;
	private JButton btnCancelar;
	
	private SistemaEspecifico miObjetoSistemaEspecifico;
	private VerModificarController miVerModificarController;
	private ConsultaController miConsultaController;
	
	private UUID miIdSistemaEspecifico = UUID.randomUUID();;//Identificador unico de cada objeto
    private UUID idVerModificarSistemaEspecifico;
    
    private int cbIdFabricante ;
    JComboBox cbFabricante;
    private ElementoComboBox elementoCbFabricante;
	
    private int cbIdModelo ;
    JComboBox cbModelo;
    private ElementoComboBox elementoCbModelo;	
	
    private int cbIdSerie ;
    JComboBox cbSerie;
    private ElementoComboBox elementoCbSerie;
    private ListUniqueID listUniqueIdVerModificarSistemaEspecifico;
    
    public static final String PROP_ACTUALIZAR_SISTEMA_ESPECIFICO = "actualizarSistemaEspecifico";
	
	
	public ModificarSistemaEspecifico(SistemaEspecifico objetoSistemaEspecifico, VerModificarController verModificarcontroller, 
									  ConsultaController consultaController, UUID idVMSistemaEspecifico, 
									  JComboBox cbFabricante, JComboBox cbModelo, JComboBox cbSerie, 
									  ListUniqueID listUniqueIdVerModificarSistemaEspecifico) {
    	
    	miObjetoSistemaEspecifico = objetoSistemaEspecifico;
		miVerModificarController = verModificarcontroller;
		miConsultaController = consultaController;
		idVerModificarSistemaEspecifico = idVMSistemaEspecifico;
		this.cbFabricante = cbFabricante;
		this.cbModelo = cbModelo;
		this.cbSerie = cbSerie;
		this.listUniqueIdVerModificarSistemaEspecifico = listUniqueIdVerModificarSistemaEspecifico;
		
		setTitle("Modificar Sistema Especifico");
		getContentPane().setLayout(null);
		
		JLabel lbNombreSistemaEspecif = new JLabel("Nombre Sistema Espec\u00EDfico ");
		lbNombreSistemaEspecif.setBounds(41, 208, 178, 14);
		getContentPane().add(lbNombreSistemaEspecif);
		
		JLabel lblNewLabel = new JLabel("Descripci\u00F3n");
		lblNewLabel.setBounds(41, 242, 113, 14);
		getContentPane().add(lblNewLabel);
		
		tfNombreSistemaEspecifico = new JTextField();
		tfNombreSistemaEspecifico.setBounds(245, 205, 235, 20);
		getContentPane().add(tfNombreSistemaEspecifico);
		tfNombreSistemaEspecifico.setColumns(10);
		tfNombreSistemaEspecifico.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		tfNombreSistemaEspecifico.setText(miObjetoSistemaEspecifico.getNombre());
		
		tpDescripcion = new JTextPane (new MyDefaultStyledDocument());	
		tpDescripcion.setText(miObjetoSistemaEspecifico.getDescripcion());
		JScrollPane scrDecripcion = new JScrollPane(tpDescripcion);
		scrDecripcion.setBounds(245, 242, 235, 63);
		getContentPane().add(scrDecripcion);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnGuardar_actionPerformed(arg0);
			}
		});
		btnGuardar.setBounds(94, 335, 141, 23);
		getContentPane().add(btnGuardar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		btnCancelar.setBounds(329, 335, 141, 23);
		getContentPane().add(btnCancelar);
		
		JLabel lbIdSistemaEspecifico = new JLabel("IdSistemaEspecifico");
		lbIdSistemaEspecifico.setHorizontalAlignment(SwingConstants.LEFT);
		lbIdSistemaEspecifico.setBounds(41, 28, 128, 14);
		getContentPane().add(lbIdSistemaEspecifico);
		
		tfIdSistemaEspecifico = new JTextField();
		tfIdSistemaEspecifico.setEditable(false);
		tfIdSistemaEspecifico.setColumns(10);
		tfIdSistemaEspecifico.setBackground(UIManager.getColor("Button.background"));
		tfIdSistemaEspecifico.setBounds(245, 28, 235, 20);
		getContentPane().add(tfIdSistemaEspecifico);
		tfIdSistemaEspecifico.setText(Integer.toString(miObjetoSistemaEspecifico.getIdSistemaEspecifico()));
		
		JLabel lbIdSerie = new JLabel("IdSerie");
		lbIdSerie.setBounds(41, 74, 128, 14);
		getContentPane().add(lbIdSerie);
		
		tfIdSerie = new JTextField();
		tfIdSerie.setEditable(false);
		tfIdSerie.setColumns(10);
		tfIdSerie.setBackground(UIManager.getColor("Button.background"));
		tfIdSerie.setBounds(245, 74, 235, 20);
		getContentPane().add(tfIdSerie);
		tfIdSerie.setText(Integer.toString(miObjetoSistemaEspecifico.getIdSerie()));
		
		JLabel lbIdModelo = new JLabel("IdModelo");
		lbIdModelo.setBounds(41, 120, 128, 14);
		getContentPane().add(lbIdModelo);
		
		tfIdModelo = new JTextField();
		tfIdModelo.setEditable(false);
		tfIdModelo.setColumns(10);
		tfIdModelo.setBackground(UIManager.getColor("Button.background"));
		tfIdModelo.setBounds(245, 120, 235, 20);
		getContentPane().add(tfIdModelo);
		tfIdModelo.setText(Integer.toString(miObjetoSistemaEspecifico.getIdModelo()));
		
		JLabel lbIdFabricante = new JLabel("IdFabricante ");
		lbIdFabricante.setBounds(41, 165, 128, 14);
		getContentPane().add(lbIdFabricante);
		
		tfIdFabricante = new JTextField();
		tfIdFabricante.setEditable(false);
		tfIdFabricante.setColumns(10);
		tfIdFabricante.setBackground(UIManager.getColor("Button.background"));
		tfIdFabricante.setBounds(245, 165, 235, 20);
		getContentPane().add(tfIdFabricante);
		tfIdFabricante.setText(Integer.toString(miObjetoSistemaEspecifico.getIdFabricante()));
		
		setVisible(true);  
		setSize(574, 418);
	
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
		
		int intIdSistemaEspecifico = miObjetoSistemaEspecifico.getIdSistemaEspecifico();
		int intIdSerie = miObjetoSistemaEspecifico.getIdSerie();
		int intIdModelo = miObjetoSistemaEspecifico.getIdModelo();
		int intIdFabricante = miObjetoSistemaEspecifico.getIdFabricante();		
		String strDescripcion = tpDescripcion.getText();
		String strNombre = tfNombreSistemaEspecifico.getText();
				
		miObjetoSistemaEspecifico = new SistemaEspecifico(intIdSistemaEspecifico, intIdSerie, intIdModelo, intIdFabricante, strNombre, strDescripcion);
		miVerModificarController.modificarSistemaEspecifico(miObjetoSistemaEspecifico, miIdSistemaEspecifico);
		
		//Se obtiene el objeto seleccionado dentro del modelo del combobox que se nos paso de VerModificarXXX
		elementoCbFabricante = (ElementoComboBox)cbFabricante.getModel().getSelectedItem();
		cbIdFabricante = elementoCbFabricante.getIdElemento();
		
		elementoCbModelo = (ElementoComboBox)cbModelo.getModel().getSelectedItem();
		cbIdModelo = elementoCbModelo.getIdElemento();
		
		elementoCbSerie = (ElementoComboBox)cbSerie.getModel().getSelectedItem();
		cbIdSerie = elementoCbSerie.getIdElemento();
		
		//Actualizo la lista en VerModificarManual, listando todos los fabricantes
		miConsultaController.listaSistemaEspecifico(cbIdFabricante, cbIdModelo, cbIdSerie, idVerModificarSistemaEspecifico, true); 
	
	}
	
	public void do_btnCancelar_actionPerformed(ActionEvent e) {
		dispose();	
		boolean uno = listUniqueIdVerModificarSistemaEspecifico.getUniqueListID().remove(miObjetoSistemaEspecifico.getIdSistemaEspecifico());
        miVerModificarController.getActualizarElemento().removePropertyChangeListener(PROP_ACTUALIZAR_SISTEMA_ESPECIFICO, this);
		
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
		String propertyName = evt.getPropertyName();
		
		ParametrosActualizarElemento parametrosActualizarElemento;
	
		
		if (PROP_ACTUALIZAR_SISTEMA_ESPECIFICO.equals(propertyName)){
			 
			parametrosActualizarElemento = (ParametrosActualizarElemento) evt.getNewValue();

			//Se comprueba el id del objeto Modelo
			if (parametrosActualizarElemento.getIdElemento().equals(miIdSistemaEspecifico)){
				MessageUtils.mensajeActualizarElemento(parametrosActualizarElemento, MyConstants.StringConstant.SISTEMA.value(), this);
			}	
			
		}
		
		
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
