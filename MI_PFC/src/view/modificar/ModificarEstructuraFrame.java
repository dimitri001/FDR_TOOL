
package view.modificar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import java.awt.Color;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.UUID;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.UIManager;

import utils.MessageUtils;
import utils.NumericUtils;
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

import libreria.ElementoComboBox;
import libreria.EstructuraFrame;
import libreria.JTextFieldLimit;
import libreria.ListUniqueID;
import libreria.MutableBoolean;
import libreria.MyConstants;
import libreria.MyDefaultStyledDocument;
import libreria.ParametrosActualizarElemento;

public class ModificarEstructuraFrame extends JFrame implements PropertyChangeListener, ModificarInterface{
	
	private JTextField tfIdEstructuraFrame;	
	private JTextField tfIdSerie;
	private JTextField tfIdModelo;
	private JTextField tfIdFabricante;
	
	private JTextField tfNumeroSubframes;
	private JTextField tfTiempoSubframe;
	private JTextField tfPalabrasSegundo;
	private JTextField tfBitsPalabra;
	private JTextPane tpDescripcion;
	
	
	private EstructuraFrame miObjetoEstructuraFrame;
	private VerModificarController miVerModificarController;
	private ConsultaController miConsultaController;
	
	private UUID miIdEstructuraFrame = UUID.randomUUID();;//Identificador unico de cada objeto
    private UUID idVerModificarEstructuraFrame;
    
    private int cbIdFabricante ;
    JComboBox cbFabricante;
    private ElementoComboBox elementoCbFabricante;
	
    private int cbIdModelo ;
    JComboBox cbModelo;
    private ElementoComboBox elementoCbModelo;	
	
    private int cbIdSerie ;
    JComboBox cbSerie;
    private ElementoComboBox elementoCbSerie;
    private ListUniqueID listUniqueIdVerModificarEstructuraFrame;
    
