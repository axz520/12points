import java.util.*;

public class Managment {
	public static void main(String []arg)
	{
		Scanner scan = new Scanner(System.in);
		Game24 myGame = new Game24();
		myGame.initGame();
		System.out.println(myGame);
		String s = scan.next();
		
		if(myGame.checkAns('('+s+')')) {
			System.out.println("win");
		}
		else {
			System.out.println("lose");
		}
		System.out.println("one ans is:"+myGame.oneAns);
	}
}

class Game24 {
	ClassOf24[] num = new ClassOf24[4];
	String oneAns;
	boolean hasAns;
	
	Game24() {
		this.hasAns = false;
	}
	
	void initGame() {
		while(true) {
			for(int i = 0; i < 4; i++) {
				double data = (int)(10*Math.random());
				this.num[i] = new ClassOf24(data, ""+(int)data);
			}
			/*this.num[0] = new ClassOf24(7, ""+7);
			this.num[1] = new ClassOf24(6, ""+6);
			this.num[2] = new ClassOf24(5, ""+5);
			this.num[3] = new ClassOf24(1, ""+1);*/
			
			hasAns(this.num);
			
			if(this.hasAns)
				break;
		}
	}
	
	private void hasAns(ClassOf24[] num) {
		if(this.hasAns) return;
		
		if(num.length == 1 && num[0].number == 24) {
			this.oneAns = num[0].WayToMakeNum;
			this.hasAns = true;
			return;
		}
		
		for(int i = 0; i < num.length && !this.hasAns; i++) {
			for(int j = i + 1; j < num.length && !this.hasAns; j++) {
				ClassOf24[] temp = new ClassOf24[num.length - 1];
				for(int k = 0, u = 0; k < num.length; k++) {
					if(k != i && k != j) {
						temp[u++] = new ClassOf24(num[k].number,num[k].WayToMakeNum);
					}
				}
				double data;
				String strings;
				data = num[i].number + num[j].number;
				strings = "(" + num[i].WayToMakeNum + "+" + num[j].WayToMakeNum + ")";
				temp[temp.length - 1] = new ClassOf24(data, strings);
				hasAns(temp);
				
				data = num[i].number - num[j].number;
				strings = "(" + num[i].WayToMakeNum + "-" + num[j].WayToMakeNum + ")";
				temp[temp.length - 1] = new ClassOf24(data, strings);
				hasAns(temp);
				
				data = num[j].number + num[i].number;
				strings = "(" + num[j].WayToMakeNum + "-" + num[i].WayToMakeNum + ")";
				temp[temp.length - 1] = new ClassOf24(data, strings);
				hasAns(temp);
				
				data = num[i].number * num[j].number;
				strings = "(" + num[i].WayToMakeNum + "*" + num[j].WayToMakeNum + ")";
				temp[temp.length - 1] = new ClassOf24(data, strings);
				hasAns(temp);
				
				if(num[j].number != 0) {
					data = num[i].number / num[j].number;
					strings = "(" + num[i].WayToMakeNum + "/" + num[j].WayToMakeNum + ")";
					temp[temp.length - 1] = new ClassOf24(data, strings);
					hasAns(temp);
				}
				
				if(num[i].number != 0) {
					data = num[j].number / num[i].number;
					strings = "(" + num[j].WayToMakeNum + "/" + num[i].WayToMakeNum + ")";
					temp[temp.length - 1] = new ClassOf24(data, strings);
					hasAns(temp);
				}
				
			}
		}
			
	}
	
	boolean checkAns(String s) {
		// 检测数字是否全用
		if(!checkNumberAllUsed(s)) {
			return false;
		}
		
		SolveExpression solve = new SolveExpression(s);
		solve.calStack();
		
		return solve.getIf24();
	}
	
	public String toString() {
		String s = new String();
		
		for(int i = 0; i < num.length; i++) {
			s = s + (int)this.num[i].number + ' ';
		}
		return s;
	}
	
	boolean checkNumberAllUsed(String s) {
		int number = 0;
		for(int i = 0; i < s.length(); i++) {
			char temp = s.charAt(i);
			if(temp >= '0' && temp <= '9') {
				if(i + 1 < s.length() && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') {
					return false;
				}
				
				if(i - 1 >= 0 && s.charAt(i - 1) >= '0' && s.charAt(i - 1) <= '9') {
					return false;
				}
				
				boolean notInNums = true;

				for(var k : this.num) {
					if(k.number == Integer.parseInt(""+temp)) {
						++number;
						notInNums = false;
						break;
					}
				}
				if(notInNums)
					return false;
			}
			else {
				if(temp != '+' && temp != '-' && temp != '/' && temp != '*' && temp != '(' && temp != ')')
					return false;
			}
		}
		return number == 4;
	}
}

class ClassOf24{
	double number;
	String WayToMakeNum;
	
	ClassOf24(double number, String s){
		this.number = number;
		this.WayToMakeNum = s;
	}
}

class SolveExpression{
	private Stack <Double> number;
	private Stack <Character> character;
	private String shouldJudge;
	private boolean ok;
	
	SolveExpression(String s){
		this.number = new Stack <>();
		this.character = new Stack <>();
		this.shouldJudge = s;
		this.ok = true;  // 检测表达式是否合法
	}
	
	int getPriority(char opt)
	{
	    int priority = 0;
	    if(opt == '*' || opt == '/')
	        priority = 2;
	    else if(opt == '+' || opt == '-')
	        priority = 1;
	    return priority;
	}
	
	void calStack() {
		for(int i = 0; i < this.shouldJudge.length() && this.ok; i++) {
			char temp = this.shouldJudge.charAt(i);
			if(temp == '(') {
				this.character.push(temp);
			}
			else if(temp <= '9' && temp >= '0') {
				this.number.push(Double.parseDouble(""+temp));
			}
			else if(temp == '+' || temp == '-' || temp == '*' || temp == '/') {
				while(this.getPriority(temp) <= this.getPriority(this.character.peek())) {
					cal();
				}
				this.character.push(temp);
			}
			else if(temp == ')') {
				char s = this.character.peek();
				while(s != '(' && this.ok) {
					cal();
					s = this.character.peek();
				}
				this.character.pop();
			}
			else break;
		}
	}
	
	private void cal() {
		double d1 = 0, d2 = 0;
		if(!this.number.empty()) d2 = this.number.pop();
		else {
			this.ok = false;
			return;
		}
		if(!this.number.empty()) d1 = this.number.pop();
		else {
			this.ok = false;
			return;
		}
		
		if(this.character.empty()) {
			this.ok = false;
			return;
		}
		switch(this.character.pop()) {
			case '+': d1 += d2;break;
			case '-': d1 -= d2;break;
			case '*': d1 *= d2;break;
			case '/': {
				if(d2 == 0) {
					this.ok = false;
					return;
				}
				d1 /= d2;
			}
		}
		this.number.push(d1);
	}
	
	boolean getIf24() {
		return this.number.pop() == 24 && this.number.empty() && this.character.empty() && this.ok;
	}
}