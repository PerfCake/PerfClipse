package org.perfclipse.ui.launching;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;

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
import org.perfclipse.scenario.ScenarioException;
import org.perfclipse.scenario.ScenarioManager;

final class PerfCakeRunJob extends Job{
	
	private IFile file;
	private MessageConsole console;
	private Shell shell;

	public PerfCakeRunJob(String name, IFile file, MessageConsole console, Shell shell) {
		super(name);
		if (file == null){
			PerfCakeLaunchDeleagate.log.warn("File with scenario is null.");
			throw new IllegalArgumentException("File with scenario is null.");
		}
		if (console == null){
			PerfCakeLaunchDeleagate.log.warn("Console for scenario run  output is null.");
			throw new IllegalArgumentException("Console for scenario run output is null."); 
			} 
		this.file = file;
		this.console = console;
		this.shell = shell;
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
			PerfCakeLaunchDeleagate.log.warn("Cannot obtain PerfCake console logger. Output will not be redirected to Eclipse console");
		}
		


		//set message and scenario paths for PerfCake 
		IFolder messageDir = file.getProject().getFolder("messages");
		IFolder scenarioDir = file.getProject().getFolder("scenarios");
		System.setProperty(PerfCakeConst.MESSAGES_DIR_PROPERTY, messageDir.getRawLocation().toString());
		System.setProperty(PerfCakeConst.SCENARIOS_DIR_PROPERTY, scenarioDir.getRawLocation().toString());
		ScenarioManager scenarioManager = new ScenarioManager();

		try {
			monitor.beginTask("PerfCake task", 100);
			scenarioManager.runScenario(file.getLocationURI().toURL());
			monitor.done();
		} catch (ScenarioException e) {
			PerfCakeLaunchDeleagate.log.warn("Cannot run scenario" + e);
			Display.getDefault().asyncExec(new ErrorDialog("Scenario error", e.getMessage()));
		} catch (MalformedURLException e) {
			PerfCakeLaunchDeleagate.log.warn("Wrong url to scenario." + e);
			Display.getDefault().asyncExec(new ErrorDialog("Scenario URL error", e.getMessage()));
		} finally {
			System.setOut(standardOut); //set System.out to standard output
			try {
				out.close();
			} catch (IOException e) {
				PerfCakeLaunchDeleagate.log.warn("Cannot close stream to eclipse consolse!" + e);
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
}