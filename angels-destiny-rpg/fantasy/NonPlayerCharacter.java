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

/*
 * These are town map non player characters
*/
class NonPlayerCharacter extends NonPlayerCharacterBase 
{
protected StateImageLibrary leftimages;
protected StateImageLibrary rightimages;
protected StateImageLibrary upimages;
protected StateImageLibrary downimages;

protected boolean moving = false;
protected String direction = "down";

protected StateTextLibrary textlib = new StateTextLibrary();
protected StateTextLibrary asktextlib = new StateTextLibrary();

public NonPlayerCharacter(int startx, int starty, int startw, int starth)
{
	super(startx,starty,startw,starth);

	leftimages = new StateImageLibrary();
	rightimages = new StateImageLibrary();
	upimages = new StateImageLibrary();
	downimages = new StateImageLibrary();
	x = startx;
	y = starty;
}

public void addLeftImage(String filename)
{
	leftimages.addImage(filename);
}

public Image getLeftImage()
{
	return leftimages.getImage();
}

public Image getLeftImage(int index)
{
	return leftimages.getImage(index);
}

public void addRightImage(String filename)
{
	rightimages.addImage(filename);
}

public Image getRightImage()
{
	return rightimages.getImage();
}

public Image getRightImage(int index)
{
	return rightimages.getImage(index);
}

public void addUpImage(String filename)
{
	upimages.addImage(filename);
}

public Image getUpImage()
{
	return upimages.getImage();
}

public Image getUpImage(int index)
{
	return upimages.getImage(index);
}

public void addDownImage(String filename)
{
	downimages.addImage(filename);
}

public Image getDownImage()
{
	return downimages.getImage();
}

public Image getDownImage(int index)
{
	return downimages.getImage(index);
}

public Image getImage()
{

	if (direction == "left")
		if (!moving)
			return leftimages.getImage(2);
		else
			return leftimages.getImage();
	if (direction == "right")
		if (!moving)
			return rightimages.getImage(2);
		else
			return rightimages.getImage();
	if (direction == "up")
		if (!moving)
			return upimages.getImage(2);
		else
			return upimages.getImage();
	if (direction == "down")
		if (!moving)
			return downimages.getImage(2);
		else
			return downimages.getImage();
	if (!moving)
		return downimages.getImage(2);
	else
  		return downimages.getImage();
}

public double distance(int x1, int x2, int y1, int y2)
{
	return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
}

public boolean collision(Entity e, double mapx)
{
	if (distance(e.getx()-(int)mapx, x, e.gety(), y) < 20) {
		System.out.println("collision");	
		return true;
	}
	else
		return false;
}

public void settomoving(String d)
{
	direction = d;
	moving = true;
}
	
public void settonotmoving()
{
	moving = false;
}

public String getdirection()
{
	return direction;
}

public String talkto()
{
	return textlib.getText();
}

public String asktalkto(int index)
{
	return asktextlib.getText(index);
}

public int talktomaxindex()
{
	return textlib.getmax();
}

public int asktalktomaxindex()
{
	return asktextlib.getmax();
}
};
