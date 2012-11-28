package fantasy;
/*
Copyright (C) 2012 Johan Ceuppens

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
import java.awt.*;
import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.*;
import java.util.Random;

public class Game extends JPanel implements ActionListener {
    String prefix = "./pics/";
    Dimension d;
    Font smallfont = new Font("Helvetica", Font.BOLD, 14);
    Font font2 = new Font("Serif", Font.PLAIN, 12);
    ///private int level = 1;
    double mapy = 0;
    double mapx = 0;
    double activationPixel = 0;
    private int SCREENWIDTH = 320;
    private int SCREENHEIGHT = 200;
    private int spritecount = 3;
    short screendata;
    Timer timer;

    String displaylanguage = "english";

    private Random rng = new Random();

    private LinkedList buildings = new LinkedList();
    private LinkedList gateways = new LinkedList();
    private int levelnumber = 1000;

    private SimpleMidiPlayer midiplayer = new SimpleMidiPlayer();

    //note that battlebackgrounddatabases are empty to start with and need to get filled every level initialisation
    private BattleBackgroundDatabase battlebackgrounddatabaselevel2000 = new BattleBackgroundDatabase();   
    private BattleBackgroundDatabase backgrounddatabaselevelXXXX = new BattleBackgroundDatabase();   
    private BattleBackgroundDatabase currentbattlebackgrounddatabase;
    private boolean battlebackgroundimageset = false;
    private Image battlebackgroundimage;

    private int overlandcitynumber = 1;
    private int overlandx = 0;
    private int overlandy = 0;
    private boolean levelnomonsters = true;
    private LinkedList nonplayercharacters = new LinkedList();
 
    private int numberofmonsters = 2; /////Random().nextInt(3)+1;
    private LinkedList monsters = new LinkedList();
    private MonsterDatabase monsterdatabase = new MonsterDatabaseLevel1();
    private CityNameDatabase citynamedatabase = new CityNameDatabase();
    private int drawcounter1 = 0;
    private int counter1 = 0;
    Player player = new Player(100,100);
    int playerindex = -1;//NOTE!
    int numberofplayercharacters = 1;//start the game with 1 player character
    Map map = new Map(0,0,640,640, new ImageIcon(prefix+"map-1024x1024-1.png").getImage(), 0, 0);

    FantasyBattleWidget battlewidget = new FantasyBattleWidget(0,200-64);
    int handcursordrawoffset = 20;//draw hand -20 pixels to the left
    int handcursormonsteroffset = 48;//hand jump offset between monsters on battle screen
    FantasyHandCursorWidget handcursorwidget = new FantasyHandCursorWidget(96,96);
    FantasyTalkWidget talkwidget = new FantasyTalkWidget(0,0);
    String currenttalktext = "";
    NonPlayerCharacter currentnonplayercharacter;
    int currenttalktextindex = -1;
    int currenttalktextmax = 0;
    int currentlearn = -1;
    AskWordDatabase askworddatabase = new AskWordDatabase();
    FantasyAskWidget askwidget = new FantasyAskWidget(0,0,askworddatabase.learnedwordsize());//NOTE! use setsize for enlarging the askwidget
    ItemWordDatabase itemworddatabase = new ItemWordDatabase();
    FantasyItemWidget itemwidget = new FantasyItemWidget(0,0,itemworddatabase.size());//NOTE! use setsize for enlarging the itemwidget
    LearnWordDatabase learnworddatabase = new LearnWordDatabase();
    FantasyLearnWidget learnwidget = new FantasyLearnWidget(0,0,learnworddatabase.size());//NOTE! use setsize for enlarging the learnwidget
    FantasyLearnWidget learnedanotherwordwidget = new FantasyLearnWidget(0,0,learnworddatabase.size());//NOTE! use setsize for enlarging the learnwidget


    boolean handmove = false;
    boolean learnedanotherwordmode = false;
    boolean askmode = false;
    boolean learnmode = false;
    boolean itemmode = false;
    boolean talk = false;
    boolean collidedwithnonplayercharacter = false;
    boolean gameover = false;
    boolean showintro = false;
    LinkedList introstrings = new LinkedList();
	//battle screen is on 
    boolean battle = false;//NOTE! false to start game
	//a battle phase is being displayed
    boolean battlegoingon = false;
    boolean chooseattackmode = false;
    boolean choosetalkmode = false;
    boolean talkmodeafterlearnedanotherword = false;
    boolean talkmodeafterask = false;
    boolean talkmodeafterlearn = false;
    boolean talkmodeafteritem = false;
    boolean attack = false;
 
    private int battlegridmonstertoattackx = 0;
    private int battlegridmonstertoattacky = 0;
    private String battlegridmonstertoattackname = "";
    BattleGrid battlegrid = new BattleGrid(6);

    public Game() {
	midiplayer.playfile("music/" + "wilderness.mid"); 

	/// intro string show setup
	introstrings.add("The land of Aricea was at peace");
	introstrings.add("For a long time...");
	introstrings.add("Celestia's denizens watched the lands");
	introstrings.add("But another power rose on another plane");
	introstrings.add("A mighty demon king arose...");
	introstrings.add("The battle between good and evil started..");
	introstrings.add("and Aricea was invaded..");
	introstrings.add("But there is still a light in the dark..");
	introstrings.add("a knighthood does not come easy...");

        addKeyListener(new TAdapter());
        setFocusable(true);
	
        d = new Dimension(800, 600);
        setBackground(Color.black);
        setDoubleBuffered(true);
        timer = new Timer(40, this);
        timer.start();

	loadlevel1000();


	//delete, for starting battle mode
				int randomnumber2 = rng.nextInt(4);
				numberofmonsters = randomnumber2 + 1;
				
				int number;
				for (number = 0; number < numberofmonsters; number++) {
					int randomnumber3 = rng.nextInt(monsterdatabase.size());
					if (levelnumber == 2000) 
						addMonsterLevel2000(randomnumber3, number);
				}
    }

    public void addNotify() {
        super.addNotify();
        GameInit();
    }

    //Inside the City of Dulandar
    public void loadlevel1000()
    {

        //midiplayer = new SimpleMidiPlayer();
	//midiplayer.playfile("music/" + "towntheme.mid"); 

	map.setxy(0,-75);

	levelnomonsters = true;

	drawcounter1 = 0;

	buildings.clear();
	gateways.clear();
	nonplayercharacters.clear();

    	map = new Map(0,0,1024,1024, new ImageIcon(prefix+"map-1024x1024-1.png").getImage(), 0, 0);
	map.setxy(overlandx,overlandy);

	buildings.add(new Building(0,0,1024,100,new ImageIcon(prefix+"wallrock-1024x100-1.png").getImage()));
	buildings.add(new Building(0,300,200,200,new ImageIcon(prefix+"building-house-herbist-200x200-2.png").getImage()));
	buildings.add(new Building(500,300,200,200,new ImageIcon(prefix+"building-house-priest-200x200-2.png").getImage()));
	buildings.add(new Building(420,280,64,64,new ImageIcon(prefix+"tomb-64x64-1.png").getImage()));
	buildings.add(new Building(430,340,64,64,new ImageIcon(prefix+"tomb-64x64-1.png").getImage()));
	buildings.add(new Building(425,400,64,64,new ImageIcon(prefix+"tomb-64x64-1.png").getImage()));
	buildings.add(new Building(432,460,64,64,new ImageIcon(prefix+"tomb-64x64-1.png").getImage()));
	buildings.add(new Building(300,600,200,200,new ImageIcon(prefix+"building-house-200x200-2.png").getImage()));
	buildings.add(new Building(800,400,200,200,new ImageIcon(prefix+"building-house-200x200-2.png").getImage()));

	//house gateways
	gateways.add(new Gateway(0,300+200,200,15,1001,-1,new ImageIcon(prefix+"nullimage.png").getImage(),0,0));
	gateways.add(new Gateway(500,300+200,200,15,1002,-1,new ImageIcon(prefix+"nullimage.png").getImage(),0,0));

	//exit map gateways
	gateways.add(new Gateway(0,0,1024,50,2000,-1,new ImageIcon(prefix+"nullimage.png").getImage(),-100,-100));
	gateways.add(new Gateway(0,1024,1024,50,2000,-1,new ImageIcon(prefix+"nullimage.png").getImage(),-100,-100));
	gateways.add(new Gateway(0,0,50,1024,2000,-1,new ImageIcon(prefix+"nullimage.png").getImage(),-100,-100));
	gateways.add(new Gateway(1024,0,50,1024,2,-1,new ImageIcon(prefix+"nullimage.png").getImage(),-100,-100));

	nonplayercharacters.add(new ElfGreen(163,500,displaylanguage));
	nonplayercharacters.add(new ElfGreen2(400,200,displaylanguage));

    }

    //Dulandar city - inside herbist house
    public void loadlevel1001()
    {

	levelnomonsters = true;

        overlandcitynumber = -1;

	drawcounter1 = 0;

	buildings.clear();
	gateways.clear();
	nonplayercharacters.clear();

    	map = new Map(0,0,320,200, new ImageIcon(prefix+"map-herbist-house-320x200-1.png").getImage(), 0, 0);
	map.setxy(overlandx,overlandy);

	buildings.add(new Furniture(50,50,24,64,new ImageIcon(prefix+"furniture-japan-24x64-1.png").getImage()));
	buildings.add(new Furniture(320-24-50,50,24,64,new ImageIcon(prefix+"furniture-japan-24x64-1.png").getImage()));
	//exit to map gateways
	gateways.add(new Gateway(0,200,320,50,1000,-1,new ImageIcon(prefix+"nullimage.png").getImage(),0,-420));

	nonplayercharacters.add(new ElfHerbist(73,50,displaylanguage));

    }

    //Dulandar city - inside priest house
    public void loadlevel1002()
    {

	levelnomonsters = true;

	drawcounter1 = 0;

        overlandcitynumber = -1;

	buildings.clear();
	gateways.clear();
	nonplayercharacters.clear();

    	map = new Map(0,0,320,200, new ImageIcon(prefix+"map-priest-house-320x200-1.png").getImage(), 0, 0);
	map.setxy(overlandx,overlandy);

	buildings.add(new Furniture(50,50,24,64,new ImageIcon(prefix+"furniture-japan-24x64-1.png").getImage()));
	buildings.add(new Furniture(320-24-50,50,24,64,new ImageIcon(prefix+"furniture-japan-24x64-1.png").getImage()));
	buildings.add(new Furniture(120,10,48,48,new ImageIcon(prefix+"macintosh-1.png").getImage()));
	//exit to map gateways
	gateways.add(new Gateway(0,200,320,50,1000,-1,new ImageIcon(prefix+"nullimage.png").getImage(),-500,-420));

	nonplayercharacters.add(new ElfPriest(83,10,displaylanguage));

    }



    public void loadlevel2000()
    {
        //midiplayer = new SimpleMidiPlayer();
	//midiplayer.playfile("music/" + "wilderness.mid"); 

	levelnomonsters = false;//you can be attacked in this map
        battlebackgrounddatabaselevel2000.addImage("battlestage-cave-1.png");   
        battlebackgrounddatabaselevel2000.addImage("battlestage-badlands-1.png");   
	currentbattlebackgrounddatabase = battlebackgrounddatabaselevel2000;

        overlandcitynumber = -1;

	drawcounter1 = 0;

	buildings.clear();
	gateways.clear();
	nonplayercharacters.clear();

    	map = new Map(0,0,1024,1024, new ImageIcon(prefix+"map-1024x1024-2.png").getImage(), 0, 0);

	map.setxy(overlandx,overlandy);

	gateways.add(new Gateway(0,350,48,48,3000,2,new ImageIcon(prefix+"gateway-1.png").getImage(),-100,-10));

    }

    public void loadlevel3000()
    {

	levelnomonsters = true;

	drawcounter1 = 0;

	buildings.clear();
	gateways.clear();
	nonplayercharacters.clear();

    	map = new Map(0,0,480,480, new ImageIcon(prefix+"map-480x480-1.png").getImage(), 0, 0);
	map.setxy(overlandx,overlandy);

	buildings.add(new Building(100,200,200,200,new ImageIcon(prefix+"building-house-200x200-2.png").getImage()));

	gateways.add(new Gateway(0,0,480,50,2000,-1,new ImageIcon(prefix+"nullimage.png").getImage(),80,-300));
	gateways.add(new Gateway(0,480,480,50,2000,-1,new ImageIcon(prefix+"nullimage.png").getImage(),0,-320));
	gateways.add(new Gateway(0,0,50,480,2000,-1,new ImageIcon(prefix+"nullimage.png").getImage(),0,-320));
	gateways.add(new Gateway(480,0,50,480,2000,-1,new ImageIcon(prefix+"nullimage.png").getImage(),0,-320));

	nonplayercharacters.add(new ElfPurple(163,125,displaylanguage));
	//monsters.add(new Slime(48,96)); //FIXME

    }


    public void addMonsterLevel2000(int index, int numberofmonster) {
	String monstername = monsterdatabase.getMonsterName(index);
	int hp = monsterdatabase.getMonsterHitpoints(index);
	//NOTE! different moduli
	if (monstername == "slime") {
		monsters.add(new Slime(48+(numberofmonster%3)*48,48+(numberofmonster%2)*48));

		//the attackx and y get set to the last monster generated for 
		//e.g. automatic battle play

		battlegridmonstertoattackx = 1 + numberofmonster % 3;
		battlegridmonstertoattacky = 1 + numberofmonster % 2;

		//sets slime monster on x and y

		battlegrid.set(battlegridmonstertoattackx-1, battlegridmonstertoattacky-1, "slime", hp);//NOTE! -1 

		//sets hand cursor to this slime monster

		battlewidget.sethandx(battlegridmonstertoattackx*48-handcursordrawoffset);
		battlewidget.sethandy(battlegridmonstertoattacky*48);
	} else {
		monsters.add(new Slime(48+(numberofmonster%4)*48,48+(numberofmonster%2)*48));
		battlegridmonstertoattackx = 1 + numberofmonster % 4;
		battlegridmonstertoattacky = 1 + numberofmonster % 2;
		battlegrid.set(battlegridmonstertoattackx-1, battlegridmonstertoattacky-1, "slime", hp);
		battlewidget.sethandx(battlegridmonstertoattackx*48-handcursordrawoffset);
		battlewidget.sethandy(battlegridmonstertoattacky*48);
	}

    }
    
    public void DrawMap(Graphics2D g2d) {
        g2d.drawImage(map.getImage(), map.getx(), map.gety(), this);
    }

    public void DrawBuildingsOnMap(Graphics2D g2d)
    {
	int i;
	for ( i = 0; i < buildings.size(); i++) {

		Object o = buildings.get(i);
		Building b = (Building)o; 
        	g2d.drawImage((Image)b.getImage(), b.getx()+map.getx(), b.gety()+map.gety(), this);
		

	}

     }

    public void DrawGatewaysOnMap(Graphics2D g2d)
    {
	int i;
	for ( i = 0; i < gateways.size(); i++) {

		Object o = gateways.get(i);
		Gateway b = (Gateway)o; 
        	g2d.drawImage((Image)b.getImage(), b.getx()+map.getx(), b.gety()+map.gety(), this);
		

	}

     }


/*
 * drawing talk
 */

    public void DrawAskLearnedWords(Graphics g2d) {
      
	g2d.setColor(Color.white);
    	Font fontfoo = new Font("Serif", Font.PLAIN, 17);
        g2d.setFont(fontfoo);


	int i;
	for (i = 0; i < askworddatabase.learnedwordsize(); i++) {

		g2d.drawString(askworddatabase.getLearnedWord(i), 10, (i+1)*21);
	}
    }

    public void DrawAskBackgroundWidget(Graphics g2d) {
	g2d.drawImage(askwidget.getBackgroundImage(), 0, 0, this);//FIXME fixed size
    }

    public void DrawTalkBackgroundWidget(Graphics g2d) {
	g2d.drawImage(talkwidget.getBackgroundImage(), 0, 0, this);//FIXME fixed size
    }

    public void DrawTalkListWidget(Graphics g2d) {
	g2d.drawImage(talkwidget.getListImage(), 48, 200-64, this);//FIXME fixed size
    }

    public void DrawTalkWidget(Graphics g2d) {
	g2d.drawImage(talkwidget.getImage(), 48, 200-64, this);
    }

    public void DrawTalkWidgetHandCursor(Graphics g2d) {
	g2d.drawImage(talkwidget.getHandImage(), talkwidget.gethandx()+50, talkwidget.gethandy(), this);//FIXME + 50
    }

    public void DrawAskWidgetHandCursor(Graphics g2d) {
	g2d.drawImage(askwidget.getHandImage(), askwidget.gethandx()+50, askwidget.gethandy(), this);//FIXME + 50
    }

    public void DrawItemLearnedWords(Graphics g2d) {
      
	g2d.setColor(Color.white);
    	Font fontfoo = new Font("Serif", Font.PLAIN, 17);
        g2d.setFont(fontfoo);


	int i;
	for (i = 0; i < itemworddatabase.size(); i++) {

		g2d.drawString(itemworddatabase.getItemWord(i), 10, (i+1)*21);
	}
    }

    public void DrawItemBackgroundWidget(Graphics g2d) {
	g2d.drawImage(itemwidget.getBackgroundImage(), 0, 0, this);//FIXME fixed size
    }

    public void DrawItemWidgetHandCursor(Graphics g2d) {
	g2d.drawImage(itemwidget.getHandImage(), itemwidget.gethandx()+50, itemwidget.gethandy(), this);//FIXME + 50
    }

    public void DrawLearnLearnedWords(Graphics g2d) {
      
	g2d.setColor(Color.white);
    	Font fontfoo = new Font("Serif", Font.PLAIN, 13);
        g2d.setFont(fontfoo);


	int i;
	for (i = 0; i < learnworddatabase.size(); i++) {

		g2d.drawString(learnworddatabase.getWord(i), 10, (i+1)*21);
	}
    }

    public void DrawLearnBackgroundWidget(Graphics g2d) {
	g2d.drawImage(learnwidget.getBackgroundImage(), 0, 0, this);//FIXME fixed size
    }

    public void DrawLearnWidgetHandCursor(Graphics g2d) {
	g2d.drawImage(learnwidget.getHandImage(), learnwidget.gethandx()+50, learnwidget.gethandy(), this);//FIXME + 50
    }

    public void DrawLearnedanotherwordLearnedWords(Graphics g2d) {
      
	g2d.setColor(Color.white);
    	Font fontfoo = new Font("Serif", Font.PLAIN, 13);
        g2d.setFont(fontfoo);


	int i;
	for (i = 0; i < learnworddatabase.size(); i++) {

		g2d.drawString(learnworddatabase.getWord(i), 10, (i+1)*21);
	}
    }

    public void DrawLearnedanotherwordBackgroundWidget(Graphics g2d) {
	g2d.drawImage(learnedanotherwordwidget.getBackgroundImage(), 0, 0, this);//FIXME fixed size
    }

    public void DrawLearnedanotherwordWidgetHandCursor(Graphics g2d) {
	g2d.drawImage(learnedanotherwordwidget.getHandImage(), learnedanotherwordwidget.gethandx()+50, learnedanotherwordwidget.gethandy(), this);//FIXME + 50
    }

