package MathFormulas;

public class GeneralMathFormulas {
    static int a, b;
    
    //For 2 args
    GeneralMathFormulas(int a, int b) {
        this.a = a;
        this.b = b;
    }
    public int add() {
        return a + b;
    }
    
    public int subtract() {
        return a - b;
    }

    public int multiply() {
        return a * b;
    }
    
    public int divide() {
        return a / b;
    }
    
    public int modulus() {
        return a % b;
    }
    
    public double power() {
		int answer = 1;
    	for(int i = 0 ; i < b; i++) {
			answer *= a;
		}
    	return answer;
    }
    
    public static void main(String[] args) {
    	
    	GeneralMathFormulas example = new GeneralMathFormulas(8,2);
    	System.out.println(example.add());
    	System.out.println(example.subtract());
    	System.out.println(example.power());
    	
    	
    }
}
