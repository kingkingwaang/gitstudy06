package testing;

public class Execute {
	
	public static void main(String[] args) throws Exception {
		String command = args[0];   
		String path = args[1];
		String sentence = args[2];
		
		if(command.equals("-f")) {
			Midterm m = new Midterm(path, sentence);
			m.sorting();
		}
	}
}
