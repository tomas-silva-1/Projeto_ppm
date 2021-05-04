package InterfaceGrafica;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Auxiliar {


    static Image img() throws FileNotFoundException {
        /*String str = "C:/Users/rodri/Desktop/Iscte/Ppm/Ppm_Projeto/Projeto_ppm/ppm/src/imagens/img.png";
        FileInputStream f = new FileInputStream(str);*/
        /*Image img = new Image("file:iscte.png", 550, 400, false, false);
        //Image img = new Image(f);
        return img;*/
        File file = new File("C:/Users/rodri/Desktop/Iscte/Ppm/Ppm_Projeto/Projeto_ppm/ppm/src/imagens/img.png");
        Image image = new Image(file.toURI().toString());
        return image;


    }
}
