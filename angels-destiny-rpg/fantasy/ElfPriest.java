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

class ElfPriest extends TownsFolk 
{

public ElfPriest(int startx, int starty, String language)
{
	super(startx,starty);

	direction = "down";

	addDownImage("elfherbist-48x48-1.png");

	if (language == "Dutch" || language == "dutch") {

		textlib.addText("Ik ben de priester.");
		//set to "-1" for not popping up the learn widget
		learnvarlib.addText("-1");

		asktextlib.addText("Uw naam weze gezegend.");
		itemtextlib.addText("Mooi onderhouden zwaard.");
		learntextlib.addText("Dulandar betekent hemel op aarde.");
		learnedanotherwordtextlib.addText("Elfen zijn stoicijns.");
	} else if (language == "English" || language == "english") {

		textlib.addText("I am the priest around here.");
		learnvarlib.addText("-1");

		asktextlib.addText("Your name be blessed.");
		itemtextlib.addText("Looks like a fine sword to me.");
		learntextlib.addText("Dulandar means heaven on earth.");
		learnedanotherwordtextlib.addText("Elves are stoic.");
	} else {


		textlib.addText("I am the priest around here.");
		learnvarlib.addText("-1");

		asktextlib.addText("Your name be blessed.");
		itemtextlib.addText("Looks like a fine sword to me.");
		learntextlib.addText("Dulandar means heaven on earth.");
		learnedanotherwordtextlib.addText("Elves are stoic.");
	}
}

};
