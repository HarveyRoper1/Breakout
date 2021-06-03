import javax.swing.*;
public class Main {

	public static void main(String[] args) {
		Gameplay gamePlay = new Gameplay();
		JFrame f = new JFrame();
		f.setBounds(10, 10, 700, 600);
		f.setTitle("Breakout");
		f.setResizable(false);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(gamePlay);
		
		
	}

}
