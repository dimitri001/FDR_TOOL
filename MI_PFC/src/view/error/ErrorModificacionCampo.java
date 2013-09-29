package view.error;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class ErrorModificacionCampo extends JFrame {
	
	private final JButton btnAceptar = new JButton("Aceptar");	
	public ErrorModificacionCampo (String mensaje) {
		setTitle("Error Ver/Modificar");
		getContentPane().setLayout(null);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnAceptar_actionPerformed(arg0);
			}
		});
		btnAceptar.setBounds(131, 68, 89, 23);
		getContentPane().add(btnAceptar);
		//De esta forma el JTextArea, se usa para mostrar un mensaje con multiples lineas
		JTextArea taUno = new JTextArea(mensaje+", revise el procedimiento o el texto e int\u00E9ntelo de nuevo.");
		taUno.setEditable(false);
		taUno.setLineWrap(true);
		taUno.setOpaque(false);
		taUno.setBorder(BorderFactory.createEmptyBorder());
		taUno.setBounds(10, 11, 334, 53);
		getContentPane().add(taUno);
		setSize(370, 140);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	protected void do_btnAceptar_actionPerformed(ActionEvent arg0) {
		dispose();
	}
}



