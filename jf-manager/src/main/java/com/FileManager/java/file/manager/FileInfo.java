package com.FileManager.java.file.manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FileInfo {
    public enum FileType {
             //файл        // директория
        FILE("F"), DIRECTORY("D");

        private String name;

        public String getName() {
            return name;
        }

        FileType(String name) {
            this.name = name;
        }
    }

    // имя файла
    private String filename;
    // тип файла
    private FileType type;
    // размер файла
    private long size;
    // Время последнего модификации файла
    private LocalDateTime lastModified;


    /**   Возвращает имя файла
     * @return
     */
    public String getFilename() {
        return filename;
    }

    /** Получаем имя файла
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /** Возвращает тип файла
     * @return
     */
    public FileType getType() {
        return type;
    }

    /** Определяет тип файла
     * @param type
     */
    public void setType(FileType type) {
        this.type = type;
    }

    /** Возвращает размер файла
     * @return
     */
    public long getSize() {
        return size;
    }

    /** Узнаем размер файла
     * @param size
     */
    public void setSize(long size) {
        this.size = size;
    }

    /** получаем дату и время модификацию файла
     * @return
     */
    public LocalDateTime getLastModified() {
        return lastModified;
    }

    /** Возвращает время последнего модификации
     * @param lastModified
     */
    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    /** получаем информацию по выбранному пути
     * @param path
     */
    public FileInfo(Path path) {
        try {
            // Получаем имя файла
            this.filename = path.getFileName().toString();
            // Получаем размер файла в байтах
            this.size = Files.size(path);
            // Получаем тип файла. Если мы смотрим на директорию то тогда типом будет директория если нет то будет файлом
            this.type = Files.isDirectory(path) ? FileType.DIRECTORY : FileType.FILE;
            // Если тип является директорией то
            if (this.type == FileType.DIRECTORY) {
                // размер будет -1
                this.size = -1L;
            }
            //  Получаем дату и время модификации файла
            this.lastModified = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneOffset.ofHours(3));
        } catch (IOException e) {
            // Ошибка в случае  если не получилось собрать информацию о файле
            throw new RuntimeException("Невозможно получить информацию о файле из пути");
        }
    }
}