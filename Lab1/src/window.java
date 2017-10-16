import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import javax.imageio.*;

public class window extends JFrame{
	private JTextField route;
	private JTabbedPane jp2;
	private JLabel jl21=new JLabel();
	public window()
	{
		JPanel jp1=new JPanel();
		
		JButton getroute=new JButton("选择文件");
		getroute.setSize(10,10);
		getroute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser=new JFileChooser(".\\");
				fileChooser.showOpenDialog(null);
				String s=fileChooser.getSelectedFile().getPath();
				route.setText(s);
				try 
				{
					jp2.setVisible(true);
					readin.getin(s);
					readin.showDirectedGraph();
					//
					readin.getBridgeWords();
				}catch(FileNotFoundException ee)
				{
					JOptionPane.showMessageDialog(null,"Wrong","文件不存在!",JOptionPane.ERROR_MESSAGE);
				}catch(IOException ee)
				{
					JOptionPane.showMessageDialog(null,"Wrong","文件异常!",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		route=new JTextField(50);
		route.setEditable(false);
		
		jp1.add(getroute);
		jp1.add(route);
		
		
		jp2=new JTabbedPane();
		JPanel jp21=new JPanel();
		JPanel jp22=new JPanel();
		JPanel jp23=new JPanel();
		JPanel jp24=new JPanel();
		JPanel jp25=new JPanel();
		jp2.add(jp21,"展示有向图");
		jp2.add(jp22,"查询桥接词");
		jp2.add(jp23,"生成新文本");
		jp2.add(jp24,"计算最短路径");
		jp2.add(jp25,"随机游走");
		
		JButton jb211=new JButton("显示有向图");
		JButton jb212=new JButton("隐藏");
		jb211.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				try{
					jl21.setIcon(new ImageIcon(ImageIO.read(new File("show.gif")))); 
				}catch(IOException ee)
				{
					ee.printStackTrace();
				}
			}
		});
		jb212.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jl21.setIcon(null);
			}
		});
		jp21.setLayout(new BorderLayout());
		jp21.add(jb211,BorderLayout.NORTH);
		jp21.add(jb212,BorderLayout.SOUTH);
		//ImageIcon icon = new ImageIcon("dotGif.gif");
		//icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth(),
		//icon.getIconHeight(), Image.SCALE_DEFAULT));
		//jl21.setBounds(0, 0, 960, 960);
		//jl21.setHorizontalAlignment(0);
		//jl21.setIcon(icon);
		JScrollPane js21=new JScrollPane(jl21);
		jp21.add(js21,BorderLayout.CENTER);
		
		JPanel jpp22=new JPanel();
		JLabel jl221=new JLabel("Word1",SwingConstants.RIGHT);
		JLabel jl222=new JLabel("Word2",SwingConstants.RIGHT);
		JTextField jt221=new JTextField();
		JTextField jt222=new JTextField();
		JButton jb22=new JButton("GO!");
		jpp22.setLayout(new GridLayout(3,2));
		jpp22.add(jl221);
		jpp22.add(jt221);
		jpp22.add(jl222);
		jpp22.add(jt222);
		jpp22.add(jb22);
		JLabel jt223=new JLabel();
		jb22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String word1=jt221.getText();
				String word2=jt222.getText();
				jt223.setText(readin.queryBridgeWords(word1, word2));
			}
		});
		jp22.setLayout(new BorderLayout());
		jp22.add(jpp22,BorderLayout.NORTH);
		jp22.add(jt223,BorderLayout.CENTER);
		
		JPanel jpp23=new JPanel();
		JTextField jt23=new JTextField();
		JLabel jl23=new JLabel();
		JButton jb23=new JButton("GO!");
		jpp23.setLayout(new GridLayout(2,1));
		jpp23.add(jt23);
		jpp23.add(jb23);
		jb23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String text=jt23.getText();
				jl23.setText(readin.generateNewText(text));
			}
		});
		jp23.setLayout(new BorderLayout());
		jp23.add(jpp23,BorderLayout.NORTH);
		jp23.add(jl23,BorderLayout.CENTER);
		
		JPanel jpp24=new JPanel();
		jpp24.setLayout(new GridLayout(4,2));
		JLabel jl241=new JLabel("Word1",SwingConstants.RIGHT);
		JLabel jl242=new JLabel("Word2",SwingConstants.RIGHT);
		JLabel jl243=new JLabel();
		JTextField jt241=new JTextField();
		JTextField jt242=new JTextField();
		JTextField jt243=new JTextField();
		JButton jb241=new JButton("GO!");
		JButton jb242=new JButton("清除有向图");
		JButton jb243=new JButton("显示有向图");
		JScrollPane js24=new JScrollPane(jl243);
		//jl241.setText();
		//jl242.setText();
		jt243.setEditable(false);
		jb241.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jt243.setText(readin.calcShortestPath(jt241.getText(),jt242.getText()));
			}
		});
		jb242.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jl243.setIcon(null);
			}
		});
		jb243.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//ImageIcon icon = new ImageIcon("calc.gif");
				//jl243.setIcon(icon);
				try{
					jl243.setIcon(new ImageIcon(ImageIO.read(new File("calc.gif")))); 
				}catch(IOException ee)
				{
					ee.printStackTrace();
				}
			}
		});
		jpp24.add(jl241);
		jpp24.add(jt241);
		jpp24.add(jl242);
		jpp24.add(jt242);
		jpp24.add(jb241);
		jpp24.add(jb242);
		jpp24.add(jb243);
		jp24.setLayout(new BorderLayout());
		jp24.add(jpp24,BorderLayout.NORTH);
		jp24.add(js24,BorderLayout.CENTER);
		jp24.add(jt243,BorderLayout.SOUTH);
		
		
		JLabel jl251=new JLabel();
		JLabel jl252=new JLabel();
		JScrollPane js25=new JScrollPane(jl251);
		JButton jb251=new JButton("GO!");
		JButton jb252=new JButton("SHOW");
		JButton jb253=new JButton("RESET");
		jp25.setLayout(new BorderLayout());
		JPanel jpp25=new JPanel();
		jpp25.setLayout(new GridLayout(1,3));
		jpp25.add(jb251);
		jpp25.add(jb252);
		jpp25.add(jb253);
		jp25.add(jpp25,BorderLayout.NORTH);
		jp25.add(js25,BorderLayout.CENTER);
		jp25.add(jl252,BorderLayout.SOUTH);
		jb251.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jl252.setText(readin.randomWalk());
				readin.deleteFile("random.gif");
				GraphViz gViz=new GraphViz("random", "D:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe");
		        gViz.start_graph();
		        //gViz.addln("A->B;");
		        String[] xx=new String[]{"black","red"};
		        for(int i=1;i<=readin.n;i++)
		        	for(int j=1;j<=readin.n;j++)
		        		if(i!=j && readin.tab[i][j]>0)
		        			gViz.addln(readin.ss[i]+"->"+readin.ss[j]+"["+"label="+readin.tab[i][j]+" color=\""+xx[readin.vis[i][j]]+"\""+"]"+";");
		        gViz.end_graph();
		        try {
		            gViz.run();
		        } catch (Exception ee) {
		            ee.printStackTrace();
		        }
			}
		});
		jb252.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
		        //jl25.setIcon(new ImageIcon("random.gif"));
		        try{
					jl251.setIcon(new ImageIcon(ImageIO.read(new File("random.gif")))); 
				}catch(IOException ee)
				{
					ee.printStackTrace();
				}
		        
		        File output=new File(".\\output.txt");
				readin.deleteFile("output.txt");
				try
				{
					output.createNewFile();
					FileWriter fileWriter=new FileWriter(output);
					fileWriter.write(readin.randomroute);
					fileWriter.close();
				}catch(IOException ee)
				{
					ee.printStackTrace();
				}
			}
		});
		jb253.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				for(int i=0;i<100;i++)
					for(int j=0;j<100;j++)
						readin.vis[i][j]=0;
				Random ra=new Random();
				readin.cur=ra.nextInt(readin.n)+1;
				readin.randomroute=readin.ss[readin.cur];
				jl252.setText(readin.randomroute);
			}
		});
		
		jp2.setVisible(false);
		
		setLayout(new BorderLayout());
		add(jp1,BorderLayout.NORTH);
		add(jp2,BorderLayout.CENTER);
		setTitle("Lab1");
		setSize(960,960);
		setResizable(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		window obj=new window();
	}
}
