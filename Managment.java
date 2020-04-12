import java.util.*;

public class Managment {
	public static void main(String []arg)
	{
		Scanner scan = new Scanner(System.in);
		Game24 myGame = new Game24();
		myGame.initGame();
		System.out.println(myGame);
		String s = scan.next();
		
		if(myGame.checkAns(s)) {
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
				int data = (int)(10*Math.random());
				this.num[i] = new ClassOf24(data, ""+data);
			}
			
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
				int data;
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
		Stack <Character> stackNum = new Stack<>();
		
		
		// 检测数字是否全用
		if(!checkNumberAllUsed(s)) {
			return false;
		}
		return true;
	}
	
	public String toString() {
		String s = new String();
		
		for(int i = 0; i < num.length; i++) {
			s = s + num[i].WayToMakeNum + ' ';
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
	int number;
	String WayToMakeNum;
	
	ClassOf24(int number, String s){
		this.number = number;
		this.WayToMakeNum = s;
	}
}