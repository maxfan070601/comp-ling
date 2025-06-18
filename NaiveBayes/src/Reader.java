import java.util.*;
import java.io.*;

public class Reader {
	private List<Map<String, String>> data; 
    private List<String> attributes;  
    private Map<String, Set<String>> attributeValues; 
    private String className;               
    private Set<String> classValues; 
    
    public Reader(String file) throws IOException {
    	data = new ArrayList<>();
        attributes = new ArrayList<>();
        attributeValues = new HashMap<>();
        parse(file);
    }
    
    private void parse(String file) throws IOException {
    	try (Scanner scanner = new Scanner(new File(file))) {
            boolean isData = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.toLowerCase().startsWith("@relation")) {
                	continue;
                }
                else if (line.toLowerCase().startsWith("@attribute")) {
                    String[] tokens = line.split(" ", 3);
                    String attribute = tokens[1];
                    attributes.add(attribute);

                    String values = tokens[2].replaceAll("[{}]", "");
                    String[] valueArray = values.split("\\s*,\\s*");
                    Set<String> attrValues = new HashSet<>();
                    for (String value : valueArray) {
                        attrValues.add(value);
                    }

                    attributeValues.put(attribute, attrValues);
                } else if (line.toLowerCase().equals("@data")) {
                    isData = true;
                    className = attributes.get(attributes.size() - 1); 
                    classValues = attributeValues.get(className);
                    attributes.remove(attributes.size()-1);
                } else if (isData) {
                    String[] values = line.split("\\s*,\\s*"); 
                    HashMap<String, String> instance = new HashMap<>();

                    for (int i = 0; i < values.length - 1; i++) {
                        instance.put(attributes.get(i), values[i].trim());
                    }
                    instance.put(className, values[values.length - 1].trim());
                    data.add(instance);
                }
            }
        }
    }

	public List<Map<String, String>> getData() {
	    return data;
	}
	
	public List<String> getAttributes() {
	    return attributes;
	}
	
    public String getClassName() {
        return className;
    }

    public Set<String> getClassValues() {
        return classValues;
    }
    
    public Map<String, Set<String>> getAttributeValues() {
    	return attributeValues;
    }
}
