package Map;
import java.math.*;
import java.util.Random;
public class GenerateRandomMap {
	
	int    map_width; 
	int    map_height;
    int    map[][];
    float  obstracle_num;
    
	public GenerateRandomMap(int width, int height, double d)
	{
		this.map_width     = width;
		this.map_height    = height;
		this.obstracle_num = (int)(d * width * height);
		this.map           = new int[this.map_height][this.map_width];
	}
	
	public int[][] GenerateMap()
	{
		
		for(int i = 0; i < this.map_height; i++)
		{
			for(int j = 0; j < this.map_width; j++)
			{
				this.map[i][j] = 0;
			}
		}
		
		for(int k = 0; k < this.obstracle_num; k++)
		{
			Random ran = new Random(k+10);
			Random ran2 = new Random(k+200);
			int y =  ran.nextInt(10);
			int x =  ran2.nextInt(10);
			this.map[x][y] = 1;
		}
		return this.map;
	}

	public static void main(String[] args) {
		
		GenerateRandomMap gpm = new GenerateRandomMap(10,10,0.1);
		
		int[][] m = gpm.GenerateMap();
		for(int i = 0; i < m.length; i++)
		{
			for(int j = 0; j < m[0].length; j++)
			{
				System.out.print(m[i][j]+" ");
			}
			System.out.println();
		}

	}

}
