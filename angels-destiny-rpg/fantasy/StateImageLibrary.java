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

class StateImageLibrary
{

private LinkedList images = new LinkedList();//FIXME static array size
String prefix = "./pics/";

private int index;
private int max;

public StateImageLibrary()
{
	index = 0;
	max = 0;
}
public void addImage(String filename)
{
      Object img2 = new ImageIcon(prefix+filename).getImage();
      Image img = (Image)img2;
      images.add(img);
      max += 1;     	
}
public Image getImage()
{
      if (index >= max)
	index = 0;
      Object img = images.get(index++);
      return (Image)img;
}
public Image getImage(int idx)
{
      if (idx >= max)
	idx = 0;
      Object img = images.get(idx);
      return (Image)img;
}

};
