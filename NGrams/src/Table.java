// 2 parts: take in text, save table to file (serializable)
// file to generated text
// modified version: instead of letter, by word
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class Table implements Serializable {
	private static final long serialVersionUID = -6043820992535173606L;
	private HashMap<String, FrequencyList> table;
	
	public Table() {
		table = new HashMap<>();
	}
	
	public void add(String words, String next) {
		for (int i = 0; i < table.size(); i++ ) {
			if (table.containsKey(words)) {
				table.get(words).updateFrequency(next);
				return;
			} 
		}
		
		FrequencyList freq = new FrequencyList();
		freq.updateFrequency(next);
        table.put(words, freq);
	}
	
	public Set<String> getKeys() {
		return table.keySet();
	}
	
	public String toString() {
		return table.toString();
	}
	
	public char getNext(String words) {
		try {
			for (int i = 0; i < table.size(); i++ ) {
				if (table.containsKey(words)) {
					return table.get(words).getNextRandom();
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ' ';
	}
	
	 private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		 out.writeObject(table);
	 }
	 
	 private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		 table = (HashMap<String, FrequencyList>) in.readObject();
	 }
	 
	 private void readObjectNoData() throws ObjectStreamException {
	 }
	 
	 public static void main(String[] args) {
		 	Table t = new Table();
		 	t.add("eee", "f");
		 	t.add("abc", "d");
		 	t.add("abc", "z");
		 	t.add("abc", "z");
		 
			try {
				FileOutputStream fos = new FileOutputStream("association.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(t);
	            oos.close();
	            fos.close();
			} catch (IOException e) {
	            e.printStackTrace();
	        }
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("association.ser"))) {
	            Table g = (Table) ois.readObject();
	    		System.out.println(g);
	        } catch (IOException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
		}

}
