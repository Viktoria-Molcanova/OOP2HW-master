package com.example.project;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Person grandparent = new Person("Виктория", "Иванова", "01.01.1930");

        Person parent = new Person("Ольга", "Моторина", "01.01.1960");
        Person child1 = new Person("Иван", "Чайковский", "01.01.1990");
        Person child2 = new Person("Юлия", "Чайковская", "01.01.1992");
        FileHandler.writeToFile("family_tree.dat", grandparent);
        Person restoredPerson = (Person) FileHandler.readFromFile("family_tree.dat");
        if (restoredPerson != null) {
            System.out.println("Восстановленный объект:");
            System.out.println(restoredPerson);
        }
        grandparent.addChild(parent);
        parent.addChild(child1);
        parent.addChild(child2);

        GenealogyTree tree = new GenealogyTree(grandparent);

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 5) {
            System.out.println("Меню выбора операции:");
            System.out.println("1. Добавить члена семьи");
            System.out.println("2. Найти члена семьи по имени");
            System.out.println("3. Удалить члена семьи");
            System.out.println("4. Вывести отсортированный список по дате рождения");
            System.out.println("5. Вывести отсортированный список по имени");
            System.out.println("6. Выйти из программы");
            System.out.print("Выберите операцию (1-6): ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            switch (choice) {
                case 1:
                    System.out.print("Введите имя члена семьи: ");
                    String name = scanner.nextLine();
                    System.out.print("Введите фамилию члена семьи: ");
                    String surname = scanner.nextLine();
                    System.out.print("Введите дату рождения члена семьи: ");
                    String birthDate = scanner.nextLine();

                    Person newPerson = new Person(name, surname, birthDate);
                    if (parent.getChildren().contains(newPerson)) {
                        System.out.println("Ошибка: такой член семьи уже существует");
                        break;
                    } else {
                        parent.addChild(newPerson);
                        System.out.println("Член семьи успешно добавлен");
                    }
                    break;
                case 2:
                    System.out.print("Введите имя члена семьи для поиска: ");
                    String searchName = scanner.nextLine();

                    try {
                        List<Person> childrenOfParent = tree.getChildrenOfPerson(searchName);
                        for (Person child : childrenOfParent) {
                            System.out.println("Имя ребёнка: " + child.getName());
                            System.out.println("Фамилия ребёнка: " + child.getSurname());
                            System.out.println("Дата рождения ребёнка: " + child.getBirthDate());
                        }
                    } catch (PersonNotFoundException e) {
                        System.out.println("Член семьи не найден: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Введите имя члена семьи для удаления: ");
                    String removeName = scanner.nextLine();

                    try {
                        tree.removePerson(removeName);
                        System.out.println("Член семьи успешно удален");
                    } catch (PersonNotFoundException e) {
                        System.out.println("Член семьи не найден: " + e.getMessage());
                    }
                    break;
                case 4:
                    tree.printGenealogyTreeSortedByBirthDate();
                    break;
                case 5:
                    tree.printGenealogyTreeSortedByName();
                    break;
                case 6:
                    System.out.println("Программа завершена");
                    break;
                default:
                    System.out.println("Неверный выбор операции. Попробуйте еще раз.");
                    break;
            }
        }
    }
}