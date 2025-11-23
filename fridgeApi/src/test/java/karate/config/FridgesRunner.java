package karate.config;

// Este codigo transforma codigos.feature em codigos legiveis as IDEs
import com.intuit.karate.junit5.Karate;

class FridgesRunner {

    @Karate.Test
    Karate testAll() {

        return Karate.run("classpath:karate/features/Users").relativeTo(getClass());
    }
}