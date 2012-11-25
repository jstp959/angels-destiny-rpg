package fantasy;
/*
Copyright (C) 2012 Johan Ceuppens

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

class Map 
{
protected int mapx, mapy, mapw, maph, dx, dy;
protected Image mapimg;
String prefix = "./pics/";

public Map(int startx, int starty, int startw, int starth, Image img, int dxx, int dyy)
{
	mapx = startx;
	mapy = starty;
	mapw = startw;
	maph = starth;
	dx = dxx;
	dy = dyy;
	mapimg = img;
}

public int getdx()
{
	return dx;
}

public int getdy()
{
	return dy;
}

public int getx()
{
	return mapx;
}

public int gety()
{
	return mapy;
}

public void moveleft() 
{
	mapx-=5;
	dx-=5;
}
public void moveright() 
{
	mapx+=5;
	dx+=5;
}
public void moveup() 
{
	mapy-=5;
	dy-=5;
}
public void movedown() 
{
	mapy+=5;
	dy+=5;
}
public Image getImage()
{
	return mapimg;
}

};
