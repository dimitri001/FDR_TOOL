package view.inicio;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import view.VentanaPrincipal;

import view.acierto.AciertoIniciarSesion;
import view.error.ErrorIniciarSesion;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import libreria.MutableBoolean;
import libreria.MutableInt;
import controller.*;

public class IniciarSesion extends JFrame implements PropertyChangeListener{
	private JTextField tfUsuario = new JTextField();
	private JPasswordField tfContrasenya;
	
	private String strUsuario;
	private String strContraseña;
	private InicioController miInicioController;
	private VentanaPrincipal miVentanaPrincipal;
	public static final String PROP_INICIAR_SESION = "iniciarSesion";

	//ventanas de mensajes de acierto o error
	MutableInt flagInicioSesion = new MutableInt(-1);
	private AciertoIniciarSesion aciertoIniciarSesion ;
	private ErrorIniciarSesion errorIniciarSesion ;
	Logger LOG = LoggerFactory.getLogger(IniciarSesion.class);
	public IniciarSesion(InicioController inicioController, VentanaPrincipal ventanaPrincipal) {
		
		miInicioController = inicioController;
		miVentanaPrincipal = ventanaPrincipal;
		setTitle("Inicio Sesion");
		getContentPane().setLayout(null);
		tfUsuario.setBounds(188, 27, 125, 20);
		getContentPane().add(tfUsuario);
		tfUsuario.setColumns(10);
		
		tfContrasenya= new JPasswordField(10);
		tfContrasenya.setBounds(188, 100, 125, 20);
		getContentPane().add(tfContrasenya);
		tfContrasenya.setColumns(10);
		
		JLabel lbUsuario = new JLabel("Usuario");
		lbUsuario.setBounds(22, 30, 46, 14);
		getContentPane().add(lbUsuario);
		
		JLabel lbContrasea = new JLabel("Contrase\u00F1a");
		lbContrasea.setBounds(22, 103, 86, 14);
		getContentPane().add(lbContrasea);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnAceptar_actionPerformed(arg0);
			}
		});
		btnAceptar.setBounds(44, 184, 89, 23);
		getContentPane().add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnCancelar_actionPerformed(arg0);
			}
		});
		btnCancelar.setBounds(202, 184, 89, 23);
		getContentPane().add(btnCancelar);
		
		setVisible(true);  
		setSize(350,258);
		
		//Asi al cerrar en la X del JFrame, se invoca a do_btnCerrar_actionPerformed
		//y se cierra correctamente el objeto
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
					   public void windowClosing(WindowEvent evt) {
						   do_btnCancelar_actionPerformed(null);
					   }
					  });
	}
	
	protected void do_btnAceptar_actionPerformed(ActionEvent arg0) {
		strUsuario = tfUsuario.getText();
		strContraseña = new String(tfContrasenya.getPassword());
		int lengthStrUsuario = strUsuario.length();
		int lengthStrContraseña = strContraseña.length();		
		LOG.info("El usuario "+ strUsuario +" va a iniciar sesión");		
		if((lengthStrUsuario>0) && (lengthStrContraseña>0)){	
			miInicioController.iniciarSesion(strUsuario,strContraseña);
		}else{
			errorIniciarSesion = new ErrorIniciarSesion("El Usuario y Contraseña nunca deben ser vacios.");
		}
		
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		int flagInicioSesion;
		
		if (PROP_INICIAR_SESION.equals(propertyName)){			
			flagInicioSesion =  (Integer) evt.getNewValue();
			//Esto se podria cambiar por un listener.
			if (flagInicioSesion == 1)//sesion iniciada con exito
			{
				aciertoIniciarSesion = new AciertoIniciarSesion(this, miVentanaPrincipal);
				LOG.info("El usuario "+ strUsuario +" ha iniciado sesión");	
			}
			else // sesion no iniciada
			{
				errorIniciarSesion = new ErrorIniciarSesion("Error iniciando sesion. Usuario o clave erróneos.");
				LOG.error("El usuario "+ strUsuario +" no ha podido iniciar sesión");	
			}
		}

		
	}
	public void do_btnCancelar_actionPerformed(ActionEvent arg0) {		
		dispose();
		miInicioController.getConexionBBDD().removePropertyChangeListener(this);//se remueve el el objeto de los listeners
		miVentanaPrincipal.setCrearVentanaIniciarSesion(new MutableBoolean(true));}
}
