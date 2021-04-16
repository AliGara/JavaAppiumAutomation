import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;

public class HomeWork5_8 extends CoreTestCase {

    @Test
    public void testSearchListAndClear () {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("QA");

        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "Few articles found",
                amount_of_search_results > 1
        );

        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsResultOfSearch();

    }

    @Test
    public void testSaveTwoArticlesToMyList () {
        String name_of_folder = "Learning programming for automation";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Python");
        SearchPageObject.clickByArticleWithSubstring("General-purpose programming language");
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.addAnotherArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName("Learning programming for automation");
        MyListsPageObject.swipeByArticleToDelete("Java (programming language)");

        SearchPageObject.clickByArticleWithSubstring("general-purpose programming language");
        ArticlePageObject.waitForTitleElement();
    }

    @Test
    public void testAssertArticleTitlePresent () {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        assertTrue(
                "Article title not found",
                !driver.findElements(By.id("org.wikipedia:id/view_page_title_text")).isEmpty()
        );
    }
}
