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
	
	public BaseComponent(IModel model){
		this.model = model;
	}

	@Override
	public void paintComponent(Graphics g){
		List<Arc2D.Float> arcList = generateArcs(model);
		Graphics2D g2 = (Graphics2D) g; 
		for(int i=0; i<arcList.size(); i++){			
		    g2.setColor(Color.gray);
		    g2.draw(arcList.get(i));
		    if(i==0){
		    	g2.setColor(Color.red);
		    }
		    else{
		    	g2.setColor(Color.blue);
		    }
		    g2.fill(arcList.get(i));
		}	    
	}

	@Override
	public void refresh(Observable o) {
		
		
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
