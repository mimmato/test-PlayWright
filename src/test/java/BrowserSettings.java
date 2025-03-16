import com.microsoft.playwright.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BrowserSettings {

    private Playwright playWright;
    private Browser browser;
    private BrowserContext context;
    private Page page; // Declare a Playwright Page
    private HomePage homePage;


    @BeforeSuite
    public void setUp() {
        this.playWright = Playwright.create();
        this.browser = playWright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)); // Set to true if running headless
        this.context = browser.newContext();

        this.page = context.newPage(); // Create a Playwright Page instance

        this.homePage = new HomePage(page); // Pass the page to HomePage
        this.page.navigate("https://www.fairlight.app/");
    }

    @AfterSuite
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
        if (playWright != null) {
            playWright.close();
        }
    }

    public Page getPage() { // Method to access Page instance in other classes
        return this.page;
    }
}

