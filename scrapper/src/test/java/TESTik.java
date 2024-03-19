import org.junit.jupiter.api.Test;
import java.net.URI;

public class TESTik {
    @Test
    void test() {
        URI uri = URI.create("https://api.stackexchange.com/2.3/");
        System.out.println(uri.getHost());
    }
}
