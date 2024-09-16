public class Polynomial {
    public double[] coefficients;

    public Polynomial(){
        this.coefficients = new double[10];
        for(int i = 0; i < this.coefficients.length; i++){
            this.coefficients[i] = 0;
        }
    }

    public Polynomial(double[] coefficients){
        this.coefficients = new double[10];
        for(int i = 0; i < 10; i++){
            if(i < coefficients.length){
                this.coefficients[i] = coefficients[i];
            }
            else{
                this.coefficients[i] = 0;
            }
        }
    }

    public Polynomial add(Polynomial p1){
        double[] new_coefficients = new double[10];
        for(int i = 0; i < p1.coefficients.length; i++){
            new_coefficients[i] = p1.coefficients[i] + this.coefficients[i];
        }
        return new Polynomial(new_coefficients);
    }

    public double evaluate(double x){
        double res = 0;
        for(int i = 0; i < this.coefficients.length; i++){
            res += this.coefficients[i] * this.exponentiate(x, i);
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