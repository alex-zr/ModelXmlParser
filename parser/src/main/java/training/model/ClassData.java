package training.model;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: al1
 * Date: 02.03.12
 */
public class ClassData {
    private String name;
    private Map<String, FieldType> fields;

    public ClassData(String name, Map<String, FieldType> fields) {
        this.name = name;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, FieldType> getFields() {
        return fields;
    }

    public void setFields(Map<String, FieldType> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "ClassData{" +
                "name='" + name + '\'' +
                ", fields=" + fields +
                '}';
    }
}
