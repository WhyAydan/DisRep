package repbot.commands;

import repbot.commands.thankwords.Thankwords;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ThankwordsTest {

    @Test
    void loadContainer() {
        Assertions.assertDoesNotThrow(Thankwords::loadContainer);
    }
}
