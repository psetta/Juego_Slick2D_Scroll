import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


public class main extends BasicGame {

	private static int altoXogo = 800;
	private static int anchoXogo = 1200;
	private static int altoVentana = 400;
	private static int anchoVentana = 600;
	private int radioPJ = 10;
	private boolean run = false;
	private punto punto_camara = new punto(0,0);
	private character pj = new character(anchoVentana/2, altoVentana/2, radioPJ, 0.2f);
	private character pj_fut = new character(anchoVentana/2, altoVentana/2, radioPJ, 0.2f);
	
	private ArrayList <Shape> arrayBloques=new ArrayList <Shape> ();
	
	public main(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public static void main(String args[]) throws SlickException {
		AppGameContainer app = new AppGameContainer(new main("Slic2DConceptos"));
		app.setDisplayMode(anchoVentana, altoVentana, false);
		app.setVSync(true);
		app.setShowFPS(false);
		app.start();
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		//ponemos color por defecto blanco
		g.setColor(new Color(255, 255, 255));
		
		//dibujamos jugador
		g.fillOval(pj.bola.getX()-punto_camara.x, pj.bola.getY()-punto_camara.y, 20, 20);
		
		for (int i=0; i<arrayBloques.size(); i++){
			g.setColor(new Color(0, 255, 0));
			if (pj.bola.intersects(arrayBloques.get(i))) g.setColor(new Color(255, 0, 0));
			g.fillRect(arrayBloques.get(i).getX()-punto_camara.x, arrayBloques.get(i).getY()-punto_camara.y, arrayBloques.get(i).getWidth(), arrayBloques.get(i).getHeight());
		}
		
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		
		
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		Input input = gc.getInput();
		if (!run) startGame();
		
		if (input.isKeyDown(Input.KEY_UP) && pj.bola.getY() > 0){
			pj_fut.bola.setY(pj_fut.bola.getY() - pj_fut.vel * delta);
		}
		
		if (input.isKeyDown(Input.KEY_DOWN) && pj.bola.getY() < altoXogo - radioPJ*2) {
			pj_fut.bola.setY(pj_fut.bola.getY() + pj_fut.vel * delta);
		}
		
		if (input.isKeyDown(Input.KEY_LEFT) && pj.bola.getX() > 0) {
			pj_fut.bola.setX(pj_fut.bola.getX() - pj_fut.vel * delta);
		}
		
		if (input.isKeyDown(Input.KEY_RIGHT) && pj.bola.getX() < anchoXogo - radioPJ*2) {
			pj_fut.bola.setX(pj_fut.bola.getX() + pj_fut.vel * delta);
		}
		
		for (int i=0; i<arrayBloques.size(); i++){
			if (pj_fut.bola.intersects(arrayBloques.get(i))) {
				
				Circle bola_cam_x = new Circle(pj_fut.bola.getCenterX(),pj.bola.getCenterY(),radioPJ);
				if (bola_cam_x.intersects(arrayBloques.get(i))) {
					pj_fut.bola.setX(pj.bola.getX());
				}
				Circle bola_cam_y = new Circle(pj.bola.getCenterX(),pj_fut.bola.getCenterY(),radioPJ);
				if (bola_cam_y.intersects(arrayBloques.get(i))) {
					pj_fut.bola.setY(pj.bola.getY());
				}
			}
		
		}
		
		pj.bola.setX(pj_fut.bola.getX());
		pj.bola.setY(pj_fut.bola.getY());
		
		//punto camara
		
		punto_camara.x = pj.bola.getX()-anchoVentana/2;
		punto_camara.y = pj.bola.getY()-altoVentana/2;
		
		if (punto_camara.x < 0) {
			punto_camara.x = 0;
		}
		
		if (punto_camara.x > anchoXogo-anchoVentana) {
			punto_camara.x = anchoXogo-anchoVentana;
		}
		
		if (punto_camara.y < 0) {
			punto_camara.y = 0;
		}
		
		if (punto_camara.y > altoXogo-altoVentana) {
			punto_camara.y = altoXogo-altoVentana;
		}
		
	}

	private void startGame(){
		crearBloques();
		run = true;
	}
	
	private void crearBloques(){
		arrayBloques.clear();
		arrayBloques.add( new Rectangle (1, 1, 50, 50)); 
		arrayBloques.add( new Rectangle (300, 250, 100, 10));
		arrayBloques.add( new Rectangle (400, 300, 50, 50));
		arrayBloques.add( new Rectangle (700, 300, 40, 50));
		arrayBloques.add( new Rectangle (600, 600, 40, 10));
		arrayBloques.add( new Rectangle (400, 700, 10, 60));
		arrayBloques.add( new Rectangle (700, 30, 60, 60));
	}

}
