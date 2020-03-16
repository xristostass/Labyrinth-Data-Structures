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