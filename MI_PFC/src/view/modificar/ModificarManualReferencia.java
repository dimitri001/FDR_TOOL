
package view.modificar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.UUID;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JButton;

import controller.ConsultaController;
import controller.VerModificarController;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.UIManager;

import utils.MessageUtils;
import view.acierto.AciertoActualizar;
import view.error.ErrorActualizar;

import java.awt.SystemColor;

import libreria.ElementoComboBox;
import libreria.JTextFieldLimit;
import libreria.ListUniqueID;
import libreria.Manual;
import libreria.MyConstants;
import libreria.MyDefaultStyledDocument;
import libreria.ParametrosActualizarElemento;

public class ModificarManualReferencia extends JFrame implements PropertyChangeListener, ModificarInterface{
	
	private JTextField tfIdManual;
	private JTextField tfIdModelo;
	private JTextField tfIdFabricante;
	
	private JTextPane tpDescripcion;
	private JTextField tfRutaFichero;
	private JTextField tfIdSerie;
    
	
	private Manual miObjetoManual;
	private VerModificarController miVerModificarController;
	private ConsultaController miConsultaController;
	
	private UUID miIdManual = UUID.randomUUID();;//Identificador unico de cada objeto
    private UUID idVerModificarManual;
    
    private int cbIdFabricante ;
    JComboBox cbFabricante;
    private ElementoComboBox elementoCbFabricante;
	
    private int cbIdModelo ;
    JComboBox cbModelo;
    private ElementoComboBox elementoCbModelo;	
	
    private int cbIdSerie ;
    JComboBox cbSerie;
    private ElementoComboBox elementoCbSerie;
    private ListUniqueID listUniqueIdVerModificarManual;
    
    public static final String PROP_ACTUALIZAR_MANUAL = "actualizarManual";
    
