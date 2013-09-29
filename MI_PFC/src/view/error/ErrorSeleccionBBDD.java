package view.error;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class ErrorSeleccionBBDD extends JFrame {
	

	
	private final JButton btnAceptar = new JButton("Aceptar");
	
	public ErrorSeleccionBBDD() {
	
		setTitle("Error");
		getContentPane().setLayout(null);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnAceptar_actionPerformed(arg0);
			}
		});
		btnAceptar.setBounds(126, 64, 89, 23);
		getContentPane().add(btnAceptar);
		
		JLabel lbUno = new JLabel("Error seleccionando la base de datos.");
		lbUno.setBounds(63, 25, 239, 14);
		getContentPane().add(lbUno);	
		
		//Define el tama�o del recuadro
		setSize(350, 130);
		setVisible(true);
		setLocationRelativeTo(null);
	
	}

	protected void do_btnAceptar_actionPerformed(ActionEvent arg0) {
		dispose();
	}
}
