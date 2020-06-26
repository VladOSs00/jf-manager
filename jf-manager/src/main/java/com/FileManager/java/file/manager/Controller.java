package com.FileManager.java.file.manager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Controller {
    @FXML
    VBox leftPanel, rightPanel;

    @FXML
    public TextField txtlabel;

    /** метод для закрытия приложения
     * @param actionEvent
     */
    public void btnExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    /** метод для открытия приложения Notepad
     * @param actionEvent
     */
    public void btnNotepad(ActionEvent actionEvent) {

        Runtime r = Runtime.getRuntime();
        Process p = null;

        try {
            p = r.exec("notepad");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Не удалось запустить приложение ");
        }
    }

    /** метод для открытия приложения Paint
     * @param actionEvent
     */
    public void btnPaint(ActionEvent actionEvent) {

        Runtime r = Runtime.getRuntime();
        Process p = null;

        try {
            p = r.exec("mspaint");
          //  Desktop.getDesktop().open(new File("C:\\Program Files\\Internet Explorer\\iexplore.exe"));
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Не удалось запустить приложение ");
        }
    }

    /** метод для открытия приложения Internet Explorer
     * @param actionEvent
     */
    public void btnIE(ActionEvent actionEvent) {

        Runtime r = Runtime.getRuntime();
        Process p = null;

        try {
            p = r.exec("C:/Program Files/internet explorer/iexplore.exe");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Не удалось запустить приложение ");
        }
    }

    /**
     * @param actionEvent
     */
    public  void  btnOnDocument(ActionEvent actionEvent){
        Runtime r = Runtime.getRuntime();
        Process p = null;

        try {
            Desktop.getDesktop().open(new File("/home/root12345/Desktop/Doc.odt"));
            JOptionPane.showMessageDialog(null,"Файл не найден");
            //p = r.exec("/Applications/Safari.app");
            //p.waitFor();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Не удалось запустить приложение ");
        }
    }



    /** метод для открытия приложения Safari
     * @param actionEvent
     */
    public void btnSafari(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().open(new File("/Applications/Safari.app"));
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Не удалось запустить приложение ");
        }
    }

    /** метод для открытия приложения текстового редактора
     * @param actionEvent
     */
    public void btnTextEdit(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().open(new File("/Applications/TextEdit.app"));
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Не удалось запустить приложение ");
        }
    }

    /** метод для открытия приложения калькулятора
     * @param actionEvent
     */
    public void btnCalculator(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().open(new File("/Applications/Calculator.app"));
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Не удалось запустить приложение ");
        }
    }

    /** метод для копирования файлов
     * @param actionEvent
     */
    public void copyBtnAction(ActionEvent actionEvent) {
          // получаем ссылку для левой панели
        PanelController leftPC = (PanelController) leftPanel.getProperties().get("ctrl");
         //  получаем ссылку для правой панели
        PanelController rightPC = (PanelController) rightPanel.getProperties().get("ctrl");

        // если не был выбран панель (левая или правая), то получаем предупреждение
        if (leftPC.getSelectedFilename() == null && rightPC.getSelectedFilename() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ни один файл не был выбран для копирования", ButtonType.OK);
            alert.showAndWait();
            return;
        }

         //
        PanelController srcPC = null, dstPC = null;
        // если в левой был выбран файл
        if (leftPC.getSelectedFilename() != null) {
            // копируем в правую панель
            srcPC = leftPC;
            dstPC = rightPC;
        }
        // если в правой был выбран файл
        if (rightPC.getSelectedFilename() != null) {
            // копируем в левую панель
            srcPC = rightPC;
            dstPC = leftPC;
        }

        // из левой панели выбираем файлы
        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
        // из правой панели выбираем файлы
        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());

        try {
            // копируем файл из источника в токчу назначения
            Files.copy(srcPath, dstPath);
            // в точке назначении обновляем лист
            dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось скопировать существующий файл", ButtonType.OK);
            alert.showAndWait();
        }
    }


    /** метод для открытия новой вкладки приложения.
     * @param actionEvent
     * @throws IOException
     */
    public void btnOnNew(ActionEvent actionEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);
        stage.setTitle("File Manager");
        stage.setScene(new Scene(root, 1280, 600));
        stage.show();
      // или вот так
        // stage.showAndWait();
    }

    /** метод для открытия текстового редактора
     * @param actionEvent
     */
    public void btnLibreOffice(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().open(new File("/home/root12345/Desktop/Doc.odt"));
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Не удалось запустить приложение ");
        }

    }

    /** метод для открытия формы создания папок
     * @param actionEvent
     * @throws IOException
     */
    public void btnCreateFolders(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/folder.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);
        stage.setTitle("File Manager");
        stage.setScene(new Scene(root, 260, 120));
        stage.show();
    }

    /** метод для открытия формы создания файлов
     * @param actionEvent
     * @throws IOException
     */
    public void btnCreateFiles(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);
        stage.setTitle("File Manager");
        stage.setScene(new Scene(root, 260, 120));
        stage.show();

    }

    public void moveBtnAction(ActionEvent actionEvent) {
        // получаем ссылку для левой панели
        PanelController leftPC = (PanelController) leftPanel.getProperties().get("ctrl");
        //  получаем ссылку для правой панели
        PanelController rightPC = (PanelController) rightPanel.getProperties().get("ctrl");

        // если не был выбран панель (левая или правая), то получаем предупреждение
        if (leftPC.getSelectedFilename() == null && rightPC.getSelectedFilename() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ни один файл не был выбран для перемещения", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        //
        PanelController srcPC = null, dstPC = null;
        // если в левой был выбран файл
        if (leftPC.getSelectedFilename() != null) {
            // копируем в правую панель
            srcPC = leftPC;
            dstPC = rightPC;
        }
        // если в правой был выбран файл
        if (rightPC.getSelectedFilename() != null) {
            // копируем в левую панель
            srcPC = rightPC;
            dstPC = leftPC;
        }

        // из левой панели выбираем файлы
        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
        // из правой панели выбираем файлы
        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());

        try {
            // перемещяем файл из источника в токчу назначения
            Files.move(srcPath, dstPath);
            // в точке назначении обновляем лист
            dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
            srcPC.updateList(Paths.get(srcPC.getCurrentPath()));
            /*
            if(Files.exists(dstPath)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Такой файл уже существует", ButtonType.OK);
                alert.showAndWait();

            } else if (Files.exists(srcPath)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Такой файл уже существует", ButtonType.OK);
                alert.showAndWait();
            }

             */
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось переместить существующий файл", ButtonType.OK);
            alert.showAndWait();
        }

    }

    public void deleteBtnAction(ActionEvent actionEvent) {
        // получаем ссылку для левой панели
        PanelController leftPC = (PanelController) leftPanel.getProperties().get("ctrl");
        //  получаем ссылку для правой панели
        PanelController rightPC = (PanelController) rightPanel.getProperties().get("ctrl");

        // если не был выбран панель (левая или правая), то получаем предупреждение
        if (leftPC.getSelectedFilename() == null && rightPC.getSelectedFilename() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ни один файл не был выбран для удаления", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        //
        PanelController srcPC = null, dstPC = null;
        // если в левой был выбран файл
        if (leftPC.getSelectedFilename() != null) {
            // копируем в правую панель
            srcPC = leftPC;
            dstPC = rightPC;
        }
        // если в правой был выбран файл
        if (rightPC.getSelectedFilename() != null) {
            // копируем в левую панель
            srcPC = rightPC;
            dstPC = leftPC;
        }

        // из левой панели выбираем файлы
        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
        // из правой панели выбираем файлы
        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());

        try {
            // удаляем файл из источника в токчу назначения
           // Files.delete(dstPath);
           Files.delete(srcPath);
            // в точке назначении обновляем лист
            dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
            srcPC.updateList(Paths.get(srcPC.getCurrentPath()));
        } catch (IOException e) {
           // Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось удалить указанный файл", ButtonType.OK);
           // alert.showAndWait();
        }
    }


    public void btnRefresf(ActionEvent actionEvent) {

        PanelController leftPC = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightPC = (PanelController) rightPanel.getProperties().get("ctrl");

        if (leftPC.getSelectedFilename() == null && rightPC.getSelectedFilename() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Выберите панель для обновления", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        PanelController srcPC = null, dstPC = null;
        if (leftPC.getSelectedFilename() != null) {
            srcPC = leftPC;
            dstPC = rightPC;
        }

        if (rightPC.getSelectedFilename() != null) {
            srcPC = rightPC;
            dstPC = leftPC;
        }


        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());

        try {

            // в точке назначении обновляем лист
            dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
            srcPC.updateList(Paths.get(srcPC.getCurrentPath()));

        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,"Не удалось обновить");
        }


    }
}