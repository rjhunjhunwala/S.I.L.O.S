#S.I.L.O.S
S.I.L.O.S or SIL is designed for a nostalgic view of the past. It is simple and minimalistic, but remains capable of universal computation.
Feel free to [try this language online](http://silos.tryitonline.net/#code=cHJpbnQgVGhlIExhbmd1YWdlIFRoYXQgV29ya3MgRm9yIFlvdSE&input=)!
<hr/>
<pre>
 ____     __     __       __     ____ 
/ ___)   (  )   (  )     /  \   / ___)
\___ \ _  )(  _ / (_/\ _(  O )_ \___ \
(____/(_)(__)(_)\____/(_)\__/(_)(____/
</pre>
S.I.L.O.S is a bit arrogant of an acronym which stands for.
<pre>
<b>S</b>uperb
<b>I</b>nterpreted
<b>L</b>anguage's
<b>O</b>bviously
<b>S</b>uperior
</pre>
It is also known as SIL (Simple Interpreted Language)
<pre>
 .----..-.  .-..-. .-. .---.  .--.  .-.  .-.
{ {__   \ \/ / |  `| |{_   _}/ {} \  \ \/ / 
.-._} }  }  {  | |\  |  | | /  /\  \ / /\ \ 
`----'   `--'  `-' `-'  `-' `-'  `-'`-'  `-'
</pre>
The syntax is simple
<pre>
One command per line
one letter variable names
Only type integer 
No declaration needed they all start at 0
Turing complete direct access to the array like such 
a = get 5
lblFoo
goto Foo
No syntax checking
VB like behavior allowing erros to pass unnoticed and unhandled
if x Foo //goes to Foo iff x>0
a - 5
a + 5
a * 5
a /  5
a % 5
a | //absolute value of a and reassigns this to a
//all assignment operators x - 5 is the equivalent of x-=5 for those familiar with C styled languages
</pre>
That is the basic syntax, note that as of this printing, integers are 32 bit signed integers [-1*2^31,2^31-1] which overflow and wrap around. 
</br>There is a plan to make them big numbers
<pre>
         o                                                               
        <|>                                                              
        / \                                                              
      o/   \o       \o__ __o   \o__ __o     o__ __o/   o      o    __o__ 
     <|__ __|>       |     |>   |     |>   /v     |   <|>    <|>  />  \  
     /       \      / \   < >  / \   < >  />     / \  < >    < >  \o     
   o/         \o    \o/        \o/        \      \o/   \o    o/    v\    
  /v           v\    |          |          o      |     v\  /v      <\   
 />             <\  / \        / \         <\__  / \     <\/>  _\o__</   
                                                          /              
                                                         o                  
                                                      __/>               
</pre> 
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
a + 10
b + 11
set b a
//sets what is at b's position to a
c = get b
//gets the number from above
printInt c
</pre>
Remember please be careful to not use any positions below 256 on the array as these can be used for ascii variable names, touch these at great peril
<pre>
 ___               ___      ________     
|\  \             /  /|    |\   __  \    
\ \  \           /  //     \ \  \|\  \   
 \ \  \         /  //       \ \  \\\  \  
  \ \  \       /  //         \ \  \\\  \ 
   \ \__\     /_ //           \ \_______\
    \|__|    |__|/             \|_______|
                                         
</pre>
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
<pre>|`````````,       .'.       |..          | |``````.   .~ooooooo~.        .'. .`.       
|'''|'''''      .''```.     |  ``..      | |       | |          |     .'   `   `.     
|    `.       .'       `.   |      ``..  | |       | |          |   .'           `.   
|      `.   .'           `. |          ``| |......'   `.ooooooo.'  .'               `. 
</pre>                                                                                 
the randomness is provided by a prng currently the
prng is provided courtesy of Oracle and uses the system time as a seed
<pre>rand x</pre>
gets a random number [0,x) 0 inclusive to max exclusive and stores it in the variable r
max may also be a literal like 12
<pre>
___________                   __  .__                      
\_   _____/_ __  ____   _____/  |_|__| ____   ____   ______
 |    __)|  |  \/    \_/ ___\   __\  |/  _ \ /    \ /  ___/
 |     \ |  |  /   |  \  \___|  | |  (  <_> )   |  \\___ \ 
 \___  / |____/|___|  /\___  >__| |__|\____/|___|  /____  >
     \/             \/     \/                    \/     \/ 
</pre>
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
<pre>
_______________________________
      __                       
    /    )              /      
----\------_/_---------/----__-
     \     /    /   / /   /___)
_(____/___(_ __(___/_/___(___ _
                  /            
              (_ /        
</pre>
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
this is a comment
is valid syntax
but don't do this
<pre>1 = foo</pre>
could break your code
instead // is recommended to start all comments
lastly as input and randomness are stored into the variables i and r when requested it is recommended that you reserve these
variables for that use
<pre>
 .----------------.  .-----------------. .----------------.  .----------------.  .----------------. 
| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |
| |  _________   | || | ____  _____  | || |     _____    | || |     ____     | || |  ____  ____  | |
| | |_   ___  |  | || ||_   \|_   _| | || |    |_   _|   | || |   .'    `.   | || | |_  _||_  _| | |
| |   | |_  \_|  | || |  |   \ | |   | || |      | |     | || |  /  .--.  \  | || |   \ \  / /   | |
| |   |  _|  _   | || |  | |\ \| |   | || |   _  | |     | || |  | |    | |  | || |    \ \/ /    | |
| |  _| |___/ |  | || | _| |_\   |_  | || |  | |_' |     | || |  \  `--'  /  | || |    _|  |_    | |
| | |_________|  | || ||_____|\____| | || |  `.___.'     | || |   `.____.'   | || |   |______|   | |
| |              | || |              | || |              | || |              | || |              | |
| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |
 '----------------'  '----------------'  '----------------'  '----------------'  '----------------' 
</pre>
