package training.common;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by IntelliJ IDEA.
 * User: al1
 * Date: 01.03.12
 */
public class JarClassLoader {
    private final ZipFile file;
    private static String CLASS_EXTENSION = ".class";

    public JarClassLoader(String filename) throws IOException {
        this.file = new ZipFile(filename);
    }
//
//    @Override
//    protected Class<?> findClass(String name) throws ClassNotFoundException {
////        ZipEntry entry = this.file.getEntry(name);
//        ZipEntry entry = this.file.getEntry(name.replace('.', '/') + ".class");
//        if (entry == null) {
//            throw new NullPointerException("Zip entry is null for name " + name);
//        }
//        //String name = null;
//        try {
//            byte[] array = new byte[1024];
//            InputStream in = this.file.getInputStream(entry);
//            ByteArrayOutputStream out = new ByteArrayOutputStream(array.length);
//            int length = in.read(array);
//            while (length > 0) {
//                out.write(array, 0, length);
//                length = in.read(array);
//            }
//            //name = classEntryToName(entry);
//            return defineClass(name, out.toByteArray(), 0, out.size());
//        } catch (IOException exception) {
//            throw new ClassNotFoundException(name, exception);
//        }
//    }
//
//    public static String classEntryToName(ZipEntry entry) {
//        String name = entry.getName().replace(CLASS_EXTENSION, "");
//        return name.replace("/", ".");
//    }
//
    private static String classNameToName(String entry) {
        String name = entry.replace(CLASS_EXTENSION, "");
        return name.replace("/", ".");
    }
//
//    public List<Class> getClasses() {
//        List<Class> classes = new ArrayList<Class>();
//        Enumeration<? extends ZipEntry> entries = this.file.entries();
//        while (entries.hasMoreElements()) {
//            ZipEntry entry = entries.nextElement();
//            if (entry.getName().contains(CLASS_EXTENSION)) {
//                System.out.println(entry.getName());
//                Class clazz;
//                try {
//                    clazz = findClass(classEntryToName(entry));
//                } catch (ClassNotFoundException e) {
//                    throw new LogicException("Class [" + entry.getName() + "] not found in " + file.getName());
//                }
//                classes.add(clazz);
//            }
//        }
//
//        return classes;
//    }

    private List<String> getClassNamesFromJar() {
        List<String> names = new ArrayList<String>();
        Enumeration<? extends ZipEntry> entries = this.file.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.getName().contains(CLASS_EXTENSION)) {
                names.add(entry.getName());
            }
        }
        return names;
    }

    public static void main(String[] args) {
        try {
//            JarClassLoader loader = new JarClassLoader("/home/al1/robocode/libs/robocode.ui-1.7.3.0.jar");
//            String path = "/home/al1/robocode/libs/roborumble.jar";
            String path = "/home/al1/.m2/repository/junit/junit/3.8.1/junit-3.8.1.jar";
//            String path = "/home/al1/ojdbc14.jar";
            JarClassLoader loader = new JarClassLoader(path);

//            JarClassLoader loader = new JarClassLoader("/home/al1/ojdbc14.jar");
            URL[] urls = { new URL("jar:file:" + path + "!/") };
            URLClassLoader urlClassLoader = URLClassLoader.newInstance(urls);
            List<String> classNamesFromJar = loader.getClassNamesFromJar();
            for(String name : classNamesFromJar) {
                Class<?> aClass = urlClassLoader.loadClass(classNameToName(name));
                System.out.println(aClass);
            }
            //List<Class> classes = loader.getClasses();
            //System.out.println(classes);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
