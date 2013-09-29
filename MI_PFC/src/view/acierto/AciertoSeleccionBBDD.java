package view.acierto;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

import view.inicio.SeleccionarBBDD;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AciertoSeleccionBBDD extends JFrame {

	private final JButton btnAceptar = new JButton("Aceptar");
	private SeleccionarBBDD seleccionarBBDD;	
	public AciertoSeleccionBBDD(SeleccionarBBDD seleccionarBBDD) {
		this.seleccionarBBDD= seleccionarBBDD;
		setTitle("Mensaje");
		getContentPane().setLayout(null);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnAceptar_actionPerformed(arg0);
			}
		});
		btnAceptar.setBounds(121, 64, 89, 23);
		getContentPane().add(btnAceptar);
		
		JLabel lbUno = new JLabel("Base de datos seleccionada correctamente.");
		lbUno.setBounds(36, 25, 259, 14);
		getContentPane().add(lbUno);
		
		setSize(340, 140);
		setVisible(true);
		setLocationRelativeTo(null);
	
	}
	
	
	protected void do_btnAceptar_actionPerformed(ActionEvent arg0) {		
		dispose();
		seleccionarBBDD.do_btnCerrar_actionPerformed(null);
	}
}
