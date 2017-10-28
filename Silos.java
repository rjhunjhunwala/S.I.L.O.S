package IDE;

/*
 * Feel free to modify and distribute the code and all relevant documentation
 * This code is provided as is and the author explicitly disclaims any warranty
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * S.I.L.O.S interpreter S.I.L.O.S or SIL is a simple interpreted, imperative
 * language
 *
 * @author rohan
 */
public class Silos {

	/**
	 * This string contains the source for the standard libraries. It contains four
	 * methods which are case sensitive Help -> Outputs useful help information to
	 * the console CosX returns 479001600*cos(x/10000) using a 12th order taylor
	 * polynomial receives input via the top of the zeroth stack and uses the m M
	 * and ? variables SinX returns sin(x/10000)*39916800 using an 11th order
	 * taylor polynomial Receives input via the top of the zeroth stack and uses
	 * the m M and ? variables returns to the top of the zeroth stack accurate to
	 * three significant figures from [-pi,pi] and Cat functions as a cat program
	 */
	public static final String[] stdlib = {"GOTO EndOfTheStandardLibraries", "/**", "* This is the source code presented for the standard libraries of SILOS", "* There is no need to download this file as it is attached with the release (in \"Silos.java\")", "* simply import this library by using \"leverage stdlib\"", "* To make a library of your own, follow the standards presented in this code. Most notably,", "* Start the code with a goto to skip all of the utility methods. ", "* Then, to import your code use \"leverage fileName.txt\" (replacing with the fully qualified file name)", "*/", "", "//Trig Methods", "", "/**", "* Sin method, returns sin(x/10000)*39916800 using an 11th order taylor polynomial", "* Receives input via the top of the zeroth stack and uses the m M and ? variables", "* returns to the top of the zeroth stack accurate to three significant figures from [-pi,pi]", "*/", "funcSinX", "    stackPop 0", "    m=(99792*m)/25-(2079*m^3)/312500000+(2079*m^5)/625000000000000000-(99*m^7)/125000000000000000000000000+(11*m^9)/100000000000000000000000000000000000-m^11/100000000000000000000000000000000000000000000 ", "    stack 0 m", "return", "", "/**", "* Cos method returns 479001600*cos(x/10000) using an 12th order taylor polynomial", "* receives input via the top of the zeroth stack and uses the m M and ? variables", "*/", "funcCosX", "    stackPop 0", "    m = 479001600*(1-m^2/200000000+m^4/240000000000000000-m^6/720000000000000000000000000+m^8/4032000000000000000000000000000000000-m^10/36288000000000000000000000000000000000000000000+m^12/479001600000000000000000000000000000000000000000000000000)", "    stack 0 m", "return", "/**", "* Echo out the first line of standard input uses m and M as a variable", "*/", "funcCat", "    loadLine", "    m=256", "    M=get m", "    lblTOPOFCATLOOP", "        printChar M", "        m+1", "        M =get m", "    if M TOPOFCATLOOP", "return", "//String manipulation Functions V", "", "//This area has been allocated for string manipulation functions", "", "//End of String manipulation Functions ^", "", "//This method will help new users by providing documentation", "funcHelp", "printLine GitHub for this language", "printLine https://github.com/rjhunjhunwala/S.I.L.O.S", "printLine IDE for this language", "printLine https://github.com/rjhunjhunwala/S.I.D.E", "return", "lblEndOfTheStandardLibraries"};
//The number of stacks and queues the user may access
	public static final int SIZE = 32;

	//Initialize stacks
	public static final Stack<Integer>[] stacks = new Stack[SIZE];
	public static final ArrayDeque<Integer>[] q = new ArrayDeque[SIZE];

	/**
	 * This integer[] represents the heap of memory which can be addressed
	 */
	private static int[] mem;

	//Strings are stored in this array to avoid expensive runtime string operation
	private static String[] texts;

	//OPCODES
	private final static int GOTO = 0 << 8;
	private final static int GOSUB = 1 << 8;
	private final static int RETURN = 2 << 8;
	private final static int PRINTLINE = 3 << 8;
	private final static int PRINTCHAR = 4 << 8;
	private final static int PRINT = 5 << 8;
	private final static int PRINTINT = 6 << 8;
	private final static int SET = 7 << 8;
	private final static int GET = 8 << 8;
	private final static int IF = 9 << 8;
	private final static int READIO = 10 << 8;
	private final static int RAND = 11 << 8;
	private final static int LOADLINE = 12 << 8;
	private final static int CANVAS = 13 << 8;
	private final static int WAIT = 14 << 8;
	private final static int UNDOCOPCODE = 15 << 8;
	private final static int PEN = 16 << 8;
	private final static int BIND = 17 << 8;
	private final static int REFRESH = 18 << 8;
	private final static int ADD = 19 << 8;
	private final static int SUB = 20 << 8;
	private final static int MUL = 21 << 8;
	private final static int DIV = 22 << 8;
	private final static int MOD = 23 << 8;
	private final static int ABS = 24 << 8;
	private final static int POW = 25 << 8;
	private final static int AND = 26 << 8;
	private final static int OR = 27 << 8;
	private final static int XOR = 28 << 8;
	private final static int XNOR = 29 << 8;
	private final static int NOT = 30 << 8;
	private final static int LSHIFT = 31 << 8;
	private final static int RSHIFT = 32 << 8;
	private final static int ASSIGN = 33 << 8;
	private final static int NEWOBJ = 34 << 8;
	private static final int MOVEOBJ = 35 << 8;
	private static final int PRINTINTNOLINE = 36 << 8;
	private static final int STACK = 37 << 8;
	private static final int QUEUE = 38 << 8;
	private static final int STACK_POP = 39 << 8;
	private static final int QUEUE_POP = 40 << 8;
	private static final int POP_UP = 41 << 8;
	private static final int GET_STRING_POP_UP = 42 << 8;
 private static final int ALLOC = 43 << 8;
	private static final int clear = 44 << 8;
	
	
	//MODES
	private final static int INTEGER = 0;
	private final static int VARIABLE = 1;
	private final static int PARSABLE = 2;


