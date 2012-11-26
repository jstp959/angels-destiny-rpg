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

class MonsterDatabase
{
protected LinkedList monsternames = new LinkedList();
protected LinkedList monsterhitpoints = new LinkedList();
protected LinkedList monsterstrengths = new LinkedList();
protected LinkedList monsterdexterities = new LinkedList();
protected LinkedList monsterintelligences = new LinkedList();
protected LinkedList monsterconstitutions = new LinkedList();
protected LinkedList monsterhitchances = new LinkedList();

public MonsterDatabase()
{

}

public int size()
{
	return monsternames.size();
}

public String getMonsterName(int index)
{
	Object o = monsternames.get(index);
	String s = (String)o;
	return s;
}

public int getMonsterHitpoints(int index)
{
	Object o = monsterhitpoints.get(index);
	String s = (String)o;
	int r = Integer.parseInt(s);	
	return (int)r;
}

public int getMonsterStrength(int index)
{
	Object o = monsterstrengths.get(index);
	String s = (String)o;
	int r = Integer.parseInt(s);	
	return (int)r;
}

public int getMonsterDexterity(int index)
{
	Object o = monsterdexterities.get(index);
	String s = (String)o;
	int r = Integer.parseInt(s);	
	return (int)r;
}

public int getMonsterIntelligence(int index)
{
	Object o = monsterintelligences.get(index);
	String s = (String)o;
	int r = Integer.parseInt(s);	
	return (int)r;
}

public int getMonsterConstitution(int index)
{
	Object o = monsterconstitutions.get(index);
	String s = (String)o;
	int r = Integer.parseInt(s);	
	return (int)r;
}

public int getMonsterHitchance(int index)
{
	Object o = monsterhitchances.get(index);
	String s = (String)o;
	int r = Integer.parseInt(s);	
	return (int)r;
}

};
