package rohan;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rohans
 */
public class AutogenerateQuine {
    public static void main(String... args){
        String[] stuff=file.getWordsFromFile("quine2.txt");
        String q="";
        int i = 0;
        for(String s:stuff){
            q+=s+(i!=stuff.length-1?"\n":"");
        i++;
        }
        for(char c:q.toCharArray()){
            System.out.println("set a "+(int) c);
            System.out.println("a + 1");
        }
    }
}
