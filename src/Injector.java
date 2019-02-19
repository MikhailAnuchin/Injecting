import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Properties;


class Injector<T> {
    private Properties properties;

    Injector(String propFile) throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream(new File(propFile)));
    }


    T inject(T obj) throws IOException, IllegalAccessException, InstantiationException {
        Class dependency;

        Class cl = obj.getClass();

        // Field[] fields = cl.getFields();
        // Если поля будут не приватные а публичные то можно использовать getFields()
        Field[] fields = cl.getDeclaredFields();
        for (Field field: fields){
            Annotation [] a = field.getDeclaredAnnotations(); // те аннотации, которые непосредственно присутствуют
            //Annotation [] a = field.getAnnotations();
            for (Annotation an: a){
                if ((an != null) && (an.annotationType() == AutoInjectable.class) ) {

                    String[] fieldType = field.getType().toString().split(" ");
                    String equalsClassName = properties.getProperty(fieldType[1], null);
                    if (equalsClassName != null) {
                        try {
                            dependency = Class.forName(equalsClassName);
                        } catch (ClassNotFoundException e) {
                            System.out.println("Not found class for " + equalsClassName);
                            continue;
                        }

                        field.setAccessible(true);
                        field.set(obj, dependency.newInstance());
                    } else
                        System.out.println("Not found properties for field type " + fieldType[1]);
                }
            }
        }
        return obj;
    }
}