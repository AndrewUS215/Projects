package com.company.radius.web.screens.group;

import com.haulmont.cuba.gui.screen.*;
import com.company.radius.entity.Group;

@UiController("radius_Group.edit")
@UiDescriptor("group-edit.xml")
@EditedEntityContainer("groupDc")
@LoadDataBeforeShow
public class GroupEdit extends StandardEditor<Group> {
}