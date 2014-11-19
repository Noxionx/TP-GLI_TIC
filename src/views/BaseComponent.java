package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import libs.GooglePalette;
import libs.Observable;
import libs.Observer;
import models.IModel;

public class BaseComponent extends JComponent implements Observer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IModel model;
	private List<Arc2D.Float> arcList;
	private Arc2D.Float whiteArc;
	private Arc2D.Float centerBounds;
	private Arc2D.Float selectedArc;
	private Color[] colors = {
		GooglePalette.RED,
		GooglePalette.PINK,
		GooglePalette.PURPLE,
		GooglePalette.INDIGO,
		GooglePalette.BLUE,
		GooglePalette.CYAN,
		GooglePalette.TEAL,
		GooglePalette.GREEN,
		GooglePalette.LIME,
		GooglePalette.YELLOW,
		GooglePalette.ORANGE,
		GooglePalette.BROWN
	};
	
	public BaseComponent(IModel model){
		this.model = model;
		model.addObserver(this);
		initComponent();
		arcList = generateArcs(model);
	}
	//Initialisation des arcs centraux (statiques)
	public void initComponent(){
		whiteArc = new Arc2D.Float();
		whiteArc.setFrame(250,150,300,300);
		whiteArc.setAngleStart(0);
		whiteArc.setAngleExtent(360);
		centerBounds = new Arc2D.Float();
		centerBounds.setFrame(300,200,200,200);
		centerBounds.setAngleStart(0);
		centerBounds.setAngleExtent(360);
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		draw(g);
	}
	
	void draw(Graphics g){
		//Création de Graphics2D pour dessiner les arcs
		Graphics2D g2D = (Graphics2D) g; 
		//Rendu des arcs de données
		for(int i=0; i<arcList.size(); i++){			
			drawArc(arcList.get(i), g2D, colors[i]);
		}	
		//Rendu de l'arc intermédiaire (séparation blanche)
		drawArc(whiteArc, g2D, Color.WHITE);
		//Rendu du cercle central (gris)
		drawArc(centerBounds, g2D, GooglePalette.BLUE_GREY);
		//Rendu du titre du model dans le cercle gris
	    Font font = new Font("Serif", Font.PLAIN, 16);
	    g2D.setFont(font);
	    g2D.setColor(Color.black);
	    g2D.drawString(model.getTitle(), 350, 280);
	}
	//Fonction de rendu d'un arc avec sa couleur
	public void drawArc(Arc2D.Float arc, Graphics2D g, Color c){
		g.setColor(c);
		g.draw(arc);
		g.fill(arc);
	}
	//Fonction de l'observer appelée lors d'un changement sur le model
	@Override
	public void refresh(Observable o) {
		this.model = (IModel) o;
		arcList = generateArcs(model);
		repaint();
	}
	//Fonction appelée lors d'un clic
	public void processClick(int x, int y){
		for(int i=0; i<arcList.size(); i++){
			//Si on a cliqué sur un arc (et pas au centre)
			if(arcList.get(i).contains(x, y)&&!centerBounds.contains(x, y)){				
				if(selectedArc!=null&&arcList.get(i)==selectedArc){
					selectedArc = null;
					//On regénère les arcs par défaut (non sélectionnés)
					arcList = generateArcs(model);
				}
				else{
					//On regénère les arcs par défaut (non sélectionnés)
					arcList = generateArcs(model);
					//On augmente les dimentions de l'arc sélectionné
					arcList.set(i, extendArc(arcList.get(i)));
					selectedArc = arcList.get(i);
				}
				//Actualisation de la vue
				repaint();
			}
		}
		
	}
	//Fonction de changement de dimentions (plus grandes) pour un arc sélectionné
	public Arc2D.Float extendArc(Arc2D.Float arc){
		arc.setFrame(180,80,440,440);
		return arc;
	}
	//Fonction de génération des arcs en fonction des données du model
	public List<Arc2D.Float> generateArcs(IModel model){
		List<Arc2D.Float> arcList = new ArrayList<Arc2D.Float>();
		int totalValue = 0;
		for(int i=0; i<model.getFields().size(); i++){
			totalValue += model.getFields().get(i).getValue();
		}
		int lastValue = 0;
		for(int i=0; i<model.getFields().size(); i++){
			Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
			arc.setFrame(200, 100, 400, 400);
			arc.setAngleStart(lastValue);
			float fieldValue = model.getFields().get(i).getValue();
			int currentValue = Math.round((fieldValue/totalValue)*360);
			arc.setAngleExtent(currentValue);
			lastValue += currentValue;
			arcList.add(arc);
		}
		return arcList;
	}

}
