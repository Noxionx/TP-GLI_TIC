package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import libs.Observable;
import libs.Observer;
import models.IModel;

public class BaseComponent extends JComponent implements Observer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IModel model;
	private Color[] colors = {
		Color.cyan,
		Color.blue,
		Color.pink,
		Color.red,
		Color.orange,
		Color.green
	};
	
	public BaseComponent(IModel model){
		this.model = model;
		model.addObserver(this);
	}

	@Override
	public void paintComponent(Graphics g){
		List<Arc2D.Float> arcList = generateArcs(model);
		Graphics2D g2 = (Graphics2D) g; 
		for(int i=0; i<arcList.size(); i++){			
		    g2.setColor(Color.gray);
		    g2.draw(arcList.get(i));
		    g2.setColor(colors[i]);
		    g2.fill(arcList.get(i));
		}	
		Arc2D.Float whiteArc = new Arc2D.Float();
		whiteArc.setFrame(250,150,300,300);
		whiteArc.setAngleStart(0);
		whiteArc.setAngleExtent(360);
		g2.setColor(Color.gray);
	    g2.draw(whiteArc);
	    g2.setColor(Color.white);
	    g2.fill(whiteArc);
		Arc2D.Float centerArc = new Arc2D.Float();
		centerArc.setFrame(300,200,200,200);
		centerArc.setAngleStart(0);
		centerArc.setAngleExtent(360);
		g2.setColor(Color.gray);
	    g2.draw(centerArc);
	    g2.fill(centerArc);
	    Font font = new Font("Serif", Font.PLAIN, 16);
	    g2.setFont(font);
	    g2.setColor(Color.black);
	    g2.drawString(model.getTitle(), 350, 280);
	}

	@Override
	public void refresh(Observable o) {
		this.model = (IModel) o;
		paintComponent(getGraphics());
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
