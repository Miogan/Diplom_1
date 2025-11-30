package practicum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockitoBurgerTest {
    @Mock
    private Bun mockBun;

    @Mock
    private Bun mockAnotherBun;

    @Mock
    private Ingredient mockIngredient1;

    @Mock
    private Ingredient mockIngredient2;

    @Mock
    private Ingredient mockIngredient3;

    private Burger burger;
    private Burger burgerAnother;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // Создаем объект
        burger = new Burger();
        // Добавляем булочку
        burger.setBuns(mockBun);
    }

    @Test
    // Проверяем конструктор
    public void testBurgerConstructor() {
        // Создаем объект
        burgerAnother = new Burger();
        // Проверяем, что объект создался
        assertNotNull(burgerAnother);
    }

    @Test
    // Проверяем что добавилась именно та булочка
    public void testSetBuns() {
        // Добавляем другую булочку в бургер
        burger.setBuns(mockAnotherBun);

        // Assert - проверяем через чек
        when(mockAnotherBun.getName()).thenReturn("черная булочка");
        String receipt = burger.getReceipt();

        // Проверяем, что в чеке есть название установленной булки
        assertTrue(receipt.contains("черная булочка"));
    }

    @Test
    // Проверяем добавление ингредиента
    public void testAddIngredient() {
        // Добавляем ингредиент в бургер
        burger.addIngredient(mockIngredient1);

        when(mockIngredient1.getName()).thenReturn("помидорка");
        when(mockIngredient1.getType()).thenReturn(IngredientType.FILLING);
        String receipt = burger.getReceipt();

        // Проверяем, что в чеке есть название установленной начинки
        assertTrue(receipt.contains("помидорка"));

    }

    @Test
    // Проверяем добавление типа начинка ингредиента
    public void testAddIngredientTypeFilling() {
        // Добавляем ингредиент в бургер
        burger.addIngredient(mockIngredient1);

        when(mockIngredient1.getName()).thenReturn("помидорка");
        when(mockIngredient1.getType()).thenReturn(IngredientType.FILLING);
        String receipt = burger.getReceipt();

        // Проверяем, что в чеке есть тип начинка
        assertTrue(receipt.contains("filling"));

    }

    @Test
    // Проверяем добавление типа соус ингредиента
    public void testAddIngredientTypeSause() {
        // Добавляем ингредиент в бургер
        burger.addIngredient(mockIngredient1);

        when(mockIngredient1.getName()).thenReturn("сладкий");
        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        String receipt = burger.getReceipt();

        // Проверяем, что в чеке есть тип соус
        assertTrue(receipt.contains("sauce"));

    }

    @Test
    // Проверяем удаление ингредиента по индексу
    public void testRemoveIngredientAbsent() {
        // Добавляем два ингредиента в бургер
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        // Настраиваем данные для первого ингредиента (который будем удалять)
        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient1.getName()).thenReturn("кетчуп");
        // Настраиваем данные для второго ингредиента (который останется)
        when(mockIngredient2.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient2.getName()).thenReturn("сыр");

        // Act - удаляем первый ингредиент (с индексом 0)
        burger.removeIngredient(0);

        // Получаем чек после удаления
        String receipt = burger.getReceipt();

        // Проверяем, что кетчуп удалился
        assertFalse("В чеке не должно быть кетчупа после удаления", receipt.contains("кетчуп"));
    }

    @Test
    // Проверяем наличие второго ингредиента, после удаления первого
    public void testRemoveIngredientAvailabilityOfRemaining() {
        // Добавляем два ингредиента в бургер
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        // Настраиваем данные для первого ингредиента (который будем удалять)
        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient1.getName()).thenReturn("кетчуп");
        // Настраиваем данные для второго ингредиента (который останется)
        when(mockIngredient2.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient2.getName()).thenReturn("сыр");

        // Act - удаляем первый ингредиент (с индексом 0)
        burger.removeIngredient(0);

        // Получаем чек после удаления
        String receipt = burger.getReceipt();

        // Проверяем, что сыр остался
        assertTrue("В чеке должен остаться сыр после удаления", receipt.contains("сыр"));
    }


    @Test(expected = IndexOutOfBoundsException.class)
    // Проверяем наличие исключения, при удалении ингредиента с неверным индексом
    public void testRemoveIngredientInvalidIndex() {
        // Arrange - добавляем только один ингредиент
        burger.addIngredient(mockIngredient1);

        // Пытаемся удалить ингредиент с несуществующим индексом
        // Ожидаем, что выбросится IndexOutOfBoundsException
        burger.removeIngredient(5);
    }


    @Test
    // Проверяем перемещение ингредиента на новую позицию
    public void testMoveIngredient() {
        // Добавляем три ингредиента в бургер
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        // Настраиваем данные для всех ингредиентов
        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient1.getName()).thenReturn("майонез");
        when(mockIngredient2.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient2.getName()).thenReturn("салат");
        when(mockIngredient3.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient3.getName()).thenReturn("помидорка");

        // Перемещаем майонез (индекс 0) на позицию после помидора (индекс 2)
        burger.moveIngredient(0, 2);

        // Получаем чек после перемещения
        String receipt = burger.getReceipt();

        // Проверяем новый порядок ингредиентов в чеке
        // Находим позиции каждого ингредиента в строке чека
        int saladIndex = receipt.indexOf("салат");
        int tomatoIndex = receipt.indexOf("помидорка");
        int mayoIndex = receipt.indexOf("майонез");

        // Проверяем, что салат теперь первый, потом помидор, потом майонез
        assertTrue("Салат должен быть перед помидором", saladIndex < tomatoIndex);
        assertTrue("Помидор должен быть перед майонезом", tomatoIndex < mayoIndex);
    }

    // Проверяем перемещение ингредиента с неверным индексом, ожидаем исключение
    @Test(expected = IndexOutOfBoundsException.class)
    public void testMoveIngredientInvalidIndex() {
        // Arrange - добавляем один ингредиент
        burger.addIngredient(mockIngredient1);

        // Пытаемся переместить ингредиент на несуществующую позицию
        // Ожидаем, что выбросится IndexOutOfBoundsException
        burger.moveIngredient(0, 5);
    }

    @Test
    // Проверяем расчет цены бургера с булочкой и без ингредиентов
    public void testGetPriceOnlyBun() {
        // Настраиваем цену булочки
        when(mockBun.getPrice()).thenReturn(50.0f);

        // Получаем цену бургера (без ингредиентов)
        float price = burger.getPrice();

        // Проверяем, что цена равна 2 * цена булочки
        assertEquals("Цена бургера без ингредиентов должна быть 2 * цена булочки", 100.0f, price, 0.001f);
    }

    @Test
    // Проверяем расчет цены бургера с одним ингредиентом
    public void testGetPriceWithOneIngredient() {
        // Настраиваем цены булочки и ингредиента
        when(mockBun.getPrice()).thenReturn(50.0f);
        when(mockIngredient1.getPrice()).thenReturn(30.0f);
        // Добавляем ингредиент в бургер
        burger.addIngredient(mockIngredient1);

        // Получаем цену бургера
        float price = burger.getPrice();

        // Проверяем расчет: 2 булочки по 50 + ингредиент 30 = 130
        assertEquals("Цена бургера с одним ингредиентом должна быть 130.0", 130.0f, price, 0.001f);
    }

    @Test
    // Проверяем расчет цены бургера с несколькими ингредиентами
    public void testGetPriceWithMultipleIngredients() {
        // Настраиваем цены всех компонентов
        when(mockBun.getPrice()).thenReturn(100.0f);
        when(mockIngredient1.getPrice()).thenReturn(50.0f);
        when(mockIngredient2.getPrice()).thenReturn(75.0f);
        when(mockIngredient3.getPrice()).thenReturn(25.0f);

        // Добавляем все три ингредиента в бургер
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        // Получаем общую цену бургера
        float price = burger.getPrice();

        // Проверяем расчет: 2*100 + 50 + 75 + 25 = 350
        assertEquals("Цена бургера с тремя ингредиентами должна быть 350.0", 350.0f, price, 0.001f);
    }

    @Test
    // Проверяем формирование полного чека с несколькими ингредиентами
    public void testGetReceiptWithMultipleIngredients() {
        // Настраиваем данные булочки
        when(mockBun.getName()).thenReturn("белая булочка");
        when(mockBun.getPrice()).thenReturn(50.0f);

        // Настраиваем данные для трех ингредиентов разных типов
        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient1.getName()).thenReturn("кетчуп");
        when(mockIngredient1.getPrice()).thenReturn(10.0f);
        when(mockIngredient2.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient2.getName()).thenReturn("котлета");
        when(mockIngredient2.getPrice()).thenReturn(50.0f);
        when(mockIngredient3.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient3.getName()).thenReturn("майонез");
        when(mockIngredient3.getPrice()).thenReturn(15.0f);

        // Добавляем все ингредиенты в бургер
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        // Получаем чек бургера
        String receipt = burger.getReceipt();

        // Проверяем все элементы чека
        assertTrue("Чек должен содержать название булочки", receipt.contains("белая булочка"));
        assertTrue("Чек должен содержать кетчуп", receipt.contains("кетчуп"));
        assertTrue("Чек должен содержать котлету", receipt.contains("котлета"));
        assertTrue("Чек должен содержать майонез", receipt.contains("майонез"));
        assertTrue("Чек должен содержать общую цену", receipt.contains("Price:"));
        // Проверяем, что булочка упоминается дважды (в начале и в конце)
        assertTrue("Булочка должна упоминаться в чеке дважды",
                receipt.indexOf("белая булочка") != receipt.lastIndexOf("белая булочка"));
    }

    @Test
    // Проверяем, что ингредиенты сохраняют порядок добавления в чеке
    public void testIngredientsOrderInReceipt() {
        // Настраиваем данные булочки
        when(mockBun.getName()).thenReturn("ржаная булочка");
        // Настраиваем данные для трех ингредиентов
        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient1.getName()).thenReturn("горчица");
        when(mockIngredient2.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient2.getName()).thenReturn("салат");
        when(mockIngredient3.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient3.getName()).thenReturn("помидор");
        // Добавляем ингредиенты в определенном порядке
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        // Получаем чек бургера
        String receipt = burger.getReceipt();

        // Проверяем порядок ингредиентов в чеке
        int mustardIndex = receipt.indexOf("горчица");
        int saladIndex = receipt.indexOf("салат");
        int tomatoIndex = receipt.indexOf("помидор");

        // Проверяем, что ингредиенты идут в том же порядке, в котором были добавлены
        assertTrue("Горчица должна быть перед салатом", mustardIndex < saladIndex);
        assertTrue("Салат должен быть перед помидором", saladIndex < tomatoIndex);
    }

    @Test
    // Проверяем вывод чека с пустым бургером (только булочка)
    public void testEmptyBurgerReceipt() {
        // Настраиваем данные булочки
        when(mockBun.getName()).thenReturn("обычная булочка");
        when(mockBun.getPrice()).thenReturn(40.0f);

        // Получаем чек пустого бургера (без ингредиентов)
        String receipt = burger.getReceipt();

        // Проверяем основные элементы чека
        assertTrue("Чек должен содержать название булочки", receipt.contains("обычная булочка"));
        assertTrue("Чек должен содержать цену", receipt.contains("Price:"));

        // Проверяем чек
        assertTrue("Чек должен содержать правильную цену",
                receipt.contains("80") || receipt.contains("80.0") || receipt.contains("80.00"));
    }
}


