/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientgabfortu;


import java.util.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author biar
 */
@XmlRootElement(name = "Course")
public class Corso {
    private int id;
    private String name;
    private List<Studente> students = new ArrayList<>();
 
    private Studente findById(int id) {
        for (Studente student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Studente> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "Corso{" + "id=" + id + ", name=" + name + ", students=" + students + '}';
    }

    public void setStudents(List<Studente> students) {
        this.students = students;
    }
    
    @Override
    public int hashCode() {
        return id + name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Studente) && (id == ((Studente) obj).getId()) && (name.equals(((Studente) obj).getName()));
    }
    
}

