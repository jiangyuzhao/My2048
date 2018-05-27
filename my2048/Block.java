import javax.swing.*;
import java.awt.*;

public class Block extends JLabel{
	private int value;
	public Block() {
		this.value = 0;
		setFont(new Font("font", Font.PLAIN, 40));//设定字体
		setBackground(Color.gray);//设定初始背景颜色为灰色
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
		if (this.value != 0)
			setText(String.valueOf(this.value));
		else
			setText("");
		setColor();
	}
	
	public void setColor() {
		switch(this.value) {
			case 0:
		      setBackground(Color.gray);
		      break;
		    case 2:
		      setBackground(new Color(238, 228, 218));
		      break;
		    case 4:
		      setBackground(new Color(238, 224, 198));
		      break;
		    case 8:
		      setBackground(new Color(243, 177, 116));
		      break;
		    case 16:
		      setBackground(new Color(243, 177, 116));
		      break;
		    case 32:
		      setBackground(new Color(248, 149, 90));
		      break;
		    case 64:
		      setBackground(new Color(249, 94, 50));
		      break;
		    case 128:
		      setBackground(new Color(239, 207, 108));
		      break;
		    case 256:
		      setBackground(new Color(239, 207, 99));
		      break;
		    case 512:
		      setBackground(new Color(239, 203, 82));
		      break;
		    case 1024:
		      setBackground(new Color(239, 199, 57));
		      break;
		    case 2048:
		      setBackground(new Color(239, 195, 41));
		      break;
		    case 4096:
		      setBackground(new Color(255, 60, 57));
		      break;
		    default:
		    	 System.out.println("value error");
		}
	}
	
	
}
