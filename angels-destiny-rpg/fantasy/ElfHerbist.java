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

class ElfHerbist extends TownsFolk 
{

public ElfHerbist(int startx, int starty, String language)
{
	super(startx,starty);

	direction = "down";

	addDownImage("elfgreen-48x48-1.png");

	if (language == "Dutch" || language == "dutch") {

		textlib.addText("Ik ben de kruidendokter.");
		textlib.addText("Ik zoek wilde ajuin : [img]onion-1.png[img].");
		//set to "-1" for not popping up the learn widget
		learnvarlib.addText("-1");
		learnvarlib.addText("-1");

		asktextlib.addText("Aangenaam.");
		itemtextlib.addText("Hopelijk heb je het niet nodig.");
		learntextlib.addText("Dulandar is dit elfen dorpje.");
		learnedanotherwordtextlib.addText("Elfen dansen en zingen graag.");
	} else if (language == "English" || language == "english") {

		textlib.addText("I am a herbist.");
		textlib.addText("I am looking for wild onion [img]onion-1.png[img].");
		learnvarlib.addText("-1");
		learnvarlib.addText("-1");

		asktextlib.addText("Pleased to meet you.");
		itemtextlib.addText("I hope you will not need it.");
		learntextlib.addText("Dulandar is this town, founded by elves.");
		learnedanotherwordtextlib.addText("Elves love to dance and sing.");
	} else {

		textlib.addText("There is trouble in the east..");
		textlib.addText("an old evil stirs again..");
		learnvarlib.addText("-1");
		learnvarlib.addText("-1");

		asktextlib.addText("Pleased to meet you.");
		itemtextlib.addText("I hope you will not need it.");
		learntextlib.addText("Dulandar is this town, founded by elves.");
		learnedanotherwordtextlib.addText("Elves love to dance and sing.");
	}
}

};
