/****************************************************
 * Kevin Jimenez 
 * The University of Texas at El Paso
 * CS2302 Data Structures
 * Lab 7
 * Professor Olac Fuentes
 ****************************************************/


import java.util.*;

//Main class to generate the maze
public class Maze {
	//Following code was provided by Dr Fuentes
	private int N;                 // dimension of maze
	private boolean[][] north;     
	private boolean[][] east;
	private boolean[][] south;
	private boolean[][] west;


	public static void main(String[] args) {
		//provided by Dr. Fuentes
		int N = 50; // Maze will have 25 rows and 25 columns
		Maze maze = new Maze(N);
		StdDraw.show(0);

		int [] S = new int [N*N];//Generate and initialize a disjoint set array
		for(int i =0; i< S.length; i++){
			S[i] = -1;       
		}	
		String s = "NSEW";//Created a string with North South East and West first letter initial to use later to randomize

		Random random = new Random();//create random variables chosing char string
		Random num = new Random();// generate random x and y

		while (maze.numsets(S) > 1){
			int index = random.nextInt(s.length());//Generates random number to use in following loop
			char d = s.charAt(index);
			int xx = num.nextInt(N)+1;//Randomizes the x and y
			int yy = num.nextInt(N)+1;

			maze.remove(S,xx,yy,d,N); //The maze calls the method to remove

		}

		maze.draw();//draw maze

	}


	public int find(int [] F, int i){//find root at index i

		if (F[i] == -1){
			return i;
		}
		return F[i]=find(F, F[i]);
	}

	// counts how many roots or how many disjoint sets there are in the maze
	public int numsets(int [] D){
		int count=0;
		for(int i = 0;i < D.length;i++){
			if(D[i]<0){
				count++;
			}
		}
		return count;    

	}

	public void union(int FS[], int i, int k){//join two sets that are being compared
		int ri = find(FS, i);
		int rk = find (FS, k);
		if(ri != rk){
			FS[rk] = ri;
		}
	}


	public Maze(int N) {//code given by Dr. Fuentes
		this.N = N;
		StdDraw.setXscale(0, N+2);
		StdDraw.setYscale(0, N+2);
		init();
	}

	private void init() {// creates grid of units with all walls
		// notice that each wall is stored twice 
		north = new boolean[N+2][N+2];
		east  = new boolean[N+2][N+2];
		south = new boolean[N+2][N+2];
		west  = new boolean[N+2][N+2];
		for (int x = 0; x < N+2; x++)
			for (int y = 0; y < N+2; y++)
				north[x][y] = east[x][y] = south[x][y] = west[x][y] = true;
	}


	public  void remove( int A[] , int x, int y, char d , int n){
		int orig = (y-1)*n+(x-1);
		int dest;

		if ((d=='N') && (y<n)){//checks if they are in the same set
			dest = orig+n; 
			if(find(A,orig) != find(A,dest)){//if not take away wall and join destination to bigset
				union(A,orig,dest);
				north[x][y] = south[x][y+1] = false;
			}
		}

		else if ((d=='S')&& (y>1)){//checks if they are in the same set
			dest = orig-n;
			if(find(A,orig) != find(A,dest)){//if not take away wall and join destination to bigset
				union(A,orig,dest);
				south[x][y] = north[x][y-1] = false;
			}
		}

		else if ((d=='W')&&(x>1)){//checks if they are in the same set
			dest = orig-1;
			if(find(A,orig) != find(A,dest)){//if not take away wall and join destination to bigset
				union(A,orig,dest);
				west[x][y] = east[x-1][y] = false;
			}
		}

		else if ((d=='E')&& (x<n)){//check if they are in the same set
			dest = orig+1;
			if(find(A,orig) != find(A,dest)){
				union(A,orig,dest);
				east[x][y] = west[x+1][y] = false;//if not take away wall

			}
		}

	} 

	public void draw() {//display maze and red dots
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.filledCircle(N + 0.5, N + 0.5, 0.375);
		StdDraw.filledCircle(1.5, 1.5, 0.375);

		StdDraw.setPenColor(StdDraw.BLACK);
		for (int x = 1; x <= N; x++) {
			for (int y = 1; y <= N; y++) {
				if (south[x][y]) StdDraw.line(x, y, x + 1, y);
				if (north[x][y]) StdDraw.line(x, y + 1, x + 1, y + 1);
				if (west[x][y])  StdDraw.line(x, y, x, y + 1);
				if (east[x][y])  StdDraw.line(x + 1, y, x + 1, y + 1);
			}
		}
		StdDraw.show(0);
	}


}


