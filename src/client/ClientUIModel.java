package client;

import javafx.beans.property.SimpleDoubleProperty;
import server.ServerUIModel;

public class ClientUIModel {

    
    private static ClientUIModel instance;
    
    
    private double timeElapsed;
    
    /**
     * @return Singleton instance of ServerUIModel
     */
    protected static ClientUIModel getInstance(){
        if( instance == null ){
            instance = new ClientUIModel();
        }
        return instance;
    }

    
    public ClientUIModel() {
       
    }


	public double getTimeElapsed() {
		return timeElapsed;
	}


	public void setTimeElapsed(double timeElapsed) {
		this.timeElapsed = timeElapsed;
	}


}
