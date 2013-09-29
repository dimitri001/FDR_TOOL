package view.error;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ErrorActualizar extends JFrame {
	
	private final JButton btnAceptar = new JButton("Aceptar");
	
	public ErrorActualizar (String Mensaje) {
		setTitle("Error Actualizar");
		getContentPane().setLayout(null);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnAceptar_actionPerformed(arg0);
			}
		});
		btnAceptar.setBounds(129, 68, 89, 23);
		getContentPane().add(btnAceptar);
	
		//De esta forma el JTextArea, se usa para mostrar un mensaje con multiples lineas
		JTextArea taUno = new JTextArea(Mensaje+" no se actualizó correctamente.");
		taUno.setEditable(false);
		taUno.setLineWrap(true);
		taUno.setOpaque(false);
		taUno.setBorder(BorderFactory.createEmptyBorder());
		taUno.setBounds(10, 22, 328, 42);
		getContentPane().add(taUno);
		
		setSize(364, 140);
		setVisible(true);
		setLocationRelativeTo(null);
		
	
	}

	protected void do_btnAceptar_actionPerformed(ActionEvent arg0) {
		dispose();
	}
}


