package com.labcorp.unit;

import com.labcorp.pages.CareersPage;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class CareersPageTest  {

    @Test
    void testExtractSecondBulletUnderHeader() throws Exception {
        String mockHtml = """
            <h2>Main responsibilities include</h2>
            <ul>
                <li>First bullet</li>
                <li>Second bullet - Important responsibility</li>
            </ul>
        """;

        CareersPage page = new CareersPage(null); // null WebDriver since not used here

        // Inject mock HTML via reflection
        Method parseMethod = CareersPage.class.getDeclaredMethod("parseDescriptionContent", String.class);
        parseMethod.setAccessible(true);
        parseMethod.invoke(page, mockHtml);

        String actualBullet = page.getSecondBulletUnderHeader("Main responsibilities include");
        assertEquals("Second bullet - Important responsibility", actualBullet);
    }

    @Test
    void testExtractThirdParagraphSentence() throws Exception {
        String mockHtml = """
        <p>Intro text here.</p>
        <p>Second paragraph goes here.</p>
        <p>The right candidate for this role will participate in the test automation technology development. They will work cross-functionally.</p>
    """;

        CareersPage page = new CareersPage(null);
        Method parseMethod = CareersPage.class.getDeclaredMethod("parseDescriptionContent", String.class);
        parseMethod.setAccessible(true);
        parseMethod.invoke(page, mockHtml);

        String actualSentence = page.getThirdParagraphFirstSentence();
        assertEquals("The right candidate for this role will participate in the test automation technology development.", actualSentence);
    }

    @Test
    void testGetSecondBulletUnderHeader_MissingHeader() throws Exception {
        String mockHtml = """
        <h2>Some Other Header</h2>
        <ul>
            <li>Item 1</li>
            <li>Item 2</li>
        </ul>
    """;

        CareersPage page = new CareersPage(null);
        Method parseMethod = CareersPage.class.getDeclaredMethod("parseDescriptionContent", String.class);
        parseMethod.setAccessible(true);
        parseMethod.invoke(page, mockHtml);

        assertNull(page.getSecondBulletUnderHeader("Non-existent header"));
    }

    @Test
    void testGetSecondBulletUnderHeader_OnlyOneItem() throws Exception {
        String mockHtml = """
        <h3>Skills and experience:</h3>
        <ul>
            <li>Only one bullet</li>
        </ul>
    """;

        CareersPage page = new CareersPage(null);
        Method parseMethod = CareersPage.class.getDeclaredMethod("parseDescriptionContent", String.class);
        parseMethod.setAccessible(true);
        parseMethod.invoke(page, mockHtml);

        assertNull(page.getSecondBulletUnderHeader("Skills and experience:"));
    }

}
