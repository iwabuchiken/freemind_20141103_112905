package freemind.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JOptionPane;

public class Methods {

	public static void 
	write_Log
	(String message,
			String fileName, int lineNumber) {
		
		String msg;
		
		////////////////////////////////

		// validate: dir exists

		////////////////////////////////
		File dir_Log = new File(CONS.Admin.dpath_Log);
		
		if (!dir_Log.exists()) {
			
			dir_Log.mkdirs();
			
			// Log
			msg = "folder created => " + dir_Log.getAbsolutePath();
			
			JOptionPane.showMessageDialog(null,
					msg,
					"message", JOptionPane.ERROR_MESSAGE);
			
		} else {
			
			// Log
			msg = "folder exists => " + dir_Log.getAbsolutePath();
			
			JOptionPane.showMessageDialog(null,
					msg,
					"message", JOptionPane.ERROR_MESSAGE);
			
		}
		
		////////////////////////////////

		// file

		////////////////////////////////
		File fpath_Log = new File(CONS.Admin.dpath_Log, CONS.Admin.fname_Log);
		
		////////////////////////////////

		// file exists?

		////////////////////////////////
		if (!fpath_Log.exists()) {
			
			try {
				
				fpath_Log.createNewFile();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
				msg = "Can't create a log file";
				
				JOptionPane.showMessageDialog(null,
						msg,
						"message", JOptionPane.ERROR_MESSAGE);
				
				return;
				
			}
			
		} else {
			
			// Log
			msg = "log file => exists";
			
			JOptionPane.showMessageDialog(null,
					msg,
					"message", JOptionPane.ERROR_MESSAGE);
			
		}
		
		////////////////////////////////

		// validate: size

		////////////////////////////////
		long len = fpath_Log.length();
		
		if (len > CONS.Admin.logFile_MaxSize) {
		
			fpath_Log.renameTo(new File(
						fpath_Log.getParent(), 
						CONS.Admin.fname_Log_Trunk
						+ "_"
						+ Methods.get_TimeLabel(fpath_Log.lastModified())
						+ CONS.Admin.fname_Log_ext
						));
			
			// new log.txt
			try {
				
				fpath_Log = new File(fpath_Log.getParent(), CONS.Admin.fname_Log);
				
				fpath_Log.createNewFile();
				
				// Log
				msg = "new log.txt => created";
				
				JOptionPane.showMessageDialog(null,
						msg,
						"message", JOptionPane.ERROR_MESSAGE);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				// Log
				msg = "log.txt => can't create!";
				
				JOptionPane.showMessageDialog(null,
						msg,
						"message", JOptionPane.ERROR_MESSAGE);
				
				e.printStackTrace();
				
				return;
				
			}
			
		}//if (len > CONS.DB.logFile_MaxSize)

		////////////////////////////////

		// write

		////////////////////////////////
		try {
			
			//REF append http://stackoverflow.com/questions/8544771/how-to-write-data-with-fileoutputstream-without-losing-old-data answered Dec 17 '11 at 12:37
			FileOutputStream fos = new FileOutputStream(fpath_Log, true);
//			FileOutputStream fos = new FileOutputStream(fpath_Log);
			
			String text = "["
							+ Methods.conv_MillSec_to_TimeLabel(
									Methods.getMillSeconds_now())
							+ "] ["
							+ fileName
							+ ": "
							+ lineNumber
							+ "] "
							+ message
							+ "\n";
			
			//REF getBytes() http://www.adakoda.com/android/000240.html
			fos.write(text.getBytes());
			
			fos.close();
			
			// Log
			msg = "log => written";
			
			JOptionPane.showMessageDialog(null,
					msg,
					"message", JOptionPane.ERROR_MESSAGE);
			
		} catch (FileNotFoundException e) {

			msg = "FileNotFoundException";
			
			JOptionPane.showMessageDialog(null,
					msg,
					"message", JOptionPane.ERROR_MESSAGE);

			e.printStackTrace();
			
		} catch (IOException e) {

			msg = "IOException";
			
			JOptionPane.showMessageDialog(null,
					msg,
					"message", JOptionPane.ERROR_MESSAGE);

			e.printStackTrace();
		}
		
	}//write_Log

	/****************************************
	 *	getMillSeconds_now()
	 * 
	 * <Caller> 
	 * 1. ButtonOnClickListener # case main_bt_start
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static long 
	getMillSeconds_now() {
		
		Calendar cal = Calendar.getInstance();
		
		return cal.getTime().getTime();
		
	}//private long getMillSeconds_now(int year, int month, int date)

	public static String
	conv_MillSec_to_TimeLabel(long millSec) {
		//REF http://stackoverflow.com/questions/7953725/how-to-convert-milliseconds-to-date-format-in-android answered Oct 31 '11 at 12:59
		String dateFormat = CONS.Admin.format_Date;
		
		DateFormat formatter = new SimpleDateFormat(dateFormat, Locale.JAPAN);

		// Create a calendar object that will convert the date and time value in milliseconds to date. 
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTimeInMillis(millSec);
		
		return formatter.format(calendar.getTime());
		
	}//conv_MillSec_to_TimeLabel(long millSec)

	/******************************
		@return format => "yyyyMMdd_HHmmss"
	 ******************************/
	public static String 
	get_TimeLabel(long millSec) {
		
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
		 
		return sdf1.format(new Date(millSec));
		
	}//public static String get_TimeLabel(long millSec)

}
