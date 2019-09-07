package game;

final public class FPSCounter {
	
	private static int startTime;
	private static int endTime;
	private static int frameTimes = 0;
	private static short frames = 0;
	public static String fps = "0";
	
	public final static void StartCounter() {
		startTime = (int) System.currentTimeMillis();
		
	}
	
	public final static void StopAndPost() {
		
		endTime = (int) System.currentTimeMillis();
		
		frameTimes = frameTimes + endTime - startTime;
		++frames;
		
		
		if(frameTimes >= 100) {
			fps = Long.toString(frames*10);
			frames = 0;
			frameTimes = 0;
		}
	}
}
