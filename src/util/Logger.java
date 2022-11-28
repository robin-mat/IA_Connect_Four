package util;

import javax.swing.JTextArea;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	public Date date;

	public JTextArea jTextArea;


	public Logger(){
	}

	public void write(String log){
		log = this.addPrefix()+log;
		//TODO
		//écrire dans un fichier de log
		if (this.jTextArea != null){
			this.jTextArea.append(log+"\n");
		}
		System.out.println(log);
	}

	public String addPrefix(){
		this.date = new Date();
		return "["+new SimpleDateFormat("HH:mm:ss").format(this.date)+"][Log] ";
		
	}

	public void write(Exception e){
		this.write(e.toString());
	}

	public void resetLogFile(){

	}

	public void setJTextArea(JTextArea j){
		this.jTextArea = j;
	}
}