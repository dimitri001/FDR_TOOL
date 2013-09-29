package view;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class WarningBorrarFilas extends JFrame {
	
	private final JButton btnAceptar = new JButton("Aceptar");
	
	public WarningBorrarFilas() {

		setTitle("Warning");
		getContentPane().setLayout(null);
		btnAceptar.setBounds(116, 66, 89, 23);
		getContentPane().add(btnAceptar);
		
		JLabel lbUno = new JLabel("Se borrar\u00E1(n) de la base de datos la(s) fila(s) escogida(s).");
		lbUno.setBounds(10, 26, 332, 14);
		getContentPane().add(lbUno);
		
		//Define el tamaño del recuadro
		setSize(350, 130);
		setVisible(true);
		setLocationRelativeTo(null);
	
	}

	
	
}
