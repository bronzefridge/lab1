import java.io.*;
import java.util.*;
public class readin {
	public static HashMap<String,String> a=new HashMap<String,String>();
	public static int[][][] rec=new int[100][100][100];
	public static int[][] tab=new int[100][100];
	public static int[][] vis=new int[100][100];
	public static int[][] dis=new int[100][100];
	public static String[] ss=new String[100];
	public static int n=0;
	public static int cur=0;
	public static String randomroute="";
	
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
	
    public static boolean check(String path)
    {
    	File file=new File(path);
    	if(file.isFile()&&file.exists())
    		return true;
    	return false;
    }
    
	public static void showDirectedGraph()
	{
		deleteFile("show.gif");
		GraphViz gViz=new GraphViz("show", "D:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe");
        gViz.start_graph();
        //gViz.addln("A->B;");
        for(int i=1;i<=n;i++)
        	for(int j=1;j<=n;j++)
        		if(i!=j && tab[i][j]>0)
        			gViz.addln(ss[i]+"->"+ss[j]+"["+"label="+tab[i][j]+" color=\"black\""+"]"+";");
        gViz.end_graph();
        try {
            gViz.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void getBridgeWords()
	{
		for(int i=0;i<=n;i++)
			for(int j=0;j<=n;j++)
				for(int k=0;k<=n;k++)
					rec[i][j][k]=0;
		for(int i=1;i<=n;i++)
			for(int j=1;j<=n;j++)
				for(int k=1;k<=n;k++)
					if(tab[j][i]>0 && tab[i][k]>0)
					{
						rec[j][k][0]++;
						rec[j][k][rec[j][k][0]]=i;
					}
		return;
	}
	
	public static String queryBridgeWords(String word1,String word2)
	{
		String res="No "+"\""+word1+"\""+"or "+"\""+word2+"\" in the graph!";
		if(a.get(word1)!=null && a.get(word2)!=null)
		{
			int id1=Integer.parseInt(a.get(word1));
			int id2=Integer.parseInt(a.get(word2));
			if(rec[id1][id2][0]>0)
			{
				res="The bridge words from "+"\""+word1+"\" "+"to "+"\""+word2+"\" ";
				int tot=rec[id1][id2][0];
				if(tot==1)
					res=res+"is: "+ss[rec[id1][id2][tot]];
				else
				{
					res=res+"are:";
					for(int i=1;i<tot;i++)
						res=res+" "+ss[rec[id1][id2][i]]+",";
					res=res+" and "+ss[rec[id1][id2][tot]]+".";
				}
			}
			else
				res="No bridge words from "+"\""+word1+"\""+" to "+"\""+word2+"\"!";
		}
		return res;
	}
	
	public static String generateNewText(String input)
	{
		String word1="";
		String word2="";
		String inputText="";
		String res="";
		for(int i=0;i<input.length();i++)
		{
			char ch=input.charAt(i);
			if((ch>='a' && ch<='z')||(ch>='A' && ch<='Z'))
				inputText=inputText+ch;
			else if((ch>6 && ch<14)||(ch>31 && ch<35)||(ch>38 && ch<42)||ch==58||ch==59||ch==63||(ch>43&&ch<47)||ch==91||ch==93||ch==123||ch==125)
				inputText=inputText+' ';
		}
		int i=0;
		for(i=0;i<inputText.length();i++)
		{
			if(inputText.charAt(i)==' ')
				break;
			res=res+inputText.charAt(i);
			word1=word1+inputText.charAt(i);
		}
		i++;
		for(;i<inputText.length();i++)
		{
			char ch=inputText.charAt(i);
			if(ch==' ')
			{
				//System.out.println(word1);
				//System.out.println(word2);
				if(a.get(word1)!=null && a.get(word2)!=null)
				{
					Random ra=new Random();
					int id1=Integer.parseInt(a.get(word1));
					int id2=Integer.parseInt(a.get(word2));
					System.out.println(rec[id1][id2][0]);
					if(rec[id1][id2][0]>0)
					{
						int tmp=ra.nextInt(rec[id1][id2][0])+1;
						res=res+" "+ss[rec[id1][id2][tmp]]+" "+word2;
					}
					else res=res+" "+word2;
				}
				else res=res+" "+word2;
				word1=word2;
				word2="";
			}
			else word2=word2+ch;
		}
		//System.out.println(word1);
		//System.out.println(word2);
		if(a.get(word1)!=null && a.get(word2)!=null)
		{
			Random ra=new Random();
			int id1=Integer.parseInt(a.get(word1));
			int id2=Integer.parseInt(a.get(word2));
			System.out.println(rec[id1][id2][0]);
			if(rec[id1][id2][0]>0)
			{
				int tmp=ra.nextInt(rec[id1][id2][0])+1;
				res=res+" "+ss[rec[id1][id2][tmp]]+" "+word2;
			}
			else res=res+" "+word2;
		}
		else res=res+" "+word2;
		return res;
	}
	
	public static void getpoint(int u,int v,int distance,int[][] vp)
	{
		if(tab[u][v]==distance)
		{
			vp[u][v]=1;
			return;
		}
		for(int i=1;i<=n;i++)
			if(dis[u][i]+dis[i][v]==dis[u][v])
			{
				getpoint(u,i,dis[u][i],vp);
				getpoint(i,v,dis[i][v],vp);
			}
		return;
	}
	
	public static String calcShortestPath(String word1,String word2)
	{
		if(a.get(word1)!=null && a.get(word2)!=null)
		{
			int id1=Integer.parseInt(a.get(word1));
			int id2=Integer.parseInt(a.get(word2));
			//if(dis[id1][id2]==999999) return "";
			int[][] vp=new int[100][100];
			for(int i=0;i<100;i++)
				for(int j=0;j<100;j++)
					vp[i][j]=0;
			String res="";
			if(dis[id1][id2]!=999999)
			{
				getpoint(id1,id2,dis[id1][id2],vp);
				res=word1;
		        int i=id1;
		        while(i!=id2)
		        {
		        	for(int j=1;j<=n;j++)
		        		if(vp[i][j]==1)
		        		{
		        			res=res+"->"+ss[j];
		        			vp[i][j]=2;
		        			i=j;
		        			break;
		        		}
		        }
			}
	        
	        deleteFile("calc.gif");
			GraphViz gViz=new GraphViz("calc", "D:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe");
	        gViz.start_graph();
	        //gViz.addln("A->B;");
	        String[] color=new String[] {"black","green","red"};
	        for(int i=1;i<=n;i++)
	        	for(int j=1;j<=n;j++)
	        		if(i!=j && tab[i][j]>0)
	        			gViz.addln(ss[i]+"->"+ss[j]+"["+"label="+tab[i][j]+" color=\""+color[vp[i][j]]+"\""+"]"+";");
	        gViz.end_graph();
	        try {
	            gViz.run();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return res;
		}
		return "";
	}
	
	public static int find(int x,int[][] vis)
	{
		int[] to=new int[100];
		to[0]=0;
		for(int i=1;i<=n;i++)
			if(tab[x][i]>0 && vis[x][i]==0)
				to[++to[0]]=i;
		if(to[0]>0)
		{
			Random ra=new Random();
			int tmp=ra.nextInt(to[0])+1;
			vis[x][to[tmp]]=1;
			return to[tmp];
		}
		return -1;
	}
	
	public static String randomWalk()
	{
		int to=0;
		if((to=find(cur,vis))!=-1)
		{
			cur=to;
			randomroute=randomroute+"->"+ss[cur];
			return randomroute;
		}
		return randomroute;
	}
	
	public static void floyd()
	{
		for(int i=1;i<=n;i++)
			for(int j=1;j<=n;j++)
				if(tab[i][j]!=0)
					dis[i][j]=tab[i][j];
				else dis[i][j]=999999;
		for(int k=1;k<=n;k++)
			for(int i=1;i<=n;i++)
				for(int j=1;j<=n;j++)
					if(dis[i][k]+dis[k][j]<dis[i][j])
						dis[i][j]=dis[i][k]+dis[k][j];
		return;
	}
	
	public static int getin(String fileroute)throws FileNotFoundException,IOException
	{
		FileReader inData=new FileReader(fileroute);
		for(int i=0;i<100;i++)
			for(int j=0;j<100;j++)
				tab[i][j]=0;
		a.clear();
		int ch=inData.read();
		String cur="",pre="";
		ss[0]="";
		while(ch!=-1 && n<99)
		{
			if((ch>='a' && ch<='z')||(ch>='A' && ch<='Z'))
				cur=cur+(char)ch;
			else if((ch>6 && ch<14)||(ch>31 && ch<35)||(ch>38 && ch<42)||ch==58||ch==59||ch==63||(ch>43&&ch<47)||ch==91||ch==93||ch==123||ch==125)
			{
				if(cur.length()!=0)
				{
					cur=cur.toLowerCase();
					if(a.get(cur)==null)
					{
						a.put(cur,String.valueOf(++n));
						ss[n]=cur;
						System.out.println(cur+" "+a.get(cur));
					}
					if(pre.length()!=0)
					{
						System.out.println(cur+" "+pre);
						tab[Integer.parseInt(a.get(pre))][Integer.parseInt(a.get(cur))]++;
					}
					pre=cur;
					cur="";
				}
			}
			ch=inData.read();
		}
		if(cur!="")
		{
			a.put(cur,String.valueOf(++n));
			ss[n]=cur;
			System.out.println(cur+" "+a.get(cur));
			tab[Integer.parseInt(a.get(pre))][Integer.parseInt(a.get(cur))]++;
		}
		inData.close();
		floyd();
		return 1;
	}
}
