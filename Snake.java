package Snake.game;
import javax.swing.*;

public class Snake extends JFrame {

	private static final long serialVersionUID = 1L;			
	Snake() {
		
		Board b = new Board();
		add(b);
		pack(); 												
		setLocationRelativeTo(null);				
		setTitle("Snake Game Java Project");
		setSize(600,600);
		setResizable(false);		
	}

	public static void main(String[] args) {
		new Snake().setVisible(true); 			
	}
}

