import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

public class FrequencyList implements Serializable {
	private static final long serialVersionUID = -7356300844606358841L;
	private Vector<Association> freq;
	private int total = 0;
	
	public FrequencyList() {
		freq = new Vector<>();
    }
	
	public void updateFrequency(String next) {
		boolean found = false;
		for (int i = 0; i < freq.size(); i++) {
			if (freq.get(i).getKey().equals(String.valueOf(next))) {
				freq.get(i).setValue(freq.get(i).getValue() + 1);
				found = true;
				break;
			} 
		}
		if (found == false) {
			freq.add(new Association(String.valueOf(next), 1));
		}
		total++;
	}
	
	public String toString() { 
		String s = "[";
		for(Association a :freq) {
			s += a + "\n";
		}
		return s + "]\n";
	}
	
	public char getNextRandom() throws Exception {
		int rand = (int)(Math.random() * total);
		int sum = 0;
		for (int i = 0; i < freq.size(); i++) {
			sum += freq.get(i).getValue();
			if (sum > rand) {
				return freq.get(i).getKey().charAt(0);
			}
		}
		throw new Exception("you did something wrong");
	}
	
	 private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		 out.writeObject(freq);
		 out.writeObject(total);
	 }
	 
	 private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		 freq = (Vector<Association>) in.readObject();
		 total = (Integer) in.readObject();
	 }
	 
	 private void readObjectNoData() throws ObjectStreamException {
	 }
	 
	 public static void main(String[] args) {
			FrequencyList f = new FrequencyList();
			f.updateFrequency("a");
			f.updateFrequency("a");
			f.updateFrequency("a");
			f.updateFrequency("a");
			f.updateFrequency("a");
			f.updateFrequency("a");
			try {
				FileOutputStream fos = new FileOutputStream("association.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(f);
	            oos.close();
	            fos.close();
			} catch (IOException e) {
	            e.printStackTrace();
	        }
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("association.ser"))) {
	            FrequencyList g = (FrequencyList) ois.readObject();
	    		System.out.println(g);
	        } catch (IOException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
		}
}
