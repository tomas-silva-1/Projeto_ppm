package InterfaceGrafica;

import javafx.scene.layout.GridPane;
import package1.Manipulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class AuxJava {

    static String path = System.getProperty("user.dir") + "\\Projeto_ppm\\ppm\\src\\imagensGUI\\";

    public static void addImg(String nome) {
        ArrayList<String> list = getImages();
        list.add(nome);
        writeFile(list);
    }

    public static void removeImg(String nome) {
        ArrayList<String> list = getImages();
        list.remove(nome);
        writeFile(list);
        /*File f = new File(path+nome);
        if(f.delete()){
            System.out.println("File deleted successfully");
        }else{
            System.out.println("Failed to delete the file");
        }*/
    }

    public static void trocar(String nome,String nome2){
        ArrayList<String> list = getImages();
        int n = list.indexOf(nome);
        int n2 = list.indexOf(nome2);
        list.set(n,nome2);
        list.set(n2,nome);
        writeFile(list);
    }

    public static void writeFile(ArrayList<String> list){
        File file = new File("imagens.txt");
        try {
            PrintWriter writer = new PrintWriter(file);
            list.forEach(writer::println);
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static ArrayList<String> getImages() {
        File file = new File("imagens.txt");
        ArrayList<String> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String img = scanner.nextLine();
                list.add(img);
            }
            scanner.close();
            return list;
        } catch (FileNotFoundException e) {
            return list;
        }

    }
    public static void trocarNome(String nome1, String nome2){
        ArrayList<String> list = getImages();
        list.set(list.indexOf(nome1),nome2 );
        writeFile(list);
        /*Manipulation.generateBitMapFromImage(path+nome1).generateImageFromBitMap(path+nome2);
        File f = new File(path+nome1);
        f.delete();*/

    }
    public static String[] getImage() {
        File file = new File("imagens.txt");
        ArrayList<String> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String img = scanner.nextLine();
                list.add(img);
            }
            scanner.close();

        } catch (FileNotFoundException e) {

        }
        String[] lista = new String[list.size()];
        for (int i=0;i< lista.length;i++){
            lista[i]=list.get(i);
        }
        return lista;
    }

    public static void deleteGrid(GridPane g){
        g.getChildren().clear();
    }

}
