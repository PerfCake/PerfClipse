/*
 * Perfclispe
 * 
 * 
 * Copyright (c) 2013 Jakub Knetl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.perfclipse.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.perfclipse.ui.launching.PerfCakeLaunchConstants;
import org.perfclipse.ui.launching.PerfCakeRunJob;

/**
 * Handlers which terminates all PerfCake executions.
 * 
 * @author Jakub Knetl
 *
 */
public class TerminateHandler extends AbstractHandler {

	private IJobManager jobManager;
	public TerminateHandler() {
		jobManager = Job.getJobManager();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Job[] jobs = jobManager.find(PerfCakeLaunchConstants.PERFCAKE_RUN_JOB_FAMILY);
		for (Job job : jobs){
			if (job instanceof PerfCakeRunJob){
				job.cancel();
			}
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		Job[] jobs = jobManager.find(PerfCakeLaunchConstants.PERFCAKE_RUN_JOB_FAMILY);
		if (jobs.length > 0)
			return true;
		return false;
	}

}
