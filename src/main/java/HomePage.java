import com.microsoft.playwright.*;

import java.util.ArrayList;
import java.util.List;

public class HomePage {

    private Page page;  // Playwright page instance
    private Locator emailField; // Locator for the email input field
    private Locator joinWaitlistButton; // Locator for the 'Join Waitlist' button
    private Locator confirmationMessage; // Locator for confirmation message
    private Locator waitingListText;

    public HomePage(Page page) {
        this.page = page;

        // Define locators for key elements on the landing page
        emailField = page.locator("xpath=/html/body/div[1]/div/div[1]/div[1]/div[3]/div[3]/div/form/input");
        joinWaitlistButton = page.locator("xpath=/html/body/div[1]/div/div[1]/div[1]/div[3]/div[3]/div/form/div[2]/input");  // Change the selector as needed
        confirmationMessage = page.locator("xpath=/html/body/div[1]/div/div[1]/div[1]/div[3]/div[3]/div"); // Modify based on actual text
//        waitingListText = page.locator("xpath=//div[contains(text(), 'There are') and contains(text(), 'people on the waiting list')]");
        waitingListText = page.locator("text=There are 4568 people on the waiting list, reserve your spot now").first();
    }

    // insert email in the email form
    public void enterEmail(String email) {
        emailField.fill(email);
    }
    // .fill could potentially be clearing the field after page reload

    // click the waitlist button
    public void clickJoinWaitlistButton() {
        joinWaitlistButton.click();
    }

    // Check if confirmation message/check mark is visible
    public boolean isConfirmationVisible() {
        confirmationMessage.waitFor(new Locator.WaitForOptions().setTimeout(5000)); // wait for up to 5 seconds
        return confirmationMessage.isVisible();

    }

    public String getWaitingListText() {
        Locator waitingListText = page.locator("text=There are 4568 people on the waiting list, reserve your spot now");  // Adjust this to match the paragraph exactly
        return waitingListText.textContent();
    }

    // generate 10 mail addresses
    public List<String> generateEmailAddresses() {
        List<String> emails = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            emails.add("mimmato" + i + "@gmail.com");
        }

        return emails;
    }

    public String getEmailFieldValue() {
        return emailField.inputValue();
    }

}


