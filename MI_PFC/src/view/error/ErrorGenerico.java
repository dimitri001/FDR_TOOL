package view.error;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.Rectangle;
import java.awt.Point;
import com.jgoodies.forms.factories.FormFactory;

public class ErrorGenerico extends JFrame {
private final JButton btnAceptar = new JButton("Aceptar");
	
	public ErrorGenerico (String Modulo, String mensaje) {
		setTitle("Error "+Modulo);
		btnAceptar.setAlignmentX(1.0f);
		btnAceptar.setBounds(new Rectangle(0, 0, 120, 23));
		//btnAceptar.setBounds(136, 105, 89, 23);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnAceptar_actionPerformed(arg0);
			}
		});
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("max(5dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("317px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(7dlu;default)"),},
			new RowSpec[] {
				RowSpec.decode("max(5dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("78px:grow"),
				RowSpec.decode("max(5dlu;default)"),
				RowSpec.decode("23px:grow"),
				RowSpec.decode("max(7dlu;default)"),}));
		//De esta forma el JTextArea, se usa para mostrar un mensaje con multiples lineas		
		JTextArea taUno = new JTextArea(mensaje);
		taUno.setLocation(new Point(5, 5));
		taUno.setAlignmentY(1.5f);
		taUno.setAlignmentX(1.5f);
		taUno.setBounds(new Rectangle(7, 7, 0, 0));
		taUno.setBackground(UIManager.getColor("Button.background"));
		JScrollPane scrTextArea = new JScrollPane(taUno);
		taUno.setEditable(false);
		taUno.setLineWrap(true);
		scrTextArea.setOpaque(false);
		scrTextArea.setBorder(BorderFactory.createEmptyBorder());
		getContentPane().add(scrTextArea, "3, 3, fill, fill");
		getContentPane().add(btnAceptar, "3, 5, center, default");
		
		setSize(364, 170);
		setVisible(true);
		setLocationRelativeTo(null);
		
	
	}

	protected void do_btnAceptar_actionPerformed(ActionEvent arg0) {
		dispose();
	}
}
