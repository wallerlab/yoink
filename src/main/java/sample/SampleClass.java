package sample;
import java.io.IOException;
import java.io.InputStream;

import org.uncommons.maths.Maths;


//This should be deprecated
@Deprecated
public class SampleClass {

    public static String yoinkTest() {
	// Run a java app in a separate system process
	Process proc = null;
	try {
		proc = Runtime.getRuntime().exec("java -jar ./examples/Yoink-0.0.1.jar");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("waiting for yoink");		
	try {
		proc.waitFor();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("Yoink done");

			
	// Then retreive the process output
	InputStream in = proc.getInputStream();
	InputStream err = proc.getErrorStream();
	return "done";
    }


}
