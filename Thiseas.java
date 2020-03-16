//Michalis Roussos 3150148
//Christos Tasiopoulos 3150170
import java.io.*;
import java.util.StringTokenizer;

public class Thiseas {
	// lines and columns of the (array) labyrinth
	int numlines = 0;
	int numcolumns = 0;
	// coordinates of the entrance
	int entranceline = 0;
	int entrancecolumn = 0;
	//checking the file's format
	boolean correctformat = true;
	
	char[][] labyrinth;

	public Thiseas() {
	}
	
	//method that prints the array's elements
	public void print() {
		System.out.println(numlines + " " + numcolumns);
		System.out.println(entranceline + " " + entrancecolumn);
		for (int k = 0; k < numlines; k++) {
			for (int j = 0; j < numcolumns; j++) {

				System.out.print(labyrinth[k][j]);
			}
			System.out.print("\n");
		}

	}
	//method that loads the file and stores the array,the entrance coordinates and the size of the array
	public void loadFile(String data) {	
		int Entrance = 0;
		boolean entrancecorrect=false;
		/*with the variables extrael and lessel 
		 we are checking the file's format whether it has more or less elememnts than required*/
		boolean extrael = false;
		boolean lessel = false;
		int counter = 0;
		BufferedReader reader = null;
		String line = " ";
		StringTokenizer tok;
		//try and catch block for successfully opening the file
		try {
			reader = new BufferedReader(new FileReader(data));

		} catch (FileNotFoundException e) {
			System.err.println("Error opening file!");
		}

		try {

			line = reader.readLine();
			while (line != null) {
				tok = new StringTokenizer(line);
				if (counter == 0) {
					//storing the array's size
					numlines = Integer.parseInt(tok.nextToken());
					numcolumns = Integer.parseInt(tok.nextToken());
					labyrinth = new char[numlines][numcolumns];
				} else if (counter == 1) {
					//storing the entrance's coordinates
					entranceline = Integer.parseInt(tok.nextToken());
					entrancecolumn = Integer.parseInt(tok.nextToken());

				} else {

					for (int i = 0; i < numcolumns; i++) {
						// in the else case the counter starts with the value of
						// 2. so we need to subtract 2 to get to column 0 of the
						// array.
						if (tok.hasMoreTokens()) {
							labyrinth[counter - 2][i] = tok.nextToken().charAt(
									0);
							//checking whether there is only one entrance
							if (labyrinth[counter - 2][i] == 'E') {
								Entrance++;
								entrancecorrect=true;
								
							}
							//checks the file's format
							if (labyrinth[counter - 2][i] != '0'
									&& labyrinth[counter - 2][i] != '1'
									&& labyrinth[counter - 2][i] != 'E') {
								correctformat = false;
								System.out.println("Wrong File Format!");
								return;
							}
						}

					}
					//the next two if blocks check whether there are extra elements to the array or not
					if ((int) labyrinth[counter - 2][numcolumns - 1] == 0) {
						lessel = true;
						break;
					}

					if (tok.hasMoreTokens()) {
						extrael = true;
						break;

					}

				}
				line = reader.readLine();
				counter++;
				//the next two if blocks check whether there are extra elements to the array or not
				if ((counter == numlines + 2) && (line != null)) {
					extrael = true;
					System.out.print(line);
					break;
				}

			}
			if ((int) labyrinth[numlines - 1][0] == 0) {
				lessel = true;
			}

		} catch (IOException e) {
			System.out.println("Error reading line " + counter + ".");
		}
		if (extrael || lessel || Entrance != 1 || entrancecorrect==false) {
			System.out.println("The file's structure is not right.");
			correctformat = false;
			return;
		}
	}
//
	public void findExits() {
		StringStackImpl<String> currentplace = new StringStackImpl<>();
		//storing the entrance coordinates
		int prevline = entranceline;
		int prevcol = entrancecolumn;
		//variable for the while loop 
		boolean continue1 = true;
		StringTokenizer tok;
		//variable to check whether Thiseas has reached a crossroad
		int paths;
		int line=entranceline;
		int	column=entrancecolumn;
		while (continue1) {
			paths=0;
			if (prevline!= 0) {//if block for moving upwards
				if(labyrinth[prevline-1][prevcol]=='0'){
						line--;
						labyrinth[prevline-1][prevcol]='X';
						paths++;
				}
			}
			if (prevline!= numlines - 1) {//if block for moving downwards
				if(labyrinth[prevline+1][prevcol]=='0'){
					if (paths==0){
						line++;
						labyrinth[prevline+1][prevcol]='X';
						paths++;
					}else if(paths>0){
						paths++;
					}
				}
			}
			if (prevcol != 0) {//if block for moving left
				if(labyrinth[prevline][prevcol-1]=='0'){
					if (paths==0){
						column--;
						labyrinth[prevline][prevcol-1]='X';
						paths++;
						
					}
					else if(paths>0){
						paths++;
						
						
					}
					
				}
			}
			if (prevcol != numcolumns - 1) {//if block for moving right
				if(labyrinth[prevline][prevcol+1]=='0'){
					if (paths==0){
						column++;
						labyrinth[prevline][prevcol+1]='X';
						paths++;
						
					}
					else if(paths>0){
						paths++;
					}
				}
			}
			//if block for checking whether there is an exit
			if (paths==0){
				//there is no exit
				if (currentplace.isEmpty()){
					System.out.println("There is no exit!");
					break;
				}
				tok=new StringTokenizer(currentplace.pop());
				line=Integer.parseInt(tok.nextToken(","));
				column=Integer.parseInt(tok.nextToken(","));
				//print the exit
			}else if(paths>1){
				currentplace.push(prevline +"," +prevcol);
			}
			prevline=line;
			prevcol=column;
			//checking for exiting the while loop
			if(((line==numlines-1) || (column==numcolumns-1) || (line==0) || (column==0))){
				
				 continue1=false;
			 }
		}
		if (continue1==false){
			System.out.println("The exit is: ("+line+","+column+")");
		}
	}
//main class
	public static void main(String[] args) {
		Thiseas th = new Thiseas();
		th.loadFile(args[0]);
		if (th.correctformat) {
			th.print();
			th.findExits();
		}

	}

}
