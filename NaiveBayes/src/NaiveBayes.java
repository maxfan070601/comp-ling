import java.util.*;
import java.io.*;


public class NaiveBayes {
	private Reader reader;
    private Map<String, Double> classCounts;
    private Map<String, Map<String, Map<String, Double>>> attributeCounts;
    private Map<String, Map<String, Double>> attributeDenoms = new HashMap<>();
    private int total = 0;
    
    public NaiveBayes(Reader reader) {
    	this.reader = reader;
        this.attributeDenoms = new HashMap<>();
        classCounts = new HashMap<>();
        attributeCounts = new HashMap<>();
        train();
    }
    
    public void train() {
    	List<Map<String, String>> data = reader.getData();
    	List<String> attributes = reader.getAttributes(); 
        String classAttribute = reader.getClassName(); 
        Set<String> classValues = reader.getClassValues();
        for (String attribute: attributes) {
        	attributeCounts.put(attribute, new HashMap());
        	Map<String, Map<String, Double>> x = attributeCounts.get(attribute);
        	attributeDenoms.put(attribute, new HashMap());
        	Map<String, Double> x1 = attributeDenoms.get(attribute);
        	for (String attributeValue: reader.getAttributeValues().get(attribute)) {
        		x.put(attributeValue, new HashMap());
        		Map<String, Double> y = x.get(attributeValue);
        		for (String classValue: classValues) {
        			x1.put(classValue, x1.getOrDefault(classValue, 0.0) + 1);
        			y.put(classValue, 1.0);
        			classCounts.put(classValue, classCounts.getOrDefault(classValue, 0.0) + 1);
        			total ++;
        		}
        	}
        }
        
        for (Map<String, String> instance: data) {
        	
        	String className = instance.get(classAttribute);
        	classCounts.put(className, classCounts.getOrDefault(className, 0.0) + 1);
        	total ++;

            for (int i = 0; i < attributes.size(); i++) { 
                String attribute = attributes.get(i);
                String value = instance.get(attribute);
                
                Map<String, Map<String, Double>> valueGivenClass = attributeCounts.get(attribute);
                Map<String, Double> classValueCounts = valueGivenClass.get(value);
                classValueCounts.put(className, classValueCounts.getOrDefault(className, 0.0) + 1);
            }
        }
    }
    
    public String classify(Map<String, String> datum) {
	    double maxProbability = Double.NEGATIVE_INFINITY;
	    String bestClass = null;

	    for (String className : classCounts.keySet()) {
	    	double probability = classCounts.get(className) / total;
	        for (String attribute : attributeCounts.keySet()) {
	            if (attribute.equals(className)) {
	            	continue; 
	            }

	            String attributeValue = datum.get(attribute);
	            double numerator = attributeCounts.get(attribute).get(attributeValue).get(className);
	            double denominator = attributeDenoms.get(attribute).get(className);
	            probability *= numerator / denominator;
	        }

	        if (probability > maxProbability) {
	            maxProbability = probability;
	            bestClass = className;
	        }
	    }

	    return bestClass; 
	}

    public double evaluate(List<Map<String, String>> data) {
        double correct = 0;
        
        for (Map<String, String> instance : data) {
            String predicted = classify(instance);
            if (predicted.equals(instance.get(reader.getClassName()))) correct++;
        }

        return correct / data.size();
    }
    
	public static void main(String[] args) throws IOException {
		String file = "titanic.arff";
		Reader reader = new Reader(file);
		List<Map<String, String>> data = reader.getData();
        
        NaiveBayes b = new NaiveBayes(reader);
        for (int i = 0; i < data.size(); i++) {
            Map<String, String> instance = data.get(i);
            System.out.println("Instance " + (i + 1) + ": " + instance + ", Predicted Class: " + b.classify(instance));
        }
        System.out.println("Accuracy: " + b.evaluate(data)*100+"%");
	}
}
