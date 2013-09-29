package view.acierto;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AciertoBaja extends JFrame {
	
	private final JButton btnAceptar = new JButton("Aceptar");
	
	public AciertoBaja (String Mensaje) {
		setTitle("Mensaje");
		getContentPane().setLayout(null);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnAceptar_actionPerformed(arg0);
			}
		});
		btnAceptar.setBounds(117, 64, 89, 23);
		getContentPane().add(btnAceptar);		
		//De esta forma el JTextArea, se usa para mostrar un mensaje con multiples lineas
		JTextArea taUno = new JTextArea(Mensaje);
		taUno.setEditable(false);
		taUno.setLineWrap(true);
		taUno.setOpaque(false);
		taUno.setBorder(BorderFactory.createEmptyBorder());
		taUno.setBounds(22, 22, 292, 39);
		getContentPane().add(taUno);		
		setSize(340, 140);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	protected void do_btnAceptar_actionPerformed(ActionEvent arg0) {
		dispose();
	}
}


