/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab_ex2;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author biar
 */
// Questa annotazione consente al compilatore di capire che questa è la root
// class per gli oggetti di tipo Student per i documenti XML che andremo
// a generare
@XmlRootElement(name = "Course")

public class Course {
    // Attributi della classe
    private int id;
    private String name;
    private List<Student> students = new ArrayList<>();
    
    // Metodo della classe che dato un id cerca uno studente con quell'id
    private Student findById(int id){
        for(Student student : students){
            if(student.getId() == id) return student;
        }
        return null;
    }

    // Questa annotazione consente al compilatore di capire con cosa rispondere
    // ad una richiesta GET sulla classe Course passando il parametro studentId
    @GET
    @Path("{studentId}")
    public Student getStudent(@PathParam("studentId")int studentId){
        return findById(studentId);
    }
    
    // Questa annotazione consente al compilatore di capire con cosa rispondere
    // ad una richiesta POST sulla classe Course, non richiede parametri
    @POST
    @Path("")
    public Response createStudent(Student student){
        for(Student stud : students){
            if(stud.getId() == student.getId())
                // Ritorno lo stato di conflitto in linguaggio HTTP se esiste
                // gia' un utente con lo stesso nome
                return Response.status(Response.Status.CONFLICT).build();
        }
        students.add(student);
        // Ritorno lo stato ok in linguaggio HTTP se sono riuscito ad 
        // inserire il nuovo studente
        return Response.ok(student).build();
    }
    
    // Questa annotazione consente al compilatore di capire con cosa rispondere
    // ad una richiesta DELETE sulla classe Course avendo passato il parametro
    // studentId
    @DELETE
    @Path("{studentId}")
    public Response deleteStudent(@PathParam("{studentId}")int studId){
        Student student = findById(studId);
        if(student == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        
        students.remove(student);
        return Response.ok(student).build();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    @Override
    public int hashCode() {
        return id + name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Student) && (id == ((Student) obj).getId()) && (name.equals(((Student) obj).getName()));
    }
}
