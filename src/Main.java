import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import views.MainView;
import models.Field;
import models.IField;
import models.IModel;
import models.Model;


public class Main extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public static void main(String[] args){
		IModel mod = new Model();
		List<IField> fields = new ArrayList<IField>();
		for(int i=0; i<4; i++){
			IField field = new Field();
			field.setTitle("Field " + i);
			field.setDetail("Details field " + i);
			field.setValue(i*10 + 5);
			fields.add(field);
		}
		mod.setFields(fields);
		for(int i=0; i<4; i++){
			System.out.println(mod.getFields().get(i).getTitle() + mod.getFields().get(i).getDetail() + " : " + mod.getFields().get(i).getValue());
		}
		MainView mainView = new MainView(mod);
		mainView.setVisible(true);
		
	}

}