/*
 * drawing battles 
 */ 

    public void DrawBattleMonsterHandCursor(Graphics g2d) {
	g2d.drawImage(handcursorwidget.getImage(), handcursorwidget.getx()-handcursordrawoffset, handcursorwidget.gety(), this);
    }

    public void DrawBattleWidget(Graphics g2d) {
	g2d.drawImage(battlewidget.getImage(), battlewidget.getx(), battlewidget.gety(), this);
    }

    public void DrawBattleHitpointsWidget(Graphics g2d) {
	g2d.drawImage(battlewidget.getHitpointsImage(), battlewidget.getx()+200, battlewidget.gety()+10, this);
	int i;
        for (i = 0; i < numberofplayercharacters; i++) {
      		g2d.setColor(Color.white);
    		g2d.drawString("" + player.getPlayerName(i) + "  " + player.getPlayerHitpoints(i) + "/" + player.getPlayerMaxHitpoints(i), battlewidget.getx()+210, battlewidget.gety()+30);
	}
    }

    public void DrawBattleWidgetHandCursor(Graphics g2d) {
	g2d.drawImage(battlewidget.getHandImage(), /*battlewidget.getx() +*/ battlewidget.gethandx(),/*battlewidget.gety()+*/ battlewidget.gethandy(), this);//FIXME
    }

    public void DrawBattleWidgetList(Graphics g2d) {
	g2d.drawImage(battlewidget.getListImage(), battlewidget.getx(), battlewidget.gety(), this);
    }

    public void DrawBattleStage(Graphics g2d) {
	//draw battle stage in combination with map environment
	////////g2d.drawImage(new ImageIcon(prefix+"battlestage-1.png").getImage(), 0, 0, this);
	g2d.drawImage(battlebackgroundimage, 0, 0, this);
    }

    public void DrawBattlePlayer(Graphics g2d, int dx, int dy) {
	//draw battle stage in combination with map environment
	g2d.drawImage(player.getLeftImage(2), 250+dx, 96+dy, this);
    }

    public void DrawBattleMonsters(Graphics2D g2d)
    {
	int i;
	for ( i = 0; i < monsters.size(); i++) {

		Object o = monsters.get(i);
		Monster m = (Monster)o; 
        	g2d.drawImage((Image)m.getImage(), m.getx(), m.gety(), this);
		

	}

     }
