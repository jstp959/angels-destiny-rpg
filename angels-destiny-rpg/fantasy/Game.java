package fantasy;
/*
Copyright (C) 2012 Johan Ceuppens

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/

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
    private int level = 1;
    double mapy = 0;
    double mapx = 0;
    double activationPixel = 0;
    int levelnumber = 1;
    private int SCREENWIDTH = 320;
    private int SCREENHEIGHT = 200;
    private int spritecount = 3;
    short screendata;
    Timer timer;

    private Random rng = new Random();

    private LinkedList buildings = new LinkedList();
    private LinkedList nonplayercharacters = new LinkedList();
 
    private int numberofmonsters = 2; /////Random().nextInt(3)+1;
    private LinkedList monsters = new LinkedList();
    private MonsterDatabase monsterdatabase = new MonsterDatabaseLevel1();

    Player player = new Player(100,100);
    Map map = new Map(0,0,640,640, new ImageIcon(prefix+"map-1024x1024-1.png").getImage(), 0, 0);

    FantasyBattleWidget battlewidget = new FantasyBattleWidget(0,200-64);
    FantasyHandCursorWidget handcursorwidget = new FantasyHandCursorWidget(96-20,96);

	//battle screen is on 
    boolean battle = false;//NOTE! false to start game
	//a battle phase is being displayed
    boolean battlegoingon = false;

    BattleGrid battlegrid = new BattleGrid(6);

    public Game() {

        addKeyListener(new TAdapter());
        setFocusable(true);
	
        d = new Dimension(800, 600);
        setBackground(Color.black);
        setDoubleBuffered(true);
        timer = new Timer(40, this);
        timer.start();

	buildings.add(new Building(0,0,100,100,new ImageIcon(prefix+"wallrock-100x100-1.png").getImage())); //FIXME
	nonplayercharacters.add(new Bartender(0,0)); //FIXME
	//monsters.add(new Slime(48,96)); //FIXME


	//delete, for starting battle mode
				int randomnumber2 = rng.nextInt(4);
				numberofmonsters = randomnumber2 + 1;
				
				int number;
				for (number = 0; number < numberofmonsters; number++) {
					int randomnumber3 = rng.nextInt(monsterdatabase.size());
					if (level == 1) 
						addMonsterLevel1(randomnumber3, number);
				}
    }

    public void addNotify() {
        super.addNotify();
        GameInit();
    }

    public void addMonsterLevel1(int index, int numberofmonster) {
	String monstername = monsterdatabase.getMonster(index);
	if (monstername == "slime")
		monsters.add(new Slime(48+(numberofmonster%3)*48,48+(numberofmonster%2)*48));
	else
		monsters.add(new Slime(48+(numberofmonster%4)*48,48+(numberofmonster%2)*48));
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

/*
 * drawing battles 
 */ 

    public void DrawBattleMonsterHandCursor(Graphics g2d) {
	g2d.drawImage(handcursorwidget.getImage(), handcursorwidget.getx(), handcursorwidget.gety(), this);
    }

    public void DrawBattleWidgets(Graphics g2d) {
	g2d.drawImage(battlewidget.getImage(), battlewidget.getx(), battlewidget.gety(), this);
    }

    public void DrawBattleStage(Graphics g2d) {
	//draw battle stage in combination with map environment
	g2d.drawImage(new ImageIcon(prefix+"battlestage-1.png").getImage(), 0, 0, this);
    }

    public void DrawBattlePlayer(Graphics g2d) {
	//draw battle stage in combination with map environment
	g2d.drawImage(player.getLeftImage(2), 250, 96, this);
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
      super.paint(g);

      Graphics2D g2d = (Graphics2D) g;

      g2d.setColor(Color.black);
      g2d.fillRect(0, 0, d.width, d.height);

	//map screen
      if (!battle) {
	DrawMap(g2d);	
	DrawBuildingsOnMap(g2d);	
	DrawNonPlayerCharacters(g2d);	
	DrawPlayer(g2d);
      } else if (battle) {//battle screen
	DrawBattleStage(g2d);
	DrawBattleMonsters(g2d);
	DrawBattlePlayer(g2d);
	DrawBattleWidgets(g2d);
	if (!battlegoingon) {
		DrawBattleMonsterHandCursor(g2d);
	} else if (battlegoingon) {
		int randomnumber = rng.nextInt(60);
		if (randomnumber == 0) {
			int randomnumber2 = rng.nextInt(numberofmonsters);
			
		}		
	}
      }
/*      g2d.setColor(Color.white);
        g2d.setFont(smallfont);
        g2d.drawString("foobar", 0,0);
*/
      Toolkit.getDefaultToolkit().sync();
      g.dispose();

    }

    class TAdapter extends KeyAdapter {
        public void keyReleased(KeyEvent e) {
        	player.settonotmoving();
	} 
      
        public void keyPressed(KeyEvent e) {

          int key = e.getKeyCode();

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
	   if (!battle) {//map screen

	   	if (key == KeyEvent.VK_LEFT) {
			player.settomoving("left");
			map.moveright();
	   	}
	   	if (key == KeyEvent.VK_RIGHT) {
			player.settomoving("right");
			map.moveleft();
	   	}
	   	if (key == KeyEvent.VK_UP) {
			player.settomoving("up");
			map.movedown();
	   	}
	   	if (key == KeyEvent.VK_DOWN) {
			player.settomoving("down");
			map.moveup();
	   	}
		if (key == KeyEvent.VK_LEFT || 
			key == KeyEvent.VK_RIGHT ||
			key == KeyEvent.VK_UP ||
			key == KeyEvent.VK_DOWN) {
      			int randomnumber = rng.nextInt(3000);
      			if (randomnumber == 0) {
				battle = true;

				int randomnumber2 = rng.nextInt(4);
				numberofmonsters = randomnumber2 + 1;
				
				int number;
				for (number = 0; number < numberofmonsters; number++) {
					int randomnumber3 = rng.nextInt(monsterdatabase.size());
					if (level == 1) 
						addMonsterLevel1(randomnumber3, number);
				}
				return;//NOTE!
			}
		}	
	   } else if (battle) {
	   	if (key == KeyEvent.VK_LEFT) {
			handcursorwidget.setx(handcursorwidget.getx()-48);//FIXME - monster height
	   	}
	   	if (key == KeyEvent.VK_RIGHT) {
			handcursorwidget.setx(handcursorwidget.getx()+48);//FIXME - monster height
	   	}
	   	if (key == KeyEvent.VK_UP) {
			handcursorwidget.sety(handcursorwidget.gety()-48);//FIXME - monster height
	   	}
	   	if (key == KeyEvent.VK_DOWN) {
			handcursorwidget.sety(handcursorwidget.gety()+48);//FIXME - monster height
	   	}
	   	if (key == KeyEvent.VK_X) {
			battlegoingon = true;
			///battlegrid.get(handcursorwidget.getx()
		}
		//flee battle
	   	if (key == KeyEvent.VK_ESCAPE) {
			battle = false;
			battlegoingon = false;
	   	}
	   }
	}
	}

    public void actionPerformed(ActionEvent e) {
        repaint();  
    }

}
