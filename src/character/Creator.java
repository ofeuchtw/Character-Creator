package character;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Creator extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8965022200690457304L;
	private Timer timer;
	private Random random;
	private boolean start;
//	private int time;
	private static final int SCALE = 2;
	private static final String[] buttons = {"size","skin","eyes","hair","shirt","pants"};
	private static int button;
	private static int size;
	private static char skin;
	private static char eye;
	private static char hair;
	private static char shirt;
	private static char pants;
	private String title;
	
	
	
	private enum Piece {
		skin,eye,shirt,hair,pants;
		
		private final String FILE_PATH = "pieces/";
		
		public String getImageAddress() {
			switch(this) {
				case skin: 
					return FILE_PATH + "/body/" + size + Creator.skin + ".png";
				case eye:
					return FILE_PATH + "/head/eyes/" + Creator.eye + ".png";
				case hair:
					return FILE_PATH + "/head/hair/" + Creator.hair + ".png";
				case shirt:
					return FILE_PATH + "/clothes/shirt/" + Creator.shirt + ".png";
				case pants:
					return FILE_PATH + "/clothes/pants/" + size + Creator.pants + ".png";
				default:
					return "";
			}
		}
		
		public int getWidth() {
			switch(this) {
				case skin: 
					return 308;
				case eye:
					return 264;
				case hair:
					if(Creator.hair == 'a' || Creator.hair == 'd' ) {
						return 440;
					} else if (Creator.hair == 'b' || Creator.hair == 'h' ) {
						return 396;
					} else if (Creator.hair == 'c' ) {
						return 352;
					} else {
						return 308;
					}
				case shirt:
					if(Creator.shirt == 'g' || Creator.shirt == 'h' || Creator.shirt == 'i') {
						return 264;
					} else {
						return 176;
					}
				case pants:
					return 176;
				default:
					return 0;
			}
		}
		
		public int getHeight() {
			switch(this) {
				case skin: 
					if(size == 1) {
						return 528;
					} else if (size == 2) {
						return 572;
					} else {
						return 616;
					}
				case eye:
					return 88;
				case hair:
					if(Creator.hair == 'c' ) {
						return 396;
					} else if (Creator.hair == 'f' || Creator.hair == 'g' ) {
						return 220;
					} else if (Creator.hair == 'e' ) {
						return 352;
					} else {
						return 264;
					}
				case shirt:
					return 176;
				case pants:
					if(size == 3 || (size == 2 && Creator.pants == 'e')) {
						 return 132;
					} else {
						return 88;
					}
				default:
					return 0;
			}
		}
		
		public int getShift() {
			if(size == 1) {
				return 88;
			} else if(size == 2) {
				return 44;
			} else {
				return 0;
			}
		}
		
		public int getPosition(boolean horizontal) {
			if(horizontal) {
				switch(this) {
					case skin:
						return 660;
					case eye:
						return 704;
					case hair:
						if(Creator.hair == 'a') {
							return 528;
						} else if(Creator.hair == 'c' || Creator.hair == 'e' || Creator.hair == 'f' || Creator.hair == 'g' ) {
							return 660;
						} else {
							return 572;
						}
					case shirt:
						if(Creator.shirt == 'g' || Creator.shirt == 'h' || Creator.shirt == 'i') {
							return 660;
						} else {
							return 704;
						}
					case pants:
						return 704;
					default:
						return 0;
				}
			} else {
				switch(this) {
					case skin:
						return 400 + getShift();
					case eye:
						return 488 + getShift();
					case hair:
						if(Creator.hair == 'c' || Creator.hair == 'e' || Creator.hair == 'f' || Creator.hair == 'g' ) {
							return 400 + getShift();
						} else {
							return 356 + getShift();
						}
					case shirt:
						return 664 + getShift();
					case pants:
						return 840 + getShift();
					default:
						return 0;
				}
			}
		}
	}
	
	
	public Creator() {
		timer = new Timer(10, this);
		random = new Random();
		start = false;
//		time = 0;
		button = 0;
		size = 1;
		skin = eye = hair = shirt = pants = 'a';
		shirt = 'c';
		title = "";
		setFocusable(true);
		addKeyListener(new TAdapter());
		setFocusTraversalKeysEnabled(false);
		start();
	}
	
	public void start() {
		timer.start();
		//TODO: set clothes to random values, or draw in blank shapes
		start = true;
	}

	public void paint(Graphics g) {
		super.paint(g);
		if(start) {
			update(g);
		} else {
			return;
		}	
	}
	
	public void drawImage(Graphics g, int row, int col, int width, int height, String address) {
		File file = new File(address);
		Image i;
		try {
			i = ImageIO.read(file);
			g.drawImage(i, row, col, width, height, null, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update(Graphics g) {
		g.setColor(new Color(160,90,110));
		g.fillRect(0,0,800,800);
		
		g.setColor(Color.LIGHT_GRAY);
		
//		int vertical = 240;
//		for(int i = 0; i < 6; i++) {
//			g.fillRect(20, vertical, 70, 40);
//			vertical += 44; 
//		}
		
		g.setColor(new Color(195,115,150));
		g.fillRect(20, 120 + 69 * button, 105, 60);
		
		//g.fillRect(240,80,320,500);
		
		int vertical = 120;
		for(String address : buttons) {
			drawImage(g, 20, vertical, 105, 60, "buttons/" + address + ".png");
			vertical += 69; 
		}
		
		for(Piece p : Piece.values()) {
			drawImage(g, p.getPosition(true) / SCALE,p.getPosition(false) / SCALE, p.getWidth() / SCALE, p.getHeight() / SCALE,p.getImageAddress());
		}
		
	}
	
	public boolean left() {
		switch(button) {
			case 0:
				if(size == 1) {
					size = 3;
				} else {
					size--;
				}
				return true;
			case 1:
				if(skin == 'a') {
					skin = 'g';
				} else {
					skin--;
				}
				return true;
			case 2:
				if(eye == 'a') {
					eye = 'm';
				} else {
					eye--;
				}
				return true;
			case 3: 
				if(hair == 'a') {
					hair = 'h';
				} else {
					hair--;
				}
				return true;
			case 4:
				if(shirt == 'a') {
					shirt = 'i';
				} else {
					shirt--;
				}
				return true;
			case 5:
				if(pants == 'a') {
					pants = 'f';
				} else {
					pants--;
				}
				return true;
			default:
				return false;
		}
	}
	
	public boolean random() {
		size = random.nextInt(3) + 1;
		skin = (char)(random.nextInt(7) + 97);
		eye = (char)(random.nextInt(13) + 97);
		hair = (char)(random.nextInt(8) + 97);
		shirt = (char)(random.nextInt(9) + 97);
		pants = (char)(random.nextInt(6) + 97);
		return true;
	}
	
	public boolean right() {
		switch(button) {
			case 0:
				if(size == 3) {
					size = 1;
				} else {
					size++;
				}
				return true;
			case 1:
				if(skin == 'g') {
					skin = 'a';
				} else {
					skin++;
				}
				return true;
			case 2:
				if(eye == 'm') {
					eye = 'a';
				} else {
					eye++;
				}
				return true;
			case 3: 
				if(hair == 'h') {
					hair = 'a';
				} else {
					hair++;
				}
				return true;
			case 4:
				if(shirt == 'i') {
					shirt = 'a';
				} else {
					shirt++;
				}
				return true;
			case 5:
				if(pants == 'f') {
					pants = 'a';
				} else {
					pants++;
				}
				return true;
			default:
				return false;
		}
	}
	
	public boolean up() {
		if(button == 0) {
			button = 5;
		} else {
			button--;
		}
		return true;
	}
	
	public boolean down() {
		if(button == 5) {
			button = 0;
		} else {
			button++;
		}
		return true;
	}
	
	public boolean save() {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());	
		title = "document" + timeStamp + ".png";
		File file = new File(title);
				  
		try {
			if (!file.createNewFile()){
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		try
        {
            BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = image.createGraphics();
            graphics2D.clip(new Rectangle(200,100,getWidth()-400,getHeight()-200));
            this.paint(graphics2D);
            image = image.getSubimage(200, 100, getWidth()-400, getHeight()-200);
            ImageIO.write(image,"png", file);
            return true;
        }
        catch(Exception exception)
        {
        	return false;
        }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
//		if(start) {
//			time++;
//		}
		repaint();
	}
	
	class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if(start) {
				int keycode = e.getKeyCode();
				switch (keycode) {
					case KeyEvent.VK_LEFT:
						left();
						break;
					case KeyEvent.VK_RIGHT:
						right();
						break;
					case KeyEvent.VK_UP:
						up();
						break;
					case KeyEvent.VK_DOWN:
						down();
						break;
					case KeyEvent.VK_ENTER:
						random();
						break;
					case KeyEvent.VK_ESCAPE:
						save();
						break;
					default:
						return;
				}
			}
		}
	}

}
