import parser.ModelXmlWriter;
import parser.configuration.Config;
import parser.common.JarClassLoader;
import parser.model.ClassStructureBuilder;
import parser.parser.PropertiesParser;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: al1
 * Date: 14.03.12
 */
public class Main {
    private JarClassLoader classLoader;
    private ClassStructureBuilder builder;
    private Config config;
    private PropertiesParser parser;
    private ModelXmlWriter writer;

    private void init() {
        config = new Config();
        config.load();
        classLoader = new JarClassLoader(config.getJarPath());
        builder = new ClassStructureBuilder(classLoader.getClasses());
        parser = new PropertiesParser(config, builder.buildClassesForest());
        writer = new ModelXmlWriter(config.getOutput());
    }

    public static void main(String[] args) {
        //TODO check input params, if absent get from properties
        Main main = new Main();
        main.init();
        Map<String, Map<String, List<Object>>> objects = main.parser.readPropertieFilesMap();
        Set<String> fileNames = objects.keySet();
        for(String name : fileNames) {
            main.writer.write(objects.get(name));
        }
        System.out.println(objects);


    }
}
