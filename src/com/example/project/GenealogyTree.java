package com.example.project;

import java.io.Serializable;
import java.util.*;

public class GenealogyTree implements Iterable<Person>, Serializable {
    private final Person root;

    public GenealogyTree(Person root) {
        this.root = root;
    }

    public List<Person> getChildrenOfPerson(String personName) throws PersonNotFoundException {
        Person person = findPersonByName(root, personName);
        if (person == null) {
            throw new PersonNotFoundException("com.example.project.Person not found: " + personName);
        }
        return person.getChildren();
    }

    public void removePerson(String personName) throws PersonNotFoundException {
        Person person = findPersonByName(root, personName);
        if (person == null) {
            throw new PersonNotFoundException("com.example.project.Person not found: " + personName);
        }
        Person father = person.getFather();
        Person mother = person.getMother();
        if (father != null) {
            father.getChildren().remove(person);
        }
        if (mother != null) {
            mother.getChildren().remove(person);
        }
    }
    public List<Person> getSortedByBirthDate() {
        List<Person> sortedList = new ArrayList<>(root.getChildren());
        sortedList.sort(Comparator.comparing(Person::getBirthDate));
        return sortedList;
    }

    public List<Person> getSortedByName() {
        List<Person> sortedList = new ArrayList<>(root.getChildren());
        sortedList.sort(Comparator.comparing(Person::getName));
        return sortedList;
    }
    public void printGenealogyTreeSortedByBirthDate() {
        List<Person> sortedList = getSortedByBirthDate();
        printSortedPerson(sortedList, 0);
    }

    public void printGenealogyTreeSortedByName() {
        List<Person> sortedList = getSortedByName();
        printSortedPerson(sortedList, 0);
    }

    private void printSortedPerson(List<Person> sortedList, int level) {
        sortedList.forEach(person -> {
            StringBuilder indent = new StringBuilder();
            for (int i = 0; i < level; i++) {
                indent.append("  ");
            }
            System.out.println(indent.toString() + person.getName());
            printSortedPerson(person.getChildren(), level + 1);
        });
    }

    private Person findPersonByName(Person currentPerson, String personName) {
        if (currentPerson.getName().equals(personName)) {
            return currentPerson;
        }

        for (Person child : currentPerson.getChildren()) {
            Person foundPerson = findPersonByName(child, personName);
            if (foundPerson != null) {
                return foundPerson;
            }
        }

        return null;
    }

    @Override
    public Iterator<Person> iterator() {
        return root.getChildren().iterator();
    }
}
