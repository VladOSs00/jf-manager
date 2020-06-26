package com.FileManager.java.file.manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.File;

public class folder {
    @FXML
    public TextField txtlabel;

    /**  метод для создания папок
     * @param actionEvent
     */

    public void onCreateFolder(ActionEvent actionEvent)  {
        String a = String.valueOf(String.format(txtlabel.getText()));
        File file = new File(a);
        String text = txtlabel.getText();
        try
        {
            if(text.isEmpty())
            {
                JOptionPane.showMessageDialog(null,"Введите название папки");
            }
            else
            {
                if (!file.isDirectory())
                {
                    file.mkdir();
                    JOptionPane.showMessageDialog(null,"Папка " + a + " создана");
                    /*
                    if (file.exists()){
                        Desktop.getDesktop().open(new File(String.valueOf(file)));
                    }
                     */
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Папка " + a + " уже существует");
                }
            }
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,"Не удалось создать папку");
        }


    }
}
