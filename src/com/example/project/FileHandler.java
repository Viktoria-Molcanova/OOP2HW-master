package com.example.project;

import java.io.*;
public class FileHandler implements Writable {
    public static void writeToFile(String fileName, Object object) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(object);
            System.out.println("Объект успешно записан в файл: " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при записи объекта в файл: " + e.getMessage());
        }
    }

    public static Object readFromFile(String fileName) {
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            Object object = objectInputStream.readObject();
            System.out.println("Объект успешно прочитан из файла: " + fileName);
            return object;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при чтении объекта из файла: " + e.getMessage());
            return null;
        }
    }
}


