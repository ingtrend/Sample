package Framework.Appium;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class Demos extends Capability {

	AndroidDriver<AndroidElement> driver;
	
	@BeforeTest
	public void bt() throws IOException, InterruptedException
	{	
		Runtime.getRuntime().exec("taskkill /F /IM /node.exe");
		Thread.sleep(5000);
		service = startServer();
		driver = capabilities(appPackage,appActivity,deviceName,platformName,chromeExecutable);
		//if any of my element takes time it will wait for extra 30 secs
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	}
	
	@Test(enabled=false)
	public void testcase1() throws InterruptedException
	{
		//I want to select radio button//female as male is default
		driver.findElement(By.xpath("//*[@text='Female']")).click();
		//Enter name in Your name text box
		driver.findElement(By.xpath("//*[@class='android.widget.EditText']")).sendKeys("Aish");
		//I want to fetch the name and store it in a variable
		String Name = driver.findElement(By.xpath("//*[@class='android.widget.EditText']")).getAttribute("text");
		//Select the country drop down
		driver.findElement(By.id("android:id/text1")).click();
		//I want to scroll to India
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"))").click();
		//I have to click on Let's Shop
		driver.findElementById("com.androidsample.generalstore:id/btnLetsShop").click();
		//click on first add to cart button
		driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
		//I want to click on second add to cart button. get(0) as text for previous changed
		driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
		//click on cart button
		driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
		Thread.sleep(4000);
		//add two product amount and compare with the final amount
		String amount1 = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(0).getText();
		// I am eliminating the $ symbol/ first character
		amount1=amount1.substring(1);
		//convert string to double to add the price amount
		Double amount1value = Double.parseDouble(amount1);		
		
		
		String amount2 = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(1).getText();
		amount2=amount2.substring(1);
		Double amount2value = Double.parseDouble(amount2);
		
		Double TotalValue = amount1value+amount2value;
		
		String Finalamount = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
		
		Finalamount =Finalamount.substring(1);
		Double Finalamountvalue = Double.parseDouble(Finalamount);
		Assert.assertEquals(TotalValue,Finalamountvalue);
		//tap on the checkbox
		WebElement tap = driver.findElementByClassName("android.widget.CheckBox");
		TouchAction t = new TouchAction(driver);
		t.tap(tapOptions().withElement(element(tap))).perform();
		//long press on terms and conditions
		WebElement LP = driver.findElementByAndroidUIAutomator("text(\"Please read our terms of conditions\")");
		t.longPress(longPressOptions().withElement(element(LP)).withDuration(ofSeconds(3))).release().perform();
		//this will click on the close button
		driver.findElementById("android:id/button1").click();
		//click on proceed
		driver.findElementById("com.androidsample.generalstore:id/btnProceed").click();
		Thread.sleep(7000);
		
		//context//web//native//hybrid 
		//Context is nothing but to know which/how many context are there at present
		//same as window handles when there are multiple windows in a web app
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
		    System.out.println(contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
		}
		//This is basically to switch between native app view and web app view in hybrid app
		driver.context("WEBVIEW_com.androidsample.generalstore");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@name='q']")).sendKeys("IBM");
		driver.findElement(By.xpath("//*[@name='q']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		driver.context("NATIVE_APP");
		service.stop();
	}
	
	@Test(enabled=false)
	public void testcase2()
	{
		//I want to select radio button//female as male is default
		driver.findElement(By.xpath("//*[@text='Female']")).click();
		//Enter name in Your name text box
		driver.findElement(By.xpath("//*[@class='android.widget.EditText']")).sendKeys("Aish");
		//I want to fetch the name and store it in a variable
		String Name = driver.findElement(By.xpath("//*[@class='android.widget.EditText']")).getAttribute("text");
		System.out.println(Name);
		//Select the country drop down
		driver.findElement(By.id("android:id/text1")).click();
		//I want to scroll to India
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"))").click();
		//I have to click on Let's Shop
		driver.findElementById("com.androidsample.generalstore:id/btnLetsShop").click();
		String Expected ="Aish";
		Assert.assertEquals(Name, Expected);
	}
	@Test
	public void testcase3()
	{
		System.out.println("Jenkins execution started");
		System.out.println("Jenkins execution completed");
	}
}
