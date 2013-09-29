package view.error;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

import libreria.ParametrosGuardarElemento;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class ErrorAlta2 extends JFrame {
	
	private final JButton btnAceptar = new JButton("Aceptar");
	
	public ErrorAlta2 (String elemento, ParametrosGuardarElemento parametros) {
		setTitle("Error Alta");
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("max(7dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("370px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(5dlu;default)"),},
			new RowSpec[] {
				RowSpec.decode("10px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("74px:grow"),
				RowSpec.decode("max(4dlu;default)"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(4dlu;default)"),}));
		
		//De esta forma el JTextArea, se usa para mostrar un mensaje con multiples lineas
		String accion = "no se insertó correctamente.";
		String msg = elemento+" "+accion+"\n\nError\n"+parametros.getMensaje()+"...";
		
		JTextArea taUno = new JTextArea(msg);
		
		taUno.setBorder(new EmptyBorder(0, 0, 0, 0));
		JScrollPane scrTextArea = new JScrollPane(taUno);
		taUno.setEditable(false);
		taUno.setLineWrap(true);
		taUno.setOpaque(false);
		scrTextArea.setBorder(BorderFactory.createEmptyBorder());
		getContentPane().add(scrTextArea, "3, 3, fill, fill");
		
		
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnAceptar_actionPerformed(arg0);
			}
		});
		getContentPane().add(btnAceptar, "3, 5, center, top");		
		
		setSize(410, 154);
		setVisible(true);
		setLocationRelativeTo(null);
		
	
	}

	protected void do_btnAceptar_actionPerformed(ActionEvent arg0) {
		dispose();
	}
}


