<h1>SIL</h1>
SIL is designed for a nostalgic view of the past. It is simple and minimalistic, but remains capable of universal computation.


<br/>



<h2>Running The code</h2>
There are many ways to run code in SILOS


You may build the Silos.java class into a jar or a .class file, and run the output. It will then prompt for a file name containing code to run. You also can <a href="http://silos.tryitonline.net/#code=cHJpbnQgU2ltcGxlIEludGVycHJldGVkIExhbmd1YWdl&input=)">"Try It Online." </a> The online interpreter is hosted by Dennis, a trusted moderator of the <a href="https://codegolf.stackexchange.com/">Code Golf Stack Exchange</a> and hosts a number of different recreational and practical languages.

If you have built the interpreter on your own computer please do not pass the file name as a command line argument. 

<b>Please note for both the interpreter online and the version which takes in the file name as a command line argument SILOS is in a non interactive "safe mode". In "safe mode" input is only taken through command line arguments. Use one argument per each line on stdin. Also, in safe mode no prompts are displayed, and no graphical output is given.</b>


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
The interpreter prints out "SOME PROMPT FOR THE USER" followed by a trailing linefeed and stores the value in the variable i.
NOTE that for the onlne version it is imperative you avoid the use of this command. All prompts are disabled in the online version. Instead, please consider using:
<pre>printLine SOME PROMPT
readIO
</pre>

Also, consider the following command.
<pre>
loadLine (Optional prompt)
</pre>

This, functions similar to readIO, however, it takes in string input. the result string is stored as a list of integers where the first character is stored at the 256th position of the global memory array the second at the 257th position and so on. More detail on how this can be used will be talked about later.

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
The first line is used by the interpreter to allocate the size of the global array used to store data. For consistent results, please do choose a number greater than 256. If you omit this line, the interpretter will just allocate 8192 slots in its memory for its future use
You are allowed to use any single character as a variable identifier, but this leads to some confusing cases.
<pre>1 = 5
printInt 1
//prints five
print 1
//1</pre>
Please, avoid abusing this.
While there is technically no comment syntax needed because the interpreter will skip over lines which it does not understand, it is recommended to use // in order to start all comments.

Numerical input is stored into the variable named "i" and rand stores its output into 'r', please reserve these variables for this use.


<hr/>
<h1>Changelog!</h1>
As of 8/24/2016 SIL 1.5 has been released. It features new, improved input, here are the new features.

Considering the following sample code

<pre>
//This sample program shows the new features of SIL
def p print load loadLine : lbl
// The above statement makes use of the pre-pocessor(which must appear on the second line) defines any number of macros in the form of def string replacement string2 replacement
//Now, p is an allias for print, and we can use it as such
p Hello,
pLine  World!
pSee
pLine !
a = 5
pInt a
//loading the next line of stdin as a string is easy.
load testing!
//the loadLine command will take one line (either one single command line argument on TIO (Try it Online!), or STDIN on java and loads it in starting at the 256th heap spot and working out)
//look, the macro now will allow : to work instead of "lbl"
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

The new features, in this version include the prepocessor and the improved input method.

<hr/>

<h1>Graphics</h1> 

Support for Graphical Output has been added in SIL 2.0. SIL 2.0 comes primarily with support for graphics, but also features a highly optimized interpreter (<b>thanks to  kckennylau!</b>), and a <pre>wait x</pre> command which as the name would suggest, waits x milliseconds. Now, let's take a look at how graphical output works. 

<pre>canvas width height wordForTheHeader</pre>

First use the above command to declare a canvas.
This command takes in a width a height, and a single bareword (no spaces) for the text at the top of the window. Each of the numerical arguments may be a numerical literal or a variable.

On this canvas there will be objects.

Each object you create will be in the current pen color.
You may set the pen color by the following command. 

<pre>pen r g b</pre> 

Replace r g and b with variables (or integer literals) representing red green and blue values of the color you would like.
Now, you may create objects using the following command:

<pre>newObj type height width</pre> 
Once again, we are looking at integer arguments. For type, a square is type 0, and an elipse is type one, a line (segment) is type 2. Width and height represent the x and y distances respectively between opposite corners of the object (in the case of line, the change in x over the line and the change in y over the line). This command will add a new object to the list of objects. This list is zero indexed.

<pre>moveObj index x y</pre> will move the top left corner of an object to a designated location. Please note that the origin is the top left corner. 

<pre>refresh</pre> This is a command to be invoked once you have moved the objects around. Refresh is the only way to actually trigger the painting. 

