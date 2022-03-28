package com.example.byblos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Rule
    public TestRule testRule = new InstantTaskExecutorRule();

    @Mock
    Context mMockContext;
    //1
    @Test
    public void branchProfileHoursIsCorrect() {
        assertTrue(BranchProfile.isValidTime("11:11"));
    }
    //2
    @Test
    public void branchProfileHoursIsNotCorrect(){
        assertFalse(BranchProfile.isValidTime("41:11"));
    }
    //3
    @Test
    public void branchProfileNumberIsCorrect() {
        assertTrue(BranchProfile.isValidPhoneNumber("613-986-0067"));
    }
    //4
    @Test
    public void branchProfileNumberIsNotCorrect() {
        assertFalse(BranchProfile.isValidPhoneNumber("613o986-0067"));
    }
    //5
    @Test
    public void serviceIsRight() {
        String name = "moving";
        Service service = new Service(name);
        assertEquals(name, service.getName());
    }
    //6
    @Test
    public void accountIsRight() {
        String userName = "Ali89";
        Account account = new Account(userName);
        assertEquals(userName, account.getUsername());
    }
    @Test
    public void serviceIsWrong() {
        String name = "moving";
        String fake = "truck";
        Service service = new Service(name);
        assertFalse(fake, service.getName() == fake);
    }
    @Test
    public void accountIsWrong() {
        String userName = "Ali89";
        String fake = "jayjay";
        Account account = new Account(userName);
        assertFalse(fake, account.getUsername() == fake);
    }
    @Test
    public void validEmail() {
        String email = "rmura096@uottawa.ca";
        FillOutServiceForm emailValid = new FillOutServiceForm();
        boolean result = emailValid.isValidEmail(email);
        assertTrue(result);
    }
    @Test
    public void nonValidEmail() {
        String email = "rmura096uottawa.ca";
        FillOutServiceForm emailValid = new FillOutServiceForm();
        boolean result = emailValid.isValidEmail(email);
        assertFalse(result);
    }
    @Test
    public void allNumbers() {
        String number = "12334454526447465765856768898769678";
        FillOutServiceForm validNumbers = new FillOutServiceForm();
        boolean result = validNumbers.isAllNumbers(number);
        assertTrue(result);
    }
    @Test
    public void notAllNumbers() {
        String number = "A12334454526447465765856768898769678";
        FillOutServiceForm validNumbers = new FillOutServiceForm();
        boolean result = validNumbers.isAllNumbers(number);
        assertFalse(result);
    }



}