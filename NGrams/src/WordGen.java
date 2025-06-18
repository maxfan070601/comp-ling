//import java.util.Scanner;
//import java.util.HashMap;
//
//public class Input {
//	
//	public static void main(String[] args) {
//		Scanner s = new Scanner(System.in);
//		String file = "";
//		
//		System.out.println("How many Ns do you want in your gram?");
//		int n = s.nextInt();
//		
//		while (s.hasNextLine()) {
//			file += s.nextLine();
//			System.out.println(file);
//		}
//		
//		HashMap<String, Integer> letterCount = new HashMap<>();
//		
//		for (int i = 0; i < file.length()-n+1; i++) {
//			String letter = file.substring(i, i+n);
//			letterCount.put(letter, letterCount.getOrDefault(letter, 0) + 1);
//		}
//		
//		System.out.println(letterCount);
//	}
//}

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.Set;
import java.util.Random;

public class WordGen {
	
	private Table table;
	private Random random;
	
	public WordGen() {
		table = new Table();
		random = new Random();
	}
	
	public static void saveTable(Table table, String file) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(table);
			oos.flush();
            oos.close();
            fos.flush();
            fos.close();
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void loadTable(String file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            table = (Table) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	
	public String fileToText(String name) {
		String text = "";
		try (Scanner s = new Scanner(new File(name))) {
			while (s.hasNextLine()) {
				text += s.nextLine() + ' ';
			}
		} catch (IOException e) {
			System.out.println("Error reading file input");
		}
		return text;
	}
	
	public void initializeTable(String text, int k) {
		for (int i = 0; i <= text.length() - k; i++) {
			String letters = text.substring(i, i+k);
			String next;
			
			if (i + k < text.length()) {
				next = text.charAt(i + k);
			} else {
				next = ' ';
			}
			
			table.add(letters, next); 
			
		}
//		System.out.println(table.toString());
		saveTable(table, "table.ser");
	}
	
	public void generateFromTable(int length, int k) {  
		System.out.println(table.toString());
		String generate = "";
		String current = "";
		Object[] keys = table.getKeys().toArray();
		int choice = (int) (Math.random() * keys.length);
		current += keys[choice];
		generate += current;
		
		for (int i = k; i < length; i++) {
			char next = table.getNext(current);
            generate += next; 
            current = current.substring(1) + next;
		}
		System.out.println(generate);
	}
	
	public static void main(String[] args) {
		// add command line for read file
        WordGen w = new WordGen();
		Scanner s = new Scanner(System.in);
		System.out.println("What do you want the length of the key to be in the frequency list (k)?");
        int k = s.nextInt();
        System.out.print("How long do you want your output?");
        int length = s.nextInt();
        System.out.println("Write table.ser if you want to generate, write input.txt if you want to read the text, otherwise write the string you want to convert to table.");
        String str = s.next();
        if (str.equals("table.ser")) {
        	w.loadTable("table.ser");
        	w.generateFromTable(length, k);
        } else if (str.equals("input.txt")) {
        	str = w.fileToText("input2.txt");
        	w.initializeTable(str, k);
        } else {
        	w.initializeTable(str, k);
        }
        //String str = w.fileToText("input2.txt");
        //w.initializeTable(str);
//        w.loadTable("table.ser");
        //System.out.println(w.table);
//        w.generateFromTable(length, k);
	}
}