package models;

import java.util.List;

import libs.Observable;

public interface IModel extends Observable {
	
	String getTitle();
	void setTitle(String title);
	
	List<IField> getFields();
	void setFields(List<IField> fields);

}
