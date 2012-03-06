package training;

import training.common.LogicException;
import training.configuration_model.ConfigClass;

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
    
    public PropertiesParser(String inputFolder) {
        File folder = new File(inputFolder);
        if(!folder.exists()) {
            throw new LogicException("Directory " + inputFolder + " does not exists");
        }

        if(!folder.isDirectory()) {
            throw new LogicException("Path " + inputFolder + " is not a directory");
        }

        resourceFolder = folder;
    }

    public List<Map<String, List<ConfigClass>>> readConfigModel() {
        List<Map<String, List<ConfigClass>>> propsFileList = new ArrayList<Map<String, List<ConfigClass>>>();
        List<String> filesFromInput = readFileNamesFromFolder(PROPERTIES_EXTENTION);
        for(String fileName : filesFromInput) {
            propsFileList.add(parsePropertyFile(fileName));
        }
        return propsFileList;
    }

    private Map<String, List<ConfigClass>> parsePropertyFile(String fileName) {
        Properties props = new Properties();
        Map<String, List<ConfigClass>> fileRecord = new HashMap<String, List<ConfigClass>>();

        try {
            props.load(new FileInputStream(fileName));
        } catch (IOException e) {
            throw new LogicException("Properties file [" + fileName + "] not found, " + e);
        }

        Enumeration<?> names = props.propertyNames();
        
        while(names.hasMoreElements()) {
            String singlePropName = (String)names.nextElement();
            String classesListStr = props.getProperty(singlePropName);
            fileRecord.put(singlePropName, parsePropStrToConfigClasses(classesListStr));
        }
        return fileRecord;
    }

    private List<ConfigClass> parsePropStrToConfigClasses(String propertyLine) {

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
        PropertiesParser parser = new PropertiesParser("src/test/resources/training/common/junit-3.8.1.jar");
    }
}
