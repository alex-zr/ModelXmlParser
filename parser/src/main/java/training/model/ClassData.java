package training.model;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: al1
 * Date: 02.03.12
 */
public class ClassData {
    private String className;
    private Map<String, FieldType> fields;

    public ClassData() {
    }

    public ClassData(String name, Map<String, FieldType> fields) {
        this.className = name;
        this.fields = fields;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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
                "className='" + className + '\'' +
                ", fields=" + fields +
                '}';
    }
}
