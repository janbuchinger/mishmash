package net.janbuchinger.code.mishmash.ui.userInput;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import net.janbuchinger.code.mishmash.ui.UIFx;

/**
 * <code>FolderPathTextField</code> combines <code>JFileChooser</code> with a
 * <code>JTextFieldWithPopUp</code> to enable easy file choice.
 * 
 * @author Jan Buchinger
 * 
 * @see JTextFieldWithPopUp
 * @see FolderPathTextField
 */
@SuppressWarnings("serial")
public class FilePathTextField extends JPanel implements ActionListener, FileDrop.Listener {
	private final JButton btSearch;
	private final JTextFieldWithPopUp tfPath;

	/**
	 * Constructs a new <code>FilePathTextField</code> with a textfielt width = 35.
	 */
	public FilePathTextField() {
		this(35);
	}

	/**
	 * Constructs a new <code>FilePathTextField</code>.
	 * 
	 * @param tfWidth
	 *            The textfield width (columns).
	 */
	public FilePathTextField(int tfWidth) {
		super(new BorderLayout());
		btSearch = new JButton("...");
		btSearch.addActionListener(this);
		tfPath = new JTextFieldWithPopUp(tfWidth);
		new FileDrop(tfPath, this);
		GridBagConstraints c = UIFx.initGridBagConstraints();
		c.weightx = 1;
		c.insets = new Insets(0, 0, 0, 0);
		JPanel pnc = new JPanel(new GridBagLayout());
		pnc.add(tfPath, c);
		add(pnc, BorderLayout.CENTER);
		add(btSearch, BorderLayout.EAST);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btSearch) {
			JFileChooser fc = new JFileChooser();
			int answer = fc.showOpenDialog(this);
			if (answer == JFileChooser.APPROVE_OPTION) {
				tfPath.setText(fc.getSelectedFile().getPath());
			}
		}
	}

	@Override
	public void filesDropped(File[] files) {
		if (files.length > 0) {
			tfPath.setText(files[0].getPath());
		}
	}

	/**
	 * Gets the selected path.
	 * 
	 * @return The selected path.
	 */
	public final String getPath() {
		return tfPath.getText();
	}

	/**
	 * Sets the path in the text field.
	 * 
	 * @param path
	 *            The path to be set.
	 */
	public void setPath(String path) {
		tfPath.setText(path);
	}

	/**
	 * Gets the <code>JTextFieldWithPopUp</code> containing the file path.
	 * 
	 * @return The <code>JTextFieldWithPopUp</code> containing the file path.
	 */
	public final JTextFieldWithPopUp getTextField() {
		return tfPath;
	}
}
