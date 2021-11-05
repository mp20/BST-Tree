import java.util.ArrayList;
import java.util.Collection;

public class test {
    public static void main(String[] args) {

        Collection<Integer> test = new ArrayList<>();
        test.add(20);
        test.add(10);
        test.add(50);
        test.add(5);
        test.add(30);
        test.add(70);






        BST<Integer> testing = new BST<>(test);

       System.out.println(testing.levelorder());
    }
}
