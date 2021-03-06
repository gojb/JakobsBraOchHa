/*
 * Copyright 2017 GoJb Development
 *
 * Permission is hereby granted, free of charge, to any
 * person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or
 *  sell copies of the Software, and to permit persons to whom
 *  the Software is furnished to do so, subject to the following
 *  conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF
 * ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package spel;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

public class Sudoku {

	static ArrayList<Integer> list = new ArrayList<>();
	int[][] integerss= new int[9][9];
	int ber;
	static Random random = new Random();

	public static void main(String[] args) {
		// FIXME Auto-generated method stub
		new Sudoku();
	}
	public Sudoku(){
		int f=0;
		for (int i = 0; i < 9; i=i+0) {
			if (f++==500000) {
				f=0;
				i-=2;
			}
			ber:{
				int[] ny = {1,2,3,4,5,6,7,8,9};
				for (int j = ny.length - 1; j > 0; j--){
					int index = random.nextInt(j + 1);
					int a = ny[index];
					ny[index] = ny[j];
					ny[j] = a;
				}
				for (int j = 0; j < 3; j++) {
					for (int j2 = 0; j2 < 3; j2++) {
						int siffrarad=ny[j2+(j*3)], siffrakol = ny[j+(j2*3)];
						
						for (int k = 0; k < 3; k++) {
							for (int k2 = i%3; k2 > 0; k2--) {
								ber++;
								if (siffrarad==integerss[i-k2][k+(j*3)]) {
									break ber;
								}
							}
							for (int k2 = i/3; k2 > 0; k2--) {
								if (siffrakol==integerss[i-(k2*3)][(3*k)+j]) {
									break ber;
								}
							}
						}
					}
				}
				integerss[i]=ny;
				i++;
				f=0;
			}
		}
		print(true);
		System.err.println(ber);
	}
	void print(boolean summa){
		PrintStream printStream;
		if (summa) {
			printStream=System.out;
		}
		else{
			printStream=System.err;
		}
		int b=0,c=0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				for (int j2 = 0; j2 < 3; j2++) {
					printStream.print(integerss[b+j][j2+c]+" ");
				}
				printStream.print("  ");
			}
			c+=3;
			if (c==9) {
				c=0;
				b+=3;
				printStream.println();
			}
			printStream.println();
		}
		printStream.println();
	}
}
