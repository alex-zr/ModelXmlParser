package training;

import training.common.JarClassLoader;
import training.configuration_model.ConfigClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: al1
 * Date: 01.03.12
 */
public class BinaryModelReader {
    public List<ConfigClass> buildConfigModel(File jarModelFile) {
        List<ConfigClass> propsFileList = new ArrayList<ConfigClass>();

        return propsFileList;
    }
    
    public List<Class> loadClassesFromJar(String jarModelFile) {
        List<Class> classes = new ArrayList<Class>();
        JarClassLoader loader = new JarClassLoader(jarModelFile);

//            classes = loader.getClasses();

        return classes;
//        URL jar = getClass().getResource(jarModelFile.getName());
//        URLClassLoader ucl = new URLClassLoader(new URL[] {jar});
//        url.loadClass("org.jdesktop.layout.Baseline");
    }

}
