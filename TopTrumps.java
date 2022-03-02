import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.awt.Component;
import java.io.*;
import java.lang.Math;
import java.util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.concurrent.atomic.AtomicBoolean;

public class TopTrumps implements Runnable {

	public static int yS;
	public static int oS;
	public static int i;
	public static JFrame frame;
	public static JLabel yourScore;
	public static JLabel oppScore;
	public static JLabel turn;
	public static JLabel round;
	public static JLabel myStat;
	public static JLabel oppStat;
	public static JLabel vs;
	public static JLabel dogName;
	public static JLabel dogPic;
	public static JButton height;
	public static JButton speed;
	public static JButton intelligence;
	public static JButton furDrop;
	public static JButton lifespan;
	public static JLabel heightVal;
	public static JLabel speedVal;
	public static JLabel intelligenceVal;
	public static JLabel furDropVal;
	public static JLabel lifespanVal;
	public static String[] whoseTurn = {"Your Turn", "Opponent's Turn"};
	public static String[] whichStat = {"Height", "Speed", "Intelligence", "Fur Drop", "Lifespan"};
	public static String[][] dogs = getDogs.dogsList();
	public static List<Integer> yourDogs = new ArrayList<Integer>();
	public static List<Integer> oppDogs = new ArrayList<Integer>();
	public static Boolean waiting;

