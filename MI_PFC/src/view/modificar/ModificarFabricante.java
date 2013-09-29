package view.modificar;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import controller.ConsultaController;
import controller.VerModificarController;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.UUID;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;

import utils.MessageUtils;
import view.acierto.AciertoActualizar;
import view.error.ErrorActualizar;

import libreria.Fabricante;
import libreria.JTextFieldLimit;
import libreria.ListUniqueID;
import libreria.MyConstants;
import libreria.ParametrosActualizarElemento;
import libreria.ParametrosListaBBDD;

public class ModificarFabricante extends JFrame implements PropertyChangeListener, ModificarInterface{
	
	private JTextField tfIdFabricante;
	private JTextField tfNombre;
	private JTextField tfWeb;
	
	
	private Fabricante miObjetoFabricante;
	private VerModificarController miVerModificarController;
	private ConsultaController miConsultaController;
	
	private UUID miIdFabricante = UUID.randomUUID();;//Identificador unico de cada objeto
    private UUID idVerModificarFabricante;
	public static final String PROP_ACTUALIZAR_FABRICANTE = "actualizarFabricante";
	private ListUniqueID listUniqueIdVerModificarFabricante;
	
	public ModificarFabricante(Fabricante objetoFabricante, VerModificarController verModificarcontroller, 
			ConsultaController consultaController, UUID idVMFabricante, ListUniqueID listUniqueIdVerModificarFabricante) {
		
		miObjetoFabricante = objetoFabricante;
		miVerModificarController = verModificarcontroller;
		miConsultaController = consultaController;
		idVerModificarFabricante = idVMFabricante;
		this.listUniqueIdVerModificarFabricante = listUniqueIdVerModificarFabricante;
		setTitle("Modificar Fabricante");
		getContentPane().setLayout(null);
		
		
		JLabel lbIdFabricante = new JLabel("IdFabricante");
		lbIdFabricante.setBounds(76, 41, 108, 14);
		getContentPane().add(lbIdFabricante);
		
		JLabel lbNombre = new JLabel("Nombre");
		lbNombre.setBounds(76, 92, 46, 14);
		getContentPane().add(lbNombre);
		
		JLabel lbWeb = new JLabel("Web");
		lbWeb.setBounds(76, 155, 46, 14);
		getContentPane().add(lbWeb);
		
		tfIdFabricante = new JTextField();
		tfIdFabricante.setBackground(UIManager.getColor("Button.background"));
		tfIdFabricante.setEditable(false);
		tfIdFabricante.setBounds(190, 38, 235, 17);
		getContentPane().add(tfIdFabricante);
		tfIdFabricante.setColumns(10);
		tfIdFabricante.setText( Integer.toString(miObjetoFabricante.getIdFabricante()) );
						
		tfNombre = new JTextField();
		tfNombre.setBounds(192, 89, 235, 20);
		getContentPane().add(tfNombre);
		tfNombre.setColumns(10);
		tfNombre.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		tfNombre.setText(miObjetoFabricante.getNombre());

		
		tfWeb = new JTextField();
		tfWeb.setBounds(192, 152, 235, 20);
		getContentPane().add(tfWeb);
		tfWeb.setColumns(10);
		tfWeb.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		tfWeb.setText(miObjetoFabricante.getWeb());
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnGuardar_actionPerformed(arg0);
			}
		});
		btnGuardar.setBounds(119, 203, 89, 23);
		getContentPane().add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		btnCancelar.setBounds(327, 203, 89, 23);
		getContentPane().add(btnCancelar);
		
		
		
		setVisible(true);  
		setSize(553, 275);
		
		
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
		
		
		int intIdFabricante = miObjetoFabricante.getIdFabricante();
		String strNombre = tfNombre.getText();
		String strWeb = tfWeb.getText();
		
		miObjetoFabricante = new Fabricante(intIdFabricante, strNombre, strWeb);
		miVerModificarController.modificarFabricante(miObjetoFabricante, miIdFabricante);
		
		//Actualizo la lista en VerModificarFabricante
		miConsultaController.listaFabricante(idVerModificarFabricante, true); //Actualizo la lista
		
		
	}
	
	
	public void do_btnCancelar_actionPerformed(ActionEvent arg0) {		
		dispose();
		boolean uno = listUniqueIdVerModificarFabricante.getUniqueListID().remove(miObjetoFabricante.getIdFabricante());
		miVerModificarController.getActualizarElemento().removePropertyChangeListener(PROP_ACTUALIZAR_FABRICANTE, this);
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		
		ParametrosActualizarElemento parametrosActualizarElemento;
	
		
		if (PROP_ACTUALIZAR_FABRICANTE.equals(propertyName)){
			parametrosActualizarElemento = (ParametrosActualizarElemento) evt.getNewValue();
			//Se comprueba el id del objeto Fabricante
			if (parametrosActualizarElemento.getIdElemento().equals(miIdFabricante)){
				MessageUtils.mensajeActualizarElemento(parametrosActualizarElemento, MyConstants.StringConstant.FABRICANTE.value(), this);
			}
		}
	}	
	
	/*
	 * Cambiar el id de este objeto.
	 * @param idFabricante, es una UUID (identificador unico)
	 *  */
	
	public void setIdFabricante(UUID idFabricante){
		
		miIdFabricante = idFabricante;
	}
	
	
	/*
	 * Devuelve el id de este objeto.
	 * 
	 *  */
	public UUID getIdFabricante(){
		
		return miIdFabricante;
	}
}
