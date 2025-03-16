import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.List;


public class HomePageTest extends BrowserSettings {
    private HomePage homePage;
    private Page page;
    @Test
    public void testEmailField() {
        // Initialize the HomePage object with the current page from BrowserSettings
        homePage = new HomePage(getPage());

        // Test: Enter an email into the email field
        String testEmail = "mimmato6@gmail.com";
        homePage.enterEmail(testEmail);

        // Verify that the email was entered (you can also check if the input value is populated)
        Assert.assertTrue(getPage().locator("//input[@value='" + testEmail + "']").isVisible(), "Email should be visible in the input field.");
    }

    @Test
    public void testJoinWaitlistButton2() {
        homePage = new HomePage(getPage());

        List<String> emailAddresses = homePage.generateEmailAddresses();

        // Loop through all emails
        for (String email : emailAddresses) {
            // Enter the email
            homePage.enterEmail(email);
            // click the join button
            homePage.clickJoinWaitlistButton();
            // check for confirmation image
            Assert.assertTrue(homePage.isConfirmationVisible(), "Confirmation message should be visible after clicking the 'Join Waitlist' button for email: " + email);
            // reload hard refreshes the page and inputted values are cleared, this is not expected when testing manually
            getPage().reload();

            // Assert that the input field is empty after reload
            String currentValue = homePage.getEmailFieldValue();
            Assert.assertFalse(currentValue.isEmpty(), "Email field should NOT be empty on this check, value found is: " + currentValue);
//            Assert.assertTrue(currentValue.isEmpty(), "Email input field should be empty after reload, but found: " + currentValue);

        }
    }

    @Test
    public void testJoinWaitlistButton() {
        homePage = new HomePage(getPage());

        // Enter a valid email and click the 'Join Waitlist' button
        homePage.enterEmail("mimmato6@gmail.com");
        homePage.clickJoinWaitlistButton();

        // Verify that the confirmation message is displayed after clicking the button
        Assert.assertTrue(homePage.isConfirmationVisible(), "Confirmation message should be visible after clicking the 'Join Waitlist' button.");
    }
    @Test
        public void testWaitingListNumberChanges() {

        homePage = new HomePage(getPage());

        // Step 1: Get the initial number of people on the waitlist
            String initialText = homePage.getWaitingListText();
            int initialNumber = extractNumberFromText(initialText);

            // Step 2: Register by entering an email and clicking the "Join Waitlist" button
            String testEmail = "mimmato7@gmail.com";
            homePage.enterEmail(testEmail);
            homePage.clickJoinWaitlistButton();

            // Step 3: Wait for the confirmation message
            Assert.assertTrue(homePage.isConfirmationVisible(), "Confirmation message should be visible");

            // Step 4: Refresh the page
            page.reload();

            // Step 5: Get the updated number of people on the waitlist
            String updatedText = homePage.getWaitingListText();
            int updatedNumber = extractNumberFromText(updatedText);

            // Step 6: Verify the number has increased
            Assert.assertTrue(updatedNumber > initialNumber, "The waiting list number should have increased after registration");

        }

        private int extractNumberFromText(String text){
            // Extract the number from the paragraph text (you can adjust the regex if needed)
            String numberStr = text.replaceAll("[^\\d]", "");
            return Integer.parseInt(numberStr);
        }
    }
