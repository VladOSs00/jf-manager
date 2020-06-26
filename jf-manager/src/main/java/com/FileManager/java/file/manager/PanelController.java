package com.FileManager.java.file.manager;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PanelController implements Initializable {
    @FXML
    TableView<FileInfo> filesTable;

    @FXML
    ComboBox<String> disksBox;

    @FXML
    TextField pathField;
    public TextField txtlabel;

    @Override
    /**
     * @param location
     * @param resources
     */

    public void initialize(URL location, ResourceBundle resources) {
         // Создаем таблицу с названиями столбцами
        // В таблицу записывается информация с файла FileInfo
        TableColumn<FileInfo, String> fileTypeColumn = new TableColumn<>();
        //                                                               запрашиваем имя, тип и значение файла
        fileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().getName()));
         // Указываем длицу столбца в pxl
        fileTypeColumn.setPrefWidth(24);

        // Столбец содержит имя файла
        TableColumn<FileInfo, String> filenameColumn = new TableColumn<>("Имя");
        filenameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFilename()));
        filenameColumn.setPrefWidth(240);

        // Столбец содержит размер файла
        TableColumn<FileInfo, Long> fileSizeColumn = new TableColumn<>("Размер");
        fileSizeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getSize()));
        //
        fileSizeColumn.setCellFactory(column -> {
            return new TableCell<FileInfo, Long>() {
                @Override
                protected void updateItem(Long item, boolean empty) {
                    super.updateItem(item, empty);
                    // Если item пустой или ячейка пустая
                    if (item == null || empty) {
                      //  ячека ничего не делает
                        setText(null);
                        setStyle("");
                       // иначе
                    } else {
                       // ячейке что-то есть       добавляем размер в байтах
                        String text = String.format("%,d bytes", item);
                        // если item = -1L
                        if (item == -1L) {
                            // то это директория
                            text = "[DIR]";
                        }
                        // вывод в ячейку
                        setText(text);
                    }
                }
            };
        });
        fileSizeColumn.setPrefWidth(120);

                   // Указываем формат вывода даты
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Столбец с датой изменения файла
        TableColumn<FileInfo, String> fileDateColumn = new TableColumn<>("Дата изменения");
        fileDateColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastModified().format(dtf)));
        fileDateColumn.setPrefWidth(120);


        // В таблицу добавляем столбцы о типе, имени, размера, дату
        filesTable.getColumns().addAll(fileTypeColumn, filenameColumn, fileSizeColumn, fileDateColumn);

        // Сортируем по директории с типами файлов
        filesTable.getSortOrder().add(fileTypeColumn);

        // очищаем
        disksBox.getItems().clear();
        // Получаем список корневых директорий
        for (Path p : FileSystems.getDefault().getRootDirectories()) {
            disksBox.getItems().add(p.toString());
        }
        // По умолчанию выбираем 1 из них
        disksBox.getSelectionModel().select(0);

        // событие на мышку
        filesTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // произошло 2 клик
                if (event.getClickCount() == 2) {
                      //  получаем текущий путь и добавляем имя файлов
                    Path path = Paths.get(pathField.getText()).resolve(filesTable.getSelectionModel().getSelectedItem().getFilename());
                      // Если это директориия
                    if (Files.isDirectory(path)) {
                        // переходим в директорию
                        updateList(path);
                    }
                }
            }
        });

         // ссылка на текущую директорию
        updateList(Paths.get("."));
    }

    /** метод, который собирает список всех файлов из директории
     * @param path
     */

    public void updateList(Path path) {
        try {
            // получаем путь к файлу
            pathField.setText(path.normalize().toAbsolutePath().toString());
            // очищаем таблицу
            filesTable.getItems().clear();
            // добавляем в таблицу файлы
            filesTable.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
            // сортируем файлы
            filesTable.sort();
        } catch (IOException e) {
            // вызываем ошибку о предупреждение
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не удалось получить список файлов", ButtonType.OK);
            // показываем окно
            alert.showAndWait();
        }
    }

    /** метод для перещения вверх по директории
     * @param actionEvent
     */

    public void btnPathUpAction(ActionEvent actionEvent) {
        // запрашиваем ссылку на  родительскую папку вверх по директории
        Path upperPath = Paths.get(pathField.getText()).getParent();
        // Если ссылка не null
        if (upperPath != null) {
            // переходим на ссылку выше
            updateList(upperPath);
        }
    }

    /** метод для выбора корневых директорий
     * @param actionEvent
     */

    public void selectDiskAction(ActionEvent actionEvent) {
             // получаем ссылку на ComboBox
        ComboBox<String> element = (ComboBox<String>) actionEvent.getSource();
        // получаем информацию о выбранном элементе
        updateList(Paths.get(element.getSelectionModel().getSelectedItem()));
    }

    /** Возвращает имена файлов от выбранной директории
     * @return
     */

    public String getSelectedFilename() {
        // Если таблица не в фокусе
        if (!filesTable.isFocused()) {
            // то ничего не делаем
            return null;
        }
        //  возвращает в таблицу  имя файлов
        return filesTable.getSelectionModel().getSelectedItem().getFilename();
    }


    /** Возвращает путь в директории к файлам
     * @return
     */
    public String getCurrentPath() {
        return pathField.getText();
    }

    public void onCreateFolder(ActionEvent actionEvent) {

        /*
        String a = String.valueOf(String.format(txtlabel.getText()));
        File file = new File(a);
        String text = txtlabel.getText();
        Path upperPath = Paths.get(pathField.getText()).getParent();
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
                    //if (file.exists()){
                    //  Desktop.getDesktop().open(new File(String.valueOf(file)));
                    // }
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


         */

    }
}

