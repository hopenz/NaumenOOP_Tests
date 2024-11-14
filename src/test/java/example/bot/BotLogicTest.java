package example.bot;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестирование логики бота BotLogic
 */
public class BotLogicTest {

    /**
     * Объект класса FakeTestBot
     */
    private FakeTestBot fakeTestBot;

    /**
     * Объект класса BotLogic
     */
    private BotLogic botLogic;

    /**
     * Объект класса User
     */
    private User user;

    /**
     * Создание объектов: fakeTestBot, botLogic and user
     */
    @BeforeEach
    public void initFakeAndLogicBotsAndUser() {
        fakeTestBot = new FakeTestBot();
        botLogic = new BotLogic(fakeTestBot);
        user = new User(1L);
    }

    /**
     * Тестирование команды /test
     * Проверка корректности проверки ответов
     * Оба ответа ПРАВИЛЬНЫЕ
     */
    @Test
    void commandTestTwoAnswersAreCorrectTest() {
        botLogic.processCommand(user, "/test");

        Assertions.assertEquals("Вычислите степень: 10^2", fakeTestBot.getMessageByIndex(0));
        botLogic.processCommand(user, "100");
        Assertions.assertEquals("Правильный ответ!", fakeTestBot.getMessageByIndex(1));

        Assertions.assertEquals("Сколько будет 2 + 2 * 2", fakeTestBot.getMessageByIndex(2));
        botLogic.processCommand(user, "6");
        Assertions.assertEquals("Правильный ответ!", fakeTestBot.getMessageByIndex(3));

        Assertions.assertEquals("Тест завершен", fakeTestBot.getMessageByIndex(4));

    }

    /**
     * Тестирование команды /test
     * Проверка корректности проверки ответов
     * Оба ответа НЕПРАВИЛЬНЫЕ
     */
    @Test
    void commandTestTwoAnswersAreInCorrectTest() {
        botLogic.processCommand(user, "/test");

        Assertions.assertEquals("Вычислите степень: 10^2", fakeTestBot.getMessageByIndex(0));
        botLogic.processCommand(user, "1");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 100", fakeTestBot.getMessageByIndex(1));

        Assertions.assertEquals("Сколько будет 2 + 2 * 2", fakeTestBot.getMessageByIndex(2));
        botLogic.processCommand(user, "1");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 6", fakeTestBot.getMessageByIndex(3));

        Assertions.assertEquals("Тест завершен", fakeTestBot.getMessageByIndex(4));

    }

    /**
     * Тестирование команды /test
     * Проверка корректности проверки ответов
     * ПЕРВЫЙ НЕПРАВИЛЬНЫЙ, ВТОРОЙ ПРАВИЛЬНЫЙ
     */
    @Test
    void commandTestFirstAnswerIsInCorrectAndSecondAnswerIsCorrectTest() {
        botLogic.processCommand(user, "/test");

        Assertions.assertEquals("Вычислите степень: 10^2", fakeTestBot.getMessageByIndex(0));
        botLogic.processCommand(user, "1");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 100", fakeTestBot.getMessageByIndex(1));

        Assertions.assertEquals("Сколько будет 2 + 2 * 2", fakeTestBot.getMessageByIndex(2));
        botLogic.processCommand(user, "6");
        Assertions.assertEquals("Правильный ответ!", fakeTestBot.getMessageByIndex(3));

        Assertions.assertEquals("Тест завершен", fakeTestBot.getMessageByIndex(4));

    }

