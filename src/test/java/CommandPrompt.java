/**
 * Command Prompt - this class contains method to run windows and mac commands  
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandPrompt {

	Process p;
	ProcessBuilder builder;

	/**
	 * This method run command on windows and mac
	 *
	 * @param command
	 *            to run
	 */
	@SuppressWarnings("unused")
	public String runCommand(String command) throws InterruptedException, IOException {
		String os = System.getProperty("os.name");
		System.out.println(os);

		// build cmd proccess according to os
		if (os.contains("Windows")) // if windows
		{
			builder = new ProcessBuilder("cmd.exe", "/c", command);
			builder.redirectErrorStream(true);
			Thread.sleep(1000);
			p = builder.start();
		} else // If Mac
		{
			System.out.println("Identified Mac - Running Command on Mac ");
			System.out.println("Command = " + command);
			p = Runtime.getRuntime().exec(command);
		}
		// get std output
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = "";
		String allLine = "";
		int i = 1;
		while (((line = r.readLine()) != null)) {
			allLine = allLine + "" + line + "\n";
			if (line.contains("Console LogLevel: debug"))
				break;
			if (line.contains("Appium REST http interface listener started"))
				break;
			i++;
		}
		return allLine;
	}

	public static void main(String[] args) throws Exception {
		CommandPrompt cmd = new CommandPrompt();
		String response = cmd.runCommand("/usr/local/bin/idevice_id -l");
		response = cmd.runCommand("appium --session-override -p 50788--chromedriver-port 50789 -bp 50790");
		System.out.println("Command Run" + response);
	}
}
