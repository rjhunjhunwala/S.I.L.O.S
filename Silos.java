/*
 *Feel free to modify and distribute the code and all relevant documentation
* This code is provided as is and the author
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * S.I.L.O.S interpeter
 *
 * @author rohan
 */
public class Silos {

	/**
	 * This integer[] represents the heap of memory which can be addressed
	 */
	static int[] mem;
	static int line = 1;

	/**
	 * Prints out the prompt and then returns the string user types
	 *
	 * @param prompt the prompt for the user
	 * @return the string typed into the console
	 */
	public static String getStringFromUser(String prompt) {
		if (interactive) {
			System.out.println(prompt);
			Scanner sc = new Scanner(System.in);
			return sc.nextLine();
		} else {
			String in = arg[line];
			line++;
			return in;
		}
	}

	static class Input implements KeyListener {

		static int[] bindings;

		public static void setNewBindings(String[] bind) {
			bindings = new int[bind.length];
			for (int i = 0; i < bindings.length; i++) {
				bindings[i] = evalToken(bind[i]);
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			char c = e.getKeyChar();
			for (int i = 0; i < bindings.length - 1; i += 2) {
				if (c == bindings[i]) {
					//System.out.println(c + ":pressed!");
					mem[bindings[i + 1]] = 1;
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

	}
	static boolean interactive = true;
	static int[] vars;
	static String[] arg;

	/**
	 * The main interpretation code
	 *
	 * @param args the command line arguments to be passed from the online
	 * interpreter the first argument represents a fileName, and the rest
	 * represent a source of input Feeding in any number of command line arguments
	 * will disable interactivity
	 */
	public static void main(String[] args) {
		arg = args;
		Stack<Integer> stack = new Stack<>();
		String[] program = getWordsFromFile((interactive = (args.length == 0)) ? getStringFromUser("FileName?") : args[0]);
		int ptr = 0;
		int length = program.length;
		String line = null;
		try {
			mem = new int[Integer.parseInt(program[0])];
		}//allocates the memory on the first line of code
		catch (Exception ex) {
			mem = new int[8192];//default size if there is no allocation specified
		}
		String def = program[1];
		ArrayList<String> macro = new ArrayList<>();
		if (def.startsWith("def")) {
			String[] defs = def.split(" ");
			for (int i = 1/*yes 1!*/; i < defs.length; i++) {
				macro.add(defs[i]);
			}
		}
		for (int i = 0; i < program.length; i++) {
			if (!program[i].startsWith("def")) {
				String replace = program[i];
				for (int j = 0; j < macro.size(); j += 2) {
					replace = replace.replaceAll(macro.get(j), macro.get(j + 1));
				}
				program[i] = replace;
			}
		}
//for(String s:program)System.out.println(s);
		while (ptr < length) {
			try {
				line = program[ptr];
				String[] tokens = line.split(" ");
				if (line.startsWith("GOTO ")) {
					for (int i = 0; i < length; i++) {
						if (program[i].startsWith("lbl")) {
							if (program[i].substring(3).equals(tokens[1])) {
								ptr = i - 1;//we increment it later
							}
						}
					}
				} else if (line.startsWith("GOSUB ")) {
					stack.add(ptr);
					for (int i = 0; i < length; i++) {
						if (program[i].startsWith("func")) {
							if (program[i].substring(4).equals(tokens[1])) {
								ptr = i - 1;//we increment it later
							}
						}
					}
				} else if (line.startsWith("return")) {
					ptr = stack.pop();
				} else if (line.startsWith("printLine ")) {
					System.out.println(line.substring(10));
				} else if (line.startsWith("printChar ")) {
					System.out.print((char) evalToken(tokens[1]));
				} else if (line.startsWith("print ")) {
					System.out.print(line.substring(6));
				} else if (line.startsWith("printInt ")) {
					System.out.println(mem[tokens[1].charAt(0)]);
				} else if (tokens[0].length() == 1) {
					if (tokens[1].equals("=")) {
						mem[tokens[0].charAt(0)] = evalGetStatement(tokens);
					} else if ("*/-+%^|".contains(tokens[1])) {
						switch (tokens[1]) {
							case "*":
								mem[tokens[0].charAt(0)] *= evalToken(tokens[2]);
								break;
							case "/":
								mem[tokens[0].charAt(0)] /= evalToken(tokens[2]);
								break;
							case "-":
								mem[tokens[0].charAt(0)] -= evalToken(tokens[2]);
								break;
							case "+":
								mem[tokens[0].charAt(0)] += evalToken(tokens[2]);
								break;
							case "%":
								mem[tokens[0].charAt(0)] %= evalToken(tokens[2]);
								break;
							case "^":
								mem[tokens[0].charAt(0)] = (int) Math.pow(mem[tokens[0].charAt(0)], evalToken(tokens[2]));
								break;
							case "|":
								mem[tokens[0].charAt(0)] = (int) Math.abs(evalToken(tokens[0]));
								break;
						}
					}
				} else if (tokens[0].equals("set")) {
					mem[evalToken(tokens[1])] = evalToken(tokens[2]);
				} else if (tokens[0].equals("if")) {
					if (evalToken(tokens[1]) > 0) {
						for (int i = 0; i < program.length; i++) {
							try {
								if (program[i].substring(3).equals(tokens[2])) {
									ptr = i - 1;//we increment it later
								}
							} catch (Exception ex) {
								//my apologies for the kludge 
							}
						}

					}
				} else if (tokens[0].equals("readIO")) {
					mem['i'] = getIntFromUser(line.substring(7));
				} else if (tokens[0].equals("rand")) {
					mem['r'] = (int) (Math.random() * evalToken(tokens[1]));
				} else if (tokens[0].equals("loadLine")) {
					String in = getStringFromUser(line.substring(9));
					for (int i = 256; i < 256 + in.length(); i++) {
						mem[i] = in.charAt(i - 256);

					}
				} else if (tokens[0].equals("canvas")) {
					if (interactive) {
						Canvas.createCanvas(evalToken(tokens[1]), evalToken(tokens[2]), line.substring(line.lastIndexOf(" ")));
					}
				} else if (tokens[0].equals("wait")) {
					Thread.sleep(evalToken(tokens[1]));
				} else if (tokens[0].equals("draw") && Canvas.createdCanvas) {
					//System.out.println(Canvas.pen);
					int startX = evalToken(tokens[2]);
					int startY = evalToken(tokens[3]);
					int widthX = evalToken(tokens[4]);
					int widthY = evalToken(tokens[5]);
					double a = widthX * widthX / 4;
					double b = widthY * widthY / 4;
					boolean square = tokens[1].equals("square");
					for (int i = startX; i < startX + widthX; i++) {
						for (int j = startY; j < startY + widthY; j++) {
							int xC = i - (startX + widthX / 2);
							int yC = j - (startY + widthY / 2);
							if (square || ((xC * xC) / a) + ((yC * yC) / b) < 1) {
								 if(i>0&&j>0&&i<Canvas.canvas.length&&j<Canvas.canvas[i].length){
									Canvas.canvas[i][j] = Canvas.pen;
							}
							}
						}
					}
				} else if (tokens[0].equals("pen")) {
					if (Canvas.createdCanvas) {
						Canvas.pen = new Color(evalToken(tokens[1]), evalToken(tokens[2]), evalToken(tokens[3]));
					}
				} else if (tokens[0].equals("bind")) {
					Input.setNewBindings(Arrays.copyOfRange(tokens, 1, tokens.length));
				} else if (tokens[0].equals("refresh")) {
					if (Canvas.createdCanvas) {
						Canvas.c.repaint();
					}
				}
				ptr++;

			} catch (Exception ex) {
				ex.printStackTrace();
				//to enable warnings uncomment this warning
				System.err.println("Some error on line: " + ptr + " \"" + line + "\"");
				//praise vb "on error resume next"!
				ptr++;
			}
		}
		System.exit(0);
	}

	public static class Canvas extends JFrame {

		public static class Panel extends JPanel {

			public Panel(int inX, int inY) {
				x = inX;
				y = inY;
			}
			public int x, y;

			@Override
			public void paintComponent(Graphics g) {
				for (int i = 0; i < x; i++) {
					for (int j = 0; j < y; j++) {
						g.setColor(canvas[i][j]);
						g.fillRect(i, j, 1, 1);
					}
				}
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(x, y);
			}
		}
		public static Color[][] canvas;
		public static Color pen = Color.white;
		static boolean createdCanvas = false;
		public static Canvas c;

		private Canvas(int x, int y, String message) {
			super(message);
			this.setVisible(true);
			this.add(new Panel(x, y));
			this.pack();
			this.setAlwaysOnTop(true);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.addKeyListener(new Input());
		}

		public static void createCanvas(int x, int y, String message) {
			if (!createdCanvas) {
				createdCanvas = true;
				canvas = new Color[x][y];
				for (int i = 0; i < canvas.length; i++) {
					for (int j = 0; j < canvas[i].length; j++) {
						canvas[i][j] = Color.white;
					}
				}
				c = new Canvas(x, y, message);
			}
		}
	}

	/**
	 *
	 * @param fileName is the path to the file or just the name if it is local
	 * @return an array of Strings where each string is one line from the file
	 * fileName.
	 */
	public static String[] getWordsFromFile(String fileName) {
		int lengthOfFile = getLengthOfFile(fileName);
		String[] wordBank = new String[lengthOfFile];
		int i = 0;
		try {
			File textFile = new File(fileName);
			Scanner sc = new Scanner(textFile);
			for (i = 0; i < lengthOfFile; i++) {
				wordBank[i] = sc.nextLine();
			}
			return wordBank;
		} catch (Exception e) {
			System.err.println(e);
			System.exit(55);
		}
		return null;
	}

	/**
	 *
	 * @param fileName is the path to the file or just the name if it is local
	 * @return the number of lines in fileName
	 */
	public static int getLengthOfFile(String fileName) {
		int length = 0;
		try {
			File textFile = new File(fileName);
			Scanner sc = new Scanner(textFile);
			while (sc.hasNextLine()) {
				sc.nextLine();
				length++;
			}
		} catch (Exception e) {
		}
		return length;
	}

	/**
	 * gets an int from the user after printing prompt
	 *
	 * @param prompt the prompt for input
	 * @return an integer typed in by the user Note we do not check input validity
	 * here
	 */
	public static int getIntFromUser(String prompt) {
		return Integer.parseInt(getStringFromUser(prompt));
	}

	/**
	 * Evaluates a token by figuring out whether it is an integer literal or a
	 * variable
	 *
	 * @param token to evaluate
	 * @return the value of the token
	 */
	private static int evalToken(String token) {
		try {
			return Integer.parseInt(token);

		} catch (Exception e) {
			return mem[token.charAt(0)];
		}

	}

	/**
	 * Evaluates a get statement for array access
	 *
	 * @param tokens the list of tokens on the line
	 * @return the value located at the respective location in memory
	 */
	private static int evalGetStatement(String[] tokens) {
		try {
			return Integer.parseInt(tokens[2]);
		} catch (Exception e) {
			try {
				return mem[evalToken(tokens[3])];
			} catch (Exception ex) {
				return mem[tokens[2].charAt(0)];
			}
		}
	}

}
