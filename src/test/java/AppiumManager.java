import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

/**
 * Appium Manager - this class contains method to start and stops appium server
 */
public class AppiumManager {

	static CommandPrompt cp = new CommandPrompt();
	static AvailabelPorts ap = new AvailabelPorts();
	private static String port;

	/**
	 * start appium with default arguments
	 *
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void startDefaultAppium() throws InterruptedException, IOException {
		// cp.runCommand("appium --session-override");
		killExistingNodeInstances();
		String output = cp.runCommand("appium --session-override");
		System.out.println(output);
		System.out.println(output);
		Thread.sleep(5000);
		if (output.contains("not")) {
			System.out.println("\nAppium is not installed");
			System.exit(0);
		}
	}

	/**
	 * start appium with auto generated ports : appium port, chrome port, and
	 * bootstap port
	 *
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public synchronized static String startAppium() throws Exception {
		// start appium server

		port = ap.getPort();
		String chromePort = ap.getPort();
		String bootstrapPort = ap.getPort();

		String command = "appium --session-override -p " + port + " --chromedriver-port " + chromePort + " -bp "
				+ bootstrapPort;
		// String command = "appium --session-override -p "+ port;
		System.out.println(command);
		// cp.runCommand(command);
		String output = cp.runCommand(command);
		System.out.println(output);

		if (output.contains("not")) {
			System.out.println("\nAppium is not installed");
			System.exit(0);
		}
		return port;
	}

	/**
	 * start appium with modified arguments : appium port, chrome port, and bootstap
	 * port as user pass port number
	 *
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void startAppium(String port, String chromePort, String bootstrapPort)
			throws InterruptedException, IOException {
		String command = "appium --session-override -p " + port + " --chromedriver-port " + chromePort + " -bp "
				+ bootstrapPort;
		System.out.println(command);
		String output = cp.runCommand(command);
		System.out.println(output);
	}

	public static void killExistingNodeInstances() {
		try {
			String scriptToKillExistingNodeInstances = System.getProperty("user.dir")
					+ "/shellscripts/Kill-Node-Instances.sh";
			System.out.println(scriptToKillExistingNodeInstances);
			cp.runCommand("sh " + scriptToKillExistingNodeInstances);
			System.out.println("Killed Existing Node Instances");
		} catch (InterruptedException | IOException e) {
			System.out.println(e.toString());
		}
	}

	public static void killExistingNodeInstancesWin() {
		try {
			String scriptToKillExistingNodeInstances = System.getProperty("user.dir")
					+ "/shellscripts/Kill-Node-Instances.bat";
			System.out.println(scriptToKillExistingNodeInstances);
			cp.runCommand(scriptToKillExistingNodeInstances);
			System.out.println("Killed Existing Node Instances");
		} catch (InterruptedException | IOException e) {
			System.out.println(e.toString());
		}
	}

	public static void main(String[] args) throws Exception {
		// port = "";
		//port = startAppium();
		startDefaultAppium();
	}

	/**
	 * start appium with default arguments
	 *
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void startDefaultAppiumWin() throws InterruptedException, IOException {

		/*
		 * String AppiumNodeFilePath = "C:/Program Files/nodejs/node.exe"; String
		 * AppiumJavaScriptServerFile = "C:/Appium/node_modules/appium/bin/Appium.js";
		 * String AppiumServerConfigurations = "--no-reset --local-timezone";
		 * executeCommand("\"" + AppiumNodeFilePath + "\" \"" +
		 * AppiumJavaScriptServerFile + "\" " + AppiumServerConfigurations);
		 */

		// cp.runCommand("appium --session-override");
		killExistingNodeInstancesWin();
		int port = 4723;
		if (!checkIfServerIsRunnning(port)) {
			startServer();
			// stopServer();
		} else {
			System.out.println("Appium Server already running on Port - " + port);
			// stopServer();
			startServer();
		}

		/*
		 * String output =
		 * cp.runCommand("appium -a 127.0.0.1 -p 4723 --session-override");
		 * System.out.println(output); System.out.println(output); Thread.sleep(5000); if
		 * (output.contains("not")) { System.out.println("\nAppium is not installed");
		 * System.exit(0); }
		 */
	}

	public static void startServer() {
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec(
					"cmd.exe /c start cmd.exe /k \"appium -a 127.0.0.1 -p 4723 --session-override -dc \"{\"\"noReset\"\": \"\"false\"\"}\"\"");
			Thread.sleep(10000);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void stopServer() {
		Runtime runtime = Runtime.getRuntime();
		try {
			// runtime.exec("taskkill /F /IM node.exe");
			runtime.exec(
					"echo off & (for /f \"tokens=5 delims= \" %%A in ('netstat -aon ^| findstr 4723') do taskkill /F /PID %AA) & echo on");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean checkIfServerIsRunnning(int port) {

		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			// If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}

	@SuppressWarnings("unused")
	private static void executeCommand(String command) {
		String s = null;

		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			// read the output from the command
			System.out.println("Standard output of the command:\n");
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			// read any errors from the attempted command
			System.out.println("Standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}
		} catch (IOException e) {
			System.out.println("exception happened: ");
			e.printStackTrace();
		}
	}

}
