import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Vector;

public class Association implements Serializable {
	private static final long serialVersionUID = 2896060202907471551L;
	private String key;
	private Integer value;
	
	public Association() {
		this.key = "";
		this.value = 0;
	}
	public Association(String key, int value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int v) {
		value = v;
	}
	
	public String toString() {
		return "*"+key+"*:" + value;
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(key);
		out.writeObject(value);
	}
 
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		key = (String) in.readObject();
		value = (Integer) in.readObject();
	}
 
	private void readObjectNoData() throws ObjectStreamException {
	}
	
	public static void main(String[] args) {
		Association a = new Association("Hello", 2);
		try {
			FileOutputStream fos = new FileOutputStream("association.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(a);
            oos.close();
            fos.close();
		} catch (IOException e) {
            e.printStackTrace();
        }
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("association.ser"))) {
            Association b = (Association) ois.readObject();
    		System.out.println(b);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
}
