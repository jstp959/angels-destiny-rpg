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

class Player extends NonPlayerCharacter
{
protected int hitpoints = 10;
protected int maxhitpoints = 10;
protected MonsterDatabasePlayers characterdatabase = new MonsterDatabasePlayers();

public Player(int startx, int starty)
{
	super(startx,starty);
	direction = "down";

	maxhitpoints = hitpoints;

	addLeftImage("girlglassesleft-32x32-1.png");
	addLeftImage("girlglassesleft-32x32-2.png");
	addLeftImage("girlglassesleft-32x32-1.png");
	addLeftImage("girlglassesleft-32x32-3.png");
	addRightImage("girlglassesright-32x32-1.png");
	addRightImage("girlglassesright-32x32-2.png");
	addRightImage("girlglassesright-32x32-1.png");
	addRightImage("girlglassesright-32x32-3.png");
	addUpImage("girlglassesup-32x32-1.png");
	addUpImage("girlglassesup-32x32-2.png");
	addUpImage("girlglassesup-32x32-1.png");
	addUpImage("girlglassesup-32x32-3.png");
	addDownImage("girlglassesdown-32x32-1.png");
	addDownImage("girlglassesdown-32x32-2.png");
	addDownImage("girlglassesdown-32x32-1.png");
	addDownImage("girlglassesdown-32x32-3.png");
}

/*
public double distance(int x1, int x2, int y1, int y2)
{
	return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
}
public boolean collision(Entity e)
{
	if (distance(e.getx(), playerx, e.gety(), playery) < 30) {
		System.out.println("collision with player...gameover.");
		System.exit(99);	
		return true;
	}
	else
		return false;
}*/

public void hit(int index, int hp)
{
	hitpoints -= hp;

	characterdatabase.setMonsterHitpoints(index, hitpoints);

}

public String getPlayerName(int index)
{
	return characterdatabase.getMonsterName(index); 
}

public int getPlayerHitpoints(int index)
{
	return characterdatabase.getMonsterHitpoints(index); 
}
public int getPlayerMaxHitpoints(int index)
{
	return characterdatabase.getMonsterMaxHitpoints(index); 
}

public int getPlayerStrength(int index)
{
	return characterdatabase.getMonsterStrength(index); 
}

public int getPlayerDexterity(int index)
{
	return characterdatabase.getMonsterDexterity(index); 
}

public int getPlayerIntelligence(int index)
{
	return characterdatabase.getMonsterIntelligence(index); 
}

public int getPlayerConstitution(int index)
{
	return characterdatabase.getMonsterConstitution(index); 
}

public int getPlayerHitchance(int index)
{
	return characterdatabase.getMonsterHitchance(index); 
}

};
