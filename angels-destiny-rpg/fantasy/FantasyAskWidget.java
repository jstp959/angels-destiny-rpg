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

class FantasyAskWidget extends FantasyWidget
{
protected FantasyAskListVerticalWidget listwidget = new FantasyAskListVerticalWidget(0,0); 

protected Image backgroundimage;
protected String prefix = "pics/";
public FantasyAskWidget(int sx, int sy, int sz)
{
	super(sx,sy);
	listwidget.setx(sx);
	listwidget.sety(sy);
	listwidget.setsize(sz);

	backgroundimage = new ImageIcon(prefix+"askwidgetlistbackground-1.png").getImage();
	///addImage("talkwidget-1.png");//FIXME
}

public Image getBackgroundImage()
{
	return backgroundimage;
}

public Image getListImage()
{
	return listwidget.getImage();
}

public void movehandup()
{
	listwidget.movehandup();
}

public void movehanddown()
{
	listwidget.movehanddown();
}

public int gethandx()
{
	return listwidget.gethandx();
}

public int gethandy()
{
	return listwidget.gethandy();
}

public void sethandx(int xx)
{
	listwidget.sethandx(xx);
}

public void sethandy(int yy)
{
	listwidget.sethandx(yy);
}

public int getindex()
{
	return listwidget.getindex();
}

public int getListSize()
{
	return listwidget.getsize();
}

public Image getHandImage()
{
	return listwidget.getHandImage();
}

public void setindex(int idx)
{
	listwidget.setindex(idx);
}

};
