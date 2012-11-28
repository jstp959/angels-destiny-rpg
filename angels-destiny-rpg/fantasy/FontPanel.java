package fantasy;
/*
Copyright (C) <year> <name of author>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.*;

public class FontPanel extends JPanel {
    ////Font smallfont = new Font("Monospaced", Font.PLAIN, 18);
    Font smallfont = new Font("Helvetica", Font.BOLD, 14);

    public FontPanel() {

        //addKeyListener(new TAdapter());
        setBackground(Color.blue);
        //setDoubleBuffered(true);
        //timer = new Timer(40, this);
        //timer.start();

	listfonts();
    }

    public void paint(Graphics g)
    {
	super.paint(g);
	Graphics2D g2d = (Graphics2D) g;

	g2d.setColor(Color.white);
	g2d.setFont(smallfont);
	g2d.drawString("foo",100,100);
    }

    public void listfonts()
    {
	String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	for (String fontName : fontNames) {
		Font f = new Font(fontName, Font.PLAIN, 12);
		if (f.canDisplay('a')) {
			//System.out.println(fontName);
		}
	}
    }

};
