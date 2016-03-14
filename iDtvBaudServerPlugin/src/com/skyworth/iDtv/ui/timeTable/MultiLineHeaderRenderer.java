package com.skyworth.iDtv.ui.timeTable;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class MultiLineHeaderRenderer extends JTextArea implements
        TableCellRenderer, Serializable {

    private static final long serialVersionUID = -3199519309107336779L;


    public MultiLineHeaderRenderer() {
        super(2, 20);
        setOpaque(false);
        setAlignmentX(JTextArea.CENTER_ALIGNMENT);
    }


    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (table != null) {
            JTableHeader header = table.getTableHeader();
            if (header != null) {
                setForeground(header.getForeground());
                setBackground(header.getBackground());
                setFont(header.getFont());
            }
        }
        String s = null;
        if (value != null) {
            String string = value.toString();
            String[] split = string.split(" ");
            s = split[0];
            for (int i = 1; i < split.length; i++) {
                s += "\n";
                s += split[i];
            }
        }
        setText(s);
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        return this;
    }
}