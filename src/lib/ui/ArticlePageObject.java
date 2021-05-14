package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;

abstract public class ArticlePageObject extends MainPageObject{

    protected static String
    TITLE,
    FOOTER_ELEMENT,
    OPTIONS_BUTTON,
    OPTIONS_ADD_TO_MY_LIST_BUTTON,
    ADD_TO_MY_LIST_OVERLAY,
    MY_LIST_NAME_INPUT,
    MY_LIST_NAME_FOLDER_BY_SUBSTRING_TPL,
    MY_LIST_OK_BUTTON,
    CLOSE_ARTICLE_BUTTON;

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring) {
        return MY_LIST_NAME_FOLDER_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring );
    }
    /* TEMPLATES METHODS */

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(TITLE, "Cannot find article title on page", 15);
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("name");
    }

    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        } else {
            this.swipeUpTitleElementAppear(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40);
        }
    }

    public void addArticleToMyList (String name_of_folder){
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                10
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find options to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                10
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of articles folder",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press 'OK' button",
                5
        );
    }

    public void addAnotherArticleToMyList(String substring) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                10
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find options to add article to reading list",
                5
        );

        String search_name_folder_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                search_name_folder_xpath,
                "Cannot find " + substring + "folder",
                10
        );
    }

    public void addArticlesToMySaved() {
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 5);
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X link",
                5
        );


    }
}
