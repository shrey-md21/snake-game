import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	
    private Image apple;
    private Image dot;
    private Image head;

    private final int DOT_SIZE = 10;    
    private final int ALL_DOTS = 1800;
    private final int RANDOM_POSITION = 59;
    
    private int apple_x;
    private int apple_y;
    
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private boolean leftDirection = false;
    private boolean rightDirection =  true;
    private boolean upDirection =  false;
    private boolean downDirection =  false;
    private boolean inGame = true;
    
    private int dots;
    private int applesEaten;
    
    private Timer timer;
	
	private static final long serialVersionUID = 1L;
	
	Board() {
	addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(600, 600));
        setFocusable(true);			
        loadImages();
        initGame();
	}
	
	public void loadImages() {
	ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snake/game/images/apple.png"));
        apple  = i1.getImage();
        
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snake/game/images/dot.png"));
        dot = i2.getImage();
        
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snake/game/images/head.png"));
        head = i3.getImage();
	}
	
	public void initGame() {
		dots = 3;
		for(int i = 0 ; i < dots ; i++) {
			x[i] = 50 - i * DOT_SIZE;			
			y[i] = 50;
		}
		locateApple();
		timer = new Timer(45, this);		
		timer.start();
	}
	
	public void locateApple() {
	int r = (int)(Math.random() * RANDOM_POSITION); 
        apple_x = (r * DOT_SIZE); 
        r = (int)(Math.random() * RANDOM_POSITION); 
        apple_y = (r * DOT_SIZE);
	}
	
	public void checkApple() {
		if((x[0] == apple_x) && (y[0] == apple_y)){
            dots++;
            applesEaten++;
            locateApple();
		}
	}

	public void draw(Graphics g) {
		if(inGame) {
			g.drawImage(apple, apple_x, apple_y, this);
			
			for(int i = 0 ; i < dots ; i++) {
				if(i == 0) {
					g.drawImage(head, x[i], y[i], this);
				}else {
					g.drawImage(dot, x[i], y[i], this);
				}
			}
			Toolkit.getDefaultToolkit().sync();
		}else {
			gameOver(g);
		}
	}
	
	public void gameOver(Graphics g) {

	String msg = "Game Over";
        Font font = new Font("TIMES NEW ROMAN", Font.BOLD, 40);
        FontMetrics metrics = getFontMetrics(font);
        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString(msg, (600 - metrics.stringWidth(msg)) / 2 , 600/2);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("TIMES NEW ROMAN",Font.BOLD,30));
        FontMetrics metrices = getFontMetrics(g.getFont());
        g.drawString("Score:" + applesEaten,(600 - metrices.stringWidth("Score"+applesEaten))/2, g.getFont().getSize());
	}
	
	public void checkCollision() {
		
		for(int i = dots ; i > 0 ; i--) {
			if((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {			
				inGame = false;
			}
		}
		
	if(y[0] >= 600){
            inGame = false;
        }
        
        if(x[0] >= 600){
            inGame = false;
        }
        
        if(x[0] < 0){
            inGame = false;
        }
        
        if(y[0] < 0 ){
            inGame = false;
        }
        
        if(!inGame){
            timer.stop();
        }
	}
	
	public void move() {

		for(int i = dots ; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		
		if(leftDirection) {
			x[0] = x[0] - DOT_SIZE;
		}
		if(rightDirection) {
			x[0] = x[0] + DOT_SIZE;
		}
		if(upDirection) {
			y[0] = y[0] - DOT_SIZE;
		}
		if(downDirection) {
			y[0] = y[0] + DOT_SIZE;
		}
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(inGame) {
			checkApple();
			checkCollision();
			move();
		}
		
		repaint();
	}
	
	private class TAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_LEFT && (!rightDirection)) {
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}
			if(key == KeyEvent.VK_RIGHT && (!leftDirection)) {
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}
			if(key == KeyEvent.VK_UP && (!downDirection)) {
				upDirection = true;
				rightDirection = false;
				leftDirection = false;
			}
			if(key == KeyEvent.VK_DOWN && (!upDirection)) {
				downDirection = true;
				rightDirection = false;
				leftDirection = false;
			}
		}	
	}
}
