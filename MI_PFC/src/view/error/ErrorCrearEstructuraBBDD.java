package view.error;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ErrorCrearEstructuraBBDD extends JFrame {
	
	private final JButton btnAceptar = new JButton("Aceptar");
	
	public ErrorCrearEstructuraBBDD () {
	
		setTitle("Error");
		getContentPane().setLayout(null);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnAceptar_actionPerformed(arg0);
			}
		});
		btnAceptar.setBounds(126, 64, 89, 23);
		getContentPane().add(btnAceptar);
		
		JLabel lbUno = new JLabel("Error creando la estructura de base de datos.");
		lbUno.setBounds(33, 18, 279, 14);
		getContentPane().add(lbUno);
		
		JLabel lblYaExistenTablas = new JLabel("Ya hay tablas existentes.");
		lblYaExistenTablas.setBounds(33, 36, 279, 14);
		getContentPane().add(lblYaExistenTablas);
		
		//Define el tamaño del recuadro
		setSize(350, 130);
		setVisible(true);
		setLocationRelativeTo(null);
		
	}
	protected void do_btnAceptar_actionPerformed(ActionEvent arg0) {
		dispose();
	}
}
