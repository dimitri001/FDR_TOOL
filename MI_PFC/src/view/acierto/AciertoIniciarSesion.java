package view.acierto;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

import view.VentanaPrincipal;
import view.inicio.IniciarSesion;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AciertoIniciarSesion extends JFrame {

	
	private final JButton btnAceptar = new JButton("Aceptar");
	private IniciarSesion miVentanaPadre;
	private VentanaPrincipal miVentanaPrincipal;

	public AciertoIniciarSesion(IniciarSesion ventanaPadre, VentanaPrincipal ventanaPrincipal) {
		
		miVentanaPadre = ventanaPadre;
		miVentanaPrincipal = ventanaPrincipal;
		setTitle("Mensaje");
		getContentPane().setLayout(null);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnAceptar_actionPerformed(arg0);
			}
		});
		btnAceptar.setBounds(121, 64, 89, 23);
		getContentPane().add(btnAceptar);
		
		JLabel lbUno = new JLabel("Sesi\u00F3n iniciada correctamente.");
		lbUno.setBounds(36, 25, 259, 14);
		getContentPane().add(lbUno);
		
		setSize(340, 140);
		setVisible(true);
		setLocationRelativeTo(null);

	}
	
	protected void do_btnAceptar_actionPerformed(ActionEvent arg0) {
		dispose();
		miVentanaPadre.do_btnCancelar_actionPerformed(null);
		miVentanaPrincipal.do_btnSeleccionarBbdd_actionPerformed(null);
		
	}
}

