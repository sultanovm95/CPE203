package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)  {
        int check = intRom("IVX");
        System.out.println(check);
    }

    public static int intRom(String numb) {
        if( numb.length() == 0) {
            return 0;
        }
        int total = 0;
        char[] chars = numb.toCharArray();
        for(int i = 0; i < chars.length; i++) {
                int j = i;
                if(j < chars.length - 1)
                    j++;
                if(chars[i] == 'I' && (chars[j] == 'V' || chars[j] == 'X')) {
                    total = total - 1;
                    System.out.println(total);
                }


                if(chars[i] == 'X' && (chars[j] == 'L' || chars[j] == 'C'))
                    total = total - 10;
                if(chars[i] == 'C' && (chars[j] == 'D' || chars[j] == 'M'))
                    total = total - 100;
                if(chars[i] == 'I') {
                    total++;
                    System.out.println(total);
                }
                if(chars[i] == 'V') {
                    total += 5;
                    System.out.println(total);
                }
                if(chars[i] == 'X')
                    total += 10;
                if(chars[i] == 'L')
                    total += 50;
                if(chars[i] == 'C')
                    total += 100;
            }
        return total;
        }

}


/*
Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
 */