package view.alta;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import utils.MessageUtils;
import view.acierto.AciertoAlta;
import view.error.ErrorAlta;
import view.error.ErrorAlta2;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.UUID;

import libreria.JTextFieldLimit;
import libreria.MyConstants;
import libreria.ParametrosGuardarElemento;

import controller.*;

public class AltaFabricante extends JFrame implements PropertyChangeListener {
	private JTextField tfNombre;
	private JTextField tfWeb;	
	private UUID miIdFabricante;//Identificador unico de cada objeto	
	private String strNombre;
	private String strWeb;
	private AltaController miAltaController;	
	public static final String PROP_INSERTAR_FABRICANTE = "insertarFabricante";
	
	public AltaFabricante(AltaController altaController, UUID idFabricante) {
		
		miIdFabricante = idFabricante;
		miAltaController = altaController;
		
		setTitle("Alta Fabricante");
		getContentPane().setLayout(null);
		
		JLabel lbNombre = new JLabel("Nombre");
		lbNombre.setBounds(39, 29, 46, 14);
		getContentPane().add(lbNombre);
		
		tfNombre = new JTextField();
		tfNombre.setBounds(155, 26, 235, 20);
		getContentPane().add(tfNombre);
		tfNombre.setColumns(10);
		tfNombre.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		
		tfWeb = new JTextField();
		tfWeb.setBounds(155, 89, 235, 20);
		getContentPane().add(tfWeb);
		tfWeb.setColumns(10);
		tfWeb.setDocument(new JTextFieldLimit(MyConstants.IntConstant.MAX_LENGTH_TEXT_FIELD.value()));
		
		JLabel lbWeb = new JLabel("Web");
		lbWeb.setBounds(39, 92, 46, 14);
		getContentPane().add(lbWeb);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnGuardar_actionPerformed(arg0);
			}
		});
		btnGuardar.setBounds(54, 153, 141, 23);
		getContentPane().add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		btnCancelar.setBounds(249, 153, 141, 23);
		getContentPane().add(btnCancelar);
		
		setVisible(true);  
		setSize(461, 229);
		
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
		
		strNombre = tfNombre.getText();
		strWeb = tfWeb.getText();		
		miAltaController.guardarFabricante(strNombre, strWeb, miIdFabricante);
		
	}
	
	protected void do_btnCancelar_actionPerformed(ActionEvent arg0) {
		dispose();//el objeto ya no se visualiza
		miAltaController.getGuardarElemento().removePropertyChangeListener(PROP_INSERTAR_FABRICANTE,this);//se remueve el objeto de los listeners
	}

	/*
	 * Se invoca cada vez que algo cambia en los objetos de los que este objeto es listener 
	 * Si este objeto es el que pidio algo al Modelo, esto se comprobara por medio del Id, que esta en 
	 * los parametrosGuardarElemento y parametrosConsultasCb.
	 * @param evt, es el parametro que nos envia el objeto que es escuchado (el modelo del MVC)
	 * */

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		ParametrosGuardarElemento parametrosGuardarElemento;		

		if (PROP_INSERTAR_FABRICANTE.equals(propertyName)){		 
			parametrosGuardarElemento = (ParametrosGuardarElemento) evt.getNewValue();
			//Se comprueba el id del objeto Modelo
			if (parametrosGuardarElemento.getIdElemento().equals(miIdFabricante)){
				MessageUtils.mensajeInsertarElemento(parametrosGuardarElemento, MyConstants.StringConstant.FABRICANTE.value());
			}		
		}
	}	
	
	private void borrarTextFields(){		
		tfNombre.setText(null);
		tfWeb.setText(null);
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