    public static final String PROP_ACTUALIZAR_ESTRUCTURA_FRAME = "actualizarEstructuraFrame";
	 
	
	public ModificarEstructuraFrame(EstructuraFrame objetoEstructuraFrame, VerModificarController verModificarcontroller, 
									ConsultaController consultaController, UUID idVMEstructuraFrame,
									JComboBox cbFabricante, JComboBox cbModelo, JComboBox cbSerie, 
									ListUniqueID listUniqueIdVerModificarEstructuraFrame) {
    	
    	miObjetoEstructuraFrame = objetoEstructuraFrame;
		miVerModificarController = verModificarcontroller;
		miConsultaController = consultaController;
		idVerModificarEstructuraFrame = idVMEstructuraFrame;
		this.cbFabricante = cbFabricante;
		this.cbModelo = cbModelo;
		this.cbSerie = cbSerie;
		this.listUniqueIdVerModificarEstructuraFrame = listUniqueIdVerModificarEstructuraFrame;
		setTitle("Modificar Estructura de Frame ");
		getContentPane().setLayout(null);
		
		JLabel lbIdFabricante = new JLabel("IdFabricante ");
		lbIdFabricante.setBounds(43, 164, 128, 14);
		getContentPane().add(lbIdFabricante);
		
		JLabel lbIdModelo = new JLabel("IdModelo");
		lbIdModelo.setBounds(43, 119, 128, 14);
		getContentPane().add(lbIdModelo);
		
		JLabel lbIdserie = new JLabel("IdSerie");
		lbIdserie.setBounds(43, 73, 128, 14);
		getContentPane().add(lbIdserie);
		
		JLabel lbIdEstructuraFrame = new JLabel("IdEstructuraFrame");
		lbIdEstructuraFrame.setBounds(43, 27, 128, 14);
		getContentPane().add(lbIdEstructuraFrame);
		
		JLabel lbNumeroDeSubframes = new JLabel("N\u00FAmero de Subframes");
		lbNumeroDeSubframes.setBounds(43, 209, 128, 14);
		getContentPane().add(lbNumeroDeSubframes);
		
		JLabel lbEstructuraDeSubframes = new JLabel("Estructura de Subframes");
		lbEstructuraDeSubframes.setBounds(43, 254, 152, 14);
		getContentPane().add(lbEstructuraDeSubframes);
		
		JLabel lbTiempoDeCada = new JLabel("Tiempo de cada Subframe");
		lbTiempoDeCada.setBounds(53, 279, 155, 14);
		getContentPane().add(lbTiempoDeCada);
		
		JLabel lbPalabrasPorSegundo = new JLabel("Palabras por Segundo");
		lbPalabrasPorSegundo.setBounds(53, 324, 155, 14);
		getContentPane().add(lbPalabrasPorSegundo);
		
		JLabel lbBitsPorPalabra = new JLabel("Bits por Palabra");
		lbBitsPorPalabra.setBounds(53, 369, 128, 14);
		getContentPane().add(lbBitsPorPalabra);
		
		JLabel lbDescripcion = new JLabel("Descripci\u00F3n");
		lbDescripcion.setBounds(43, 414, 128, 14);
		getContentPane().add(lbDescripcion);
		
		tfNumeroSubframes = new JTextField();
		tfNumeroSubframes.setBackground(Color.WHITE);
		tfNumeroSubframes.setBounds(218, 206, 235, 20);
		getContentPane().add(tfNumeroSubframes);
		tfNumeroSubframes.setColumns(10);
		tfNumeroSubframes.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		tfNumeroSubframes.setText(Integer.toString(miObjetoEstructuraFrame.getNSubframes()));
		
		tfTiempoSubframe = new JTextField();
		tfTiempoSubframe.setBounds(218, 276, 235, 20);
		getContentPane().add(tfTiempoSubframe);
		tfTiempoSubframe.setColumns(10);
		tfTiempoSubframe.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		tfTiempoSubframe.setText(Double.toString(miObjetoEstructuraFrame.getTiempoSubframe()));
		
		tfPalabrasSegundo = new JTextField();
		tfPalabrasSegundo.setBounds(218, 321, 235, 20);
		getContentPane().add(tfPalabrasSegundo);
		tfPalabrasSegundo.setColumns(10);
		tfPalabrasSegundo.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		tfPalabrasSegundo.setText(Integer.toString(miObjetoEstructuraFrame.getWPS()));
				
		tfBitsPalabra = new JTextField();
		tfBitsPalabra.setBounds(218, 366, 235, 20);
		getContentPane().add(tfBitsPalabra);
		tfBitsPalabra.setColumns(10);
		tfBitsPalabra.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		tfBitsPalabra.setText(Integer.toString(miObjetoEstructuraFrame.getBitsPW()));
		
		tpDescripcion = new JTextPane (new MyDefaultStyledDocument());
		tpDescripcion.setText(miObjetoEstructuraFrame.getDescripcion());	
		JScrollPane scrDescripcion = new JScrollPane(tpDescripcion);
		scrDescripcion.setBounds(218, 414, 235, 63);
		getContentPane().add(scrDescripcion);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnGuardar_actionPerformed(arg0);
			}
		});
		btnGuardar.setBounds(106, 498, 89, 23);
		getContentPane().add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		btnCancelar.setBounds(301, 498, 89, 23);
		getContentPane().add(btnCancelar);
		
		tfIdEstructuraFrame = new JTextField();
		tfIdEstructuraFrame.setEditable(false);
		tfIdEstructuraFrame.setBackground(UIManager.getColor("Button.background"));
		tfIdEstructuraFrame.setColumns(10);
		tfIdEstructuraFrame.setBounds(218, 24, 235, 20);
		getContentPane().add(tfIdEstructuraFrame);
		tfIdEstructuraFrame.setText(Integer.toString(miObjetoEstructuraFrame.getIdEstructuraFrame()));
		
		tfIdSerie = new JTextField();
		tfIdSerie.setEditable(false);
		tfIdSerie.setBackground(UIManager.getColor("Button.background"));
		tfIdSerie.setColumns(10);
		tfIdSerie.setBounds(218, 70, 235, 20);
		getContentPane().add(tfIdSerie);
		tfIdSerie.setText(Integer.toString(miObjetoEstructuraFrame.getIdSerie()));
		
		tfIdModelo = new JTextField();
		tfIdModelo.setEditable(false);
		tfIdModelo.setBackground(UIManager.getColor("Button.background"));
		tfIdModelo.setColumns(10);
		tfIdModelo.setBounds(218, 116, 235, 20);
		getContentPane().add(tfIdModelo);
		tfIdModelo.setText(Integer.toString(miObjetoEstructuraFrame.getIdModelo()));
		
		tfIdFabricante = new JTextField();
		tfIdFabricante.setEditable(false);
		tfIdFabricante.setBackground(UIManager.getColor("Button.background"));
		tfIdFabricante.setColumns(10);
		tfIdFabricante.setBounds(218, 161, 235, 20);
		getContentPane().add(tfIdFabricante);
		tfIdFabricante.setText(Integer.toString(miObjetoEstructuraFrame.getIdFabricante()));
		
		setVisible(true);  
		setSize(513, 581);
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
		
		String mensaje= null;				
		int intIdEstructuraFrame = miObjetoEstructuraFrame.getIdEstructuraFrame();
		int intIdSerie = miObjetoEstructuraFrame.getIdSerie();
		int intIdModelo = miObjetoEstructuraFrame.getIdModelo();
		int intIdFabricante = miObjetoEstructuraFrame.getIdFabricante();		
		
		mensaje = "La entrada Número de Subframes";
		MutableBoolean blNumeroSubframes = new MutableBoolean(false);
		int intNumeroSubframes = NumericUtils.parseIntPositive(tfNumeroSubframes.getText(), MyConstants.StringConstant.VER_MODIFICAR.value(), mensaje, blNumeroSubframes);
		
		mensaje = "La entrada Tiempo de Subframes";
		MutableBoolean blTiempoSubframe = new MutableBoolean(false);
		double dblTiempoSubframe = NumericUtils.parseDoublePositive(tfTiempoSubframe.getText(), MyConstants.StringConstant.VER_MODIFICAR.value(), mensaje, blTiempoSubframe);
		
		mensaje = "La entrada Palabras por Segundo";
		MutableBoolean blPalabrasSegundo = new MutableBoolean(false);
		int intPalabrasSegundo = NumericUtils.parseIntPositive(tfPalabrasSegundo.getText(), MyConstants.StringConstant.VER_MODIFICAR.value(), mensaje, blPalabrasSegundo);
		
		mensaje = "La entrada Bits por Palabra";
		MutableBoolean blBitsPalabra = new MutableBoolean(false);
		int intBitsPalabra = NumericUtils.parseIntPositive(tfBitsPalabra.getText(), MyConstants.StringConstant.VER_MODIFICAR.value(), mensaje, blBitsPalabra);
		
		
		String strDescripcion = tpDescripcion.getText();
		
		//if (esCorrectoNS & esCorrectoTS & esCorrectoPS & esCorrectoBP){
		if (blNumeroSubframes.valor && blTiempoSubframe.valor && blPalabrasSegundo.valor && blBitsPalabra.valor){			
				
			miObjetoEstructuraFrame = new EstructuraFrame(intIdEstructuraFrame, intIdSerie, intIdModelo, intIdFabricante, intNumeroSubframes, dblTiempoSubframe, intPalabrasSegundo, intBitsPalabra, strDescripcion);
			
			miVerModificarController.modificarEstructuraFrame(miObjetoEstructuraFrame, miIdEstructuraFrame);
			
			//Se obtiene el objeto seleccionado dentro del modelo del combobox que se nos paso de VerModificarXXX
			elementoCbFabricante = (ElementoComboBox)cbFabricante.getModel().getSelectedItem();
			cbIdFabricante = elementoCbFabricante.getIdElemento();
			
			elementoCbModelo = (ElementoComboBox)cbModelo.getModel().getSelectedItem();
			cbIdModelo = elementoCbModelo.getIdElemento();
			
			elementoCbSerie = (ElementoComboBox)cbSerie.getModel().getSelectedItem();
			cbIdSerie = elementoCbSerie.getIdElemento();
			
			//Actualizo la lista en VerModificarManual, listando todos los fabricantes
			miConsultaController.listaEstructuraFrame(cbIdFabricante, cbIdModelo, cbIdSerie, idVerModificarEstructuraFrame, true); 				
		}
	}
	
	public void do_btnCancelar_actionPerformed(ActionEvent e) {
		dispose();		
		boolean uno = listUniqueIdVerModificarEstructuraFrame.getUniqueListID().remove(miObjetoEstructuraFrame.getIdEstructuraFrame());
        miVerModificarController.getActualizarElemento().removePropertyChangeListener(PROP_ACTUALIZAR_ESTRUCTURA_FRAME, this);
		
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		ParametrosActualizarElemento parametrosActualizarElemento;
		if (PROP_ACTUALIZAR_ESTRUCTURA_FRAME.equals(propertyName)){
			parametrosActualizarElemento = (ParametrosActualizarElemento) evt.getNewValue();
			//Se comprueba el id del objeto Modelo
			if (parametrosActualizarElemento.getIdElemento().equals(miIdEstructuraFrame)){
				MessageUtils.mensajeActualizarElemento(parametrosActualizarElemento, MyConstants.StringConstant.ESTRUCTURA_FRAME.value(), this);
			}			
		}		
	}
	
	

	
	/*
	 * Cambiar el id de este objeto.
	 * @param idEStructuraFrame, es una UUID (identificador unico)
	 *  */
	
	public void setIdSistemaEspecifico(UUID idSistemaEspecifico){		
		miIdEstructuraFrame = idSistemaEspecifico;	}
	
	
	/*
	 * Devuelve el id de este objeto.
	 * 
	 *  */
	public UUID getIdSistemaEspecifico(){		
		return miIdEstructuraFrame;
	}
}
