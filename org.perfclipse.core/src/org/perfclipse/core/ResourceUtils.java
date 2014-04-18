package org.perfclipse.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.perfclipse.core.logging.Logger;

public class ResourceUtils {
	
	
	static final Logger log = Activator.getDefault().getLogger();

	/**
	 * Check if message is local with no path. It means it contains only filename
	 * and thus it should be placed in the messages directory
	 * @param url url to be checked
	 * @return
	 */
	public static boolean isMessageLocal(String url){
		if (url == null){
			throw new IllegalArgumentException("url cannot be null");
		}
		// if there is no slash than url is just name
		if (url.indexOf("/") < 0)
			return true;
		
//		//check if url is not name like ./name.msg
//		if (url.length() > 2){
//				//check if message starts with ./
//				if (url.charAt(0) == '.' && url.charAt(1) == '/'){
//					//check if there is no other slash than on the second position 
//					if (url.indexOf("/", 2) < 0)
//						return true;
//			}
//		}
		
		return false;
	}
	
	/**
	 * Checks if message with given name exits in the project's messages directory.
	 * @param name name of the message
	 * @param project name of the project
	 * @return true if message exists in messages directory of the project. Else otherwise
	 */
	public static boolean messageExists(String name, IProject project){
		if (name == null){
			throw new IllegalArgumentException("Name cannot be null");
		}
		if (project == null){
			throw new IllegalArgumentException("Project cannot be null");
		}
		
		IFile message = getMessageFileByName(name, project);
		if (message == null)
			return false;
		return message.exists();
	}
	
	/**
	 * Finds message file resource for given message specified by name.
	 * @param name
	 * @param project
	 * @return {@link IFolder#getFile(String)}.
	 */
	private static IFile getMessageFileByName(String name, IProject project){
		if (name == null){
			throw new IllegalArgumentException("Name cannot be null");
		}
		if (project == null){
			throw new IllegalArgumentException("Project cannot be null");
		}
		
		IFolder messagesFolder = project.getFolder(PerfClipseConstants.MESSAGE_DIR_NAME);
		if (messagesFolder == null || !messagesFolder.exists())
			return null;
			
		IFile message = messagesFolder.getFile(name);

		return message;
	}
	
	/**
	 * Creates message with file name in messages directory of the project
	 * @param name name of the message file
	 * @param contents Contents of the new created file.
	 * @param project project in which message should be created
	 * @return True if the message was successfuly created.
	 * @throws CoreException if message resource cannot be created.
	 */
	public static boolean createMessage(String name, byte[] contents,
			IProject project) throws CoreException{
		if (name == null){
			throw new IllegalArgumentException("Name cannot be null");
		}
		if (project == null){
			throw new IllegalArgumentException("Project cannot be null");
		}
		
		IFile message = getMessageFileByName(name, project);
		ByteArrayInputStream in = null;
		try {
			in = new ByteArrayInputStream(contents);
			message.create(in, false, null);
		} finally {
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {
					log.error("Cannot close input stream for newly created message", e);
				}
			}
		}
		
		
		return true;
	}
	
	
	/**
	 * Removes message with given file name in the project
	 * 
	 * @param name message name 
	 * @return Contents of the deleted file or null if the file was not deleted.
	 * @throws CoreException 
	 * @throws IOException 
	 */
	public static byte[] deleteMessage(String name, IProject project) throws CoreException, IOException{
		if (name == null){
			throw new IllegalArgumentException("Name cannot be null");
		}
		
		byte[] contents = new byte[0];

		IFile messageFile = getMessageFileByName(name, project);

		if (messageFile == null || !messageFile.exists())
			return null;

		InputStream in = null;
		try{
			in = messageFile.getContents();
			contents = IOUtils.toByteArray(in);
			if (contents == null)
				contents = new byte[0];
		} finally {
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {
					log.error("Cannot close message contents stream.", e);
				}
			}
		}

		try{
			messageFile.delete(true, null);
		} catch (CoreException e) {
			log.error("Cannot delete message file", e);
			throw e;
		}
		
		return contents;
		
	}
	
	/**
	 * Change message resource name.
	 * @param oldName
	 * @param newName
	 * @param project
	 * @return true if message was moved (renamed).
	 * @throws CoreException 
	 */
	public static boolean moveMessage(String oldName, String newName, IProject project) throws CoreException{
		if (oldName == null || newName == null){
			throw new IllegalArgumentException("Name cannot be null.");
		}
	
		IFile file = getMessageFileByName(oldName, project);
		if (file == null)
			return false;
		
		IPath newPath = new Path(newName);
		
		//TODO: progressmonitor
		file.move(newPath, false, null);
		
		return true;
	}
}
