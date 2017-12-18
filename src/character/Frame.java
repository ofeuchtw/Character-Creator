package character;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;


public class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -142912801018517425L;
	
	public Frame() /* throws IOException */ {
		

	       
        Creator c = new Creator();
        add(c);

        setSize(800, 740);
        setBackground(new Color(160,90,110));
        setResizable(false);
        
        setTitle("Character Creator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        

   }


    public static void main(String[] args) throws IOException {

        Frame page = new Frame();
        page.setLocationRelativeTo(null);
        page.setVisible(true);

    } 

}
