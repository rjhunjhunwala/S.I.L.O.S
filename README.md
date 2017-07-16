<h1>S.I.L.O.S</h1>
S.I.L.O.S or SIL is designed for a nostalgic view of the past. It is simple and minimalistic, but remains capable of universal computation.
Feel free to [try this language online]( http://silos.tryitonline.net/#code=cHJpbnQgU2ltcGxlIEludGVycHJldGVkIExhbmd1YWdl&input=)! The interpreter is hosted by Dennis Mitchell. <b>Please do not pass the file name as a command line argument.For Both the interpreter online and the version using command line arguments for file names SILOS is in a non interactive "safe mode". In safe mode input is only taken through command line arguments one argument per each line on stdin. No prompts are displayed, and no graphical output is given!</b>
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
<h2> S.I.L.O.S </h2>
S.I.L.O.S stands for.
<pre>
<b>S</b>uperb
<b>I</b>nterpreted
<b>L</b>anguage's
<b>O</b>bviously
<b>S</b>uperior
</pre>
It is also known as SIL (Simple Interpreted Language)
<h2> Syntax </h2>
The syntax is simple
<pre>
//One command per line
//one letter variable names
//Only type integer 
//No declaration needed they all start at 0
//direct access to the memory array 
a = get 5
lblFoo
GOTO Foo
No syntax checking
VB like behavior allowing erros to pass unnoticed and unhandled
if x Foo //goes to Foo iff x>0
a - b subtraction
a + b addition
a * b multiplication
a / b division
a % b modulus
a | //absolute value of a and reassigns this to 
a ~ bitwise not
a < b left shift
a > b right shift
a & b bitwise and
a : b or
a ! b xor
a ? b xnor
a ^ b a raised to the b

//all assignment operators x - 5 is the equivalent of x-=5 for those familiar with C styled languages
</pre>
That is the basic syntax, note that as of this printing, integers are 32 bit signed integers [-1*2^31,2^31-1] which overflow and wrap around. 
<br/>There is a plan to make them big numbers with unbounded size.
<h2> Memory Buffer </h2>

You can directly write to the memory buffer like such
<pre>
set 288 57
//acccesses the heap at 288 and sets it to 57
a = get 288
//gets back what the thing at position 288
printInt a
set a 88
//sets the thing at position a to 88
b = get a
//gets the thing at position a and sets it in the value of b
printInt b
printIntNoLine a
printLine
a + 10
b + 11
set b a
//sets what is at b's position to a
c = get b
//gets the number from above
printInt c
</pre>
Remember please be careful to not use any positions below 256 on the array as these can be used for ascii variable names, touch these at great peril
<h2>IO</h2>
input and output is done like so
<pre>readIO SOME PROMPT FOR THE USER</pre>
the magical interpreter thus prints out SOME PROMPT FOR THE USER  and stores the value in the variable i
NOTE that for the onlne version it is imperative you avoid the use of this command. All prompts are disabled in the online version, so
<pre>printLine SOME PROMPT</pre>
and then
<pre>readIO :</pre>
Printing is done with three commands
<pre>printInt a
//prints out the value of a with a trailing newline
printLine a
//prints out "a" with a trailing newline
print a
//prints out "a" with no trailing new line
printChar x prints out the value of x as a character
</pre>

<h2> Entropy </h2>                                                                                                                                                    
the randomness is provided by a prng currently the
prng is provided courtesy of Oracle and uses the system time as a seed
<pre>rand x</pre>
gets a random number [0,x) 0 inclusive to max exclusive and stores it in the variable r
max may also be a literal like 12
<h2> Functions </h2>
See the usage of functions in functions.txt to better comperehend it, but here is the basics. Function should be declared as
such
<pre>
GOTO endOfAllFunctions
funcfunctionname
//code the function here
return
funcsomeFunc
return
lblendOfAllFunctions
</pre>
now at any point in the code you may calll the function named functionname as such
<pre>
GOSUB functionname
</pre>
The stack is automagically handled works similar to most languages. However one must  be cuatious of properly enclosing the function safely with returns.
Also ensure to jump around the functions with the Goto's otherwise all the functions will run.

Ideally functions should have only one return statement.

As of this printing functions can only be void and have no return type. 
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
