/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wallerlab.yoink.service.request;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * this class is for job request, to get all xml files in a folder
 * 
 * @author Min Zheng
 *
 */
@Service
public class CmlFilesRequest implements ItemReader<List<File>> {

	@Value("${yoink.job.inputlocation}")
	private String inputlocation;

	protected static final Log log = LogFactory.getLog(CmlFilesRequest.class);

	private boolean running = true;

	private File[] files;

	/**
	 * get all xml files in the input directory
	 * 
	 * @return files, a list of files
	 */
	@Override
	public List<File> read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {

		while (running) {

			File dir = new File(inputlocation);
			log.info("the input files are in: "+dir.getAbsolutePath());
			FileFilter fileFilter = new WildcardFileFilter("*.xml");
			files = dir.listFiles(fileFilter);
			log.info("get all  " + files.length + " requests ");
			// if not find xml files, go to sleep
			if (files.length == 0) {
				Thread.sleep(1000);
			} else
				break;// if find xml files, go to execute those requests
		}
		return Arrays.asList(files);
	}

}

