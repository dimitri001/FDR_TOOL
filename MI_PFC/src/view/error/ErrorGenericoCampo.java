package view.error;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

public class ErrorGenericoCampo extends JFrame {
	
	public ErrorGenericoCampo (String Modulo, String mensaje) {
		setTitle("Error "+Modulo);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("323px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(6dlu;default)"),},
			new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("54px:grow"),
				RowSpec.decode("max(4dlu;default)"),
				RowSpec.decode("max(15dlu;default)"),
				RowSpec.decode("max(5dlu;default)"),}));
		
		
		//De esta forma el JTextArea, se usa para mostrar un mensaje con multiples lineas
		JTextArea taUno = new JTextArea(mensaje+" no es correcta, revise el texto e int\u00E9ntelo de nuevo.");
		taUno.setEditable(false);
		taUno.setLineWrap(true);
		taUno.setOpaque(false);
		taUno.setBorder(BorderFactory.createEmptyBorder());
		getContentPane().add(taUno, "2, 2, fill, fill");
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnAceptar_actionPerformed(arg0);
			}
		});
		getContentPane().add(btnAceptar, "2, 4, center, default");
		
		setSize(370, 140);
		setVisible(true);
		setLocationRelativeTo(null);	
	}
	protected void do_btnAceptar_actionPerformed(ActionEvent arg0) {
		dispose();
	}
}

	


	



