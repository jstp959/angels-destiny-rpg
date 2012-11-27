package fantasy;
/*
Copyright (C) <year> <name of author>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.*;

class FantasyListWidget extends FantasyListWidgetBase
{
protected FantasySmallHandCursorWidget hand = new FantasySmallHandCursorWidget(0,0);//FIXME x y 
protected int size = 1; 
public FantasyListWidget(int startx, int starty, int sz)
{
	super(startx,starty);
	size = sz;
	hand.setx(startx);
	hand.sety(starty);

	///hand.addImage("widget-handcursor-20x20-1.png");
}

public int getsize()
{
	return size;
}

public void setsize(int sz)
{
	size = sz;
}

public Image getHandImage()
{
	return hand.getImage();
}

public void sethandx(int xx)
{
	hand.setx(xx);
}

public void sethandy(int yy)
{
	hand.sety(yy);
}

public int gethandx()
{
	return hand.getx();
}

public int gethandy()
{
	return hand.gety();
}

};
