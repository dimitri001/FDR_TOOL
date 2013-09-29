package view;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ConfirmacionBaja extends JFrame {
	private final JButton btnAceptar = new JButton("Aceptar");
	private final JButton btnCancelar = new JButton("Cancelar");
	
	public ConfirmacionBaja() {
		setTitle("Confirmacion Baja");
		getContentPane().setLayout(null);
		btnAceptar.setBounds(67, 64, 89, 23);
		getContentPane().add(btnAceptar);
		
		
		btnCancelar.setBounds(220, 67, 89, 23);
		getContentPane().add(btnCancelar);
		
		JLabel lbUno = new JLabel("Estos elementos se borraran permanentemente de la base de datos.");
		lbUno.setBounds(25, 11, 335, 14);
		getContentPane().add(lbUno);
		
		JLabel lbDos = new JLabel("\u00BFDesea continuar con la operacion?.");
		lbDos.setBounds(25, 34, 236, 14);
		getContentPane().add(lbDos);
	}
}
