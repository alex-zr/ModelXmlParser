package training.model;

import training.common.JarClassLoader;
import training.common.LogicException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: al1
 * Date: 02.03.12
 */
public class ClassStructureBuilder {
    private List<NestedSetTree<Class>> classesForest;
    private List<Class> modelClasses;

    public List<NestedSetTree<Class>> buildClassesForest(List<Class> classes) {
        if(classes == null) {
            throw new LogicException("Classes list can't be null");
        }
        modelClasses = classes;
        ArrayList<NestedSetTree<Class>> forest = new ArrayList<NestedSetTree<Class>>(classes.size());

        for(Class clazz :classes) {
            NestedSetTree<Class> classTree = new NestedSetTree<Class>();
            addClass(classTree, null, clazz);
            forest.add(classTree);
            System.out.println(classTree);
            System.out.println("------------------------------");
        }
        return forest;
    }
//
//    private NestedSetTree<Class> buildClassTree(Class clazz) {
//        NestedSetTree<Class> classTree = new NestedSetTree<Class>();
//        Field[] fields = clazz.getDeclaredFields();
//        for(Field field : fields) {
//            Class<?> type = field.getType();
//            String name = field.getName();
//            System.out.println(type + ", " + name);
//        }
//        return classTree;
//    }

    public Long addClass(NestedSetTree<Class> classTree, Long parentId, Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Long nodeId = classTree.add(parentId, clazz);
        if(fields.length == 0 || !modelClasses.contains(clazz)) {
            return nodeId;
        }
        for(Field field : fields) {
            addClass(classTree, nodeId, field.getType());
//            System.out.println(field.getType() + ", " + field.getName());
        }

        return nodeId;
    }
    
//    private ClassData getClassData(Class clazz) {
//        return new ClassData(clazz.getName(), makeFieldsMap(clazz));
//    }
//
//    private Map<String, FieldType> makeFieldsMap(Class clazz) {
//        // TODO implement
//        HashMap<String, FieldType> fieldsMap = new HashMap<String, FieldType>();
//        Field[] fields = clazz.getDeclaredFields();
//        for(Field field : fields) {
//            fieldsMap.put(field.getName(), resolveFieldType(field));
//        }
//        return fieldsMap;
//
//    }
//
//    private FieldType resolveFieldType(Field field) {
//        return null;
//    }

    private boolean isLoaded(Class clazz) {
        return false;
    }

    public static void main(String[] args) {
        ClassStructureBuilder builder = new ClassStructureBuilder();
//        JarClassLoader loader = new JarClassLoader("src/test/resources/training/common/junit-empty.jar");
        JarClassLoader loader = new JarClassLoader("/home/al1/IdeaProjects/ModelXmlParser/parser/src/test/resources/training/common/junit-3.8.1.jar");

        List<NestedSetTree<Class>> trees = builder.buildClassesForest(loader.getClasses());
        //System.out.println(trees );
    }
}
