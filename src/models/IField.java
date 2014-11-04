package models;

import libs.Observable;

public interface IField extends Observable{
	
	String getTitle();
	void setTitle(String title);
	
	String getDetail();
	void setDetail(String detail);
	
	int getValue();
	void setValue(int value);

}
