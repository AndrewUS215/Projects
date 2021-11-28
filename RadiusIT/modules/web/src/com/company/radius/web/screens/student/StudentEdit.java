package com.company.radius.web.screens.student;

import com.haulmont.cuba.gui.screen.*;
import com.company.radius.entity.Student;

@UiController("radius_Student.edit")
@UiDescriptor("student-edit.xml")
@EditedEntityContainer("studentDc")
@LoadDataBeforeShow
public class StudentEdit extends StandardEditor<Student> {
}