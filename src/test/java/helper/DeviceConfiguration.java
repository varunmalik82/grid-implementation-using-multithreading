package helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * DeviceConfiguration - this class contains methods to start adb server, to get
 * connected android, ios devices and their information.
 */
public class DeviceConfiguration {

	CommandPrompt cmd = new CommandPrompt();
	Map<String, String> devices = new HashMap<>();

	/**
	 * This method start adb server
	 *
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void startADB() throws InterruptedException, IOException {
		String output = cmd.runCommand("adb start-server");
		String[] lines = output.split("\n");
		if (lines.length == 1)
			System.out.println("adb service already started");
		else if (lines[1].equalsIgnoreCase("* daemon started successfully *"))
			System.out.println("adb service started");
		else if (lines[0].contains("internal or external command")) {
			System.out.println("adb path not set in system varibale");
			System.exit(0);
		}
	}

	/**
	 * This method stop adb server
	 *
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void stopADB() throws InterruptedException, IOException {
		cmd.runCommand("adb kill-server");
	}

	/**
	 * This method return connected android devices
	 *
	 * @return hashmap of connected android devices information
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public Map<String, String> getAndroidDevices() throws InterruptedException, IOException {

		startADB();
		String output = cmd.runCommand("adb devices");
		String[] lines = output.split("\n");

		if (lines.length <= 1) {
			System.out.println("No Android Device Connected with the machine");
			stopADB();
			System.exit(0);
		}

		for (int i = 1; i < lines.length; i++) {
			lines[i] = lines[i].replaceAll("\\s+", "");

			if (lines[i].contains("device")) {
				lines[i] = lines[i].replaceAll("device", "");
				String deviceID = lines[i];
				String model = cmd
						.runCommand("adb -s " + deviceID + " shell getprop ro.product.model")
						.replaceAll("\\s+", "");
				String brand = cmd
						.runCommand("adb -s "+ deviceID + " shell getprop ro.product.brand")
						.replaceAll("\\s+", "");
				String osVersion = cmd
						.runCommand(
								"adb -s " + deviceID + " shell getprop ro.build.version.release")
						.replaceAll("\\s+", "");
				String deviceName = brand + " " + model;

				devices.put("deviceID" + i, deviceID);
				devices.put("deviceName" + i, deviceName);
				devices.put("DEVICE_OS_VERSION" + i, osVersion);

				System.out.println("Following device is connected");
				System.out.println(deviceID + " " + deviceName + " " + osVersion + "\n");
			} else if (lines[i].contains("unauthorized")) {
				lines[i] = lines[i].replaceAll("unauthorized", "");
				String deviceID = lines[i];

				System.out.println("Following device is unauthorized");
				System.out.println(deviceID + "\n");
			} else if (lines[i].contains("offline")) {
				lines[i] = lines[i].replaceAll("offline", "");
				String deviceID = lines[i];

				System.out.println("Following device is offline");
				System.out.println(deviceID + "\n");
			}
		}
		return devices;
	}

	/**
	 * This method return connected ios devices
	 *
	 * @return hashmap of connected ios devices information
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unused")
	public Map<String, String> getiOSDevices() throws InterruptedException, IOException {
		String scriptPath = System.getProperty("user.dir") + "/shellscripts/Connected-iOS-Devices.sh";
		System.out.println(scriptPath);
		String output = cmd.runCommand("sh " + scriptPath);
		String[] lines = output.split("\n");

		for (int i = 0; i < lines.length; i++) {
			String deviceID = null;
			// command returns 1 line even if there is no ios device connected,
			// if this line is empty that means no device connected
			if (lines[i].isEmpty()) {
				System.out.println("No IOS Deivce Connected");
				System.exit(0);
			}
			devices.put("deviceID" + i, getUDID(lines[i]));
			devices.put("deviceName" + i, getDeviceName(lines[i]));
			System.out.println("Connected Device - " + getDeviceName(lines[i]) + "  -  " + getUDID(lines[i]));
		}
		return devices;
	}

	public String getDeviceName(String deviceName) {
		for (int i = 0; i < deviceName.length(); i++) {
			if (deviceName.charAt(i) == '[') {
				return deviceName.substring(0, i - 1);
			}
		}
		return deviceName;
	}

	public String getUDID(String deviceUDID) {
		if(deviceUDID.contains("[") && deviceUDID.contains("]"))
		{
			int startChar = deviceUDID.indexOf("[");
			int endChar = deviceUDID.indexOf("]");
			return deviceUDID.substring(startChar + 1, endChar);
		}
		else
		{
			for (int i = 0; i < deviceUDID.length(); i++) {
				if (deviceUDID.charAt(i) == '[') {
					return deviceUDID.substring(i + 1, i + 41);
				}
			}
		}
		return deviceUDID;
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		DeviceConfiguration dc = new DeviceConfiguration();
		dc.getiOSDevices();
	}

	public String getSimulatorPlatformVersion() {

		String scriptPath = System.getProperty("user.dir") + "/shellscripts/SimulatorPlatformVersion.sh";
		System.out.println(scriptPath);
		String output = null;
		try {
			output = cmd.runCommand("sh " + scriptPath);

		} catch (Exception e) {
			System.out.println("\n\nError While getting platform Version of available Simulators\n\n");
		}

		String[] platformVersion = output.split("\n");
		return platformVersion[0];
	}

}
