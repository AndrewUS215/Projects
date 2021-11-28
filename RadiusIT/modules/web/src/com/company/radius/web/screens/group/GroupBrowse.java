package com.company.radius.web.screens.group;

import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.screen.*;
import com.company.radius.entity.Group;

@UiController("radius_Group.browse")
@UiDescriptor("group-browse.xml")
@LookupComponent("groupsTable")
@LoadDataBeforeShow
public class GroupBrowse extends StandardLookup<Group> {

}