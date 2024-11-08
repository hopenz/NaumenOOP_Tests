package example.container;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестирование методов {@link Container}
 */
public class ContainerTest {

    /**
     * Объект класса Container
     */
    private Container container;

    /**
     * Создание объекта класса Container
     */
    @BeforeEach
    public void initContainer() {
        container = new Container();
    }

    /**
     * Тестирование методов add, size и contains
     */
    @Test
    void methodsAddAndSizeAndContainsTest() {
        Item item1 = new Item(1);
        Item item2 = new Item(2);
        container.add(item1);
        container.add(item2);
        Assertions.assertEquals(2, container.size());
        Assertions.assertTrue(container.contains(item1));
        Assertions.assertTrue(container.contains(item2));
    }

    /**
     * Тестирование метода удаления
     */
    @Test
    void methodsRemoveTest() {
        Item item1 = new Item(1);
        container.add(item1);
        container.remove(item1);
        Assertions.assertFalse(container.contains(item1));
    }
}
