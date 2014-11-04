package models;

import java.util.ArrayList;
import java.util.List;

import libs.Observer;

public class Field implements IField{
	
	private List<Observer> observers = new ArrayList<Observer>();
	
	private String title;
	private String detail;
	private int value;

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
	public String getDetail() {
		return this.detail;
	}

	@Override
	public void setDetail(String detail) {
		this.detail = detail;
		notifyObservers();
	}

	@Override
	public int getValue() {
		return this.value;
	}

	@Override
	public void setValue(int value) {
		this.value = value;
		notifyObservers();
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
}
