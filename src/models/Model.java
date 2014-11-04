package models;

import java.util.ArrayList;
import java.util.List;

import libs.Observable;
import libs.Observer;

public class Model implements IModel, Observer{
	
	private List<Observer> observers = new ArrayList<Observer>();
	
	private String title;
	private List<IField> fields;
	
	public Model(){
		this.title = "Default title";
		this.fields = new ArrayList<IField>();
	}
	public Model(String title){
		this.title = title;
		this.fields = new ArrayList<IField>();
	}
	public Model(String title, List<IField> fields){
		this.title = title;
		this.fields = fields;
		for(IField field : fields){
			field.addObserver(this);
		}
	}
	

	@Override
	public void addObserver(Observer o) {
		observers.add(o);		
	}
	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}
	@Override
	public void notifyObservers() {
		for(Observer o : observers){
			o.refresh(this);
		}
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
		notifyObservers();
	}

	@Override
	public List<IField> getFields() {
		return fields;
	}

	@Override
	public void addField(IField field) {
		this.fields.add(field);
		field.addObserver(this);
	}
	@Override
	public void removeField(IField field) {
		field.removeObserver(this);
		this.fields.remove(field);
	}

	@Override
	public void refresh(Observable o) {
		notifyObservers();
	}

}
