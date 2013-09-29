package view.error;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ErrorSeleccionListadoBBDD extends JFrame {

	private final JButton btnAceptar = new JButton("Aceptar");
	
	public ErrorSeleccionListadoBBDD () {
	
		setTitle("Error");
		//getContentPane().setSize(350, 130);
		getContentPane().setLayout(null);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnAceptar_actionPerformed(arg0);
			}
		});
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
	
	protected void do_btnAceptar_actionPerformed(ActionEvent arg0) {
		dispose();
	}
}