    /**
     * Тестирование команды /test
     * Проверка корректности проверки ответов
     * ПЕРВЫЙ ПРАВИЛЬНЫЙ, ВТОРОЙ НЕ ПРАВИЛЬНЫЙ
     */
    @Test
    void commandTestFirstAnswerIsCorrectAndSecondAnswerIsIncorrectTest() {
        botLogic.processCommand(user, "/test");

        Assertions.assertEquals("Вычислите степень: 10^2", fakeTestBot.getMessageByIndex(0));
        botLogic.processCommand(user, "100");
        Assertions.assertEquals("Правильный ответ!", fakeTestBot.getMessageByIndex(1));

        Assertions.assertEquals("Сколько будет 2 + 2 * 2", fakeTestBot.getMessageByIndex(2));
        botLogic.processCommand(user, "1");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 6", fakeTestBot.getMessageByIndex(3));

        Assertions.assertEquals("Тест завершен", fakeTestBot.getMessageByIndex(4));

    }

    /**
     * Тестирование команды /notify
     * Проверка отложенного сообщения в течении заданного ПОЛОЖИТЕЛЬНОГО значения
     */
    @Test
    void commandNotifyTest() throws InterruptedException {
        botLogic.processCommand(user, "/notify");
        Assertions.assertEquals("Введите текст напоминания", fakeTestBot.getMessageByIndex(0));
        botLogic.processCommand(user, "I need to buy a car tomorrow :D");

        Assertions.assertEquals("Через сколько секунд напомнить?", fakeTestBot.getMessageByIndex(1));
        botLogic.processCommand(user, "1");

        Assertions.assertEquals("Напоминание установлено", fakeTestBot.getMessageByIndex(2));
        Thread.sleep(510);
        Assertions.assertEquals(3, fakeTestBot.getSizeListOfBotsAnswers());
        Thread.sleep(500);

        Assertions.assertEquals("Сработало напоминание: 'I need to buy a car tomorrow :D'",
                fakeTestBot.getMessageByIndex(3));
    }

    /**
     * Тестирование команды /notify
     * Проверка отлавливания ошибки, при вводе ОТРИЦАТЕЛЬНОГО значения
     * <p> При вводе отрицательного значения - вылетает ошибка IllegalArgumentException.
     */
    @Test
    void commandNotifyWithANegativeValueTest() {
        botLogic.processCommand(user, "/notify");
        Assertions.assertEquals("Введите текст напоминания", fakeTestBot.getMessageByIndex(0));
        botLogic.processCommand(user, "I need to buy a car tomorrow :D");

        Exception e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> botLogic.processCommand(user, "-1"));
        Assertions.assertEquals("Negative delay.", e.getMessage());
    }

    /**
     * Тестирование команды /repeat
     */
    @Test
    void commandRepeatTest() {
        // Нет вопросов для повторения
        botLogic.processCommand(user, "/repeat");
        Assertions.assertEquals("Нет вопросов для повторения",
                fakeTestBot.getMessageByIndex(0));

        // вопрос на повторение добавляется при неправильном ответе
        botLogic.processCommand(user, "/test");
        Assertions.assertEquals("Вычислите степень: 10^2", fakeTestBot.getMessageByIndex(1));
        botLogic.processCommand(user, "100");
        Assertions.assertEquals("Правильный ответ!", fakeTestBot.getMessageByIndex(2));

        Assertions.assertEquals("Сколько будет 2 + 2 * 2", fakeTestBot.getMessageByIndex(3));
        botLogic.processCommand(user, "7");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 6", fakeTestBot.getMessageByIndex(4));
        Assertions.assertEquals("Тест завершен", fakeTestBot.getMessageByIndex(5));

        botLogic.processCommand(user, "/repeat");
        Assertions.assertEquals("Сколько будет 2 + 2 * 2",
                fakeTestBot.getMessageByIndex(6));
        botLogic.processCommand(user, "6");
        Assertions.assertEquals("Правильный ответ!",
                fakeTestBot.getMessageByIndex(7));
        Assertions.assertEquals("Тест завершен",
                fakeTestBot.getMessageByIndex(8));

        // Проверка, что в конце вопросов на повторение нет
        botLogic.processCommand(user, "/repeat");
        Assertions.assertEquals("Нет вопросов для повторения",
                fakeTestBot.getMessageByIndex(9));
    }
}
