import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{

	private int SPEED = 2;
	
	private boolean play = false;
	private int score = 0;
	private int totalBricks;
	private Timer timer;
	private int delay = 5;
	
	private int playerX = 310;
	private int ballposX = 350;
	private int playervelX = 0;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private boolean right;
	private boolean left;
	
	Random r = new Random();	
	
	private MapGenerator map;
	
	
	public Gameplay() {
		int col = 5;
		int row = 7;
		map = new MapGenerator(5, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		totalBricks = col * row;
		}
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, 692, 592);
		
		//map
		map.draw((Graphics2D)g);
		
		//border
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//score
		g.setColor(Color.WHITE);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+score, 590, 30);
		
		//paddle
		g.setColor(Color.GREEN);
		g.fillRect(playerX, 550, 100, 8);
		
		//ball
		g.setColor(Color.YELLOW);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		if(totalBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You Won!", 250, 300);
			g.drawString("Score: "+score, 265, 330);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 350);
		}
		
		
		if(ballposY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over", 250, 300);
			g.drawString("Score: "+score, 265, 330);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 350);
		}
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		go();
		if(play) {
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX,550,100,8))) {
				ballYdir = -ballYdir;
				
			}
			
			A: for(int i = 0; i < map.map.length; i++) {
				for(int j = 0; j < map.map[0].length ; j++) {
					if(map.map[i][j] > 0) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;
							}
							else {
								ballYdir = -ballYdir;
							}
							
							break A;
						
						}
					}
				}
			}
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX < 0) {
				ballXdir = -ballXdir;
			}
			if(ballposY < 0) {
				ballYdir = -ballYdir;
			}
			if(ballposX > 670) {
				ballXdir = -ballXdir;
			}
		}
		
		repaint();
		
	}

	public void go() {
		
		if(playerX >= 600) {
			playerX = 600;
		}else { playerX += playervelX; }
		if(playerX <= 0) {
			playerX = 0;
		}else { playerX += playervelX; }
		
		
	}
	
	public void update() {
		playervelX = 0;
		if(left) playervelX = -2;
		if(right) playervelX = 2;
		
	}
	
	public void keyPressed(KeyEvent e) {

		switch(e.getKeyCode()) {
		case KeyEvent.VK_RIGHT: right = true; break;
		case KeyEvent.VK_LEFT: left = true; break;
		}
		
		update();
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = 350;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3, 7);
				
				repaint();
			
			}
			}
		
	}
	
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_RIGHT: right = false; break;
		case KeyEvent.VK_LEFT: left = false; break;
		}
		update();
	}

	public void moveRight() {
		play = true;
		playerX += 4;
	}
	
	public void moveLeft() {
		play = true;
		playerX -= 20;
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {}

	
	
}
