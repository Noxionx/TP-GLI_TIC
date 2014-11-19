package controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import models.IModel;
import views.MainView;

public class MainController {
	private IModel model;
	private MainView view;
	public MainController(IModel model, MainView view){
		this.setModel(model);
		this.setView(view);
		initBinding();
	}
	
	public void initBinding(){
		view.addMouseListener(new MouseAdapter() {
		     @Override
		     public void mousePressed(MouseEvent e) {
		        view.getBaseComponent().processClick(e.getX(), e.getY());
		     }
		  });
	}
	public IModel getModel() {
		return model;
	}
	public void setModel(IModel model) {
		this.model = model;
	}
	public MainView getView() {
		return view;
	}
	public void setView(MainView view) {
		this.view = view;
	}

}
