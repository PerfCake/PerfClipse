/**
 *
 */
package org.perfclipse.core.reflect;

import org.eclipse.core.runtime.FileLocator;
import org.osgi.framework.Bundle;
import org.perfclipse.core.Activator;
import org.perfclipse.core.PerfClipseConstants;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.jar.JarFile;

/**
 * @author jknetl
 *
 */
public class ReflectUtils {

	private ReflectUtils(){};

	/**
	 * Returns jar file with perfcake and all its dependency which is embedded inside of org.perfclipse.core bundle.
	 * @return  JarFile with perfcake
	 * @throws IOException when it is imposible to get perfcakeJar
	 */
	public static JarFile getPerfcakeLibrary() throws IOException{
		JarFile perfcakeJar = null;

		Bundle bundle = Activator.getDefault().getBundle();

		URL perfcakeUrl = bundle.getEntry(PerfClipseConstants.PERFCKAGE_LIB_PATH);
		URL perfcakeFileUrl = FileLocator.toFileURL(perfcakeUrl);

		perfcakeJar = new JarFile(new File(perfcakeFileUrl.getFile()));



		return perfcakeJar;

	}

}