	/**
	 * Prints out the prompt and then returns the string user types
	 *
	 * @param prompt the prompt for the user
	 * @return the string typed into the console
	 */
	public static String getStringFromSTDIN(String prompt, Scanner sc) {
		System.out.println(prompt);
		String line = sc.nextLine();
		return line;
	}

	/**
	 * Takes in an arrayList of Strings and parses it to an array list of doubles.
	 * It treats "\" as an escape for the minus sign
	 */
	private static ArrayList<Double> parseToDoubles(ArrayList<String> arrayList) {
		ArrayList<Double> ret = new ArrayList<>();
		for (String s : arrayList) {
			try {
				ret.add(Double.parseDouble(s.replaceAll("rand", Math.random() + "").replaceAll("\\Q\\\\E", "-").replaceAll(" ", "")));
			} catch (Exception ex) {
				ret.add(mem[s.charAt(0)] / 1.0);
			}
		}
		return ret;
	}

	/**
	 * Allows Programs to have (somewhat) asynchronous listening to keyboard input
	 */
	static class Input implements KeyListener {

		static int[] bindings = new int[]{};

		public static void setNewBindings(int[] bind) {
			bindings = new int[bind.length / 2 - 1];
			for (int i = 0; i < bindings.length; i++) {
				bindings[i] = evalToken(bind[i * 2 + 1], bind[i * 2 + 2], 0);
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			char c = e.getKeyChar();
			for (int i = 0; i < bindings.length; i += 2) {
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

	static class Drawable {

		int x, y;
		int type;
		int width;
		int height;
		Color color = Canvas.pen;

		public Drawable(int inType, int inWidth, int inHeight) {
			type = inType;
			width = inWidth;
			height = inHeight;
		}
	}

//Files with this name are being invoked from the IDE
	public static String IDEFileName = "234567890-45678900ihb567890oijhb213dsa_TempFILE_v";

	/**
	 * The main interpretation code
	 *
	 * @param args the command line arguments to be passed from the online
	 * interpreter the first argument represents a fileName, and the rest represent
	 * a source of input Feeding in any number of command line arguments will
	 * generally disable interactivity.
	 */
	public static void main(String... args) {
		for (int i = 0; i < SIZE; i++) {
			q[i] = new ArrayDeque<Integer>();
			stacks[i] = new Stack<Integer>();
		}
		Stack<Integer> stack = new Stack<>();
		Scanner sc = new Scanner(System.in);
		boolean interactive = (args.length == 0);

		int[][] program = compile(args.length == 0 ? getStringFromSTDIN("FileName?", sc) : args[0]);
		if (args.length == 1) {
			if (args[0].equals(IDEFileName)) {
				interactive = true;
			}
		}

		int arg_index = 1;
		int ptr = 0;
		int length = program.length;
		int[] tokens = null;
		memory = mem;
//for(int[] p:program)System.out.println(java.util.Arrays.toString(p));
//for(String s:texts)System.out.println(s);
		while (ptr < length) {
			try {
				tokens = program[ptr];//System.out.println(mem['x']);System.out.println(ptr+" "+(tokens[0]>>8));
				switch (tokens[0] & ~0b11111111) {
					case GOTO:
						ptr = tokens[1];
						continue;
					case IF:
						ptr = evalToken(tokens[0], tokens[1], 0) > 0 ? tokens[2] : ptr + 1;
						continue;
					case GOSUB:
						stack.add(ptr);
						ptr = tokens[1];
						continue;
					case RETURN:
						ptr = stack.pop();
						break;
					case PRINTLINE:
						System.out.println(tokens.length > 1 ? texts[tokens[1]] : "");
						break;
					case POP_UP:

						if (Canvas.c != null) {
							JOptionPane.showMessageDialog(Canvas.c, (tokens.length > 1 ? texts[tokens[1]] : ""), Canvas.c.getTitle(), JOptionPane.INFORMATION_MESSAGE);
						}

						break;
					case GET_STRING_POP_UP:
						if (Canvas.c != null) {
							String temp = JOptionPane.showInputDialog(Canvas.c, (tokens.length > 1 ? texts[tokens[1]] : ""), Canvas.c.getTitle(), JOptionPane.INFORMATION_MESSAGE);
							char[] in = temp.toCharArray();
							for (int i = 0, j = 256; i < in.length; i++, j++) {
								mem[j] = in[i];
							}
						}
						break;
					case PRINTCHAR:
						System.out.print((char) evalToken(tokens[0], tokens[1], 0));
						break;
					case NEWOBJ:
						int type = evalToken(tokens[0], tokens[1], 0);
						int width = evalToken(tokens[0], tokens[2], 1);
						int height = evalToken(tokens[0], tokens[3], 2);
						Drawable next = new Drawable(type, width, height);
						Canvas.Panel.toDraw.add(next);
						break;
					case MOVEOBJ:
						int index = evalToken(tokens[0], tokens[1], 0);
						int x = evalToken(tokens[0], tokens[2], 1);
						int y = evalToken(tokens[0], tokens[3], 2);

						Drawable d = Canvas.Panel.toDraw.get(index);
						d.x = x;
						d.y = y;
						break;
					case PRINT:
						//potential no-op
						System.out.print(tokens.length > 1 ? texts[tokens[1]] : "");
						break;
					case PRINTINT:
						System.out.println(evalToken(Silos.VARIABLE, tokens[1], 0));
						break;
					case PRINTINTNOLINE:
						System.out.print(evalToken(Silos.VARIABLE, tokens[1], 0));
						break;
					case ASSIGN:
						if (((tokens[0] >> 1) & 1) == 1) {
							mem[tokens[1]] = (int) parse(texts[tokens[2]]);
						} else {
							mem[tokens[1]] = evalToken(tokens[0], tokens[2], 0);
						}
						break;
					case STACK:

						stacks[evalToken(tokens[0], tokens[1], 0)].push(evalToken(tokens[0], tokens[2], 1));

						break;
					case QUEUE:

						q[evalToken(tokens[0], tokens[1], 0)].add(evalToken(tokens[0], tokens[2], 1));

						break;
					case QUEUE_POP:
						mem['m'] = q[evalToken(tokens[0], tokens[1], 0)].isEmpty() ? 0 : q[evalToken(tokens[0], tokens[1], 0)].remove();
						break;
					case STACK_POP:
						mem['m'] = stacks[evalToken(tokens[0], tokens[1], 0)].isEmpty() ? 0 : stacks[evalToken(tokens[0], tokens[1], 0)].pop();
						break;
					case GET:
						mem[tokens[1]] = mem[evalToken(Silos.INTEGER, evalToken(tokens[0], tokens[2], 0), 0)];
						break;
					case ADD:
						mem[tokens[1]] += evalToken(tokens[0], tokens[2], 0);
						break;
					case SUB:
						mem[tokens[1]] -= evalToken(tokens[0], tokens[2], 0);
						break;
					case MUL:
						mem[tokens[1]] *= evalToken(tokens[0], tokens[2], 0);
						break;
					case DIV:
						mem[tokens[1]] /= evalToken(tokens[0], tokens[2], 0);
						break;
					case MOD:
						mem[tokens[1]] %= evalToken(tokens[0], tokens[2], 0);
						break;
					case POW:
						mem[tokens[1]] = (int) Math.pow(mem[tokens[1]], evalToken(tokens[0], tokens[2], 0));
						break;
					case ABS:
						mem[tokens[1]] = Math.abs(mem[tokens[1]]);
						break;
					case AND:
						mem[tokens[1]] &= evalToken(tokens[0], tokens[2], 0);
						break;
					case OR:
						mem[tokens[1]] |= evalToken(tokens[0], tokens[2], 0);
						break;
					case XOR:
						mem[tokens[1]] ^= evalToken(tokens[0], tokens[2], 0);
						break;
					case XNOR:
						mem[tokens[1]] ^= ~evalToken(tokens[0], tokens[2], 0);
						break;
					case NOT:
						mem[tokens[1]] = ~mem[tokens[1]];
						break;
					case LSHIFT:
						mem[tokens[1]] <<= evalToken(tokens[0], tokens[2], 0);
						break;
					case RSHIFT: //signed right-shift
						mem[tokens[1]] >>= evalToken(tokens[0], tokens[2], 0);
						break;
					case SET:
						mem[evalToken(tokens[0], tokens[1], 0)] = evalToken(tokens[0], tokens[2], 1);
						break;
					case READIO:
						if (interactive) {
							if (tokens.length > 1) {
								mem['i'] = Integer.parseInt(getStringFromSTDIN(texts[tokens[1]], sc));
							} else {
								mem['i'] = Integer.parseInt(sc.nextLine());
							}
						} else {
							mem['i'] = Integer.parseInt(args[arg_index++]);
						}
						break;
					case RAND:
						mem['r'] = (int) (Math.random() * evalToken(tokens[0], tokens[1], 0));
						break;
					case LOADLINE:
						char[] in;
						if (interactive) {
							if (tokens.length > 1) {
								in = getStringFromSTDIN(texts[tokens[1]], sc).toCharArray();
							} else {
								in = sc.nextLine().toCharArray();
							}
						} else {
							in = args[arg_index++].toCharArray();
						}
						for (int i = 0, j = 256; i < in.length; i++, j++) {
							mem[j] = in[i];
						}
						break;

					case CANVAS:
						if (interactive) {
							Canvas.createCanvas(
															evalToken(tokens[0], tokens[1], 0),
															evalToken(tokens[0], tokens[2], 1),
															texts[tokens[3]]
							);
						}
						break;
					case WAIT:
						Thread.sleep(evalToken(tokens[0], tokens[1], 0));
						break;

					case PEN:
						if (Canvas.createdCanvas) {
							Canvas.pen = new Color(
															evalToken(tokens[0], tokens[1], 0),
															evalToken(tokens[0], tokens[2], 1),
															evalToken(tokens[0], tokens[3], 2)
							);
						}
						break;
					case BIND:
						Input.setNewBindings(tokens);
						break;
					case REFRESH:
						if (Canvas.createdCanvas) {
							Canvas.c.repaint();
						}
						break;
				}
				ptr++;
			} catch (Exception ex) {
				ex.printStackTrace();
				//praise vb "on error resume next"!
				ptr++;
			}
		}
		System.exit(0);
	}

	/**
	 * GUI Class
	 */
	static class Canvas extends JFrame {

		static class Panel extends JPanel {

			public static ArrayList<Drawable> toDraw = new ArrayList<>();

			public Panel(int inX, int inY) {
				x = inX;
				y = inY;
			}
			public int x, y;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				for (Drawable d : toDraw) {
					g.setColor(d.color);
					if (d.type == 1) {
						g.fillOval(d.x, d.y, d.width, d.height);
					} else  if(d.type==0){
						g.fillRect(d.x, d.y, d.width, d.height);
					}else{
						g.drawLine(d.x, d.y, d.x+d.width, d.y+d.height);
					}
				}
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(x, y);
			}
		}

		public static Color pen = Color.black;
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
			this.setResizable(false);
		}

		public static void createCanvas(int x, int y, String message) {
			if (!createdCanvas) {
				createdCanvas = true;
				c = new Canvas(x, y, message);
					}
				}

			}
		
	

	/**
	 * Another way to temporarily hold the memory
	 */
	static int[] memory;

	/**
	 * Compile the program, and link other sources
	 *
	 * @param fileName
	 * @return A two dimensional integer array representing a parsed program
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public static int[][] compile(String fileName) {
		ArrayList<String> tokens = new ArrayList<>();
		try {
			File textFile = new File(fileName);
			Scanner sc = new Scanner(textFile);
			while (sc.hasNextLine()) {
				tokens.add(sc.nextLine().replaceAll("^\\s+", ""));
			}
			sc.close();
			List program = new ArrayList();
			String[] replace = new String[]{""};
			if (tokens.size() > 1 && tokens.get(1).startsWith("def")) {
				replace = tokens.remove(1).split(" ");
			}
			if (tokens.size() > 0) {
				try {
					mem = new int[Integer.parseInt(tokens.get(0))];
					tokens.remove(0);
				}//allocates the memory on the first line of code
				catch (Exception ex) {
					mem = new int[8192];//default size if there is no allocation specified
				}
			}

			int progSize = tokens.size();
			String[] stuff = new String[progSize];
			for (int i = 0; i < progSize; i++) {
				stuff[i] = tokens.get(i);
			}
			String[] replace2 = null;
			String t;
			for (String s : stuff) {
				String s1 = s;
				for (int j = 1; j < replace.length; j += 2) {
					s1 = s1.replaceAll(replace[j], replace[j + 1]);
				}
				String[] temp = s.split(" ");
				if (temp[0].equals("leverage")) {
					for (int i = 1; i < temp.length; i++) {
						if (!temp[i].equals("stdlib")) {
							textFile = new File(temp[i]);
							sc = new Scanner(textFile);
							while (sc.hasNextLine()) {
								t = sc.nextLine();
								t = t.replaceAll("^\\s+", "");
								if (t.startsWith("def")) {
									replace2 = t.split(" ");
								} else if (replace2 != null) {
									for (int j = 1; j < replace2.length; j += 2) {
										t = t.replaceAll(replace2[j], replace2[j + 1]);
									}
								}
								tokens.add(t);
							}
						} else {
							for (String line : stdlib) {
								tokens.add(line.replaceAll("^\\s+", ""));
							}
						}
					}
				}
			}
			ArrayList<String> labels = new ArrayList<>();
			ArrayList<Integer> label_pos = new ArrayList<>();
			ArrayList<String> label_temp = new ArrayList<>();
			ArrayList<String> funcs = new ArrayList<>();
			ArrayList<Integer> func_pos = new ArrayList<>();
			ArrayList<String> func_temp = new ArrayList<>();
			ArrayList<String> texts = new ArrayList<>();
			for (int i = 0; i < tokens.size(); i++) {
				String command = tokens.get(i);
				if (command.startsWith("def")
												|| command.startsWith("//")
												|| command.startsWith("#")
												|| command.startsWith("*")
												|| command.startsWith("/*")) {
					continue;
				}
				for (int j = 1; i < progSize && j < replace.length; j += 2) {
					command = command.replaceAll(replace[j], replace[j + 1]);
				}

				if (command.length() == 0) {
					continue;
				}
				String temp = command;
				if (command.startsWith("lbl")) {
					if (command.indexOf(' ') >= 0) {
						//GOTO only takes the first token
						//if label has space, it will never be accessed
						continue;
					}
					String label = command.substring(3);
					if (labels.contains(label)) {
						System.err.println("The label " + label + " is repeated in line " + (i + 1) + ".");
						//only use the first occurrence
						continue;
					}
					labels.add(label);
					label_pos.add(program.size());
					continue;
				}
				if (command.startsWith("func")) {
					if (command.indexOf(' ') >= 0) {
						//GOSUB only takes the first token
						//if function name has space, it will never be accessed
						continue;
					}
					String func = command.substring(4);
					if (funcs.contains(func)) {
						System.err.println("The function name " + func + " is repeated in line " + (i + 1) + ".");
						//only use the first occurrence
						continue;
					}
					funcs.add(func);
					func_pos.add(program.size());
					continue;
				}
				String command_clone = new String(command);
				command = command.replaceAll("\\s+", " ");
				if (command.length() > 1) {
					switch (command.charAt(1)) {
						case '+':
						case '-':
						case '*':
						case '/':
						case '%':
						case '^':
						case '=':
						case '|':
						case '&':
						case ':':
						case '!':
						case '?':
						case '~':
						case '<':
						case '>':
							command = command.charAt(0) + " " + command.substring(1);
							break;
					}
				}
				if (command.length() > 2) {
					switch (command.charAt(2)) {
						case '+':
						case '-':
						case '*':
						case '/':
						case '%':
						case '^':
						case '=':
						case '|':
						case '&':
						case ':':
						case '!':
						case '?':
						case '~':
						case '<':
						case '>':
							if (command.length() > 3 && command.charAt(3) != ' ') {
								command = command.substring(0, 3) + " " + command.substring(3);
							}
							break;
					}
				}
				String[] words = command.split(" ");
				if (words.length == 0) {
					continue;
				}

				String instruction = words[0];
				//control flows
				if (instruction.equals("GOTO")) {
					String label = words[1];
					int index = label_temp.indexOf(label);
					if (index == -1) {
						index = label_temp.size();
						label_temp.add(label);
					}
					program.add(new int[]{Silos.GOTO, index});
				}
				if (instruction.equals("GOSUB")) {
					String func = words[1];
					int index = func_temp.indexOf(func);
					if (index == -1) {
						System.err.println("Warning possible undeclared function. Could not find symbol:" + func + "\nThis could be because the function is undefined, or defined later.");
						index = func_temp.size();
						func_temp.add(func);
					}
					program.add(new int[]{Silos.GOSUB, index});
					continue;
				}

				//IO
				if (instruction.equals("readIO")) {
					if (command_clone.length() > 7) {
						String text = command_clone.substring(7);
						int index = texts.indexOf(text);
						if (index == -1) {
							index = texts.size();
							texts.add(text);
						}
						program.add(new int[]{Silos.READIO, index});
					} else {
						program.add(new int[]{Silos.READIO});
					}
					continue;
				}
				if (instruction.equals("loadLine")) {
					if (command_clone.length() > 9) {
						String text = command_clone.substring(9);
						int index = texts.indexOf(text);
						if (index == -1) {
							index = texts.size();
							texts.add(text);
						}
						program.add(new int[]{Silos.LOADLINE, index});
					} else {
						program.add(new int[]{Silos.LOADLINE});
					}
					continue;
				}
				if (instruction.equals("loadLinePopUp")) {
					if (command_clone.length() > 14) {
						String text = command_clone.substring(14);
						int index = texts.indexOf(text);
						if (index == -1) {
							index = texts.size();
							texts.add(text);
						}
						program.add(new int[]{Silos.GET_STRING_POP_UP, index});
					} else {
						program.add(new int[]{Silos.GET_STRING_POP_UP});
					}
					continue;
				}
				if (instruction.equals("prompt")) {
					if (command_clone.length() > 9) {
						String text = command_clone.substring(9);
						int index = texts.indexOf(text);
						if (index == -1) {
							index = texts.size();
							texts.add(text);
						}
						program.add(new int[]{Silos.GET_STRING_POP_UP, index});
					} else {
						program.add(new int[]{Silos.GET_STRING_POP_UP});
					}
					continue;
				}
				if (instruction.equals("print")) {
					if (command_clone.length() > 6) {
						String text = command_clone.substring(6);
						int index = texts.indexOf(text);
						if (index == -1) {
							index = texts.size();
							texts.add(text);
						}
						program.add(new int[]{Silos.PRINT, index});
					} else {
						program.add(new int[]{Silos.PRINT});
					}
					continue;
				}
				if (instruction.equals("popUp")) {
					if (command_clone.length() > 6) {
						String text = command_clone.substring(6);
						int index = texts.indexOf(text);
						if (index == -1) {
							index = texts.size();
							texts.add(text);
						}
						program.add(new int[]{Silos.POP_UP, index});
					} else {
						program.add(new int[]{Silos.POP_UP});
					}
					continue;
				}
				if (instruction.equals("alert")) {

					if (command_clone.length() > 6) {
						String text = command_clone.substring(6);
						int index = texts.indexOf(text);
						if (index == -1) {
							index = texts.size();
							texts.add(text);
						}

						program.add(new int[]{Silos.POP_UP, index});
					} else {
						program.add(new int[]{Silos.POP_UP});
					}
					continue;
				}

				if (instruction.equals("printLine")) {
					if (command_clone.length() > 10) {
						String text = command_clone.substring(10);
						int index = texts.indexOf(text);
						if (index == -1) {
							index = texts.size();
							texts.add(text);
						}
						program.add(new int[]{Silos.PRINTLINE, index});
					} else {
						program.add(new int[]{Silos.PRINTLINE});
					}
					continue;
				}

				//no arguments
				if (instruction.equals("return")) {
					program.add(new int[]{Silos.RETURN});
					continue;
				}
				if (instruction.equals("refresh")) {
					program.add(new int[]{Silos.REFRESH});
					continue;
				}

				//one argument
				if (instruction.equals("printChar")) {
					int mode;
					int argument;
					try {
						argument = Integer.parseInt(words[1]);
						mode = Silos.INTEGER;
					} catch (Exception e) {
						argument = (int) words[1].charAt(0);
						mode = Silos.VARIABLE;
					}
					program.add(new int[]{Silos.PRINTCHAR + mode, argument});
					continue;
				}
				if (instruction.equals("printInt")) {
					program.add(new int[]{Silos.PRINTINT + Silos.VARIABLE, (int) words[1].charAt(0)});
					continue;
				}
				if (instruction.equals("printIntNoLine")) {
					program.add(new int[]{Silos.PRINTINTNOLINE + Silos.VARIABLE, (int) words[1].charAt(0)});
					continue;
				}
				if (instruction.equals("stackPop")) {
					int mode;
					int argument;
					try {
						argument = Integer.parseInt(words[1]);
						mode = Silos.INTEGER;
					} catch (Exception e) {
						argument = (int) words[1].charAt(0);
						mode = Silos.VARIABLE;
					}
					program.add(new int[]{Silos.STACK_POP + mode, argument});
					continue;
				}
				if (instruction.equals("queuePop")) {
					int mode;
					int argument;
					try {
						argument = Integer.parseInt(words[1]);
						mode = Silos.INTEGER;
					} catch (Exception e) {
						argument = (int) words[1].charAt(0);
						mode = Silos.VARIABLE;
					}
					program.add(new int[]{Silos.QUEUE_POP + mode, argument});
					continue;
				}

				if (instruction.equals("get")) {
					int mode;
					int argument;
					try {
						argument = Integer.parseInt(words[1]);
						mode = Silos.INTEGER;
					} catch (Exception e) {
						argument = (int) words[1].charAt(0);
						mode = Silos.VARIABLE;
					}
					program.add(new int[]{Silos.GET + mode, argument});
					continue;
				}
				if (instruction.equals("rand")) {
					int mode;
					int argument;
					try {
						argument = Integer.parseInt(words[1]);
						mode = Silos.INTEGER;
					} catch (Exception e) {
						argument = (int) words[1].charAt(0);
						mode = Silos.VARIABLE;
					}
					program.add(new int[]{Silos.RAND + mode, argument});
					continue;
				}
				if (instruction.equals("wait")) {
					int mode;
					int argument;
					try {
						argument = Integer.parseInt(words[1]);
						mode = Silos.INTEGER;
					} catch (Exception e) {
						argument = (int) words[1].charAt(0);
						mode = Silos.VARIABLE;
					}
					program.add(new int[]{Silos.WAIT + mode, argument});
					continue;
				}

				//two arguments
				if (instruction.equals("if")) {
					int mode;
					int check;
					try {
						check = Integer.parseInt(words[1]);
						mode = Silos.INTEGER;
					} catch (Exception e) {
						check = (int) words[1].charAt(0);
						mode = Silos.VARIABLE;
					}
					String label = words[2];
					int index = label_temp.indexOf(label);
					if (index == -1) {
						index = label_temp.size();
						label_temp.add(label);
					}
					program.add(new int[]{Silos.IF + mode, check, index});
					continue;
				}
				if (instruction.equals("set")) {
					int mode1, mode2;
					int arg1, arg2;
					try {
						arg1 = Integer.parseInt(words[1]);
						mode1 = Silos.INTEGER;
					} catch (Exception e) {
						arg1 = (int) words[1].charAt(0);
						mode1 = Silos.VARIABLE;
					}
					try {
						arg2 = Integer.parseInt(words[2]);
						mode2 = Silos.INTEGER;
					} catch (Exception e) {
						arg2 = (int) words[2].charAt(0);
						mode2 = Silos.VARIABLE;
					}
					program.add(new int[]{Silos.SET + (mode2 << 1) + (mode1), arg1, arg2});
					continue;
				}
				if (instruction.equals("stack")) {
					int mode1, mode2;
					int arg1, arg2;
					try {
						arg1 = Integer.parseInt(words[1]);
						mode1 = Silos.INTEGER;
					} catch (Exception e) {
						arg1 = (int) words[1].charAt(0);
						mode1 = Silos.VARIABLE;
					}
					try {
						arg2 = Integer.parseInt(words[2]);
						mode2 = Silos.INTEGER;
					} catch (Exception e) {
						arg2 = (int) words[2].charAt(0);
						mode2 = Silos.VARIABLE;
					}
					program.add(new int[]{Silos.STACK + (mode2 << 1) + (mode1), arg1, arg2});
					continue;
				}
				if (instruction.equals("queue")) {
					int mode1, mode2;
					int arg1, arg2;
					try {
						arg1 = Integer.parseInt(words[1]);
						mode1 = Silos.INTEGER;
					} catch (Exception e) {
						arg1 = (int) words[1].charAt(0);
						mode1 = Silos.VARIABLE;
					}
					try {
						arg2 = Integer.parseInt(words[2]);
						mode2 = Silos.INTEGER;
					} catch (Exception e) {
						arg2 = (int) words[2].charAt(0);
						mode2 = Silos.VARIABLE;
					}
					program.add(new int[]{Silos.QUEUE + (mode2 << 1) + (mode1), arg1, arg2});
					continue;
				}

				//three arguments
				if (instruction.equals("canvas")) {
					int mode1, mode2;
					int arg1, arg2;
					try {
						arg1 = Integer.parseInt(words[1]);
						mode1 = Silos.INTEGER;
					} catch (Exception e) {
						arg1 = (int) words[1].charAt(0);
						mode1 = Silos.VARIABLE;
					}
					try {
						arg2 = Integer.parseInt(words[2]);
						mode2 = Silos.INTEGER;
					} catch (Exception e) {
						arg2 = (int) words[2].charAt(0);
						mode2 = Silos.VARIABLE;
					}
					String text = words[words.length - 1];
					int index = texts.indexOf(text);
					if (index == -1) {
						index = texts.size();
						texts.add(text);
					}
					program.add(new int[]{Silos.CANVAS + (mode2 << 1) + (mode1), arg1, arg2, index});
					continue;
				}
				if (instruction.equals("pen")) {
					int mode1, mode2, mode3;
					int arg1, arg2, arg3;
					try {
						arg1 = Integer.parseInt(words[1]);
						mode1 = Silos.INTEGER;
					} catch (Exception e) {
						arg1 = (int) words[1].charAt(0);
						mode1 = Silos.VARIABLE;
					}
					try {
						arg2 = Integer.parseInt(words[2]);
						mode2 = Silos.INTEGER;
					} catch (Exception e) {
						arg2 = (int) words[2].charAt(0);
						mode2 = Silos.VARIABLE;
					}
					try {
						arg3 = Integer.parseInt(words[3]);
						mode3 = Silos.INTEGER;
					} catch (Exception e) {
						arg3 = (int) words[3].charAt(0);
						mode3 = Silos.VARIABLE;
					}
					program.add(new int[]{Silos.PEN + (mode3 << 2) + (mode2 << 1) + (mode1), arg1, arg2, arg3});
					continue;
				}
				if (instruction.equals("newObj")) {
					int mode1, mode2, mode3;
					int arg1, arg2, arg3;
					try {
						arg1 = Integer.parseInt(words[1]);
						mode1 = Silos.INTEGER;
					} catch (Exception e) {
						arg1 = (int) words[1].charAt(0);
						mode1 = Silos.VARIABLE;
					}
					try {
						arg2 = Integer.parseInt(words[2]);
						mode2 = Silos.INTEGER;
					} catch (Exception e) {
						arg2 = (int) words[2].charAt(0);
						mode2 = Silos.VARIABLE;
					}
					try {
						arg3 = Integer.parseInt(words[3]);
						mode3 = Silos.INTEGER;
					} catch (Exception e) {
						arg3 = (int) words[3].charAt(0);
						mode3 = Silos.VARIABLE;
					}
					program.add(new int[]{Silos.NEWOBJ + (mode3 << 2) + (mode2 << 1) + (mode1), arg1, arg2, arg3});
					continue;
				}
				if (instruction.equals("moveObj")) {
					int mode1, mode2, mode3;
					int arg1, arg2, arg3;
					try {
						arg1 = Integer.parseInt(words[1]);
						mode1 = Silos.INTEGER;
					} catch (Exception e) {
						arg1 = (int) words[1].charAt(0);
						mode1 = Silos.VARIABLE;
					}
					try {
						arg2 = Integer.parseInt(words[2]);
						mode2 = Silos.INTEGER;
					} catch (Exception e) {
						arg2 = (int) words[2].charAt(0);
						mode2 = Silos.VARIABLE;
					}
					try {
						arg3 = Integer.parseInt(words[3]);
						mode3 = Silos.INTEGER;
					} catch (Exception e) {
						arg3 = (int) words[3].charAt(0);
						mode3 = Silos.VARIABLE;
					}
					program.add(new int[]{Silos.MOVEOBJ + (mode3 << 2) + (mode2 << 1) + (mode1), arg1, arg2, arg3});
					continue;
				}
				
				//n arguments 
				if (instruction.equals("bind")) {
					int[] bindings = new int[words.length * 2 + 1];
					bindings[0] = Silos.BIND;
					for (int j = 1; j < words.length; j++) {
						int mode, argument;
						try {
							argument = Integer.parseInt(words[j]);
							mode = Silos.INTEGER;
						} catch (Exception e) {
							argument = (int) words[j].charAt(0);
							mode = Silos.VARIABLE;
						}
						bindings[j * 2 - 1] = mode;
						bindings[j * 2] = argument;
					}
					program.add(bindings);
				}

				//arithmetics (and assignments)
				if (instruction.length() > 1) {
					continue;
				}
				if (words[1].length() > 1) {
					continue;
				}
				int instr = -1;
				int mode = -1;
				int arg1 = (int) instruction.charAt(0);
				int arg2 = -1;
				switch (words[1].charAt(0)) {
					case '+':
						instr = Silos.ADD;
						break;
					case '-':
						instr = Silos.SUB;
						break;
					case '*':
						instr = Silos.MUL;
						break;
					case '/':
						instr = Silos.DIV;
						break;
					case '%':
						instr = Silos.MOD;
						break;
					case '^':
						instr = Silos.POW;
						break;
					case '|':
						instr = Silos.ABS;
						break;
					case '=':
						instr = Silos.ASSIGN;
						break;
					case '&':
						instr = Silos.AND;
						break;
					case ':':
						instr = Silos.OR;
						break;
					case '!':
						instr = Silos.XOR;
						break;
					case '?':
						instr = Silos.XNOR;
						break;
					case '~':
						instr = Silos.NOT;
						break;
					case '<':
						instr = Silos.LSHIFT;
						break;
					case '>':
						instr = Silos.RSHIFT;
						break;
				}
				if (instr >= 0) {
					if (instr == Silos.ABS || instr == Silos.NOT) {
						program.add(new int[]{instr, arg1});
					} else if (instr == Silos.ASSIGN) {
//If the last phrase is a mathematical expression
						String s;
						if ((s = temp.substring(temp.lastIndexOf('=') + 1).replaceAll(" ", "")).matches("(.*)[\\Q()*%-+/^\\E](.*)") || words[2].contains("rand")) {
							arg2 = texts.size();
							texts.add(s);
							mode = Silos.PARSABLE;
//go into parsable mode
						} else {
							try {
								arg2 = Integer.parseInt(words[2]);
								mode = Silos.INTEGER;
							} catch (Exception e) {
								try {
									try {
										arg2 = Integer.parseInt(words[3]);
										mode = Silos.INTEGER;
									} catch (Exception ex) {
										arg2 = (int) words[3].charAt(0);
										mode = Silos.VARIABLE;
									}
									instr = Silos.GET;
								} catch (Exception ex) {
									arg2 = (int) words[2].charAt(0);
									mode = Silos.VARIABLE;
								}
							}
						}
						program.add(new int[]{instr + mode, arg1, arg2});
					} else {
						try {
							arg2 = Integer.parseInt(words[2]);
							mode = Silos.INTEGER;
						} catch (Exception e) {
							arg2 = (int) words[2].charAt(0);
							mode = Silos.VARIABLE;
						}
						program.add(new int[]{instr + mode, arg1, arg2});
					}
				}
			}
			//labels and function names
			for (int i = 0; i < program.size(); i++) {
				int[] instruction = (int[]) program.get(i);
				String label;
				int index;
				switch (instruction[0] & ~0b11111111) {
					case GOTO:
						label = label_temp.get(instruction[1]);
						index = labels.indexOf(label);
						if (index >= 0) {
							instruction[1] = label_pos.get(index);
						} else {
							instruction[1] = i;
						}
						break;
					case IF:
						label = label_temp.get(instruction[2]);
						index = labels.indexOf(label);
						if (index >= 0) {
							instruction[2] = label_pos.get(index);
						} else {
							instruction[2] = i;
						}
						break;
					case GOSUB:
						label = func_temp.get(instruction[1]);
						index = funcs.indexOf(label);
						if (index >= 0) {
							instruction[1] = func_pos.get(index);
						} else {
							instruction[1] = i;
						}
						break;
				}
			}
			Silos.texts = texts.toArray(new String[texts.size()]);
			return (int[][]) program.toArray(new int[program.size()][]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String program = "";

	/**
	 * Evaluates a token by figuring out whether it is an integer literal or a
	 * variable
	 *
	 * @param mode, argument, index
	 * @return the value of the token
	 */
	private static int evalToken(int mode, int argument, int index) {
		return ((mode >> index) & 1) == VARIABLE ? mem[argument] : argument;
	}

	public static double parse(String toParse) {

		try {
			return Double.parseDouble(toParse);
		} catch (Exception ex) {
			return (parser(toParse));
		}
	}

	/**
	 * Evaluates a mathematical expression Currently supported operators (+-//*%^)
	 * addition subtraction multiplication division (/) modulus and exponentiation
	 *
	 * @param toParse
	 * @return
	 */
	public static double parser(String toParse) {
		toParse = toParse.replaceAll(" ", "");
		Stack<Integer> match = new Stack<>();
		int[] matchMap = new int[toParse.length()];

		for (int i = 0; i < toParse.length(); i++) {
			if (toParse.charAt(i) == '(') {
				match.push(i);
			} else if (toParse.charAt(i) == ')') {
				int temp = match.pop();
				matchMap[i] = temp;
				matchMap[temp] = i;
			} else {
				matchMap[i] = -1;
			}
		}
		int firstIndex = -1;
		for (int i = 0; i < matchMap.length; i++) {
			if (matchMap[i] >= 0) {
				firstIndex = i;
				break;
			}
		}
		if (firstIndex >= 0) {
			String s = parse(toParse.substring(firstIndex + 1, matchMap[firstIndex])) + "";
			s = Double.parseDouble(s) > 0 ? s : "\\" + s.substring(1);
			return parse(toParse.substring(0, firstIndex) + s + toParse.substring(matchMap[firstIndex] + 1));
		} else {

			ArrayList<Double> num = parseToDoubles(new ArrayList<>(Arrays.asList(toParse.split("[\\Q+-^*%/\\E]"))));

			ArrayList<Character> operations = new ArrayList<>();

			for (char c : toParse.toCharArray()) {
				if ("^*/%+-".contains(c + "")) {
					operations.add(c);
				}
			}

			if (toParse.charAt(0) == '-') {
				num.set(0, num.get(0) * -1);
				operations.remove(0);
			}
			if (operations.isEmpty()) {
				return num.get(0);
			}
//pemdas, comes back to bite me
			for (int i = 0; i < operations.size(); i++) {
				if ("^".contains(operations.get(i) + "")) {
					//	System.out.println(num+"|"+operations);
					num.add(i, operate(operations.remove(i), num.remove(i), num.remove(i)));
					i--;
				}
			}
			for (int i = 0; i < operations.size(); i++) {
				if ("*/%".contains(operations.get(i) + "")) {
					//	System.out.println(num+"|"+operations);
					num.add(i, operate(operations.remove(i), num.remove(i), num.remove(i)));
					i--;
				}
			}
			for (int i = 0; i < operations.size(); i++) {
				if ("+-".contains(operations.get(i) + "")) {
					//	System.out.println(num+"|"+operations);
					num.add(i, operate(operations.remove(i), num.remove(i), num.remove(i)));
					i--;
				}
			}
			return num.get(0).doubleValue();
		}

	}

	/**
	 * a op b
	 *
	 * @param op
	 * @param a
	 * @param b
	 * @return
	 */
	public static double operate(char op, double a, double b) {
		switch (op) {
			case '+':
				return a + b;
			case '-':
				return a - b;
			case '*':
				return a * b;
			case '/':
				return a / b;
			case '%':
				return a % b;
			case '^':
				return Math.pow(a, b);
			default:
				System.err.println("Malformed pattern, did not recognize the character:\"" + op + "\" as a valid operation ");
				return -1.1111;
		}
	}
}
