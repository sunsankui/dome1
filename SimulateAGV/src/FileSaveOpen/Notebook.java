package FileSaveOpen;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.io.*;
public class Notebook extends JFrame implements ActionListener {

	
	JTextArea jta  = null;
	JMenuBar jmb   = null;
	JMenu jm, jm2;
	JMenuItem jmi1,jmi2;
	JScrollPane jsp = null;
	public Notebook()
	{
		jta = new JTextArea();
		jmb = new JMenuBar();
		jm  = new JMenu("文件");
		jm2 = new JMenu("编辑");
		jmi1 = new JMenuItem("打开(O)");
		jmi1.setActionCommand("open");
		jmi1.addActionListener(this);
		
		jmi2 = new JMenuItem("保存");
		jmi2.setActionCommand("save");
		jmi2.addActionListener(this);
		jsp = new JScrollPane(jta);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//jsp.add(jta);
		jm.add(jmi1);
		jm.add(jmi2);
		jmb.add(jm);
		jmb.add(jm2);
		this.add(jsp);
		this.setJMenuBar(jmb);
		

		
		this.setSize(400,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Notebook nb = new Notebook();
	}
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JFileChooser jfc =  new JFileChooser();
		
		jfc.setDialogTitle("NoteBook");
		
		FileWriter fw     = null;
		BufferedWriter bw = null;
		FileReader fr     = null;
		BufferedReader br = null;
		String filepath = "";
		if(arg0.getActionCommand().equals("open"))
		{
			jfc.showOpenDialog(null);
		    filepath = jfc.getSelectedFile().getPath();
			try {
				fr = new FileReader(filepath);
				br = new BufferedReader(fr);
				String s = "";
				String str = "";
				while((s = br.readLine()) != null)
				{
					str += s+"\r\n";
				}
				jta.setText(str);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally
			{
				try {
					fr.close();
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		}else if(arg0.getActionCommand().equals("save"))
		{
			jfc.showSaveDialog(null);
			filepath = jfc.getSelectedFile().getAbsolutePath();
			try {
				fw = new FileWriter(filepath);
				System.out.println(filepath);
				bw = new BufferedWriter(fw);
				String s = jta.getText();
				bw.write(s);
				System.out.println(s);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally
			{
				try {
					bw.close();
					fw.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
	}

}
