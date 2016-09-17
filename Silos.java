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
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * S.I.L.O.S interpeter
 *
 * @author rohan
 */
public class Silos {
static boolean safeModeEnabled = true;
	/**
	 * This integer[] represents the heap of memory which can be addressed
	 */
	private static int[] mem;
	private static String[] texts;

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
	private final static int DRAW = 15 << 8;
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

	private final static int INTEGER = 0;
	private final static int VARIABLE = 1;

	private final static int ELLIPSE = 0;
	private final static int SQUARE = 1;

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
	static int[] vars;

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
public static final String IDEFileName = "TEMPFILE_1518259122315123_SILOS_V";
	/**
	 * The main interpretation code
	 *
	 * @param args the command line arguments to be passed from the online
	 * interpreter the first argument represents a fileName, and the rest
	 * represent a source of input Feeding in any number of command line arguments
	 * will generally disable interactivity. Interactivity can be forced enabled by
	 * Toggling safeMOdeEnabled into false
	 */
	public static void main(String... args) {
		Stack<Integer> stack = new Stack<>();
		Scanner sc = new Scanner(System.in);
		boolean interactive = (args.length == 0);
				interactive|=!safeModeEnabled;
				
		int[][] program = compile(args.length==0?getStringFromSTDIN("FileName?", sc):args[0]);
		
		if(args.length==1){
			if(args[0].equals(IDEFileName)){
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
						mem[tokens[1]] = evalToken(tokens[0], tokens[2], 0);
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
						mem[tokens[1]] ^=~ evalToken(tokens[0], tokens[2], 0);
						break;
					case NOT:
						mem[tokens[1]] =~ mem[tokens[1]];
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
					case DRAW:
						if (Canvas.createdCanvas) {
							int startX = evalToken(tokens[0], tokens[1], 0);
							int startY = evalToken(tokens[0], tokens[2], 1);
							int widthX = evalToken(tokens[0], tokens[3], 2);
							int widthY = evalToken(tokens[0], tokens[4], 3);
							double a = widthX * widthX / 4;
							double b = widthY * widthY / 4;
							boolean square = ((tokens[0] >> 4) & 1) == Silos.SQUARE;
							for (int i = startX; i < startX + widthX; i++) {
								for (int j = startY; j < startY + widthY; j++) {
									int xC = i - (startX + widthX / 2);
									int yC = j - (startY + widthY / 2);
									if (square || ((xC * xC) / a) + ((yC * yC) / b) < 1) {
										if (i > 0 && j > 0 && i < Canvas.canvas.length && j < Canvas.canvas[i].length) {
											Canvas.canvas[i][j] = Canvas.pen;
										}
									}
								}
							}
						}
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
					} else {
						g.fillRect(d.x, d.y, d.width, d.height);
					}
				}
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(x, y);
			}
		}
		public static Color[][] canvas;
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
	static int[] memory;

	/**
	 *
	 * @param fileName is the path to the file or just the name if it is local
	 * @return an array of Strings where each string is one line from the file
	 * fileName.
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
					|| command.startsWith("/*")
				) {
					continue;
				}
				for (int j = 1; j < replace.length; j += 2) {
					command = command.replaceAll(replace[j], replace[j + 1]);
				}
				if (command.length() == 0) {
					continue;
				}
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
				//five arguments
				if (instruction.equals("draw")) {
					int mode1, mode2, mode3, mode4;
					int arg1, arg2, arg3, arg4;
					int shape;
					if (words[1].equals("square")) {
						shape = Silos.SQUARE;
					} else {
						shape = Silos.ELLIPSE;
					}
					try {
						arg1 = Integer.parseInt(words[2]);
						mode1 = Silos.INTEGER;
					} catch (Exception e) {
						arg1 = (int) words[2].charAt(0);
						mode1 = Silos.VARIABLE;
					}
					try {
						arg2 = Integer.parseInt(words[3]);
						mode2 = Silos.INTEGER;
					} catch (Exception e) {
						arg2 = (int) words[3].charAt(0);
						mode2 = Silos.VARIABLE;
					}
					try {
						arg3 = Integer.parseInt(words[4]);
						mode3 = Silos.INTEGER;
					} catch (Exception e) {
						arg3 = (int) words[4].charAt(0);
						mode3 = Silos.VARIABLE;
					}
					try {
						arg4 = Integer.parseInt(words[5]);
						mode4 = Silos.INTEGER;
					} catch (Exception e) {
						arg4 = (int) words[5].charAt(0);
						mode4 = Silos.VARIABLE;
					}
					program.add(new int[]{Silos.DRAW + (shape << 4) + (mode4 << 3) + (mode3 << 2) + (mode2 << 1) + (mode1), arg1, arg2, arg3, arg4});
					continue;
				}

				//please
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

}