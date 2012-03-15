package training.parser;

import training.common.LogicException;
import training.model.ClassData;
import training.model.FieldType;
import training.model.NestedSetTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: al1
 * Date: 01.03.12
 */
public class PropertiesParser {

    private final String PROPERTIES_EXTENTION = "properties";
    private File resourceFolder;
    private List<NestedSetTree<Class>> classForest;
    
    public Map<String, Object[]> parseObjects() {
        return new HashMap<String, Object[]>();
    }
    
    public PropertiesParser(String inputFolder, List<NestedSetTree<Class>> forest) {
        File folder = new File(inputFolder);
        if(!folder.exists()) {
            throw new LogicException("Directory " + inputFolder + " does not exists");
        }

        if(!folder.isDirectory()) {
            throw new LogicException("Path " + inputFolder + " is not a directory");
        }
        
        if(forest == null) {
            throw new LogicException("Class forest can't be null");
        }

        this.resourceFolder = folder;
        this.classForest = forest;
    }

    public List<Map<String, List<ClassData>>> readConfigModel() {
        List<Map<String, List<ClassData>>> propsFileList = new ArrayList<Map<String, List<ClassData>>>();
        List<String> filesFromInput = readFileNamesFromFolder(PROPERTIES_EXTENTION);
        for(String fileName : filesFromInput) {
            propsFileList.add(parsePropertyFile(fileName));
        }
        return propsFileList;
    }

    private Map<String, List<ClassData>> parsePropertyFile(String fileName) {
        Properties props = new Properties();
        Map<String, List<ClassData>> fileRecord = new HashMap<String, List<ClassData>>();

        try {
            props.load(new FileInputStream(fileName));
        } catch (IOException e) {
            throw new LogicException("Properties file [" + fileName + "] not found, " + e);
        }

        Enumeration<?> names = props.propertyNames();
        
        while(names.hasMoreElements()) {
            String singlePropName = (String)names.nextElement();
            String classesListStr = props.getProperty(singlePropName);
            fileRecord.put(singlePropName, parsePropsLineToClassData(classesListStr));
        }
        return fileRecord;
    }

    private List<ClassData> parsePropsLineToClassData(String propertyLine) {
        final String CLASS_DELIMITER = ";";
        final char LEFT_BRACKET = '(';
        final char RIGHT_BRACKET = ')';
        
        List<ClassData> classesData = new ArrayList<ClassData>();
        String[] classes = propertyLine.split(CLASS_DELIMITER);
        for(String clazz : classes) {
            int leftBrIndex = clazz.indexOf(LEFT_BRACKET);
            int rightBrIndex = clazz.indexOf(RIGHT_BRACKET);
            String className = clazz.substring(0, leftBrIndex).trim();
            String fieldsLine = clazz.substring(leftBrIndex, rightBrIndex);
            ClassData classData = new ClassData();
            classData.setClassName(className);
            classData.setFields(parseFieldsLine(fieldsLine));
            classesData.add(classData);
        }
        return null;
    }

    private Map<String, FieldType> parseFieldsLine(String fieldsLine) {
        final String FIELDS_DELIMITER = ";";
        final char LEFT_BRACKET = '(';
        final char RIGHT_BRACKET = ')';
        return null;
    }

    private List<String> readFileNamesFromFolder(String ext) {
        List<String> fileNames = new ArrayList<String>();

        File[] files = resourceFolder.listFiles();

        for(File file : files) {
            if(file.getName().contains(ext)) {
                fileNames.add(file.getName());
            }
        }

        return fileNames;
    }

    public static void main(String[] args) {
        PropertiesParser parser = new PropertiesParser("src/test/resources/training/common/junit-3.8.1.jar", null);
    }
}
