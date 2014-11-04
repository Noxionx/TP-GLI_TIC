package models;

import java.util.ArrayList;
import java.util.List;

import libs.Observer;

public class Model implements IModel{
	
	private List<Observer> observers = new ArrayList<Observer>();
	
	private String title;
	private List<IField> fields = new ArrayList<IField>();

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
		for(int i=0; i < observers.size(); i++){
			observers.get(i).refresh(this);
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
	public void setFields(List<IField> fields) {
		this.fields = fields;
		notifyObservers();
	}

}
