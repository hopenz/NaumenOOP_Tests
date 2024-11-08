package example.note;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестирование методов класса {@link NoteLogic}
 */
public class NoteLogicTest {

    /**
     * Объект класса NoteLogic
     */
    private NoteLogic noteLogic;

    /**
     * Создание объекта класса NoteLogic
     */
    @BeforeEach
    public void initNoteLogic() {
        noteLogic = new NoteLogic();
    }

    /**
     * Тестирование команд /add и /notes.
     * Проверяется реальное добавление заметки,
     * а не вывод сообщения "Note added!"
     */
    @Test
    void commandsAddAndNotesTest() {
        noteLogic.handleMessage("/add first notes");
        Assertions.assertEquals("Your notes: \n1. first note",
                noteLogic.handleMessage("/notes"));
    }

    /**
     * Тестирование команды /edit.
     * Проверяется реальное изменение заметки,
     * а не вывод сообщения "Note edited!"
     */
    @Test
    void commandEditTest() {
        noteLogic.handleMessage("/add first note");
        noteLogic.handleMessage("/edit 1 second note");
        Assertions.assertEquals("Your notes: \n1. second note",
                noteLogic.handleMessage("/notes"));
    }

    /**
     * Тестирование команды /del.
     * Тестирование команды реального удаления заметки,
     * а не вывод сообщения "Note deleted!"
     */
    @Test
    void commandDelTEst() {
        noteLogic.handleMessage("/add first note");
        noteLogic.handleMessage("/add second note");
        noteLogic.handleMessage("/del 2");
        Assertions.assertEquals("""
                        Your notes:
                        1. first note""",
                noteLogic.handleMessage("/notes"));
    }

}
