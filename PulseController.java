import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class PulseController {
	private final static int PULSE_SPEED =10;
	private final static float PULSE_PROBABILITY = 0.2f;
	
	private final String OUTPUT = "Hack:space";
	private final int FONT_SIZE = 100;
	private final int TOP_BAR_HEIGHT = 25;
	
	private final PulseView view = new PulseView(); //shows Text
	private final Pulsator pulsator = new Pulsator(); //knows current state & how to change it
	
	private void runPulsation() throws InterruptedException {
		while(true) { //program is closed, when view is closed
			pulsator.pulsate();
			view.show(pulsator.getStats());
			
			TimeUnit.MILLISECONDS.sleep(1000 / PULSE_SPEED);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new PulseController().runPulsation();
	}
	
	public class PulseView extends JFrame {
		Font defaultFont = new Font("sansserif", Font.BOLD, FONT_SIZE);
		JTextPane[] letterPanes = new JTextPane[OUTPUT.length()];
		
		public PulseView() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel panel = new JPanel();
			
			for(int index=0; index<letterPanes.length; index++) {
				String letter = OUTPUT.substring(index, index+1);
				
				letterPanes[index] = new JTextPane();
				letterPanes[index].setEditable(false);
				letterPanes[index].setFont(defaultFont);
				letterPanes[index].setText(letter);
				panel.add(letterPanes[index]);
			}
			
			add(panel);
			setTitle("Pulsator");
			setSize(panel.getPreferredSize().width, panel.getPreferredSize().height + TOP_BAR_HEIGHT);
			setVisible(true);
		}
		
		public void show(Color[] letterColors) {
			for(int index=0; index<letterPanes.length; index++) {
				letterPanes[index].setForeground(letterColors[index]);
			}
			
			revalidate();
		}
	}

	public class Pulsator {
		Color[] colors = new Color[OUTPUT.length()];
		
		public Pulsator() {
			//init colors
			for(int index=0; index<colors.length; index++) {
				colors[index] = createRandomColor();
			}
		}
		
		public void pulsate() {
			for(int index=0; index<colors.length; index++) {
				if(doIchange()) {
					colors[index] = createRandomColor();
				}
			}
		}
		
		public Color[] getStats() {
			return colors;
		}
		
		private Color createRandomColor() {
			return new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
		}
		
		private boolean doIchange() {
			return Math.random() < PULSE_PROBABILITY;
		}
	}
}
