import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

public class TESTik {
    private final static Pattern COMMAND_GITHUB_PATTERN =
        Pattern.compile("^/track https://github\\.com/(.*)/(.*)$");
    private final static Pattern COMMAND_STACK_OVERFLOW_PATTERN =
        Pattern.compile("^/track https://stackoverflow\\.com/questions/(.*)/(.*)$");
    @Test
    void test() {
//        GitHubClient gitHubClient = new GitHubClient();
//        GitHubResponse gitHubResponse = gitHubClient.fetch("/NurtilekMamashakirov/tinkoff_java_course_term2");
//        WebClient webClient = WebClient.builder().baseUrl("https://api.stackexchange.com/2.3").build();
//        StackOverflowResponse response = webClient.get()
//            .uri("/questions/123?order=desc&sort=activity&site=stackoverflow")
//            .retrieve()
//            .bodyToMono(StackOverflowResponse.class)
//            .block();
//        System.out.println(response.items().get(0));
        String text = "/track https://stackoverflow.com/questions/218384/what-is-a-nullpointerexception-and-how-do-i-fix-it";
        Matcher matcher = COMMAND_STACK_OVERFLOW_PATTERN.matcher(text);
        System.out.println(matcher.matches());
        System.out.println(matcher.group(1));

        String text2 = "/track https://github.com/Sneguradik/PredprofInf";
        Matcher matcher2 = COMMAND_GITHUB_PATTERN.matcher(text2);
        System.out.println(matcher2.matches());
        System.out.println(matcher2.group(1));
        System.out.println(matcher2.group(2));
    }
}
