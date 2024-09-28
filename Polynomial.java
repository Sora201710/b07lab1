import java.util.HashMap;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
    public double[] coefficients;
    public int[] exponents;

    public Polynomial(){
        this.coefficients = new double[5];
        this.exponents = new int[5];
        for(int i = 0; i < this.coefficients.length; i++){
            this.coefficients[i] = 0;
            this.exponents[i] = i;
        }
    }

    public Polynomial(double[] coefficients, int[] exponents){
        this.coefficients = coefficients;
        this.exponents = exponents;
    }
    public Polynomial(File file) throws IOException{
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        String line = "";
        try{
            line = reader.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Polynomial p = this.stringToPolynomial(line);
        this.coefficients = p.coefficients;
        this.exponents = p.exponents;
    }
    public Polynomial stringToPolynomial(String str){
        String editedStr = "";
        for(int i = 0; i < str.length(); i++){
            editedStr += str.charAt(i);
            if (str.charAt(i) == '-'){
                editedStr += "]";
            }    
        }
        String[] tokens = editedStr.split("-|\\+");
        double[] coefficients = new double[tokens.length];
        int[] exponents = new int[tokens.length];
        double c = 0;
        int ex = 0;
        for(int i = 0; i < tokens.length; i++){
            if(!tokens[i].contains("x")){
                if(tokens[i].contains("]")){
                    c = Double.parseDouble(String.valueOf(tokens[i].charAt(1)));
                    c *= -1;
                }
                else{
                    c = Double.parseDouble(tokens[i]);
                }
                ex = 0;
            }
            else{
                ex = Integer.parseInt(tokens[i].split("x")[1]);
                if(tokens[i].split("x")[0].contains("]")){
                    c =  Double.parseDouble(String.valueOf(tokens[i].split("x")[0].charAt(1)));
                    c *= -1;
                }
                else{
                    c = Double.parseDouble(tokens[i].split("x")[0]);
                }
            }
            coefficients[i] = c;
            exponents[i] = ex;
        }
        return new Polynomial(coefficients, exponents);
    }
    public String polynomialToString(Polynomial p){
        String res = "";
        for(int i = 0; i < p.coefficients.length; i++){
            if(p.coefficients[i] >= 0 && i > 0){
                res += "+";
            }
            res += String.valueOf(p.coefficients[i]);
            if(p.exponents[i] != 0){
                res += "x" + String.valueOf(p.exponents[i]);
            }
        }
        return res;
    }
    public void saveToFile(String fileName) throws IOException{
        String lineToSave = this.polynomialToString(this);
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter writer = new BufferedWriter(fw);
        try{
            writer.write(lineToSave);
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Polynomial add(Polynomial p1){
        // hashmap that maps exponents to their respective coefficient
        HashMap<Integer, Double> poly_map = new HashMap<>();
        // initialize poly_map for first polynomial
        for(int i = 0; i < p1.exponents.length; i++){
            poly_map.put(p1.exponents[i], p1.coefficients[i]);
        }
        // add coefficients when the exponents from the other polynomial match
        for(int i = 0; i < this.exponents.length; i++){
            if(poly_map.containsKey(this.exponents[i])){
                poly_map.put(this.exponents[i], poly_map.get(this.exponents[i]) + this.coefficients[i]);
            }else{
                poly_map.put(this.exponents[i], this.coefficients[i]);
            }
        }
        // convert map back to polynomial
        double[] new_coefficients = new double[poly_map.size()];
        int[] new_exponents = new int[poly_map.size()];
        int i = 0;
        for(HashMap.Entry<Integer, Double> set: poly_map.entrySet()){
            new_exponents[i] = set.getKey();
            new_coefficients[i] = set.getValue();
            i++;
        }
        return new Polynomial(new_coefficients, new_exponents);
    }
    public Polynomial multiply(Polynomial p){
        HashMap<Integer, Double> new_poly_hash = new HashMap<Integer, Double>();
        int curr_exp = -1;
        double curr_coefficient = -1;
        for(int i = 0; i < p.coefficients.length; i++){
            for(int j = 0; j < this.coefficients.length; j++){
                curr_exp = p.exponents[i] + this.exponents[j];
                curr_coefficient = p.coefficients[i] * this.coefficients[j];
                if(new_poly_hash.containsKey(curr_exp)){
                    new_poly_hash.put(curr_exp, new_poly_hash.get(curr_exp) + curr_coefficient);
                }else {
                    new_poly_hash.put(curr_exp, curr_coefficient);
                }
            }
        }
        // convert back to polynomial
        double[] new_coefficients = new double[new_poly_hash.size()];
        int[] new_exponents = new int[new_poly_hash.size()];
        int i = 0;
        for(HashMap.Entry<Integer, Double> set: new_poly_hash.entrySet()){
            new_exponents[i] = set.getKey();
            new_coefficients[i] = set.getValue();
            i++;
        }
        return new Polynomial(new_coefficients, new_exponents);
    }

    public double evaluate(double x){
        double res = 0;
        for(int i = 0; i < this.coefficients.length; i++){
            res += this.coefficients[i] * this.exponentiate(x, this.exponents[i]);
        }
        return res;
    }

    private double exponentiate(double num, int exp){
        double res = 1;     
        while(exp > 0){
            res = res * num;
            exp--;
        }
        return res;
    }

    public boolean hasRoot(double x){
        return this.evaluate(x) == 0;
    }
}