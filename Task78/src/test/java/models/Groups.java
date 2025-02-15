package models;

import java.util.List;
import java.util.ArrayList;

public class Groups {
    private List<String> groupList;

    public Groups() {
        groupList = new ArrayList<>();
    }

    // Метод для получения списка групп
    public List<String> getGroups() {
        return groupList;
    }

    // Метод для добавления группы в список
    public void addGroup(String groupName) {
        groupList.add(groupName);
    }

    // Метод для получения группы по индексу
    public String getGroupByIndex(int index) {
        return groupList.size() > index ? groupList.get(index) : null;
    }
}
