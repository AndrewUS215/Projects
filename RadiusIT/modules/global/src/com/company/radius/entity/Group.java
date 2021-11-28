package com.company.radius.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Table(name = "RADIUS_GROUP", indexes = {
        @Index(name = "IDX_RADIUS_GROUP_NUMBER_", columnList = "NUMBER_")
})
@Entity(name = "radius_Group")
public class Group extends StandardEntity {
    private static final long serialVersionUID = -513614526975060185L;

    @NotNull
    @Column(name = "NUMBER_", nullable = false, unique = true)
    @NotBlank(message = "Enter the number of your group")
    private String number;

    @NotNull
    @Column(name = "FACULTY", nullable = false, unique = true)
    @NotBlank(message = "Enter your faculty")
    private String faculty;

    @OneToMany(mappedBy = "group")
    private List<Student> student_id;

    public List<Student> getStudent_id() {
        return student_id;
    }

    public void setStudent_id(List<Student> student_id) {
        this.student_id = student_id;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}