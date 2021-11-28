package com.company.radius.web.screens.student;

import com.haulmont.cuba.gui.screen.*;
import com.company.radius.entity.Student;

@UiController("radius_Student.browse")
@UiDescriptor("student-browse.xml")
@LookupComponent("studentsTable")
@LoadDataBeforeShow
public class StudentBrowse extends StandardLookup<Student> {
    
}