import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import models.Field;
import models.IField;
import models.IModel;
import models.Model;
import views.MainView;
import controllers.MainController;


public class Main extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static boolean testMode = false;

	public static void main(String[] args){

		List<IField> fields = new ArrayList<IField>();
		for(int i=0; i<4; i++){
			IField field = new Field();
			field.setTitle("Field " + i);
			field.setDetail("Details field " + i);
			field.setValue(i*10 + 5);
			fields.add(field);
		}
		IModel mod = new Model("Model test", fields);
		MainView mainView = new MainView(mod);
		mainView.setVisible(true);
		MainController ctrl = new MainController(mod, mainView);
		if(testMode){
			System.out.println("Création du modèle de données :");
			for(int i=0; i<4; i++){
				System.out.println(mod.getFields().get(i).getTitle() + mod.getFields().get(i).getDetail() + " : " + mod.getFields().get(i).getValue());
			}
			try {
				Thread.sleep(3000);
				mod.setTitle("Model test : nouveau nom");
				mod.getFields().get(0).setValue(33);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
