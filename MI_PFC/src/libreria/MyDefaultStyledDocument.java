package libreria;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;


@SuppressWarnings("serial")
public class MyDefaultStyledDocument extends DefaultStyledDocument {

	@Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

		if ((getLength() + str.length()) <= MyConstants.IntConstant.MAX_LENGTH_TEXT_PANE.value()) {
            super.insertString(offs, str, a);
        }
        else {
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
