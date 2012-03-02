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
public class ConfigurationModelReader {

    private final String PROPERTIES_EXTENTION = "properties";

    public List<Map<String, List<ConfigClass>>> readConfigModel(String inputFolder) {
        List<Map<String, List<ConfigClass>>> propsFileList = new ArrayList<Map<String, List<ConfigClass>>>();
        List<String> filesFromInput = readFileNamesFromFolder(inputFolder, PROPERTIES_EXTENTION);
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

    private List<ConfigClass> parsePropStrToConfigClasses(String classesListStr) {

        return null;
    }

    private List<String> readFileNamesFromFolder(String inputFolder, String ext) {
        List<String> fileNames = new ArrayList<String>();
        File folder = new File(inputFolder);

        if(!folder.exists()) {
            throw new LogicException(inputFolder + " does not exists");
        }

        if(!folder.isDirectory()) {
            throw new LogicException(inputFolder + " is not a directory");
        }

        File[] files = folder.listFiles();

        for(File file : files) {
            if(file.getName().contains(ext)) {
                fileNames.add(file.getName());
            }
        }

        return fileNames;
    }
}
