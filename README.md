<h1>SIL</h1>
SIL is designed for a nostalgic view of the past. It is simple and minimalistic, but remains capable of universal computation.

Feel free to <a href="http://silos.tryitonline.net/#code=cHJpbnQgU2ltcGxlIEludGVycHJldGVkIExhbmd1YWdl&input=)">Try It Online. </a> The interpreter is hosted by Dennis Mitchell. <b> 
  <br/>
Please do not pass the file name as a command line argument. For Both the interpreter online and the version using command line arguments for file names SILOS is in a non interactive "safe mode". In "safe mode" input is only taken through command line arguments one argument per each line on stdin. No prompts are displayed, and no graphical output is given.</b>

<hr/>
<p xmlns:dct="http://purl.org/dc/terms/">
  <a rel="license"
     href="http://creativecommons.org/publicdomain/zero/1.0/">
    <img src="http://i.creativecommons.org/p/zero/1.0/88x31.png" style="border-style: none;" alt="CC0" />
  </a>
  <br />
  To the extent possible under law,
  <a rel="dct:publisher"
     href="https://github.com/rjhunjhunwala">
    <span property="dct:title">Rohan Jhunjhunwala</span></a>
  has waived all copyright and related or neighboring rights to
  <span property="dct:title">S.I.L.O.S/SIL</span>.
</p>
<h2> SIL </h2>
SIL, was orignally known as S.I.L.OS, but is now known as SIL (Simple Interpreted Language).
<h2> Overview of Syntax </h2>
The syntax is simple. The below program is a sample program, which does nothing, but demonstrates the way the language works. 
<pre>
//One command per line
//one letter variable names
//Only type integer 
//No declaration needed. All variables are automatically initialised to 0
//direct access to the memory array 
a = get 5
//In SIL, all data is generally stored on a global array (including variables). When you get the element at index 5, you access the 6th Element (0 indexed)
//The variable 'A' is stored at spot 65, and in general the variable stored at some variable with a name consisting of a character c, is stored at the integer value of that character.
lblFoo 
//labels are declared like this. There may be no space in the variable name
GOTO Foo
//Goes to the first occurence of the foo label
// The language does not generally enforce syntax, all syntax which does not comply with the specifications will invoke undefined behavior. This generally means the statement is ignored.
//C style comments ("//") and Python style ("#") comments are supported by the standard, as well as some other commenting styles, most notably /* and * will comment out a line, as homage to Javadoc traditions. 
//conditional jumo
if x Foo 
//This line will jump to the label marked foo if x has a value greater than zero.

//The following operations function as assignment operators. a - b, means a = a-b

a - b 
//subtraction
a + b 
//addition
a * b 
//multiplication
a / b 
//division
a % b 
//modulus
a | 
//absolute value of a and reassigns this back to a

// the following operators are bitshift operators
a ~ 
//bitwise not
//For each bit in a, reassign it to its negation
a < b 
//bitwise left shift a by b bits
a > b 
//right shift a by b bits
a & b 
//bitwise and
a : b 
//bitwise or
a ! b 
//xor
a ? b 
//xnor
a ^ b 
//a raised to the b
//all assignment operators x - 5 is the equivalent of x-=5 for those familiar with C styled languages

a = (x+y)^2
//We support a get call, a single variable (or integer), or an expression to the left of an equals sign
// The expression, must be well formed, and will be evalauted based on the order of operations set by the conventions of modern mathematics. Internally, the expression will be processed as a double, and the final value will be truncated to an integer. 
//Currently, bitwise, and unary operators are not supported by SIL
</pre>

That is the basic syntax. Integers are 32 bit signed integers [-1*2^31,2^31-1] which overflow and wrap around. 
<br/>

<h2> Memory Buffer </h2>

You can directly write to the memory buffer like such
<pre>
set 288 57
//acccesses the buffer at the index 288 and sets it to 57
a = get 288
//gets back the value at position 288 and stores it to 288
printInt a
//printInt is the print statement which works with integers, it will print the value of A followed by a single trailing line feed.
set a 88
//sets the thing at position a to 88
b = get a
//gets the thing at position a and sets it in the value of b
printInt b
printIntNoLine a
// function is identical to printInt, but there is no trailing linefeed.
printLine
// printLine can optionally be followed by a string to output, it outputs the string (which may be empty), and follows it with a trailing linefeed.
//for example
printLine Hello, World!
//Displays the classic text, and follows it with a linefeed. This means that there are no strings in SIL. There are only barewords.
a + 10
b + 11
set b a
//sets what is at b's position to a
c = get b
//gets the number from above
printInt c
</pre>
Remember, please be careful to not use any positions below 256 on the array as these can be used for ascii variable names. Access these locations at great peril.
<h2>IO</h2>
There are various methods to perform Input and Output in SIL.
<pre>readIO SOME PROMPT FOR THE USER</pre>
The interpreter prints out "SOME PROMPT FOR THE USER" and stores the value in the variable i
NOTE that for the onlne version it is imperative you avoid the use of this command. All prompts are disabled in the online version. Instead please consider using:
<pre>printLine SOME PROMPT
readIO :
</pre>

As alluded to above, printing is done with a few different commands.

<pre>printInt x
//prints out the value stored in x with a trailing newline
printIntNoLine x
//prints out the value of x without the trailing newline
printLine a
//prints out "a" with a trailing newline
print a
//prints out "a" without trailing new line
printChar x 
//prints out the value of x as a character
// If the value of x was 66 we would output "B"
</pre>

