package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPageObject {

    protected AppiumDriver driver;

    //Конструктор, к которому будут обращаться все тесты (создали экземпляр класса AppiumDriver)
    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }


    // Метод поиска и ожидания элемента
    public WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    // Метод переиспользования метода поиска и ожидания элемента
    public WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    // Метод поиска элемента и клика по нему
    public WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    // Метод поиска элемента и ввода данных
    public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    // Метод, проверяющий отсутствие элемента
    public boolean waitForElementNotPresent (By by, String  error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    // Метод поиска элемента и очистки поля ввода
    public WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void swipeUp (int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize(); // Задаем размеры экрана
        int x = size.width / 2; // Т.к. свайп снизу вверх, то значение по оси Х не меняется, поэтому задаем середину экрана
        int start_y = (int) (size.height * 0.8); // Получаем начальную точку в 80% экрана внизу, т.е. немного над нижним краем экрана
        int end_y = (int) (size.height * 0.2);
        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform(); // Сам свайп

    }

    public void swipeUpQuick () {
        swipeUp(200);
    }

    // Метод свайпа до нужного элемента
    public void swipeUpToFindElement (By by, String error_message, int max_swipes){
//        driver.findElements(by); // Функция для нахождеия всех элементов (findElements находит только 1 элемент)
//        driver.findElements(by).size(); // Функция, которая выдает кол-во элементов, найденных findElements

        int already_swiped = 0; // Объявили переменную, считающую кол-во свайпов, присвоили значение 0
        while (driver.findElements(by).size() == 0) { // Запускается цикл, который ищет элементы, которые передаются в by, а когда находятся, цикл завершается

            if (already_swiped > max_swipes) {
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0 );
                return; // Если элемент нашелся в последний момент, то выходим из цикла и идем дальше по коду
            }
            swipeUpQuick(); // Если цикл не нахдит элементы, то постоянно свайпаем вверх
            ++already_swiped; // после каждого свайпа увеличиваем счетчик
        }
    }

    // Метод свайпа влево
    public void swipeElementToLeft (By by, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10
        );
        int left_x = element.getLocation().getX(); // Функция записывает в left_x самую левую точку нашего элемента по оси Х
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;
        TouchAction action = new TouchAction(driver); // Инициализируем драйвер
        action
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    public int getAmountOfElements (By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent (By by, String error_message){
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError (default_message + " " + error_message );
        }
    }

    public String waitForElementAndGetAttribute (By by, String attribute, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    public void waitForMenuToRender (By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);

    }
}
