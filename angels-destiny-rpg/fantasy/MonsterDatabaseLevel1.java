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
 */

class MonsterDatabaseLevel1 extends MonsterDatabase
{

public MonsterDatabaseLevel1()
{
	super();

	//add slime monster
	monsternames.add("slime");
	monsterhitpoints.add("21");	
	monsterstrengths.add("2");
	monsterdexterities.add("2");
	monsterintelligences.add("4");
	monsterconstitutions.add("3");
	monsterhitchances.add("3");

	//add goblin monster
////////monsternames.add("goblin");
}

};
