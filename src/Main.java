import java.io.IOException;


public class Main {
    public static void main(String... args) throws IOException, InstantiationException, IllegalAccessException {

        SomeBean sb = (new Injector<SomeBean>("src/config/config.properties").inject(new SomeBean()));
        sb.foo();
    }
}
