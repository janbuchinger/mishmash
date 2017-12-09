/*
 * Copyright 2017 Jan Buchinger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.janbuchinger.code.mishmash.ui.dialog.dirChooser;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import net.janbuchinger.code.mishmash.ui.UIFx;
import net.janbuchinger.code.mishmash.ui.dialog.DialogEscapeHook;
import net.janbuchinger.code.mishmash.ui.userInput.JTextFieldWithPopUp;


/**
 * <code>DirChooserDialog</code> is a <code>JDialog</code> that displays a
 * <code>JTree</code> representing the file system(s) available. Like the name
 * says, it lets the user choose a directory. by right clicking hidden
 * directories can be shown or hidden. Upon display it expands to the given
 * directory.
 * <p>
 * <code>DirChooserDialog</code> should be windows compatible.
 * 
 * @author Jan Buchinger
 * 
 */
@SuppressWarnings("serial")
public final class DirChooserDialog extends JDialog implements ActionListener, TreeWillExpandListener,
		TreeSelectionListener, MouseListener {

	private JButton btOk, btCancel;
	private JTree tree;
	private JTextFieldWithPopUp lbPreview;
//	private JLabel lbPreview;
	private TreePopupMenu treePopupMenu;
	// private DefaultMutableTreeNode root;
	// private JScrollPane spTree;
	private JPanel treePanel;

	private final File rootFile;

	private boolean hasMultipleRoots;

	public final static int OK = 0, CANCEL = 1;
	private int answer;
	private String path;

	private final boolean relative;

	long click;

	/**
	 * Creates a modal dialog that offers to choose a directory from the file
	 * system.
	 * <p>
	 * To display call <code>setVisible(true)</code>.
	 * 
	 * @param parent
	 *            The parent frame this dialog is modal to.
	 * @param expandPath
	 *            The path to expand to after the dialog is opened. If
	 *            expandPath is null or an empty String ("") the user home
	 *            directory is automatically selected to expand to.
	 */
	public DirChooserDialog(Frame parent, String expandPath) {
		super(parent, "Ordner Wählen", true);
		relative = false;
		rootFile = new File("/");
		setUpUI(parent, expandPath);
	}

	/**
	 * Creates a modal dialog that offers to choose a directory from the file
	 * system.
	 * <p>
	 * To display call <code>setVisible(true)</code>.
	 * 
	 * @param parent
	 *            The parent dialog this dialog is modal to.
	 * @param expandPath
	 *            The path to expand to after the dialog is opened. If
	 *            expandPath is null or an empty String ("") the user home
	 *            directory is automatically selected to expand to.
	 */
	public DirChooserDialog(Dialog parent, String expandPath) {
		super(parent, "Ordner Wählen", true);
		relative = false;
		rootFile = new File("/");
		setUpUI(parent, expandPath);
	}

	/**
	 * Creates a modal dialog that offers to choose a directory under the root
	 * directory supplied.
	 * <p>
	 * To display call <code>setVisible(true)</code>.
	 * 
	 * @param parent
	 *            The parent dialog this dialog is modal to.
	 * @param rootDir
	 *            The root directory / node the tree expands from
	 * @param expandPath
	 *            The path to expand to after the dialog is opened. If
	 *            expandPath is null or an empty String ("") the user home
	 *            directory is automatically selected to expand to.
	 */
	public DirChooserDialog(Dialog parent, File rootDir, String expandPath) {
		super(parent, "Ordner Wählen", true);
		relative = true;
		this.rootFile = rootDir;
		setUpUI(parent, expandPath);
	}

	/**
	 * Creates a modal dialog that offers to choose a directory under the root
	 * directory supplied.
	 * <p>
	 * To display call <code>setVisible(true)</code>.
	 * 
	 * @param parent
	 *            The parent frame this dialog is modal to.
	 * @param rootDir
	 *            The root directory / node the tree expands from
	 * @param expandPath
	 *            The path to expand to after the dialog is opened. If
	 *            expandPath is null or an empty String ("") the user home
	 *            directory is automatically selected to expand to.
	 */
	public DirChooserDialog(Frame parent, File rootDir, String expandPath) {
		super(parent, "Ordner Wählen", true);
		relative = true;
		this.rootFile = rootDir;
		setUpUI(parent, expandPath);
	}

	private final void setUpUI(Window parent, String expandPath) {
		File[] roots = File.listRoots();
		if (roots.length > 1)
			hasMultipleRoots = true;
		if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
			hasMultipleRoots = true;
		}
		if (expandPath == null)
			expandPath = System.getProperty("user.home");
		if (expandPath.equals(""))
			expandPath = System.getProperty("user.home");
		DefaultMutableTreeNode expFrm = null;
		int expFrmIndex = 0;
		new DialogEscapeHook(this);
		answer = CANCEL;
		path = "";
		treePopupMenu = new TreePopupMenu(this);
		btOk = new JButton("Ok");
		btOk.addActionListener(this);
		btCancel = new JButton("Abbrechen");
		btCancel.addActionListener(this);
		JPanel pnButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnButtons.add(btCancel);
		pnButtons.add(btOk);
//		lbPreview = new JLabel(" ");
		lbPreview = new JTextFieldWithPopUp();
		lbPreview.setEditable(false);

		File f = new File(expandPath);
		File fParent;
		Vector<String> vcPath = new Vector<String>();
		String str;
		vcPath.add(f.getName());
		while ((fParent = f.getParentFile()) != null) {
			str = fParent.getName();
			if (str.equals(""))
				str = fParent.getPath();
			f = f.getParentFile();
			vcPath.add(str);
		}
		DefaultMutableTreeNode[] path = new DefaultMutableTreeNode[vcPath.size()];
		for (int i = 0; i < path.length; i++) {
			path[i] = new DefaultMutableTreeNode(vcPath.get(path.length - 1 - i));
		}

		DefaultMutableTreeNode root = null;
		DefaultMutableTreeNode xNode = null;
		if (!hasMultipleRoots || relative) {
			root = new DefaultMutableTreeNode(rootFile.getAbsolutePath());
			File[] dirs = lsDirs(rootFile);
			File[] subDirs;
			DefaultMutableTreeNode node;
			for (int i = 0; i < dirs.length; i++) {
				node = new DefaultMutableTreeNode(dirs[i].getName());
				if (path.length > 1 && node.toString().equals(path[1].toString())) {
					expFrm = node;
					expFrmIndex = 2;
				}
				subDirs = lsDirs(dirs[i]);
				if (subDirs == null)
					continue;
				for (int j = 0; j < subDirs.length; j++) {
					node.add(xNode = new DefaultMutableTreeNode(subDirs[j].getName()));
				}
				root.add(node);
			}
		} else {
			root = new DefaultMutableTreeNode("+");
			for (int i = 0; i < roots.length; i++) {
				root.add(xNode = new DefaultMutableTreeNode(roots[i].getAbsolutePath()));
				if (xNode.toString().equals(path[0].toString())) {
					expFrm = xNode;
					expFrmIndex = 1;
				}
			}
		}
		tree = new JTree(root);

		if (hasMultipleRoots && !relative)
			tree.setRootVisible(false);

		tree.addTreeSelectionListener(this);
		tree.addMouseListener(this);
		tree.addTreeWillExpandListener(this);
		tree.setComponentPopupMenu(treePopupMenu);

		treePanel = new JPanel(new BorderLayout());
		treePanel.add(UIFx.initScrollPane(tree, 15), BorderLayout.CENTER);

		JPanel pnContent = new JPanel(new BorderLayout());
		pnContent.add(treePanel, BorderLayout.CENTER);
		pnContent.add(lbPreview, BorderLayout.SOUTH);
		pnContent.setBorder(BorderFactory.createEmptyBorder(4, 4, 2, 4));
		
		JPanel pnContent2 = new JPanel(new BorderLayout());
		pnContent2.add(pnContent, BorderLayout.CENTER);
		pnContent2.add(pnButtons, BorderLayout.SOUTH);
		

		setContentPane(pnContent2);
		UIFx.sizeAndCenter(this, parent, 0.3, 0.8);
		UIFx.center(this, parent);
		if (expFrm != null)
			expandPath(expFrm, path, expFrmIndex);
		// setVisible(true);
	}

	private final void expandPath(DefaultMutableTreeNode expandFrom, DefaultMutableTreeNode[] path,
			int expandFromIndex) {
		if (!hasMultipleRoots || relative) {

		} else {
			File[] fa = lsDirs(new File(path[0].toString()));
			if (fa != null)
				for (int i = 0; i < fa.length; i++) {
					expandFrom.add(new DefaultMutableTreeNode(fa[i].getName()));
				}
		}
		DefaultMutableTreeNode ndx = null;
		String filename;
		Enumeration<?> nodes;
		for (int i = expandFromIndex; i < path.length; i++) {
			filename = path[i].toString();
			// System.out.println(expandFrom);
			tree.expandPath(new TreePath(expandFrom.getPath()));
			nodes = expandFrom.children();
			while (nodes.hasMoreElements()) {
				ndx = (DefaultMutableTreeNode) nodes.nextElement();
				if (ndx.toString().equals(filename)) {
					expandFrom = ndx;
				}
			}
		}
		TreePath tp = new TreePath(expandFrom.getPath());
		tree.setSelectionPath(tp);
		tree.scrollPathToVisible(tp);
	}

	private final File[] lsDirs(File f) {
		File[] dirs = f.listFiles(new DirFileFilter(treePopupMenu.showHiddenFolders()));
		if (dirs != null)
			Arrays.sort(dirs);
		return dirs;
	}

	private final String parsePath(TreePath tp) {
		int x = 0;
		if (hasMultipleRoots && !relative)
			x++;
		File f = new File(tp.getPathComponent(x++).toString());
		for (int i = x; i < tp.getPathCount(); i++) {
			f = new File(f, tp.getPathComponent(i).toString());
		}
		return f.getAbsolutePath();
	}

	@Override
	public final void actionPerformed(ActionEvent e) {
		if (e.getSource() == btOk) {
			answer = OK;
		}
		setVisible(false);
	}

	@Override
	public final void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
		File[] subDirs;
		DefaultMutableTreeNode node;
		Enumeration<?> nodes;
		TreeNode[] pathNodes;
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
		nodes = root.children();
		File fx;
		while (nodes.hasMoreElements()) {
			node = (DefaultMutableTreeNode) nodes.nextElement();
			pathNodes = node.getPath();

			int i = 0;
			if (hasMultipleRoots && !relative)
				i++;
			fx = new File(pathNodes[i++].toString());

			for (; i < pathNodes.length; i++) {
				fx = new File(fx, pathNodes[i].toString());
			}
			subDirs = lsDirs(fx);

			if (subDirs == null)
				continue;
			for (int j = 0; j < subDirs.length; j++) {
				node.add(new DefaultMutableTreeNode(subDirs[j].getName()));
			}
		}
	}

	@Override
	public final void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {}

	@Override
	public final void valueChanged(TreeSelectionEvent e) {
		path = parsePath(e.getPath());
		lbPreview.setText(path);
	}

	/**
	 * Gets the user answer. By default <code>DirChooserDialog.CANCEL</code> is
	 * returned. If the dialog is closed by clicking the ok button the answer
	 * will be <code>DirChooserDialog.OK</code>.
	 * 
	 * @return <code>DirChooserDialog.CANCEL</code> or
	 *         <code>DirChooserDialog.OK</code>.
	 */
	public final int getAnswer() {
		return answer;
	}

	/**
	 * Gets the path to the selected directory.
	 * 
	 * @return The path to the selected directory.
	 */
	public final String getPath() {
		return path;
	}

	/**
	 * Gets the relative path if possible.
	 * 
	 * @return If the <code>DirChooserDialog</code> is initiated with a relative
	 *         root directory the relative path starting with the first sub
	 *         directory is returned. Else the full path is returned.
	 */
	public final String getRelativePath() {
		if (relative){
			try {
				return path.substring(rootFile.getAbsolutePath().length() + 1);
			} catch (StringIndexOutOfBoundsException e) {
			}
		}
		return path;
	}

	/**
	 * This method is called by <code>TreePopupMenu</code>.
	 */
	public final void reload() {

		File f = new File(this.path);
		File fParent;
		Vector<String> vcPath = new Vector<String>();
		String str;
		vcPath.add(f.getName());
		while ((fParent = f.getParentFile()) != null) {
			str = fParent.getName();
			if (str.equals(""))
				str = fParent.getPath();
			f = f.getParentFile();
			vcPath.add(str);
		}
		DefaultMutableTreeNode[] path = new DefaultMutableTreeNode[vcPath.size()];
		for (int i = 0; i < path.length; i++) {
			path[i] = new DefaultMutableTreeNode(vcPath.get(path.length - 1 - i));
		}

		DefaultMutableTreeNode root = null;
		DefaultMutableTreeNode xNode = null;
		DefaultMutableTreeNode expFrm = null;
		int expFrmIndex = 0;
		if (!hasMultipleRoots || relative) {
			root = new DefaultMutableTreeNode(rootFile.getAbsolutePath());
			File[] dirs = lsDirs(rootFile);
			File[] subDirs;
			DefaultMutableTreeNode node;
			for (int i = 0; i < dirs.length; i++) {
				node = new DefaultMutableTreeNode(dirs[i].getName());
				if (node.toString().equals(path[1].toString())) {
					expFrm = node;
					expFrmIndex = 2;
				}
				subDirs = lsDirs(dirs[i]);
				if (subDirs == null)
					continue;
				for (int j = 0; j < subDirs.length; j++) {
					node.add(new DefaultMutableTreeNode(subDirs[j].getName()));
				}
				root.add(node);
			}
		} else {
			root = new DefaultMutableTreeNode("+");
			File[] roots = File.listRoots();
			for (int i = 0; i < roots.length; i++) {
				root.add(xNode = new DefaultMutableTreeNode(roots[i].getAbsolutePath()));
				if (xNode.toString().equals(path[0].toString())) {
					expFrm = xNode;
					expFrmIndex = 1;
				}
			}
		}

		tree = new JTree(root);
		if (hasMultipleRoots && !relative)
			tree.setRootVisible(false);

		tree.addTreeWillExpandListener(this);
		tree.addTreeSelectionListener(this);
		tree.addMouseListener(this);
		tree.setComponentPopupMenu(treePopupMenu);
		treePanel.removeAll();
		treePanel.add(UIFx.initScrollPane(tree, 15), BorderLayout.CENTER);
		treePanel.revalidate();
		expandPath(expFrm, path, expFrmIndex);
	}

	@Override
	public final void mouseClicked(MouseEvent e) {
		if (e.getSource() == tree) {
			if (System.currentTimeMillis() - click < 500) {
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (root.isLeaf()) {
					File[] subDirs;
					File froot = new File(parsePath(tree.getLeadSelectionPath()));
					subDirs = lsDirs(froot);
					if (subDirs == null || subDirs.length == 0)
						return;
					for (int y = 0; y < subDirs.length; y++) {
						root.add(new DefaultMutableTreeNode(subDirs[y].getName()));
					}
					tree.expandPath(tree.getLeadSelectionPath());
				}
			}
			click = System.currentTimeMillis();
		}
	}

	@Override
	public final void mouseEntered(MouseEvent e) {}

	@Override
	public final void mouseExited(MouseEvent e) {}

	@Override
	public final void mousePressed(MouseEvent e) {}

	@Override
	public final void mouseReleased(MouseEvent e) {}
}