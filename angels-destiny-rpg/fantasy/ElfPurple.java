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

class ElfPurple extends TownsFolk 
{

public ElfPurple(int startx, int starty, String language)
{
	super(startx,starty);

	direction = "down";

	addDownImage("elfpurple-48x48-1.png");

	if (language == "Dutch" || language == "dutch") {

		textlib.addText("Waar je nu bent is een kerker geweest..");
		textlib.addText("maar ik heb hem tot mijn huis gemaakt.");

		asktextlib.addText("Hallo vriend.");
		itemtextlib.addText("Ik heb een kort zwaard voor mezelf.");

	} else if (language == "English" || language == "english") {

		textlib.addText("This used to be a dungeon..");
		textlib.addText("Now it is my home.");

		asktextlib.addText("Hello friend.");
		itemtextlib.addText("I have a short sword for my own use.");
	} else {

		textlib.addText("This used to be a dungeon..");
		textlib.addText("Now it is my home.");

		asktextlib.addText("Hello friend.");
		itemtextlib.addText("I have a short sword for my own use.");
	}
}

};
