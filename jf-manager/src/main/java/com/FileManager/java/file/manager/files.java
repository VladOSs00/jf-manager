package com.FileManager.java.file.manager;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class files {

    public TextField txtlabel;

    /**  метод для  создания файлов
     * @param actionEvent
     * @throws IOException
     */

    public void onCreateFiles(ActionEvent actionEvent) throws IOException {
        String a = String.valueOf(String.format(txtlabel.getText()));
        File file = new File(a);
        String text = txtlabel.getText();
        try
        {
            if(text.isEmpty())
            {
                JOptionPane.showMessageDialog(null,"Введите название файла");
            }
            else
            {
                if (file.createNewFile()){
                    JOptionPane.showMessageDialog(null,"Файл " + a + " создан");
                    /*
                    if (file.exists()){
                        Desktop.getDesktop().open(new File(String.valueOf(file)));
                    }
                     */
                }else{
                    JOptionPane.showMessageDialog(null,"Файл " + a + " уже существует");
                }
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,"Не удалось создать файл");
        }



    }
}
