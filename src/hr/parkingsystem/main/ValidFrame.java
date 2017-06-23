package hr.parkingsystem.main;

public class ValidFrame {
	
	private static boolean[] poljeSlika = new boolean[50];
	private static int pozicija = 0;
	
	public static void valid(boolean pronaden){
		poljeSlika[pozicija] = pronaden;
		pozicija++;
		if (pozicija>=poljeSlika.length-1){
			pozicija=0;
		}
	}
	
	private static int validFrames = 0;
	public static boolean getValidator(){
		validFrames=0;
		for(int i =0;i<poljeSlika.length-1;i++){
			if (poljeSlika[i]==true){
				validFrames++;
			}
		}
		if (validFrames>=10){
			return true;
		}
		return false;
	}
}

