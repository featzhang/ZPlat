package com.zys.utils.ui;

import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JDialog;

public class ZDialog extends JDialog {

	private static final long serialVersionUID = 2686603835992827906L;

	public ZDialog() {
		super();
		addAction();
	}

	Point loc = null;
	Point tmp = null;
	boolean isDragged = false;

	private void addAction() {
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {
				isDragged = false;
				ZDialog.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(java.awt.event.MouseEvent e) {
				tmp = new Point(e.getX(), e.getY());
				isDragged = true;
				ZDialog.this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			}
		});
		this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent e) {
				if (isDragged) {
					loc = new Point(ZDialog.this.getLocation().x + e.getX()
							- tmp.x, ZDialog.this.getLocation().y + e.getY()
							- tmp.y);
					ZDialog.this.setLocation(loc);
				}
			}
		});
	}

	public ZDialog(Dialog owner, boolean modal) {
		super(owner, modal);
		addAction();
	}

	public ZDialog(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		addAction();
	}

	public ZDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		addAction();
	}

	public ZDialog(Dialog owner, String title) {
		super(owner, title);
		addAction();
	}

	public ZDialog(Dialog owner) {
		super(owner);
		addAction();

	}

	public ZDialog(Frame owner, boolean modal) {
		super(owner, modal);
		addAction();

	}

	public ZDialog(Frame owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		addAction();

	}

	public ZDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		addAction();

	}

	public ZDialog(Frame owner, String title) {
		super(owner, title);
		addAction();

	}

	public ZDialog(Frame owner) {
		super(owner);
		addAction();

	}

	public ZDialog(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		addAction();

	}

	public ZDialog(Window owner, String title, ModalityType modalityType,
			GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		addAction();

	}

	public ZDialog(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		addAction();
	}

	public ZDialog(Window owner, String title) {
		super(owner, title);
		addAction();
	}

	public ZDialog(Window owner) {
		super(owner);
		addAction();

	}

}
