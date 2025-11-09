import io.qt.widgets.QApplication;
import org.junit.jupiter.api.Test;
import pbg.Generator;

public class PngGenTest {

    @Test
    public void test1() throws Exception {
        QApplication.initialize(new String[0]);
        Generator generator = new Generator();
        generator.addArea(1, "测试A");
        generator.addArea(2, "测试B");
        generator.addArea(4, "测试C");
        generator.addArea(8, "测试D");
        generator.addArea(10, "测试E");
        generator.addArea(14, "测试F");
        generator.gen("test.png");
    }
}
