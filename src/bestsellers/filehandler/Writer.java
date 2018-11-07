package bestsellers.filehandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
/*
 * Created by Michael T. Meikle
 * Note: This was pulled from another project of mine and re-arranged for use within this program.
 */
//Singleton Implementation to ensure only one instance of this class is created
public class Writer {
	private static Writer writerInstance;
	//Keeps a queue of all reports written to be filed on program termination
	//Keeps a queue of all database searches conducted to be filed on program termination
	private ArrayList<String> reportList, database;
	
	private Writer() {
		reportList = new ArrayList<String>();
		database = new ArrayList<String>();
	}
	//Writes defined list to file when called
	private void writeFile(String fileName, ArrayList<String> list) {
		File file = new File(fileName);
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(file, true));
			for(String x : reportList) {
				printWriter.println(x);
			}
			printWriter.close();
		} catch (FileNotFoundException e) {
			this.writeError("Writer.writeFile() failure");
			e.printStackTrace();
		} catch (IOException e) {
			this.writeError("Writer.writeFile() failure");
			e.printStackTrace();
		}
	}
	//Can be called to write an error message to a report
	public void writeError(String error) {
		System.out.println("Writing system error");
		File file = new File("src/dojinteractions/resources/textfiles/errorlog.txt");
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(file, true));
			int seconds = (int) (System.currentTimeMillis() / 1000);
			int hours = (seconds / 3600 % 24);
			int mins = (seconds / 60 % 60);
			int secs = (seconds % 60);
			String formattedTime = hours + ":" + mins + ":" + secs;
			printWriter.println( formattedTime + " UTC || " + error);
			printWriter.close();
			//SUGGESTION: Look into implementing error logging using the ConsoleHandler class
		} catch (FileNotFoundException e) {
			System.out.println("Error Report Failure");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error Report Failure");
			e.printStackTrace();
		}
		System.out.println("Error report saved");
	}
	//Triggers a write of whatever is in the database list and clears the list
	public void writeDatabase() {
		System.out.println("Writing searched entries to file...");
		this.writeFile("src/dojinteractions/resources/textfiles/database.txt", database);
		database.clear();
		System.out.println("Write complete");
	}
	public void writeReports() {
		System.out.println("Writing reports to file...");
		this.writeFile("src/dojinteractions/resources/textfiles/reports.txt", reportList);
		reportList.clear();
		System.out.println("Write complete");
	}
	public static Writer getInstance() {
		if(writerInstance == null) {
			writerInstance = new Writer();
		}
		return writerInstance;
	}
	
}
