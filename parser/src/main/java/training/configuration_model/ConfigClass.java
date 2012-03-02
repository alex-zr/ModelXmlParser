package training.configuration_model;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: al1
 * Date: 01.03.12
 */
public class ConfigClass {
    private String className;
    private Map<FieldType, String> fields;

    public ConfigClass() {
    }

    public ConfigClass(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<FieldType, String> getFields() {
        return fields;
    }

    public void setFields(Map<FieldType, String> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "ConfigClass{" +
                "className='" + className + '\'' +
                ", fields=" + fields +
                '}';
    }
}
