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
     */
    @Test
    void commandTestTest() {
        botLogic.processCommand(user, "/test");
        //Сообщение бота: Вычисление 10^2
        botLogic.processCommand(user, "100");
        Assertions.assertEquals("Правильный ответ!",
                fakeTestBot.getMessageByIndex(1));
        //Сообщение бота: Вычислить 2 + 2 * 2
        botLogic.processCommand(user, "1234");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 6",
                fakeTestBot.getMessageByIndex(3));
    }

    /**
     * Тестирование команды /notify
     * Проверка отложенного сообщения в течении заданного ПОЛОЖИТЕЛЬНОГО значения
     */
    @Test
    void commandNotifyTest() throws InterruptedException {
        botLogic.processCommand(user, "/notify");
        // Текст напоминания
        botLogic.processCommand(user, "I need to buy a car tomorrow :D");
        // Время, через которое напомнить
        botLogic.processCommand(user, "1");
        Thread.sleep(666);
        Assertions.assertEquals(3, fakeTestBot.getSizeListOfBotsAnswers());
        Thread.sleep(666);
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
        // Текст напоминания
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
        // Вычислить 10^2
        botLogic.processCommand(user, "100");
        // Правильный ответ
        botLogic.processCommand(user, "7");
        // Вы ошиблись
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
