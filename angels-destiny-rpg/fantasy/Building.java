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

/*
 * Rectangular Buildings
 */

/*
 * Code comment : mapx is the coordinate of the building on the map.
 * if the map moves, the coordinate gets relatively added to the map's
 * mapx etc.
 */

class Building extends BoundingBox implements BuildingBaseImage
{
	Image buildingbaseimage;//NOTE: maybe later versions of java support interface image variable

public Building(int startx, int starty, int startw, int starth, Image img)
{
	super(startx,starty,startw,starth);

	mapx = startx;
	mapy = starty;
	mapw = startw;
	maph = starth;

	buildingbaseimage = img;
}

public Image getImage() 
{
	return buildingbaseimage;
}

};