/*
 * drawing map game entities
 */

    public void DrawPlayer(Graphics2D g2d) {
       	g2d.drawImage(player.getImage(), player.getx(), player.gety(), this);
    }

    public void DrawNonPlayerCharacters(Graphics2D g2d)
    {
	int i;
	for ( i = 0; i < nonplayercharacters.size(); i++) {

		Object o = nonplayercharacters.get(i);
		NonPlayerCharacter npc = (NonPlayerCharacter)o; 
        	g2d.drawImage((Image)npc.getImage(), npc.getx()+map.getx(), npc.gety()+map.gety(), this);
		

	}

     }

/*
 * Collision detection code
 */

    public boolean collision(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
	if (x1 > x2 && y1 > y2 && x1 < x2 + w2 && y1 < y2 + h2)//FIXME
		return true;
	else
		return false;
    }

    public int CollideGateways()
    {
	int i;
	boolean collide = false;

	for ( i = 0; i < gateways.size(); i++) {

		Object o = gateways.get(i);
		Gateway b = (Gateway)o; 
		
		collide = collision(player.getx(), player.gety(), 32,32, b.getx()+map.getx(), b.gety()+map.gety(), b.getw(), b.geth()); //FIXME fixed width&height of player
		if (collide) {
			overlandcitynumber = b.getoverlandcitynumber();
			overlandx = b.getxoffset();
			overlandy = b.getyoffset();
			return b.getid();
		}
	}

	return -1;
     }

    public boolean CollideBuildings()
    {
	int i;
	boolean collide = false;

	for ( i = 0; i < buildings.size(); i++) {

		Object o = buildings.get(i);
		Building b = (Building)o; 
		
		collide = collision(player.getx(), player.gety(), 32,32, b.getx()+map.getx(), b.gety()+map.gety(), b.getw(), b.geth()); //FIXME fixed width&height of player
		if (collide) 
			return collide;
	}

	return collide;
     }

    public boolean SetTexts()
    {

		NonPlayerCharacter b = currentnonplayercharacter;

		if (talkmodeafterlearnedanotherword) {
			currenttalktext = b.learnedanotherwordtalkto();
			currenttalktextmax = b.learnedanotherwordtalktomaxindex();
		}
		else if (talkmodeafterlearn) {//FIXME elses
			currenttalktext = b.learntalkto();
			currenttalktextmax = b.learntalktomaxindex();
		}
		else if (talkmodeafteritem) {
			currenttalktext = b.itemtalkto();
			currenttalktextmax = b.itemtalktomaxindex();
		}
		else if (talkmodeafterask) {
			currenttalktext = b.asktalkto();
			currenttalktextmax = b.asktalktomaxindex();
		}
		else if (!talkmodeafterask || !talkmodeafteritem || !talkmodeafterlearn || !talkmodeafterlearnedanotherword) {
			currenttalktext = b.talkto();
			currenttalktextmax = b.talktomaxindex();
			currentlearn = b.learn();
		}
	return true;
    }

    public boolean CollideNonPlayerCharacters()
    {
	int i;
	boolean collide = false;

	for ( i = 0; i < nonplayercharacters.size(); i++) {

		Object o = nonplayercharacters.get(i);
		NonPlayerCharacter b = (NonPlayerCharacter)o; 
		
		collide = collision(player.getx(), player.gety(), 32,32, b.getx()+map.getx(), b.gety()+map.gety(), b.getw(), b.geth()); //FIXME fixed width&height of player

		if (talkmodeafterlearnedanotherword) {
			currenttalktext = b.learnedanotherwordtalkto();
			currenttalktextmax = b.learnedanotherwordtalktomaxindex();
		}
		else if (talkmodeafterlearn) {//FIXME elses
			currenttalktext = b.learntalkto();
			currenttalktextmax = b.learntalktomaxindex();
		}
		else if (talkmodeafteritem) {
			currenttalktext = b.itemtalkto();
			currenttalktextmax = b.itemtalktomaxindex();
		}
		else if (talkmodeafterask) {
			currenttalktext = b.asktalkto();
			currenttalktextmax = b.asktalktomaxindex();
		}
		else if (!talkmodeafterask || !talkmodeafteritem || !talkmodeafterlearn || !talkmodeafterlearnedanotherword) {
			currenttalktext = b.talkto();
			currenttalktextmax = b.talktomaxindex();
			currentlearn = b.learn();
		}
		currentnonplayercharacter = b;

		if (collide) 
			return collide;
	}

	return collide;
     }

    public void GameInit() {
        LevelInit();
    }


    public void LevelInit() {
//        int i;
//        for (i = 0; i < nrofblocks * nrofblocks; i++)
//            screendata[i] = leveldata[i];
//
    }


    public void GetImages()
    {
    /**********  
      explosion1 = new ImageIcon(prefix+"explosion48x35-1.png").getImage();//prefix+"ghost.png")).getImage();
      tile1 = new ImageIcon(prefix+"tile60x60-1.png").getImage();//prefix+"ghost.png")).getImage();
      tile2 = new ImageIcon(prefix+"tile60x60-2.png").getImage();//prefix+"ghost.png")).getImage();
      bg1 = new ImageIcon(prefix+"bg800x1200-1.png").getImage();//prefix+"ghost.png")).getImage();
      //bg2 = new ImageIcon(prefix+"bg1200x800-2.png").getImage();//prefix+"ghost.png")).getImage();
      bg2 = new ImageIcon(prefix+"bg4000x600-3.png").getImage();//prefix+"ghost.png")).getImage();
      bg3 = new ImageIcon(prefix+"bg4000x600-4.png").getImage();//prefix+"ghost.png")).getImage();
      //player = new ImageIcon(Board.class.getResource("/Users/roguelord/java/2d/shooter/pics/ghost.png")).getImage();//prefix+"ghost.png")).getImage();
****************/
    }

	
    public void paint(Graphics g)
    {
      Graphics2D g2d = (Graphics2D) g;
	//FIXME use some return and else statements for speed
	if (gameover) {
      		Image gameoverimage = new ImageIcon(prefix+"gameoverscreen.png").getImage();
    		Font fontfoo = new Font("Serif", Font.PLAIN, 20);
        	g2d.setFont(fontfoo);
      		g2d.setColor(Color.white);
		
        	g2d.fillRect(0, 0, SCREENWIDTH, SCREENHEIGHT);
        	g2d.drawImage(gameoverimage, 0,0, this);
		String str = "The demon king prevails!";
       		g2d.drawString(str, 10,200);
		repaint();
		return;
	}

	if (showintro) {
		float DELTA = -0.01f;
		float alpha = 1f;

		setOpaque(true);
		setBackground(Color.black);
        	g2d.setFont(font2);
		int i = 0;

		for (i = 0; i < introstrings.size(); i++) {
			alpha = 1f;
			for ( ; alpha >= 0; alpha += DELTA) {
			
        			g2d.fillRect(0, 0, SCREENWIDTH, SCREENHEIGHT);
        			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
        			g2d.setPaint(Color.white);

				Object o = introstrings.get(i);
				String introstr = (String)o;
        			g2d.drawString(introstr, 10,100);
				repaint();
			}
			/**try {
				Thread.currentThread().sleep(700);//FIXME battle timer wait!
			}
			catch(InterruptedException ie){}
			**/
		}
	}

      g2d.setColor(Color.black);
      g2d.fillRect(0, 0, d.width, d.height);

	//map screen
      if (!battle) {
	DrawMap(g2d);	
	DrawBuildingsOnMap(g2d);	
	DrawGatewaysOnMap(g2d);	
	DrawNonPlayerCharacters(g2d);	
	DrawPlayer(g2d);

	if (overlandcitynumber >= 0 && drawcounter1++ < 108) {
    		Font fontfoo = new Font("Helvetica", Font.PLAIN, 18);
        	g2d.setFont(fontfoo);
      		g2d.setColor(Color.lightGray);
		g2d.drawString(citynamedatabase.getCityName(overlandcitynumber-1), 100, 30);//NOTE - 1
	}

	if (talk && collidedwithnonplayercharacter) {

		if (askmode) {

			DrawAskBackgroundWidget(g2d);
			DrawAskLearnedWords(g2d);
			DrawAskWidgetHandCursor(g2d);	
			
		}

		if (itemmode) {

			DrawItemBackgroundWidget(g2d);
			DrawItemLearnedWords(g2d);
			DrawItemWidgetHandCursor(g2d);	
			
		}

		if (learnmode) {

			DrawLearnBackgroundWidget(g2d);
			DrawLearnLearnedWords(g2d);
			DrawLearnWidgetHandCursor(g2d);	
			
		}

		if (learnedanotherwordmode) {

			DrawLearnedanotherwordBackgroundWidget(g2d);
			DrawLearnedanotherwordLearnedWords(g2d);
			DrawLearnedanotherwordWidgetHandCursor(g2d);	
			
		}

		if (!learnedanotherwordmode && !learnmode && !itemmode && !talkmodeafterask && !talkmodeafteritem && !talkmodeafterlearn && !talkmodeafterlearnedanotherword && !askmode && !choosetalkmode && currenttalktextmax-1 == currenttalktextindex) {	
			DrawTalkWidget(g2d);
			DrawTalkListWidget(g2d);
			//currenttalktextindex = -1;

			choosetalkmode = true;
		}

		if ((!learnmode && !askmode && !itemmode && !learnedanotherwordmode) || talkmodeafterask || talkmodeafteritem || talkmodeafterlearn || talkmodeafterlearnedanotherword) {//FIXME !itemmode ?
			DrawTalkBackgroundWidget(g2d);
			if (choosetalkmode) {

				DrawTalkWidget(g2d);
				DrawTalkListWidget(g2d);
			
				DrawTalkWidgetHandCursor(g2d);	

			}
      			g2d.setColor(Color.white);
    			Font fontfoo = new Font("Serif", Font.PLAIN, 17);
        		g2d.setFont(fontfoo);

			//display images inside text widget e.g. onion, 1 at a time
			String imagefilename = "pics/" + ParseTalkImage(currenttalktext);
			String textwithoutfilename = ParseTalkImageText(currenttalktext);


			if (imagefilename != "") {
				g2d.drawString(textwithoutfilename,20, 20);
        			g2d.drawImage(new ImageIcon(prefix+imagefilename).getImage(), 320-64, 25, this);
			} else {
				g2d.drawString(currenttalktext,20, 20);
			}

			if (currentlearn > 0) {
				learnedanotherwordmode = true;
				talkwidget.setindex(currentlearn);
				learnworddatabase.addWord(learnworddatabase.getNewWord(currentlearn));//if the word is already in the db it does not learn it again
	
				learnedanotherwordwidget.setListSize(learnedanotherwordwidget.getListSize() + 1);

				currentnonplayercharacter.erasecurrent(currentlearn);	// do not learn again

				currentlearn = -1;
			}

		}
	}
      } else if (battle) {//battle screen

	if (battlebackgroundimageset) {
		DrawBattleStage(g2d);
	} else {
        	//midiplayer = new SimpleMidiPlayer();
		//midiplayer.playfile("music/" + "battle.mid");

		int randomnumber = rng.nextInt(currentbattlebackgrounddatabase.size());
		battlebackgroundimage = currentbattlebackgrounddatabase.getImage(randomnumber); 
		battlebackgroundimageset = true;
	}

	DrawBattleMonsters(g2d);
	DrawBattlePlayer(g2d,0,0);
	DrawBattleWidget(g2d);
	DrawBattleHitpointsWidget(g2d);
	if (!battlegoingon) {
		if (chooseattackmode) {
			battlewidget.sethandx(battlewidget.getx());//redundant
			battlewidget.sethandy(battlewidget.gety());
			DrawBattleWidgetList(g2d);
			DrawBattleWidgetHandCursor(g2d);
		} else if (!chooseattackmode) {
			DrawBattleMonsterHandCursor(g2d);
		}	
	} else if (battlegoingon) {
            int monsterindex;
	    for (monsterindex = 0; monsterindex < battlegrid.getsizex()*battlegrid.getsizey(); monsterindex++) {
		int randomnumber = rng.nextInt(60);
		if (randomnumber == 0) {	//monster attacks first
			int randomnumber2 = rng.nextInt(numberofmonsters);
			
			int gridxx, gridyy;

			for (gridyy = 0; gridyy < battlegrid.getsizey(); gridyy++) {	
				for (gridxx = 0; gridxx < battlegrid.getsizex(); gridxx++) {

					if (randomnumber2 == gridxx + gridyy*battlegrid.getsizex()) {

						String monstername = battlegrid.get(gridxx,gridyy);
						if (monstername != "none") {
						
							String str = DoMonsterAttack(gridxx, gridyy);
						        g2d.setColor(Color.white);
        						g2d.setFont(smallfont);
        						g2d.drawString(str, player.getx(), player.gety());
							try {
								Thread.currentThread().sleep(1000);//FIXME battle timer wait!
							}
							catch(InterruptedException ie){}
							
						}

					}

							

					}
				}
			}
		}

		//walk through all charcters to let them attack

        	int i;
        	for (i = 0; i < numberofplayercharacters; i++) {
			//leveraging player towards left before he/she fights
			int j;
			for (j = 0; j < 48; j+=2) {
      				g2d.setColor(Color.white);
      				g2d.fillRect(0, 0, d.width, d.height);

				DrawBattlePlayer(g2d,-j,0);
				try {
					Thread.currentThread().sleep(20);//FIXME battle timer wait!
				} catch(InterruptedException ie){}
				
      				Toolkit.getDefaultToolkit().sync();
      				g.dispose();

			}

			String str = DoPlayerAttack(i);

			g2d.setColor(Color.white);
        		g2d.setFont(smallfont);
        		g2d.drawString(str, battlegridmonstertoattackx, battlegridmonstertoattacky);
			try {
				Thread.currentThread().sleep(1000);//FIXME battle timer wait!
			}
			catch(InterruptedException ie){}
			//leveraging player towards left before he/she fights
			for (j = 0; j < 48; j+=2) {
      				g2d.setColor(Color.white);
      				g2d.fillRect(0, 0, d.width, d.height);

				DrawBattlePlayer(g2d,j,0);
				try {
					Thread.currentThread().sleep(20);//FIXME battle timer wait!
				} catch(InterruptedException ie){}
				
      				Toolkit.getDefaultToolkit().sync();
      				g.dispose();
			}
		}

		//let monsters attack
		int gridxx, gridyy;
		for (gridyy = 0; gridyy < battlegrid.getsizey(); gridyy++) {	
			for (gridxx = 0; gridxx < battlegrid.getsizex(); gridxx++) {
			
				int randomnumber4 = rng.nextInt(3);//FIXME use hitchance
				if (randomnumber4 != 0 && randomnumber4 != 1)
					DoMonsterAttack(gridxx, gridyy);
			}
		}

		battlegoingon = false;
		chooseattackmode = false;	
		}		
	}
      
      Toolkit.getDefaultToolkit().sync();
      g.dispose();

    }

    /*
     */

    public String DoPlayerAttack(int index)
    {
	String monstername = battlegrid.get(battlegridmonstertoattackx-1,battlegridmonstertoattacky-1);
	if (monstername == "none") {//no monster selected
		/********int i;
		int randomnumber = rng.nextInt(numberofmonsters);
	
		//skip a few monsters so the attacked monster gets randomized	
		for (i = 0; i < (randomnumber * battlegrid.getsize()); i++)//FIXME fixed size 6
			;

		String s = battlegrid.get(i % battlegrid.getsizex(), i % battlegrid.getsizey());
			if (s == "none")//never reached
				return "none";

		*******/
		int j;
		//for (j = 0; j < monsters.size(); j++) {
			Object o = monsters.get(0);
			Monster mo = (Monster)o;
		
		//	battlegridmonstertoattackx = 1 + mo.getx() / 48;
		//	battlegridmonstertoattacky = 1 + mo.gety() / 48;
		//}
	}
		
	int chancetohit = player.getPlayerHitchance(index);

	int randomnumber = rng.nextInt(chancetohit);
	if (randomnumber == 0)//player fails to hit
		return "Miss!";

	int str = player.getPlayerStrength(index);
	int randomnumber2 = rng.nextInt(str) + 1;
	int die = battlegrid.hit(battlegridmonstertoattackx-1, battlegridmonstertoattacky-1, randomnumber2);
	if (die <= 0) {

		battlegrid.set(battlegridmonstertoattackx-1, battlegridmonstertoattacky-1, "none", 0);//FIXME "0"

		int j;
		for (j = 0; j < monsters.size(); j++) {
			Object o = monsters.get(j);
			//instanceof
			Monster m = (Monster)o;

			//System.out.println("m.x= " + m.getx()/48 + " m.y= " + m.gety()/48 + " x=" + battlegridmonstertoattackx +" y=" + battlegridmonstertoattacky);

			if (battlegridmonstertoattackx == m.getx() / 48 && battlegridmonstertoattacky == m.gety() / 48) {
				monsters.remove(j);
				numberofmonsters -= 1;

				if (monsters.size() <= 0) {

					battlegoingon = false;
					chooseattackmode = false;
					attack = false;
					battle = false;
					
					battlebackgroundimageset = false;
					counter1 = 0;

					return "0";
				}
				break;
			}
		}
	}
	Object o = monsters.get(0);
	Monster mo = (Monster)o;
		
	battlegridmonstertoattackx = 1 + mo.getx() / 48;
	battlegridmonstertoattacky = 1 + mo.gety() / 48;

	String returnstring = "" + randomnumber2;
	return returnstring;
	
    }	

    public int GetMonsterIndex(String monstername)
    {
	int i;
	for (i = 0; i < monsterdatabase.size(); i++) {
		if (monsterdatabase.getMonsterName(i) == monstername)
			return i;
	}
	return 0;//NOTE! should never be reached
    }

    public String DoMonsterAttack(int xx, int yy) 
    {

	String monstername = battlegrid.get(xx,yy);
	int index = GetMonsterIndex(monstername);
	int chancetohit = monsterdatabase.getMonsterHitchance(index);

	int randomnumber = rng.nextInt(chancetohit);
	if (randomnumber == 0)//monster fails to hit
		return "Miss!";

	int str = monsterdatabase.getMonsterStrength(index);
	int randomnumber2 = rng.nextInt(str) + 1;

	//hit a random player character
	int randomnumber3 = rng.nextInt(numberofplayercharacters);

	player.hit(randomnumber3, randomnumber2);

	int i;
	int hp = 0;
	for (i = 0; i < numberofplayercharacters; i++) {
		hp += player.getPlayerHitpoints(i);
	}

	if (hp <= 0) {
		gameover = true;
		return "0";//FIXME
	}
	String returnstring = "" + randomnumber2;
	return returnstring;
    }

    class TAdapter extends KeyAdapter {
        public void keyReleased(KeyEvent e) {
        	player.settonotmoving();
	} 
      
        public void keyPressed(KeyEvent e) {

          int key = e.getKeyCode();

	if (showintro) {
   		if (key == KeyEvent.VK_ESCAPE) {
			showintro = false;
		}
	}

	if (gameover) {
   		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}
	
	int lvl = CollideGateways();
	if (lvl != -1) {// || lvl == levelnumber) {

		levelnumber = lvl;

		switch(lvl) {
			case 1000:
				loadlevel1000();
				break;
			case 1001:
				loadlevel1001();
				break;
			case 1002:
				loadlevel1002();
				break;
			case 2000:
				loadlevel2000();
				break;
			case 3000:
				loadlevel3000();
				break;
			default:
				break;
		}

		return;//NOTE

	}

	//do not move if collided
	   if (CollideBuildings()) {
		if (player.getdirection() == "left")
			map.moveleft();
		if (player.getdirection() == "right")
			map.moveright();
		if (player.getdirection() == "up")
			map.moveup();
		if (player.getdirection() == "down")
			map.movedown();
		return;
	   }
	   if (CollideNonPlayerCharacters()) {

		if (player.getdirection() == "left")
			map.moveleft();
		if (player.getdirection() == "right")
			map.moveright();
		if (player.getdirection() == "up")
			map.moveup();
		if (player.getdirection() == "down")
			map.movedown();

		collidedwithnonplayercharacter = true;

		//FIXMEif (!choosetalkmode) {
			return;//FIXMENOTE leave this, collidedwithnonplayercharacter gets set in (key == ...) further on
		//}
	   } else {//NOTE!
		if (!choosetalkmode) {//FIXME
			talk = false;
		}
	   }
	   if (!battle) {//map screen

	   	if (key == KeyEvent.VK_LEFT) {

			if (!choosetalkmode) {
				player.settomoving("left");
				map.moveright();
			}
	   	}
	   	if (key == KeyEvent.VK_RIGHT) {
			if (!choosetalkmode) {
				player.settomoving("right");
				map.moveleft();
			}
	   	}
	   	if (key == KeyEvent.VK_UP) {

			if (choosetalkmode) {
				talkwidget.movehandup();
				handmove = true;
			} else if (askmode) {
				askwidget.movehandup();
				handmove = true;
			} else if (learnmode) {
				learnwidget.movehandup();
				handmove = true;
			} else if (itemmode) {
				itemwidget.movehandup();
				handmove = true;
			} else if (learnedanotherwordmode) {
				learnedanotherwordwidget.movehandup();
				handmove = true;

			} else if (!talk) {		
				player.settomoving("up");
				map.movedown();
			}
	   	}
	   	if (key == KeyEvent.VK_DOWN) {
			if (choosetalkmode) {
				talkwidget.movehanddown();
				handmove = true;
			} else if (askmode) {
				askwidget.movehanddown();
				handmove = true;
			} else if (learnmode) {
				learnwidget.movehanddown();
				handmove = true;
			} else if (itemmode) {
				itemwidget.movehanddown();
				handmove = true;
			} else if (learnedanotherwordmode) {
				learnedanotherwordwidget.movehanddown();
				handmove = true;
				
			} else if (!talk) {		
				player.settomoving("down");
				map.moveup();
			}
	   	}
		if (key == KeyEvent.VK_LEFT || 
			key == KeyEvent.VK_RIGHT ||
			key == KeyEvent.VK_UP ||
			key == KeyEvent.VK_DOWN) {

			if (!choosetalkmode) {

				collidedwithnonplayercharacter = false;
				//talk = false;
				//currenttalktext = "";
			}

			if (!levelnomonsters) {//generate monsters by walking around
				if (counter1++ > 77) {
      					int randomnumber = rng.nextInt(77);//FIXMENOTE! 3200 initiate battle chance
			
      					if (randomnumber == 0) {
						battle = true;
	
						int randomnumber2 = rng.nextInt(4);
						numberofmonsters = randomnumber2 + 1;
				
						int number;
						for (number = 0; number < numberofmonsters; number++) {
							int randomnumber3 = rng.nextInt(monsterdatabase.size());
							if (levelnumber == 2000) 
								addMonsterLevel2000(randomnumber3, number);
						}
						return;//NOTE!
					}
				}
			}
		}	
	   	if (key == KeyEvent.VK_X) {

			boolean b = SetTexts();
			if (currenttalktextindex >= currenttalktextmax && currenttalktextindex >= 0) {
		       	    learnedanotherwordmode = false;
			    askmode = false;
			    learnmode = false;
			    itemmode = false;
			    talk = false;
    			    talkmodeafterlearnedanotherword = false;
    			    talkmodeafterask = false;
    			    talkmodeafterlearn = false;
    			    talkmodeafteritem = false;
					
    			    currenttalktext = "";
    			    currenttalktextindex = 0;//-1;
    			    currenttalktextmax = 0;
    			    currentlearn = -1;
				
				return;
			}

			if (askmode) {

				int idx = askwidget.getindex();
				//String word = askworddatabase.getLearnedWord(idx);

				
				currenttalktext = currentnonplayercharacter.asktalkto(idx);	
				currenttalktextmax = currentnonplayercharacter.asktalktomaxindex();
				currenttalktextindex = -1;
				askmode = false;
				//choosetalkmode = true;
				//talk = true;
				talkmodeafterask = true;
			}

			if (itemmode) {

				int idx = itemwidget.getindex();
				//String word = itemworddatabase.getItemWord(idx);

				
				currenttalktext = currentnonplayercharacter.itemtalkto(idx);	
				currenttalktextmax = currentnonplayercharacter.itemtalktomaxindex();
				currenttalktextindex = -1;
				itemmode = false;
				//choosetalkmode = true;
				//talk = true;
				talkmodeafteritem = true;
			}

			if (learnmode) { // && !learnedanotherwordmode) {//FIXMENOTE!

				int idx = learnwidget.getindex();
				//String word = learnworddatabase.getWord(idx);

				
				currenttalktext = currentnonplayercharacter.learntalkto(idx);	
				currenttalktextmax = currentnonplayercharacter.learntalktomaxindex();
				currenttalktextindex = 0;//FIXMENOTE! -1
				learnmode = false;
				//choosetalkmode = true;
				//talk = true;
				talkmodeafterlearn = true;
			}

			if (learnedanotherwordmode) {

				int idx = learnedanotherwordwidget.getindex();
				//String word = learnworddatabase.getWord(idx);

				
				currenttalktext = currentnonplayercharacter.learnedanotherwordtalkto(idx);	
				currenttalktextmax = currentnonplayercharacter.learntalktomaxindex();
				currenttalktextindex = 0;//FIXMENOTE! -1;
				///////learnmode = false;//FIXMENOTE
				learnedanotherwordmode = false;
				//choosetalkmode = true;
				//talk = true;
				talkmodeafterlearnedanotherword = true;
			}


			if (choosetalkmode) {

				choosetalkmode = false;
				//ask learn item
				int idx = talkwidget.getindex();
				switch (idx) {
				case 0:
					askmode = true;
					break;
				case 1:
					//if (learnedanotherwordmode, not (!) set above in pure learnmode FIXMENOTE
					//if (currentlearn >= 0)
					//	learnedanotherwordmode = true;
					//else
					learnmode = true;
					break;
				case 2:
					itemmode = true;
					break;
				default:
					askmode = true;
					break;
				}
					
			} else if (!choosetalkmode) {
				//FIXME!
				if (talk && collidedwithnonplayercharacter) {//FIXMENOTE
					//end talking when through all text
					if (currenttalktextindex > currenttalktextmax && currenttalktextindex >= 0) {//sometimes currenttalktextindex starts at -1 
					    learnedanotherwordmode = false;
					    askmode = false;
					    learnmode = false;
					    itemmode = false;
					    talk = false;
    					    talkmodeafterlearnedanotherword = false;
    					    talkmodeafterask = false;
    					    talkmodeafterlearn = false;
    					    talkmodeafteritem = false;
					
    					    currenttalktext = "";
    					    currenttalktextindex = 0;//-1;
    					    currenttalktextmax = 0;
    					    currentlearn = -1;
					}
				} else if (collidedwithnonplayercharacter) {

					//if (!handmove) {
    						if (currenttalktextindex >= currenttalktextmax)
							currenttalktextindex = -1;
						currenttalktextindex++;
					//} 
					//handmove = false;
					talk = true;
				}
			}
	   	}
	   	if (key == KeyEvent.VK_Z) {//go back to history of talkmodes
			boolean b = SetTexts();

			learnedanotherwordmode = false;
			askmode = false;
			learnmode = false;
			itemmode = false;
			talk = false;
    			talkmodeafterlearnedanotherword = false;
    			talkmodeafterask = false;
    			talkmodeafterlearn = false;
    			talkmodeafteritem = false;
				
    			currenttalktext = "";
    			currenttalktextindex = 0;//-1;
    			currenttalktextmax = 0;
    			currentlearn = -1;
			return;
		}
	   } else if (battle) {
	   	if (key == KeyEvent.VK_LEFT) {
			if (!chooseattackmode) {
				handcursorwidget.setx(handcursorwidget.getx()-48);//FIXME - monster height
	   		}
		}
	   	if (key == KeyEvent.VK_RIGHT) {
			if (!chooseattackmode) {
				handcursorwidget.setx(handcursorwidget.getx()+48);//FIXME - monster height
	   		}
		}
	   	if (key == KeyEvent.VK_UP) {
			if (!chooseattackmode) {
				handcursorwidget.sety(handcursorwidget.gety()-48);//FIXME - monster height
				battlegridmonstertoattackx = handcursorwidget.getx() % 48 + 1;
				battlegridmonstertoattacky = handcursorwidget.gety() % 48 + 1;
				
	   		} else {
				battlewidget.movehandup();
			}
		}
	   	if (key == KeyEvent.VK_DOWN) {
			if (!chooseattackmode) {
				handcursorwidget.sety(handcursorwidget.gety()+48);//FIXME - monster height
				battlegridmonstertoattackx = handcursorwidget.getx() % 48 + 1;
				battlegridmonstertoattacky = handcursorwidget.gety() % 48 + 1;
	   		} else {
				battlewidget.movehanddown();
			}
	   	}
	   	if (key == KeyEvent.VK_X) {

			if (!chooseattackmode) {
				chooseattackmode = true;
				
			} else if (chooseattackmode) {
				
				switch(battlewidget.getindex()) {
					case 0://Attack
						attack = true;
						battlegoingon = true;
						break;
					default://Attack
						attack = true;
						battlegoingon = true;
						break;
				}

			}
			//battlegoingon = true;
			//battlegrid.get(handcursorwidget.getx() % handcursormonsteroffset, handcursorwidget.gety() % handcursormonsteroffset);

			
		}
		
		//flee battle
	   	if (key == KeyEvent.VK_ESCAPE) {
			battle = false;
			chooseattackmode = false;
			battlegoingon = false;
			attack = false;
	   	}
	   }
	}
	}

    public void actionPerformed(ActionEvent e) {
        repaint();  
    }

    public String ParseTalkImage(String str)
    {

	int idx1 = str.indexOf(']',0);
	int idx2 = str.lastIndexOf('[',0);

	String filename = "";
	int i;
	for (i = idx1+1; i < idx2; i++) {
		filename += str.charAt(i);
	}

    	return filename;
    }

    public String ParseTalkImageText(String str)
    {

	int idx1 = str.indexOf('[',0);
	String returntext = "";
	int i;
	for (i = 0; i < idx1; i++) {
		returntext += str.charAt(i);
	}

	return returntext;

    }
}
