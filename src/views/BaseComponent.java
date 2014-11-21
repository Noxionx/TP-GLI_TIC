package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

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
	private List<Rectangle2D.Float> rectList;
	private Arc2D.Float whiteArc;
	private Arc2D.Float centerBounds;
	private JButton btnLeft;
	private JButton btnRight;
	JTextField detailedField;
	private int selectedArcIndex = -1;
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
		generateArcs();
		generateRects();
	}
	//Initialisation des arcs centraux (statiques), de boutons de controle et du field "détails"
	public void initComponent(){
		whiteArc = new Arc2D.Float();
		whiteArc.setFrame(250,150,300,300);
		whiteArc.setAngleStart(0);
		whiteArc.setAngleExtent(360);
		centerBounds = new Arc2D.Float();
		centerBounds.setFrame(300,200,200,200);
		centerBounds.setAngleStart(0);
		centerBounds.setAngleExtent(360);
		btnLeft = new JButton("<");
		btnLeft.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				int i = selectedArcIndex;
		        i++;
		        if(i==arcList.size()){
		        	i=0;
		        }
		        setFieldSelection(i);
		     }
		});
		btnLeft.setBounds(300,5,50, 20);
		btnRight = new JButton(">");
		btnRight.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				int i = selectedArcIndex;
		        if(i==0){
		        	i=arcList.size();
		        }
		        i--;
		        setFieldSelection(i);
		     }
		});
		btnRight.setBounds(500,5,50, 20);
		btnLeft.setVisible(false);
		btnRight.setVisible(false);
		add(btnLeft);
		add(btnRight);
	    detailedField = new JTextField();
	    detailedField.setText("");
	    detailedField.setBounds(300, 30, 300, 30);
	    detailedField.setEditable(false);
	    detailedField.setVisible(false);
	    add(detailedField);
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		draw(g);
	}
	
	void draw(Graphics g){
		//Création de Graphics2D pour dessiner les arcs
		Graphics2D g2D = (Graphics2D) g; 
		Font font = new Font("Serif", Font.PLAIN, 16);
		//Rendu des arcs de données
		for(int i=0; i<arcList.size(); i++){			
			drawArc(arcList.get(i), g2D, colors[i]);
			Rectangle2D.Float currentRect = rectList.get(i);
			drawRect(currentRect, g2D, GooglePalette.GREY);
			drawInfoText(model.getFields().get(i).getTitle(), g2D, currentRect);
		}	
		//Rendu de l'arc intermédiaire (séparation blanche)
		drawArc(whiteArc, g2D, Color.WHITE);
		//Rendu du cercle central (gris)
		drawArc(centerBounds, g2D, GooglePalette.BLUE_GREY);
		//Rendu du titre du model dans le cercle gris
	    g2D.setFont(font);
	    g2D.setColor(Color.black);
	    g2D.drawString(model.getTitle(), 350, 280);
	    if(selectedArcIndex>-1){
	    	showSelectedState(true);
		    g2D.setFont(font);
		    g2D.setColor(Color.black);
		    g2D.drawString("Détails : ", 200, 48);
	    }
	    else{
	    	showSelectedState(false);
		    g2D.setFont(font);
		    g2D.setColor(Color.black);
		    g2D.drawString("", 200, 48);
	    }
	}
	//Fonction de rendu d'un arc avec sa couleur
	public void drawArc(Arc2D.Float arc, Graphics2D g, Color c){
		g.setColor(c);
		g.draw(arc);
		g.fill(arc);
	}
	//Fonction de rendu d'un arc avec sa couleur
	public void drawRect(Rectangle2D.Float rect, Graphics2D g, Color c){
		g.setColor(c);
		g.draw(rect);
		//g.fill(arc);
	}
	//Fonction de rendu du texte à l'intérieur du rectangle passé en paramètres
	public void drawInfoText(String text,  Graphics2D g, Rectangle2D.Float rect){
		Font font = new Font("Serif", Font.PLAIN, 16);
	    g.setFont(font);
	    g.setColor(Color.black);
	    g.drawString(text, (int)(rect.getX()+5), (int)(rect.getY()+30));
	}
	public void showSelectedState(boolean state){
		btnLeft.setVisible(state);
		btnRight.setVisible(state);
		detailedField.setVisible(state);
		detailedField.setText(state?model.getFields().get(selectedArcIndex).getDetail():"");		
	}
	
	//Fonction de l'observer appelée lors d'un changement sur le model
	@Override
	public void refresh(Observable o) {
		this.model = (IModel) o;
		generateArcs();
		generateRects();
		repaint();
	}
	//Fonction appelée lors d'un clic
	public void processClick(int x, int y){
		for(int i=0; i<arcList.size(); i++){
			//Si on a cliqué sur un arc (et pas au centre)
			if(arcList.get(i).contains(x, y)&&!centerBounds.contains(x, y)){				
				setFieldSelection(i);
			}
		}	
	}
	public void setFieldSelection(int index){
		if(selectedArcIndex>-1&&index==selectedArcIndex){
			selectedArcIndex = -1;
			//On regénère les arcs par défaut (non sélectionnés)
			generateArcs();
		}
		else{
			//On regénère les arcs par défaut (non sélectionnés)
			generateArcs();
			//On augmente les dimentions de l'arc sélectionné
			Arc2D.Float extendedArc = arcList.get(index);
			extendedArc.setFrame(180,80,440,440);
			arcList.set(index, extendedArc);
			selectedArcIndex = index;
		}
		//Actualisation de la vue
		repaint();
	}
	//Fonction de génération des arcs en fonction des données du model
	public void generateArcs(){
		arcList = new ArrayList<Arc2D.Float>();
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
	}

	public void generateRects(){
		int rectWidth = 100;
		int rectHeight = 50;
		rectList = new ArrayList<Rectangle2D.Float>();
		for(Arc2D.Float arc : arcList){
			int centerX = 400;
			int centerY = 300;
			//Calcul du demi angle par rapport à l'arc
			int semiAngle = (int) (arc.getAngleStart() + Math.round(arc.getAngleExtent()/2));
			//Calcul des coordonnées d'un point par rapport au cercle extérieur et en fonction du demi angle calculé
			int x = (int) (250*Math.cos(Math.toRadians(semiAngle)));
			int y = (int) (250*Math.sin(Math.toRadians(semiAngle)));
			if(x<0){
				x-=rectWidth;
			}
			if(y<0){
				y+=rectHeight;
			}
			Rectangle2D.Float rect = new Rectangle2D.Float();
			rect.setRect(centerX+x, centerY-y, 100, 50);
			rectList.add(rect);
		}
	}
}
