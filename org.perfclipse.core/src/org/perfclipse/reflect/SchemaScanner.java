package org.perfclipse.reflect;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.perfclipse.PerfClipseConstants;

public class SchemaScanner {

	public static URL getSchema() throws IOException{
		Bundle bundle = Platform.getBundle(PerfClipseConstants.PERFCAKE_BUNDLE);
		URL schemaURL = bundle.getEntry(PerfClipseConstants.PERFCAKE_XML_SCHEMA_PATH);
		
		return FileLocator.toFileURL(schemaURL);
	}
}
