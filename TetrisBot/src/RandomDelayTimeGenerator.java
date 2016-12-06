
public class RandomDelayTimeGenerator {
	
	/**
	 * Generates a value between x and y non-linearly
	 * */
	public static int generate(int x, int y){
		if(x < 0)
			return y;
		double rand = Math.random();
		int ans = x;
		
		y -= x;
		
		if(rand < 0.3){
			ans += rand/2*y;
		}else if(rand < 0.9){
			ans += rand*y;
		}else{
			ans += rand*y*2;
		}
		return ans;
	}
}
