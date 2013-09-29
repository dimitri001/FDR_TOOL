package view;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

public class WarningVerModificar extends JFrame {
	
	private final JButton btnAceptar = new JButton("Aceptar");
	
	public WarningVerModificar() {

		setTitle("Warning");
		getContentPane().setLayout(null);
		btnAceptar.setBounds(116, 66, 89, 23);
		getContentPane().add(btnAceptar);
		
		JLabel lbUno = new JLabel("Selecciona solo un elemento de los mostrados. ");
		lbUno.setBounds(25, 26, 248, 14);
		getContentPane().add(lbUno);
		
		//Define el tamaño del recuadro
		setSize(350, 130);
		setVisible(true);
		setLocationRelativeTo(null);
		
	
	}

	
	
}
