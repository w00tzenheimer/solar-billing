/*
    jBilling - The Enterprise Open Source Billing System
    Copyright (C) 2003-2009 Enterprise jBilling Software Ltd. and Emiliano Conde

    This file is part of jbilling.

    jbilling is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    jbilling is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with jbilling.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.sapienter.jbilling.server.pluggableTask;

import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskDTO;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;

public abstract class PaymentTaskWithTimeout extends PaymentTaskBase {
	public static final String PARAM_TIMEOUT_SECONDS = "timeout_sec";
	
	private int myTimeout;
	
	@Override
	public void initializeParameters(PluggableTaskDTO task)
			throws PluggableTaskException {
		
		super.initializeParameters(task);

		String timeoutText = getOptionalParameter(PARAM_TIMEOUT_SECONDS, "10");
		try {
			myTimeout = Integer.parseInt(timeoutText);  
		} catch (NumberFormatException e){
			throw new PluggableTaskException("" // 
					+ "Integer expected for parameter: " + PARAM_TIMEOUT_SECONDS // 
					+ ", actual: " + timeoutText);
		}
	}
	
	protected int getTimeoutSeconds() {
		return myTimeout;
	}
}
