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

package org.wallerlab.yoink.processor;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;
import org.wallerlab.yoink.Yoink;

public class TestYoink {

	@Test
	public void testYoink() throws Exception {

		File files_in = new File("./inputs");
		File files_out = new File("./outputs");
		deleteDirectory(files_in);
		deleteDirectory(files_out);
		new File("./outputs").mkdirs();
		Process p = Runtime.getRuntime().exec(
				"cp -r ./src/integration-test/resources/inputs ./inputs");
		Yoink yoink = new Yoink();
		String[] args = new String[1];
		args[0] = "integration test- run batch version";
		yoink.main(args);
		try {

			File input_dir = new File("./inputs");
			File[] input_files = input_dir.listFiles((d, name) -> name.endsWith(".xml"));
			
			File output_dir = new File("./outputs");
			File[] output_files = output_dir.listFiles((d, name) -> name.endsWith(".xml"));
			if (input_files.length > 0 && output_files.length > 0) {
				Assert.assertEquals(input_files.length,
						output_files.length);
				System.out.println("pass integration test");
				deleteDirectory(files_in);
				deleteDirectory(files_out);
			} else {
				throw new Exception("example test failed");
			}

		} catch (SecurityException | IllegalArgumentException e) {
			e.printStackTrace();

		}
	}

	/* * Right way to delete a non empty directory in Java */
	private boolean deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirectory(children[i]);
				if (!success) {
					return false;
				}
			}
		} // either file or an empty directory
		System.out.println("removing file or directory : " + dir.getName());
		return dir.delete();
	}
}
