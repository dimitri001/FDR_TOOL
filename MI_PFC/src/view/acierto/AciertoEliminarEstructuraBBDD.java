package view.acierto;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class AciertoEliminarEstructuraBBDD extends JFrame {

	private final JButton btnAceptar = new JButton("Aceptar");
	
	public AciertoEliminarEstructuraBBDD() {
		setTitle("Mensaje");
		getContentPane().setLayout(null);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnAceptar_actionPerformed(arg0);
			}
		});
		btnAceptar.setBounds(121, 64, 89, 23);
		getContentPane().add(btnAceptar);
		
		JLabel lbUno = new JLabel("Estructura de  BBDD eliminada correctamente.");
		lbUno.setBounds(28, 25, 275, 14);
		getContentPane().add(lbUno);
		
		setSize(340, 140);
		setVisible(true);
		setLocationRelativeTo(null);
	
	}
	
	protected void do_btnAceptar_actionPerformed(ActionEvent arg0) {
		dispose();
	}
}
