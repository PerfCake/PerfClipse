package org.perfclipse.ui.launching;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.console.MessageConsole;
import org.perfcake.PerfCakeConst;
import org.perfclipse.logging.Logger;
import org.perfclipse.scenario.ScenarioException;
import org.perfclipse.scenario.ScenarioManager;
import org.perfclipse.ui.Activator;

final public class PerfCakeRunJob extends Job{
	
	static final Logger log = Activator.getDefault().getLogger();
	

	private static final long CHECK_INTERVAL = 500;
	private IFile file;
	private MessageConsole console;
	private Shell shell;

	public PerfCakeRunJob(String name, IFile file, MessageConsole console, Shell shell) {
		super(name);
		if (file == null){
			log.warn("File with scenario is null.");
			throw new IllegalArgumentException("File with scenario is null.");
		}
		if (console == null){
			log.warn("Console for scenario run  output is null.");
			throw new IllegalArgumentException("Console for scenario run output is null."); 
			} 
		this.file = file;
		this.console = console;
		this.shell = shell;
	}

	
	@Override
	public boolean belongsTo(Object family) {
		if (PerfCakeLaunchConstants.PERFCAKE_RUN_JOB_FAMILY.equals(family)){
			return true;	
		}
		
		return false;
	}


	@Override
	protected IStatus run(IProgressMonitor monitor) {

		//redirect System.out to eclipse console
		OutputStream out = console.newOutputStream();
		PrintStream standardOut = System.out;
		System.setOut(new PrintStream(out));
		
		//Add appender for Log4j to eclipse console
		org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger.getRootLogger();

		Appender appender = rootLogger.getAppender("CONSOLE");
		if (appender instanceof ConsoleAppender){
			ConsoleAppender consoleAppender = (ConsoleAppender) rootLogger.getAppender("CONSOLE");
			consoleAppender.activateOptions();
		} else{
			log.warn("Cannot obtain PerfCake console logger. Output will not be redirected to Eclipse console");
		}
		


		//set message and scenario paths for PerfCake 
		IFolder messageDir = file.getProject().getFolder("messages");
		IFolder scenarioDir = file.getProject().getFolder("scenarios");
		System.setProperty(PerfCakeConst.MESSAGES_DIR_PROPERTY, messageDir.getRawLocation().toString());
		System.setProperty(PerfCakeConst.SCENARIOS_DIR_PROPERTY, scenarioDir.getRawLocation().toString());
		ScenarioManager scenarioManager = new ScenarioManager();

		try {
			monitor.beginTask("PerfCake task", 100);
			Thread perfCakeThread = new Thread(new PerfCakeRun(scenarioManager, file.getLocationURI().toURL()));
			perfCakeThread.start();
			while(perfCakeThread.isAlive()){
				//TODO: get RunInfo and set progress
				if (monitor.isCanceled()){
					scenarioManager.stopScenario();
				}
				try {
					Thread.sleep(CHECK_INTERVAL);
				} catch (InterruptedException e) {
					log.warn("Sleep exception. Doing busy waiting.", e);
				}
			}
			monitor.done();
		} catch (MalformedURLException e) {
			log.warn("Wrong url to scenario.", e);
			Display.getDefault().asyncExec(new ErrorDialog("Scenario URL error", e.getMessage()));
		} finally {
			System.setOut(standardOut); //set System.out to standard output
			try {
				out.close();
			} catch (IOException e) {
				log.warn("Cannot close stream to eclipse consolse!", e);
			}
		}
		return Status.OK_STATUS;
	}
	
	final class ErrorDialog implements Runnable{
		
		private String title;
		private String message;

		public ErrorDialog(String title, String message){
			this.title = title;
			this.message = message;
		}

		@Override
		public void run() {
			MessageDialog.openError(shell, title, message);
			
		}
	}
	
	final class PerfCakeRun implements Runnable{

		private ScenarioManager manager;
		private URL scenarioURL;
		
		public PerfCakeRun(ScenarioManager manager, URL scenarioURL) {
			super();
			this.manager = manager;
			this.scenarioURL = scenarioURL;
		}

		@Override
		public void run() {
			try {
				manager.runScenario(scenarioURL);
			} catch (ScenarioException e) {
				Display.getDefault().asyncExec(new ErrorDialog("Scenario error", e.getMessage()));
				log.error("Cannot run scenario", e);
			}
		}
	}
}