    public ModificarManualReferencia(Manual objetoManual, VerModificarController verModificarcontroller, ConsultaController consultaController, 
    		UUID idVMManual, JComboBox cbFabricante, JComboBox cbModelo, JComboBox cbSerie, ListUniqueID listUniqueIdVerModificarManual) {
    	
    	miObjetoManual = objetoManual;
		miVerModificarController = verModificarcontroller;
		miConsultaController = consultaController;
		idVerModificarManual = idVMManual;
		this.cbFabricante = cbFabricante;
		this.cbModelo = cbModelo;
		this.cbSerie = cbSerie;
		this.listUniqueIdVerModificarManual = listUniqueIdVerModificarManual;
		
		setTitle("Modificar Manual de Referencia");
		getContentPane().setLayout(null);
		
		JLabel lbFabricante = new JLabel("IdFabricante");
		lbFabricante.setBounds(43, 198, 102, 14);
		getContentPane().add(lbFabricante);
		
		JLabel lbIdModelo = new JLabel("IdModelo");
		lbIdModelo.setBounds(43, 144, 102, 14);
		getContentPane().add(lbIdModelo);
		
		JLabel lbIdSerie = new JLabel("IdSerie");
		lbIdSerie.setBounds(43, 93, 102, 14);
		getContentPane().add(lbIdSerie);
		
		JLabel lbIdManual = new JLabel("IdManual");
		lbIdManual.setBounds(43, 41, 102, 14);
		getContentPane().add(lbIdManual);
		
		JLabel lbDescripcion = new JLabel("Descripci\u00F3n");
		lbDescripcion.setBounds(43, 247, 102, 14);
		getContentPane().add(lbDescripcion);
		
		JLabel lbRutaFicheroPdf = new JLabel("Ruta Fichero PDF");
		lbRutaFicheroPdf.setBounds(43, 328, 102, 14);
		getContentPane().add(lbRutaFicheroPdf);
		
		tpDescripcion = new JTextPane (new MyDefaultStyledDocument());
		tpDescripcion.setText(miObjetoManual.getDescripcion());
		JScrollPane scrDecripcion = new JScrollPane(tpDescripcion);
		scrDecripcion.setBounds(203, 247, 235, 63);
		getContentPane().add(scrDecripcion);
		
		tfRutaFichero = new JTextField();
		tfRutaFichero.setBounds(203, 325, 235, 20);
		getContentPane().add(tfRutaFichero);
		tfRutaFichero.setColumns(10);
		tfRutaFichero.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		tfRutaFichero.setText(miObjetoManual.getRutaFicheroPdf());
		
		tfIdSerie = new JTextField();
		tfIdSerie.setEditable(false);
		tfIdSerie.setColumns(10);
		tfIdSerie.setBackground(UIManager.getColor("Button.background"));
		tfIdSerie.setBounds(203, 90, 235, 20);
		getContentPane().add(tfIdSerie);
		tfIdSerie.setText(Integer.toString(miObjetoManual.getIdSerie()));
		
		tfIdManual = new JTextField();
		tfIdManual.setEditable(false);
		tfIdManual.setColumns(10);
		tfIdManual.setBackground(UIManager.getColor("Button.background"));
		tfIdManual.setBounds(203, 38, 235, 20);
		getContentPane().add(tfIdManual);
		tfIdManual.setText(Integer.toString(miObjetoManual.getIdManual()));
		
		tfIdModelo = new JTextField();
		tfIdModelo.setEditable(false);
		tfIdModelo.setBackground(UIManager.getColor("Button.background"));
		tfIdModelo.setColumns(10);
		tfIdModelo.setBounds(203, 141, 235, 20);
		getContentPane().add(tfIdModelo);
		tfIdModelo.setText(Integer.toString(miObjetoManual.getIdModelo()));
		
		tfIdFabricante = new JTextField();
		tfIdFabricante.setEditable(false);
		tfIdFabricante.setBackground(UIManager.getColor("Button.background"));
		tfIdFabricante.setColumns(10);
		tfIdFabricante.setBounds(203, 195, 235, 20);
		getContentPane().add(tfIdFabricante);
		tfIdFabricante.setText(Integer.toString(miObjetoManual.getIdFabricante()));
		
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_btnGuardar_actionPerformed(e);
			}
		});
		btnGuardar.setBounds(87, 381, 89, 23);
		getContentPane().add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_btnCancelar_actionPerformed(e);
			}
		});
		btnCancelar.setBounds(310, 381, 89, 23);
		getContentPane().add(btnCancelar);
		
		
		setVisible(true);  
		setSize(515, 453);
				
		//Asi al cerrar en la X del JFrame, se invoca a do_btnCerrar_actionPerformed
		//y se cierra correctamente el objeto	
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
					   public void windowClosing(WindowEvent evt) {
						   do_btnCancelar_actionPerformed(null);
					   }
					  });
		
	}


	protected void do_btnGuardar_actionPerformed(ActionEvent e) {
		
		int intIdManual = miObjetoManual.getIdManual();
		int intIdSerie = miObjetoManual.getIdSerie();
		int intIdModelo = miObjetoManual.getIdModelo();
		int intIdFabricante = miObjetoManual.getIdFabricante();		
		String strDescripcion = tpDescripcion.getText();
		String strRutaFichero = tfRutaFichero.getText();
		
		miObjetoManual = new Manual(intIdManual, intIdSerie, intIdModelo,intIdFabricante, strDescripcion, strRutaFichero);
		miVerModificarController.modificarManual(miObjetoManual, miIdManual);
		
		//Se obtiene el objeto seleccionado dentro del modelo del combobox que se nos paso de VerModificarXXX
		elementoCbFabricante = (ElementoComboBox)cbFabricante.getModel().getSelectedItem();
		cbIdFabricante = elementoCbFabricante.getIdElemento();
		
		elementoCbModelo = (ElementoComboBox)cbModelo.getModel().getSelectedItem();
		cbIdModelo = elementoCbModelo.getIdElemento();
		
		elementoCbSerie = (ElementoComboBox)cbSerie.getModel().getSelectedItem();
		cbIdSerie = elementoCbSerie.getIdElemento();
		
		//Actualizo la lista en VerModificarManual, listando todos los fabricantes
		miConsultaController.listaManual(cbIdFabricante, cbIdModelo, cbIdSerie, idVerModificarManual, true); 
		
	
	}
	public void do_btnCancelar_actionPerformed(ActionEvent e) {
		dispose();	
		boolean uno = listUniqueIdVerModificarManual.getUniqueListID().remove(miObjetoManual.getIdManual());		
        miVerModificarController.getActualizarElemento().removePropertyChangeListener(PROP_ACTUALIZAR_MANUAL, this);
		
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		ParametrosActualizarElemento parametrosActualizarElemento;
		if (PROP_ACTUALIZAR_MANUAL.equals(propertyName)){			 
			parametrosActualizarElemento = (ParametrosActualizarElemento) evt.getNewValue();
			//Se comprueba el id del objeto Modelo
			if (parametrosActualizarElemento.getIdElemento().equals(miIdManual)){				
				MessageUtils.mensajeActualizarElemento(parametrosActualizarElemento, MyConstants.StringConstant.MANUAL.value(), this);
			}				
		}
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
