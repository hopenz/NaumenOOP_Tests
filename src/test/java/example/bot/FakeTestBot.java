package example.bot;

import java.util.ArrayList;
import java.util.List;

/**
 * Фейковый бот для тестов
 */
public class FakeTestBot implements Bot {

    /**
     * Хранилище для ответов бота пользователю
     */
    private final List<String> botsAnswer = new ArrayList<>();

    @Override
    public void sendMessage(Long chatId, String message) {
        botsAnswer.add(message);
    }

    /**
     * Получение ответа бота из хранилища по индексу
     *
     * @param index индекс сообщения
     * @return ответ бота
     */
    public String getMessageByIndex(int index) {
        return botsAnswer.get(index);
    }

    /**
     * Получение количества ответов бота в хранилище
     *
     * @return количество ответов бота пользователю
     */
    public int getSizeListOfBotsAnswers() {
        return botsAnswer.size();
    }
}
