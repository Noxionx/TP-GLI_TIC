package views;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import models.IModel;

public class MainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BaseComponent baseComponent;
	

	public MainView(IModel model) {
		setSize(800, 600);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		this.baseComponent = new BaseComponent(model);
		add(this.baseComponent);
		
	}
	public BaseComponent getBaseComponent(){
		return this.baseComponent;
	}

}