	public static void createWindow() {

		//create frame
		frame = new JFrame("Dog Top Trumps");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//scoreboard
		JPanel overview = new JPanel();
		overview.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		yourScore = new JLabel("",SwingConstants.CENTER);
		yourScore.setFont(yourScore.getFont().deriveFont(16.0f));
		yourScore.setPreferredSize(new Dimension(100,40));
		oppScore = new JLabel("",SwingConstants.CENTER);
		oppScore.setFont(oppScore.getFont().deriveFont(16.0f));
		oppScore.setPreferredSize(new Dimension(100,40));
		turn = new JLabel("",SwingConstants.CENTER);
		turn.setFont(turn.getFont().deriveFont(16.5f));
		turn.setPreferredSize(new Dimension(190,40));
		overview.add(yourScore);
		overview.add(turn);
		overview.add(oppScore);

		//previous round summary
		round = new JLabel(" ",SwingConstants.CENTER);
		round.setFont(round.getFont().deriveFont(14.0f));
		round.setAlignmentX(Component.CENTER_ALIGNMENT);
		JPanel comparison = new JPanel();
		overview.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		myStat = new JLabel(" ",SwingConstants.CENTER);
		myStat.setFont(myStat.getFont().deriveFont(16.0f));
		myStat.setPreferredSize(new Dimension(160,30));
		oppStat = new JLabel(" ",SwingConstants.CENTER);
		oppStat.setFont(oppStat.getFont().deriveFont(16.0f));
		oppStat.setPreferredSize(new Dimension(160,30));
		vs = new JLabel(" ",SwingConstants.CENTER);
		vs.setFont(vs.getFont().deriveFont(15.0f));
		vs.setPreferredSize(new Dimension(50,30));
		comparison.add(myStat);
		comparison.add(vs);
		comparison.add(oppStat);
		
		//set up the card
		JPanel card = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Dimension arcs = new Dimension(30,30);
				int width = 320;
				int height = 470;
				Graphics2D graphics = (Graphics2D) g;
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
			}
		};
		card.setAlignmentX(Component.CENTER_ALIGNMENT);
		card.setPreferredSize(new Dimension(320,500));
		card.setMaximumSize(new Dimension(320,500));
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
		dogName = new JLabel("",SwingConstants.CENTER);
		dogName.setFont(myStat.getFont().deriveFont(18.0f));
		dogName.setAlignmentX(Component.CENTER_ALIGNMENT);
		dogPic = new JLabel();
		dogPic.setAlignmentX(Component.CENTER_ALIGNMENT);
		JPanel stats = new JPanel();
		stats.setAlignmentX(Component.CENTER_ALIGNMENT);
		stats.setPreferredSize(new Dimension(300,225));
		stats.setMaximumSize(new Dimension(300,225));
		stats.setLayout(new GridLayout(5,2));
		height = new JButton("Height");
		height.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonPressed(1,true);				
			}
		});
		speed = new JButton("Speed");
		speed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonPressed(2,true);				
			}
		});
		intelligence = new JButton("Intelligence");
		intelligence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonPressed(3,true);				
			}
		});
		furDrop = new JButton("<html><center>Fur Drop<br/><small>(lowest wins)</small></center></html>");
		furDrop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonPressed(4,false);				
			}
		});
		lifespan = new JButton("Lifespan");
		lifespan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonPressed(5,true);				
			}
		});
		heightVal = new JLabel("",SwingConstants.RIGHT);
		speedVal = new JLabel("",SwingConstants.RIGHT);
		intelligenceVal = new JLabel("",SwingConstants.RIGHT);
		furDropVal = new JLabel("",SwingConstants.RIGHT);
		lifespanVal = new JLabel("",SwingConstants.RIGHT);
		stats.add(height);
		stats.add(heightVal);
		stats.add(speed);
		stats.add(speedVal);
		stats.add(intelligence);
		stats.add(intelligenceVal);
		stats.add(furDrop);
		stats.add(furDropVal);
		stats.add(lifespan);
		stats.add(lifespanVal);
		card.add(Box.createRigidArea(new Dimension(400, 10)));
		card.add(dogName);
		card.add(Box.createRigidArea(new Dimension(400, 10)));
		card.add(dogPic);
		card.add(Box.createRigidArea(new Dimension(400, 10)));
		card.add(stats);

		refreshWindow();

		//display the window
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.add(Box.createRigidArea(new Dimension(400, 10)));
		container.add(overview);
		container.add(Box.createRigidArea(new Dimension(400, 35)));
		container.add(round);
		container.add(comparison);
		container.add(Box.createRigidArea(new Dimension(400, 25)));
		container.add(card);
		frame.add(container);
		frame.pack();
		frame.setVisible(true);
		frame.setSize(400,700);
		frame.setResizable(false);

	}

	public static void buttonPressed(int b, boolean greater) {	//compares chosen stats and advances hands
		
		round.setText(whichStat[b-1] + ":");
		myStat.setText(removeSpace(yourDogs.get(0)) + ": " + dogs[yourDogs.get(0)][b]);
		oppStat.setText(removeSpace(oppDogs.get(0)) + ": " + dogs[oppDogs.get(0)][b]);
		vs.setText("vs");
		
		if (greater) {
			if (Integer.parseInt(dogs[yourDogs.get(0)][b]) > Integer.parseInt(dogs[oppDogs.get(0)][b])) {
				myStat.setForeground(Color.GREEN);
				oppStat.setForeground(Color.RED);
				yS = yS + 1;
				oS = oS - 1;
				yourDogs.add(oppDogs.get(0));
				yourDogs.add(yourDogs.get(0));
				yourDogs.remove(0);
				oppDogs.remove(0);
				i = 0;
				height.setEnabled(true);
				speed.setEnabled(true);
				intelligence.setEnabled(true);
				furDrop.setEnabled(true);
				lifespan.setEnabled(true);
			}
			else if (Integer.parseInt(dogs[yourDogs.get(0)][b]) < Integer.parseInt(dogs[oppDogs.get(0)][b])) {
				myStat.setForeground(Color.RED);
				oppStat.setForeground(Color.GREEN);
				yS = yS - 1;
				oS = oS + 1;
				oppDogs.add(yourDogs.get(0));
				oppDogs.add(oppDogs.get(0));
				yourDogs.remove(0);
				oppDogs.remove(0);
				i = 1;
			}
			else {
				myStat.setForeground(Color.GRAY);
				oppStat.setForeground(Color.GRAY);
				yourDogs.add(yourDogs.get(0));
				oppDogs.add(oppDogs.get(0));
				yourDogs.remove(0);
				oppDogs.remove(0);
			}
		}
		else {
			if (Integer.parseInt(dogs[yourDogs.get(0)][b]) < Integer.parseInt(dogs[oppDogs.get(0)][b])) {
				myStat.setForeground(Color.GREEN);
				oppStat.setForeground(Color.RED);
				yS = yS + 1;
				oS = oS - 1;
				yourDogs.add(oppDogs.get(0));
				yourDogs.add(yourDogs.get(0));
				yourDogs.remove(0);
				oppDogs.remove(0);
				i = 0;
				height.setEnabled(true);
				speed.setEnabled(true);
				intelligence.setEnabled(true);
				furDrop.setEnabled(true);
				lifespan.setEnabled(true);
			}
			else if (Integer.parseInt(dogs[yourDogs.get(0)][b]) > Integer.parseInt(dogs[oppDogs.get(0)][b])) {
				myStat.setForeground(Color.RED);
				oppStat.setForeground(Color.GREEN);
				yS = yS - 1;
				oS = oS + 1;
				oppDogs.add(yourDogs.get(0));
				oppDogs.add(oppDogs.get(0));
				yourDogs.remove(0);
				oppDogs.remove(0);
				i = 1;
			}
			else {
				myStat.setForeground(Color.GRAY);
				oppStat.setForeground(Color.GRAY);
				yourDogs.add(yourDogs.get(0));
				oppDogs.add(oppDogs.get(0));
				yourDogs.remove(0);
				oppDogs.remove(0);
			}
		}

		if (yS == 0) {
			winLossScreen(false);
		}
		else if (oS == 0) {
			winLossScreen(true);
		}
		else {
			refreshWindow();
			if (i == 1) {
				height.setEnabled(false);
				speed.setEnabled(false);
				intelligence.setEnabled(false);
				furDrop.setEnabled(false);
				lifespan.setEnabled(false);
				AI();
			}
		}
	}

	public static void refreshWindow() {	//sets content to relevant dog

		yourScore.setText("You: " + Integer.toString(yS));
		oppScore.setText("Opp: " + Integer.toString(oS));
		turn.setText(whoseTurn[i%2]);
		dogName.setText(dogs[yourDogs.get(0)][0]);
		heightVal.setText(dogs[yourDogs.get(0)][1] + " cm");
		speedVal.setText(dogs[yourDogs.get(0)][2] + " mph");
		intelligenceVal.setText(dogs[yourDogs.get(0)][3] + "/50");
		furDropVal.setText(dogs[yourDogs.get(0)][4] + "/10");
		lifespanVal.setText(dogs[yourDogs.get(0)][5] + " years");
		ClassLoader cl = TopTrumps.class.getClassLoader();
		ImageIcon dogImg = new ImageIcon(cl.getResource("images/" + dogs[yourDogs.get(0)][0] + ".jpg"));
		//ImageIcon dogImg = new ImageIcon("images/" + dogs[yourDogs.get(0)][0] + ".jpg");
		Image image = dogImg.getImage();
		Image newimg = image.getScaledInstance(300, 180,  java.awt.Image.SCALE_SMOOTH); 
		dogImg = new ImageIcon(newimg);
		dogPic.setIcon(dogImg);

	}

	public static void AI() {	//computers turn; need new thread to have pause
		
		Thread t = new Thread(new TopTrumps());
		t.start();

	}

	public static String removeSpace(int idx) {	//changes names with spaces so they fit in summary box

		String name = dogs[idx][0];
		int count = 1;
		int s = name.indexOf(" ");
		while (s != -1) {
			String left = name.substring(0,count);
			String right = name.substring(s+1);
			name = left + "." + right;
			count = count + 2;
			s = name.indexOf(" ");
		}
		return(name);
	}

	public static void winLossScreen(boolean won) {		//screen at the end of a game allowing to play again

		frame.setVisible(false);
		JFrame wlFrame = new JFrame("Dog Top Trumps");
		wlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel winorloss = new JLabel("",SwingConstants.CENTER);
		winorloss.setFont(myStat.getFont().deriveFont(32.0f));
		winorloss.setAlignmentX(Component.CENTER_ALIGNMENT);
		if (won) {
			winorloss.setText("You Won!");
		}
		else {
			winorloss.setText("You Lost...");
		}

		JButton playAgain = new JButton("Play Again");
		playAgain.setFont(myStat.getFont().deriveFont(18.0f));
		playAgain.setAlignmentX(Component.CENTER_ALIGNMENT);
		playAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wlFrame.dispose();
				frame.setVisible(true);
				frame.setLocation(wlFrame.getX(),Math.max(wlFrame.getY() - 250,0));
				newGame();
				round.setText(" ");
				myStat.setText(" ");
				oppStat.setText(" ");
				vs.setText(" ");
				height.setEnabled(true);
				speed.setEnabled(true);
				intelligence.setEnabled(true);
				furDrop.setEnabled(true);
				lifespan.setEnabled(true);
				refreshWindow();
			}
		});

		JPanel container2 = new JPanel();
		container2.setLayout(new BoxLayout(container2, BoxLayout.Y_AXIS));
		container2.add(Box.createRigidArea(new Dimension(400, 30)));
		container2.add(winorloss);
		container2.add(Box.createRigidArea(new Dimension(400, 20)));
		container2.add(playAgain);
		container2.add(Box.createRigidArea(new Dimension(400, 15)));
		
		wlFrame.setLocation(frame.getX(),frame.getY() + 250);
		wlFrame.add(container2);
		wlFrame.pack();
		wlFrame.setVisible(true);
		wlFrame.setSize(400,200);
		wlFrame.setResizable(false);
	}

	public static void newGame() {		//initializes a new game
	
		yS = 15;
		oS = 15;
		i = 0;
		yourDogs.clear();
		oppDogs.clear();

		for (int d = 0; d < 30; d++) {
			if (yourDogs.size() == 15) {
				oppDogs.add(d);
			}
			else if (oppDogs.size() == 15) {
				yourDogs.add(d);
			}
			else {
				if (Math.random() < 0.5) {
					yourDogs.add(d);
				}
				else {
					oppDogs.add(d);
				}
			}
		}
		Collections.shuffle(yourDogs);
		Collections.shuffle(oppDogs);

	}

	public static void main(String[] args) {

		newGame();
		createWindow();

	}

	public void run() {	//new thread used by AI
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		String oppTurn = whoseTurn[i];
		for (int n = 0; n < 3; n++) {
			oppTurn = oppTurn + ".";
			turn.setText(oppTurn);
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		int bestStat = Integer.parseInt(dogs[oppDogs.get(0)][dogs[oppDogs.get(0)].length-1]);
		boolean greater = true;
		if (bestStat == 4) {
			greater = false;
		}
		buttonPressed(bestStat,greater);
	}
}

