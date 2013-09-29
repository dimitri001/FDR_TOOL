package view.error;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;


public class ErrorSeleccionListado extends JFrame {

	private final JButton btnAceptar = new JButton("Aceptar");
	
	public ErrorSeleccionListado () {	
		setTitle("Error");
		getContentPane().setLayout(null);
		btnAceptar.setBounds(126, 64, 89, 23);
		getContentPane().add(btnAceptar);		
		JLabel lbUno = new JLabel("Se debe escoger un solo elemento de la lista.");
		//Se debe sumar a 52 a width, despues de hacer que el texto llene la JLabel
		lbUno.setBounds(31, 25, 268, 14);
		getContentPane().add(lbUno);		
		//Define el tamaño del recuadro
		setSize(350, 130);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
}
