package fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Suvo on 18-Dec-15.
 */
public class Controller {
    public ImageView image;
    Image im;
    String str[]={
            String.valueOf(getClass().getResource("a.gif")),String.valueOf(getClass().getResource("b.gif")),
            String.valueOf(getClass().getResource("s.gif"))
    };
    int iN;

    @FXML
    public void initialize(){
        im=new Image(str[0]);
        image.setImage(im);
        iN=0;
    }

    public void joinGameAction(ActionEvent actionEvent) {

    }

    public void createGameAction(ActionEvent actionEvent) {

    }
    @FXML
    public void changeImage (){
        System.out.println("Change"+iN);
        im=new Image(str[iN]);
        image.setImage(im);
        iN=(iN+1)%3;
    }
}
