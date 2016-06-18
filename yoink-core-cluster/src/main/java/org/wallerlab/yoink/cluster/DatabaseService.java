package org.wallerlab.yoink.cluster;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.io.fs.FileUtils;

public class DatabaseService {

	private GraphDatabaseService graphDb;
	private static String DB_PATH;

	public GraphDatabaseService graphDb() {
		return graphDb;
	}

	static DatabaseService createAt(String path) {
		return new DatabaseService(path);
	}

	private DatabaseService(String path) {
		 if (path.isEmpty()){
		 throw new RuntimeException();
		 }
		DB_PATH = path;
	}

	public DatabaseService startDb() {
		
		
		File file = Paths.get(DB_PATH).toFile();
		
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(file);
		registerShutdownHook(graphDb);

		return this;

	}

	/**
	 * 
	 * Attention: clearDb() will delete everything under DB_PATH!
	 *
	 * @return DatabaseService instance after clearing
	 *
	 */
	protected DatabaseService clearDb() {
		try {
			FileUtils.deleteRecursively(new File(DB_PATH));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	public void shutdown() {
		graphDb.shutdown();
	}

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}

}
