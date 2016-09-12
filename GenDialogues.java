import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;


public class GenDialogues {

	/**
	 * @param args
	 */
	public static String inputFolder="C:/Users/43853881/Desktop/ionic/数据包/情景包/";
	public static String outputFolder = "C:/Users/43853881/Desktop/ionic/数据包-输出/情景包/";
	public static String outputFile = "dialogue_";
	
	public static void main(String[] args) {
		File outFolder = new File(outputFolder);
		if(!outFolder.exists())outFolder.mkdir();
		
		//StringBuffer sb = new StringBuffer();
		File inputfolder = new File(inputFolder);
		if(!inputfolder.isDirectory()){
			System.out.println("It's not a folder");
		}else{
			File[] translateFolders = inputfolder.listFiles();
			for(int f=0;f<translateFolders.length;f++){
				//Start analysis folder
				String langName = translateFolders[f].getName();
				File targetFolder = new File(outputFolder+langName);
				if(!targetFolder.exists())targetFolder.mkdir();
				File outFile = new File(outputFolder+langName+"/"+outputFile+langName+".json");
				
				File langFolder = new File(inputFolder+langName);
				List<String> record = new ArrayList<String>();
				record.add("{");
				record.add("   \"results\": [");
				
				if(langFolder.isDirectory()){
					File[] dialogueList = langFolder.listFiles();
					for(int d=0;d<dialogueList.length;d++){
						String dialogueName = dialogueList[d].getName();
						
						record.add("     {");
						record.add("        \"id\": \""+d+"\",");
						record.add("        \"name\": \""+dialogueName+"\",");
						record.add("        \"subList\": [");
						
						
						/***
						 * 新建文件夹。
						File outputDialogue = new File(outputFolder+langName+"/"+d); //以数字命名
						 * 
						File outputDialogue = new File(outputFolder+langName+"/"+dialogueName); //以文件夹命名
						 */
						File outputDialogue = new File(outputFolder+langName+"/"+dialogueName); //以文件夹命名
						
						if(!outputDialogue.exists())outputDialogue.mkdir();
						
						File folder = new File(inputFolder+langName+"/"+dialogueName);
						File[] files = folder.listFiles();
						for(int i=0;i<files.length;i++){
							String filename = files[i].getName();
							String prefix = filename.substring(0,filename.lastIndexOf("."));
							String postfix = filename.substring(filename.lastIndexOf("."),filename.length());
							
							record.add("          {");
							record.add("             \"subid\": \""+i+"\",");
							record.add("             \"subName\": \""+prefix+"\",");
							
							/****
							 * 重命名注释
							  record.add("             \"subAudio\": \"../www/data/audio/"+d+"/"+i+postfix+"\"");
							  Copy(files[i].getAbsolutePath(),outputFolder+langName+"/"+d+"/"+i+postfix);
							   
							  record.add("             \"subAudio\": \"../www/data/audio/"+dialogueName+"/"+filename+"\"");
							  Copy(files[i].getAbsolutePath(),outputFolder+langName+"/"+dialogueName+"/"+filename);
							 */
							  record.add("             \"subAudio\": \"../www/data/audio/"+dialogueName+"/"+filename+"\"");
							  Copy(files[i].getAbsolutePath(),outputFolder+langName+"/"+dialogueName+"/"+filename);
							
							if(i != files.length-1)
								record.add("          },");
							else{
								record.add("          }");
								record.add("        ]");
							}
						}
						
						if(d !=dialogueList.length-1){
							record.add("     },");
						}else{
							record.add("     }");
						}
						
					}
					
					record.add("  ]");
					record.add("}");
					//Write output file
					
					try {
						FileUtils.writeLines(outFile, record);
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println("Finished Analysis for folder [ "+langName+" ]");
				}
				
			}
			
		}

	}
	
	public static void Copy(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