<h2> Randomness </h2>                                                                                                                                                    
Randomness can be obtained by a prng.
<pre>rand x</pre>
The above gets a random number on the range [0,x) stores it in the variable r.
Like in all commands, you can use a literal integer value like 12 instead of a variable.
<h2> Functions </h2>
Please do see the usage of functions in functions.txt for more example, but here is the basics. Functions should be declared as
such. 
<pre>
GOTO endOfAllFunctions
funcfunctionname
//code the function here
return
funcsomeFunc
return
lblendOfAllFunctions
</pre>
Place this code at the beginning of your file. Although SIL will run when you call a function declared below it, it will output a warning to STDERR.
At any point in the code you may now call the function named functionname as such.
<pre>
GOSUB functionname
</pre>
The call stack is handled by the interpreter, but make sure that you end each function with a return statement. 
Be sure to jump around the functions with the Goto's otherwise all the functions will run, even if you do not call them elsewhere.
As a matter of principle, functions should have only one return statement.
Currently, functions can only be void and have no return type. 
<h2> Style </h2>
the first line is used by the interpreter to allocate the size of the array (heap) this number should be at least 256
if you omit this line the interpretter will just allocate 8192 slots in its memory for its future use
you are allowed to use single character identifiiers
<pre>1 = 5
printInt 1
//prints five
print 1
//1</pre>
But never do this unless it is for obfuscation purposes the compiler will struggle with resolving what you are talking about
also as far as commenting goes the compiler will ignore non code thus
<pre>
this is a comment
</pre>
is valid syntax
but don't abuse this as
<pre>1 = foo</pre>
could break your code
instead // is recommended to start all comments
lastly as input and randomness are stored into the variables i and r when requested it is recommended that you reserve these
variables for that use
As of 8/24/2016 SIL has new, improved input, here are the new features
<pre>
<h1>S.I.L.O.S 1.5</h1>
//This shows the new features of SIL
def p print load loadLine : lbl
//With the new "illiterate preprocessor" (tm) the above statement (which must appear on the second line) defines any number of macros in the form of def string replace string2 replace

p Hello,
pLine  World!
pSee
pLine!
a = 5
pInt a
//loading the next line of stdin as a string is easy.
load testing!
//the loadLine command will take one line (either one single command line argument on TIO (Try it Online!), or STDIN on java and loads it in starting at the 256th heap spot and working out)
//look the macro now will allow : to work as a label
a = 256
:a
x = get a
printInt x
if x continue
GOTO end
:continue
pChar x
a + 1
if x a
:end
</pre>
<hr/>
<h1>Graphics</h1> 
Support for Graphical Output has been added in SILOS 2.0. SILOS 2.0 comes primarily with support for graphics, but also features I highly optimized interpreter (<b>thanks to  kckennylau!</b>), and a <pre>wait x</pre> command which as the name would suggest, waits x milliseconds. Now, let's take a look at how graphical output works. 
<pre>canvas width height wordForTheHeader</pre>
You first must use the above command to declare a canvas
THan you must decide to create objects
Each object will be in the current pen color.
THe pen color gets set with 
<pre>pen r g b</pre> where r g and b are variables represent red green and blue values of a color (of any name) or integer literals.
Than you can create an object
<pre>newObj type height width</pre> a square is type 0 an elipse is type one. Height and width and type are all variables/integer literals. This will add a new object to the drawable list. This list is zero indexed.
<pre>moveObj index x y</pre> will move an object to a designated location.
<pre>refresh</pre> is a mandatory command to be invoked once you have moved the objects around. refresh is the only way to actually trigger the painting. 
Please see the game.txt and gui.txt files in the examples folder for an explanation.
<h1>Keyboard Bindings</h1>
At any point you can place a binding to certain characters
<pre>bind intRepresentingChar1 heapSpot1 intOfChar2 heapSpot2 etcetera</pre> Where intRepresentingChar is an integer literal or variable representing a char ((ascii convention used) 97 would mean 'a') and heapspot is an integer value of a valid spot on the heap. 
When the corresponding characters get typed (and a canvas has already been created) the corresponding heapspot will be set to one. <b>This feature only works on the desktop version when a canvas is in focus</b>
<hr/>
<h1>Whitespace</h1>
Most whitespace dependency is gone. The only necessary whitespace separates arguments and the new lines between commands. 
All lines are now indentable!
<h2>Bitwise operators now have support. Please view the bitwise.txt document for a full rundown </h2>
<h1>Stacks and Queues </h1>
You have access to 32 stacks and 32 queues

stack x y

will add y to the xth stack

queue x y

will queue y onto the xth queue

stackPop x

will pop a value off of the xth stack and store in variable m

queuePop x

will dequeu a value off of the front of the xth queue and store in variable m

<h1>SIL 3.0</h1>

SIL 3.0 has been released after heavy developement, please do enjoy the followign features.

- Pop up alerts simply type "alert string" 
- Pop up string input boxes "loadLinePopUp stringAsPrompt"
- Mathematical expressions x = (Whatever) (Do note that whatever has to be a well formed mathematical expression which can consist of any amount of preceding whitespace, but may not contain whitespace within it).
- A standard library, simply type leverage stdlib to use the power of the ever expanding standard Library, feel free to contribute more libraries which get imported by typing "leverage fileName.txt"

<h1><b>Please note that all interactivity including graphics support is unavailable on Try it Online! all input must be through command line arguments</b></h1>

<p xmlns:dct="http://purl.org/dc/terms/">
  <a rel="license"
     href="http://creativecommons.org/publicdomain/zero/1.0/">
    <img src="http://i.creativecommons.org/p/zero/1.0/88x31.png" style="border-style: none;" alt="CC0" />
  </a>
  <br />
  To the extent possible under law,
  <a rel="dct:publisher"
     href="https://github.com/rjhunjhunwala">
    <span property="dct:title">Rohan Jhunjhunwala</span></a>
  has waived all copyright and related or neighboring rights to
  <span property="dct:title">S.I.L.O.S/SIL</span>.
</p>