Please see the game.txt and gui.txt files in the examples folder for an explanation.

<h1>Keyboard Bindings</h1>

Another new feature in SIL 2.0. At any point you can place a binding to certain characters
<pre>bind intRepresentingChar1 heapSpot1 intOfChar2 heapSpot2 etcetera</pre> Where intRepresentingChar is an integer literal or variable representing a char ((ascii convention used) 97 would mean 'a') and heapspot is an integer value of a valid spot on the heap. 
When the corresponding characters get typed (and a canvas has already been created) the corresponding heapspot will be set to one. <b>This feature only works on the desktop version when a canvas is in focus</b>
<hr/>
<h1>Whitespace</h1>
Most whitespace dependency is gone. The only necessary whitespace separates arguments and the new lines between commands. 
All lines are now indentable!
<h2>Bitwise operators now have support. Please view the bitwise.txt document for a full rundown </h2>

<h1>Stacks and Queues </h1>

You have access to 32 stacks and 32 queues.

Consider the following sample code.

<pre>
stack x y

//will add y to the xth stack

queue x y

//will queue y onto the xth queue

stackPop x

//will pop a value off of the xth stack and store in variable m

queuePop x

//will pop a value off of the front of the xth queue and store in the variable marked m.
</pre>
<h1>SIL 3.0</h1>

SIL 3.0 has been released after heavy developement, please do enjoy the followign features.
You may pop up an alert boc with any string (really, a set of bare words) by saying.
<pre>alert String</pre>
You may also pop up string input boxes 
<pre>
loadLinePopUp stringAsPrompt
</pre>
This functions like loadLine would, but it allows the user to input the desired string into a pop-up dialog box.

While you could only have a single variable or integer literal on the other side of an equals side, now you may input expressions consisting of standard mathematical operators (currently no bit-shift is supported on the right of an equals sign).

A standard library has been added. 

In any program use the following command:
<pre>
leverage stdlib
</pre>
And you now may use functions defined within the standard library. Other libraries may be imported by typing:
<pre>
leverage fileName.txt
</pre>

Essentially, this takes the text in the file (runs it through the prepocessor independently) and appends it to your code. Here, is the source for the standard library, but know that you do not need to keep the source of the standard library in the same directory in order to use it.

<pre>
GOTO EndOfTheStandardLibraries
/**
* This is the source code presented for the standard libraries of SILOS
* There is no need to download this file as it is attached with the release (in \"Silos.java\")
* simply import this library by using \"leverage stdlib\"
* To make a library of your own, follow the standards presented in this code. Most notably,
* start the code with a goto to skip all of the utility methods. 
* Then, to import your code, use \"leverage fileName.txt\" (replacing with the fully qualified file name)
*/

//Trig Methods

/**
* Sin method, returns sin(x/10000)*39916800 using an 11th order taylor polynomial
* Receives input via the top of the zeroth stack and uses the m M and ? variables
* returns to the top of the zeroth stack accurate to three significant figures from [-pi,pi]
*/
funcSinX
    stackPop 0
    m=(99792*m)/25-(2079*m^3)/312500000+(2079*m^5)/625000000000000000-(99*m^7)/125000000000000000000000000+(11*m^9)/100000000000000000000000000000000000-m^11/100000000000000000000000000000000000000000000 
    stack 0 m
return

/**
* Cos method returns 479001600*cos(x/10000) using an 12th order taylor polynomial
* receives input via the top of the zeroth stack and uses the m M and ? variables
*/
funcCosX
    stackPop 0
    m = 479001600*(1-m^2/200000000+m^4/240000000000000000-m^6/720000000000000000000000000+m^8/4032000000000000000000000000000000000-m^10/36288000000000000000000000000000000000000000000+m^12/479001600000000000000000000000000000000000000000000000000)
    stack 0 m
return
/**
* Echo out the first line of standard input uses m and M as a variable
*/
funcCat
    loadLine
    m=256
    M=get m
    lblTOPOFCATLOOP
        printChar M
        m+1
        M =get m
    if M TOPOFCATLOOP
return
//String manipulation Functions V

//This area has been allocated for string manipulation functions

//End of String manipulation Functions ^

//This method will help new users by providing documentation
funcHelp
printLine GitHub for this language
printLine https://github.com/rjhunjhunwala/S.I.L.O.S
printLine IDE for this language
printLine https://github.com/rjhunjhunwala/S.I.D.E
return
lblEndOfTheStandardLibraries
</pre>

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
