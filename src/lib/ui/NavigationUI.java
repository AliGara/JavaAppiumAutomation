package lib.ui;

import io.appium.java_client.AppiumDriver;

public class NavigationUI extends MainPageObject{

    protected static  String
        MY_LISTS_LINK,
    WINDOW_CLOSE;

    public NavigationUI(AppiumDriver driver){
        super(driver);
    }

    public void clickMyLists() {
        this.waitForElementAndClick(
                MY_LISTS_LINK,
                "Cannot find navigation button to My list",
                5
        );
    }

    public void clickCloseWindow() {
        this.waitForElementAndClick(
                WINDOW_CLOSE,
                "Cannot find navigation button to close window",
                5

        );
    }
}
