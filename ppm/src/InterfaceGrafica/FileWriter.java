package InterfaceGrafica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class FileWriter {


    public static void addImg(String nome) {
        ArrayList<String> list = getImages();
        list.add(nome);
        writeFile(list);
    }


    public static void removeImg(String nome) {
        ArrayList<String> list = getImages();
        list.remove(nome);
        writeFile(list);
    }
    public static void writeFile(ArrayList<String> list){
        File file = new File("imagens.txt");
        try {
            PrintWriter writer = new PrintWriter(file);
            list.forEach(writer::println);
          /*  for (String str: list) {
                writer.println(str);
            }*/
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
                //scanner.nextLine();
            }
            scanner.close();
            return list;
        } catch (FileNotFoundException e) {
            return list;
        }

    }
    public static String[] getImage() {
        File file = new File("imagens.txt");
        ArrayList<String> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String img = scanner.nextLine();
                list.add(img);
                //scanner.nextLine();
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

}
