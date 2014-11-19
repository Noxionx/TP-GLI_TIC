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
	private Arc2D.Float centerBounds;
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
		arcList = generateArcs(model);
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		draw(g);
	}
	
	void draw(Graphics g){
		Graphics2D g2 = (Graphics2D) g; 
		for(int i=0; i<arcList.size(); i++){			
		    g2.setColor(GooglePalette.GREY);
		    g2.draw(arcList.get(i));
		    g2.setColor(colors[i]);
		    g2.fill(arcList.get(i));
		}	
		Arc2D.Float whiteArc = new Arc2D.Float();
		whiteArc.setFrame(250,150,300,300);
		whiteArc.setAngleStart(0);
		whiteArc.setAngleExtent(360);
		g2.setColor(GooglePalette.GREY);
	    g2.draw(whiteArc);
	    g2.setColor(Color.white);
	    g2.fill(whiteArc);
	    centerBounds = new Arc2D.Float();
		centerBounds.setFrame(300,200,200,200);
		centerBounds.setAngleStart(0);
		centerBounds.setAngleExtent(360);
		g2.setColor(GooglePalette.BLUE_GREY);
	    g2.draw(centerBounds);
	    g2.fill(centerBounds);
	    Font font = new Font("Serif", Font.PLAIN, 16);
	    g2.setFont(font);
	    g2.setColor(Color.black);
	    g2.drawString(model.getTitle(), 350, 280);
	}

	@Override
	public void refresh(Observable o) {
		this.model = (IModel) o;
		arcList = generateArcs(model);
		repaint();
	}
	
	public void processClick(int x, int y){
		for(int i=0; i<arcList.size(); i++){
			if(arcList.get(i).contains(x, y)&&!centerBounds.contains(x, y)){
				arcList = generateArcs(model);
				arcList.set(i, extendArc(arcList.get(i)));
				repaint();
			}
		}
		
	}
	public Arc2D.Float extendArc(Arc2D.Float arc){
		arc.setFrame(180,80,440,440);
		return arc;
	}
	
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
