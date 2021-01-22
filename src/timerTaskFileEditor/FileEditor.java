package timerTaskFileEditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileEditor {

	public static void main(String[] args) {

		// edit this for the correct file directory
		String filePath = "/Users/christian/Documents/git/timerTaskFileEditor/files/";

// ==========================================================================================================================		
// ==========================================================================================================================
// ==========================================================================================================================
// ==========================================================================================================================
// ==========================================================================================================================
		System.out.println("Welcome to the Timer Task File Editor...\n"
				+ "              ...hoping to reduce 8 hours to 8 minutes!\n");

		String oldFile = getString("Enter name of file you want to read: ", true);
		String newFile = getString("Enter name of file you want to save: ", true);
		String oldFilePath = filePath + oldFile;
		String newFilePath = filePath + newFile;
		ArrayList<String> timerTaskArray = new ArrayList<String>();

		if (verifyFile(oldFilePath) == true) {
			writeFile(newFilePath, newFile);
			String line;
			try (BufferedReader readFile = new BufferedReader(new FileReader(oldFilePath))) {				
				while ((line = readFile.readLine()) != null) {

					// split the read line into three parts separated by tabs
					String[] printArray = line.split("\\s+");

					// add a variable
					String[] timerTaskVar = printArray[0].split("[.]");
					timerTaskArray.add(timerTaskVar[0]);

					// format the time to ##:##
					if (printArray[1].length() < 5) {
						printArray[1] = "0" + printArray[1];
					}

					// format days indicator to 1234567
					if (printArray[2].equalsIgnoreCase("all")) {
						printArray[2] = "1234567";
					} else {
						String One = "0";
						String Two = "0";
						String Thr = "0";
						String For = "0";
						String Fiv = "0";
						String Six = "0";
						String Sev = "0";
						if (printArray[2].contains("1")) {
							One = "1";
						}
						if (printArray[2].contains("2")) {
							Two = "2";
						}
						if (printArray[2].contains("3")) {
							Thr = "3";
						}
						if (printArray[2].contains("4")) {
							For = "4";
						}
						if (printArray[2].contains("5")) {
							Fiv = "5";
						}
						if (printArray[2].contains("6")) {
							Six = "6";
						}
						if (printArray[2].contains("7")) {
							Sev = "7";
						}
						printArray[2] = One + Two + Thr + For + Fiv + Six + Sev;	
					}
					String printLine = (timerTaskVar[0] + " = ['" + printArray[0] + "', '" + printArray[1] + "', '"
							+ printArray[2] + "']\n");

					appendFile(newFilePath, printLine);
				}
				// add the final string, setting
				int idx = 0;
				appendFile(newFilePath, "\n\n\ntimer_tasks = [");
				for (String timer_task : timerTaskArray) {
					if (idx < (timerTaskArray.size() -1)) {
						appendFile(newFilePath, timer_task + ", ");
						idx ++;
					} else {
						appendFile(newFilePath, timer_task);
					}
					
				}
				appendFile(newFilePath, "]\n");
				
				System.out.println("Formatting has completed.");
				
				
			} catch (IOException e) {
				e.printStackTrace();
				line = null;
			}

		} else if (verifyFile(oldFilePath) == false) {
			System.out.println("\nFile " + oldFilePath + " has not been found. Please try again.");
		}
	}

	private static Scanner sc = new Scanner(System.in);

	// these next two strings ensure a response is required and checks requirements
	public static String getString(String prompt) {
		return getString(prompt, false);
	}

	public static String getString(String prompt, boolean isRequired) {
		String s = "";
		boolean isValid = false;
		while (!isValid) {
			System.out.print(prompt);
			s = sc.nextLine();
			if (isRequired && s.equals("")) {
				System.out.println("\nError! This entry is required. Try again.");
			} else {
				isValid = true;
			}
		}
		return s;
	}

	// verifies file to be formatted
	public static Boolean verifyFile(String filepath) {
		File userData = new File(filepath);
		if (userData.exists() == false) {
			return false;
		} else {
			return true;
		}
	}

	// creates a new file
	public static void writeFile(String filepath, String filetext) {
		try {
			FileWriter writer = new FileWriter(filepath);
			writer.write("");
			writer.close();
		} catch (IOException e) {
			System.out.println("\nWriting file failed. Please try again.");
			e.printStackTrace();
		}
	}

	// writes to created file
	// adds a line to the string, will need this to enter weigh-ins
	public static void appendFile(String newFilePath, String printLine) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(newFilePath, true));
			writer.write(printLine);
			writer.close();
		} catch (IOException e) {
			System.out.println("\nAppending file failed. Please try again.");
			e.printStackTrace();
		}
	}

}
