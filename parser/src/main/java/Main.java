import training.ModelXmlWriter;
import training.common.JarClassLoader;
import training.common.MainConfiguration;
import training.model.ClassStructureBuilder;
import training.parser.PropertiesParser;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: al1
 * Date: 14.03.12
 */
public class Main {
    private JarClassLoader classLoader;
    private ClassStructureBuilder builder;
    private MainConfiguration config;
    private PropertiesParser parser;
    private ModelXmlWriter writer;

    private void init() {
        config = new MainConfiguration();
        config.load();
        classLoader = new JarClassLoader(config.getJarPath());
        builder = new ClassStructureBuilder(classLoader.getClasses());
        parser = new PropertiesParser(config.getInput(), builder.buildClassesForest());
        writer = new ModelXmlWriter(config.getOutput());
    }

    public static void main(String[] args) {
        //TODO check input params, if absent get from properties
        Main main = new Main();
        main.init();
        Map<String,Object[]> objects = main.parser.parseObjects();
        System.out.println(objects);
        main.writer.write(objects);

    }
}
