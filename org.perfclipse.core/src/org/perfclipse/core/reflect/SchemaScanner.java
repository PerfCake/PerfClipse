package org.perfclipse.core.reflect;

import org.eclipse.core.runtime.FileLocator;
import org.osgi.framework.Bundle;
import org.perfclipse.core.Activator;
import org.perfclipse.core.PerfClipseConstants;

import java.io.IOException;
import java.net.URL;

public class SchemaScanner {

	public static URL getSchema() throws IOException{
		Bundle bundle = Activator.getDefault().getBundle();
		URL schemaUrl = bundle.getResource(PerfClipseConstants.PERFCAKE_XML_SCHEMA_PATH);
		URL schemaFileUrl = FileLocator.toFileURL(schemaUrl);
		return schemaFileUrl;
	}
}
