import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

import javax.swing.*;

public class My2048 extends JFrame {

	private JPanel contentPane;
	private Block[] blocks;
	private HashSet<Integer> roundDoubled;
	
	// 要明白这个程序是不会退出的，除非点了x，否则一直会listen一些东西，只要按键盘就会有响应。
	
	/**
	 * Create the frame.
	 */
	public My2048() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("2048");
		setSize(400, 400);//设定窗口大小
		contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(4, 4, 5, 5));
		setContentPane(contentPane);
		
		// 增加组件，把block加入进去，这个部分是开始游戏的部分
		blocks = new Block[16];
		for (int i = 0; i < 16; ++i) {
			blocks[i] = new Block();
			// 这里少了两条语句，比如添加不透明标签，不加不知道会如何
		    blocks[i].setHorizontalAlignment(JLabel.CENTER);// 不透明的标签
		    blocks[i].setOpaque(true);
			contentPane.add(blocks[i]);
		}	
		for (int i = 0; i < 2; ++i) {
			addNewNum();
		}
		roundDoubled = new HashSet<Integer>();
	}
	
	public boolean judgeCanAdd() {
		for (int i = 0; i < 16; ++i)
			// 为0说明可以加入，有空位
			if (blocks[i].getValue() == 0)
				return true;
		return false;
	}
	
	public void addNewNum() {
		if (judgeCanAdd()) {
			while (true) {
				int num = (int)(Math.random() * 16);
				
				if (blocks[num].getValue() == 0) {
					if (Math.random() <= 0.6)
						blocks[num].setValue(2);
					else
						blocks[num].setValue(4);
					break;
				}
			}
		}
	}
	
	/**
	 * keyboard listener
	 */
	
	public KeyAdapter myAdapter = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				if (dealWithUP()) {
					if (judgeCanAdd())
						addNewNum();
					judgeOver();
				}
				break;
			case KeyEvent.VK_DOWN:
				if (dealWithDown()) {
					if (judgeCanAdd())
						addNewNum();
					judgeOver();
				}
				break;
			case KeyEvent.VK_LEFT:
				if (dealWithLeft()) {
					if (judgeCanAdd())
						addNewNum();
					judgeOver();
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (dealWithRight()) {
					if (judgeCanAdd())
						addNewNum();
					judgeOver();
				}
				break;
				
			default:
				break;
			}
		}
	};
	
	
	// 0  1  2  3
	// 4  5  6  7
	// 8  9  10 11
	// 12 13 14 15
	/**
	 * 该函数给定position，计算该位置的数在up操作下的落点，该函数需要按顺序调用，才不会出现漏洞。
	 * @param position
	 * @return
	 */
	public int judgeUP(int position) {
		for (int i = position - 4; i >= 0; i = i - 4) {
			if (blocks[i].getValue() != 0)
				if (blocks[i].getValue() == blocks[position].getValue() && (!roundDoubled.contains(i)))
					return i;
				else
					return i + 4;
		}
		// 只要发现不为0就会return，没有return说明都是0，直接放到最上面
		return position % 4;
	}
	public int judgeDown(int position) {
		int i;
		for (i = position + 4; i < 16; i += 4) {
			if (blocks[i].getValue() != 0)
				if (blocks[i].getValue() == blocks[position].getValue() && (!roundDoubled.contains(i)))
					return i;
				else
					return i - 4;
		}
		// 该函数大体同上
		return i - 4;
	}
	
	public int judgeLeft(int position) {
		int foundation = ((int)(position / 4)) * 4;
		for (int i = position - 1; i >= foundation; --i) {
			if (blocks[i].getValue() != 0)
				// 要求在当前轮没有被double过
				if (blocks[i].getValue() == blocks[position].getValue() && (!roundDoubled.contains(i)))
					return i;
				else
					return i + 1;
		}
		return foundation;
	}
	public int judgeRight(int position) {
//		System.out.println(position);
//		System.out.println((int)(Math.ceil(position / 4.0)));
		int ceiling = ((int)(position / 4)) * 4 + 4;
//		System.out.println("ceiling" + ceiling);
		for (int i = position + 1; i < ceiling; ++i) {
			if (blocks[i].getValue() != 0)
				if (blocks[i].getValue() == blocks[position].getValue() && (!roundDoubled.contains(i)))
					return i;
				else
					return i - 1;
		}
		return ceiling - 1;
	}
	
	public boolean dealWithUP() {
		boolean hasChange = false;
		roundDoubled.clear();
		for (int i = 0; i < 16; ++i) {
			if (blocks[i].getValue() == 0)
					continue;
			int position = judgeUP(i);
			if (position != i) {
				hasChange = true;
				if (blocks[position].getValue() == blocks[i].getValue()) {
					blocks[position].setValue(2 * blocks[position].getValue());
					roundDoubled.add(position);
					blocks[i].setValue(0);
				}
				else {
					if (blocks[position].getValue() != 0)
						System.out.println("dealwithup, not 0!");
					blocks[position].setValue(blocks[i].getValue());
					blocks[i].setValue(0);
				}	
			}
		}
		return hasChange;
	}
	
	public boolean dealWithDown() {
		boolean hasChange = false;
		roundDoubled.clear();
		for (int i = 15; i >= 0; --i) {
			if (blocks[i].getValue() == 0)
					continue;
			int position = judgeDown(i);
			if (position != i) {
				hasChange = true;
				if (blocks[position].getValue() == blocks[i].getValue()) {
					blocks[position].setValue(2 * blocks[position].getValue());
					roundDoubled.add(position);
					blocks[i].setValue(0);
				}	
				else {
					if (blocks[position].getValue() != 0)
						System.out.println("dealwithDown, not 0!");
					blocks[position].setValue(blocks[i].getValue());
					blocks[i].setValue(0);
				}	
			}
		}
		return hasChange;
	}
	
	public boolean dealWithLeft() {
		boolean hasChange = false;
		roundDoubled.clear();
		for (int i = 0; i < 16; i += 4) {
			int ceiling = ((int)(i / 4)) * 4 + 4;
			for (int j = i; j < ceiling; ++j) {
				if (blocks[j].getValue() == 0)
					continue;
				int position = judgeLeft(j);
				System.out.println(j + " " + position);
				if (position != j) {
					hasChange = true;
					if (blocks[position].getValue() == blocks[j].getValue()) {
						blocks[position].setValue(2 * blocks[position].getValue());
						roundDoubled.add(position);
						blocks[j].setValue(0);
					}
					else {
						if (blocks[position].getValue() != 0)
							System.out.println("dealwithLeft, not 0!");
						blocks[position].setValue(blocks[j].getValue());
						blocks[j].setValue(0);
					}
				}
			}
		}
		return hasChange;
	}
	
	public boolean dealWithRight() {
		boolean hasChange = false;
		roundDoubled.clear();
		for (int i = 3; i < 16; i += 4) {
			int foundation = ((int)(i / 4)) * 4;
			for (int j = i; j >= foundation; --j) {
				if (blocks[j].getValue() == 0)
					continue;
				int position = judgeRight(j);
//				System.out.println(i + " " + j + " " +position);
				if (position != j) {
					hasChange = true;
					if (blocks[position].getValue() == blocks[j].getValue()) {
						blocks[position].setValue(2 * blocks[position].getValue());
						roundDoubled.add(position);
						blocks[j].setValue(0);
					}
					else {
						if (blocks[position].getValue() != 0)
							System.out.println("dealwithRight, not 0!");
						blocks[position].setValue(blocks[j].getValue());
						blocks[j].setValue(0);
					}
				}
			}
		}
		return hasChange;
	}
	
	public void judgeOver() {
		int[] position = new int[4];
		for (int i = 0; i < 16; ++i) {
			if (blocks[i].getValue() == 4096) {
				blocks[0].setText("Y");
			    blocks[1].setText("O");
			    blocks[2].setText("U");
			    blocks[12].setText("W");
			    blocks[13].setText("I");
			    blocks[14].setText("N");
			    blocks[4].setText("New");
			    blocks[5].setText("GAME");
			    blocks[4].addMouseListener(new MouseAdapter() {
			    	@Override
			    	public void mousePressed(MouseEvent e) {
			    		restart();
			    	}
				});
			    blocks[5].addMouseListener(new MouseAdapter() {
			    	@Override
			    	public void mousePressed(MouseEvent e) {
			    		restart();
			    	}
				});
			}
			position[0] = judgeUP(i);
			position[1] = judgeDown(i);
			position[2] = judgeLeft(i);
			position[3] = judgeRight(i);
			for (int j = 0; j < 4; ++j)
				if (position[j] != i)
					return;
		}
		blocks[4].setText("G");
		blocks[5].setText("A");
		blocks[6].setText("M");
		blocks[7].setText("E");
		blocks[8].setText("O");
		blocks[9].setText("V");
		blocks[10].setText("E");
		blocks[11].setText("R");
		
		blocks[12].setText("New");
	    blocks[13].setText("GAME");
	    blocks[12].addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mousePressed(MouseEvent e) {
	    		restart();
	    	}
		});
	    blocks[13].addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mousePressed(MouseEvent e) {
	    		restart();
	    	}
		});
	}
	
	public void restart() {
		for (int i = 0; i < 16; ++i)
			blocks[i].setValue(0);
		for (int i = 0; i < 2; ++i)
			addNewNum();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					My2048 frame = new My2048();
//					JFrame.setDefaultLookAndFeelDecorated(true);//设定Frame的缺省外观
					frame.addKeyListener(frame.myAdapter);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	

}
