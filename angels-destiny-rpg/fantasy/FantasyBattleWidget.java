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

class FantasyBattleWidget extends FantasyWidget
{
protected FantasyAttackListVerticalWidget listwidget = new FantasyAttackListVerticalWidget(0, 0);//set x y later on 
protected int listwidgetoffsetx = 50;
protected FantasyHitpointsWidget hitpointswidget = new FantasyHitpointsWidget(220,200-50);
protected int hitpointswidgetoffsetx = 220;
protected int hitpointswidgetoffsety = 10;

public FantasyBattleWidget(int sx, int sy)
{
	super(sx,sy);

	listwidget.setx(sx+listwidgetoffsetx);
	listwidget.sety(sy);
	listwidget.setsize(3);

        hitpointswidget.setx(sx+hitpointswidgetoffsetx);
        hitpointswidget.sety(sy+hitpointswidgetoffsety);

	addImage("battlewidget-1.png");
}

public Image getListImage()
{
	return listwidget.getImage();
}
public Image getHandImage()
{
	return listwidget.getHandImage();
}

public Image getHitpointsImage()
{
	return hitpointswidget.getImage();
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

};
