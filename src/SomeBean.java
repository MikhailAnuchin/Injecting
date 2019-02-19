import reflectPackage.SomeInterface;
import reflectPackage.SomeOtherInterface;

public class SomeBean {
    @AutoInjectable
    private SomeInterface field1;
    @AutoInjectable
    private SomeOtherInterface field2;

    //private SomeOtherInterface field3;

    public SomeBean() {
    }

    public  void foo(){
        field1.doSomething();
        field2.doSomething();
    }
}
