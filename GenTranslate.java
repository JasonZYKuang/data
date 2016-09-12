import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;


public class GenTranslate {

	/**
	 * @param args
	 */
	public static String inputFolder="C:/Users/43853881/Desktop/ionic/数据包/翻译包/";
	public static String outputFolder = "C:/Users/43853881/Desktop/ionic/数据包-输出/翻译包/";
	public static String outputFile = "trans_";
	
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
				String langName = translateFolders[f].getName();
				File targetFolder = new File(outputFolder+langName);
				if(!targetFolder.exists())targetFolder.mkdir();
				
				File outFile = new File(outputFolder+langName+"/"+outputFile+langName+".json");
				
				List<String> record = new ArrayList<String>();
				record.add("[");
				File folder = new File(inputFolder+langName);
				File[] files = folder.listFiles();
				for(int i=0;i<files.length;i++){
					/*{"name": "你",
					    "value": {
					      "val": "You",
					      "audio": "data/audio/dialogues/1.wav"}
					  },*/
					String filename = files[i].getName();
					String prefix = filename.substring(0,filename.lastIndexOf("."));
					String postfix = filename.substring(filename.lastIndexOf("."),filename.length());
					
					record.add(" {\"name\":\""+prefix+"\",");
					record.add(" \"value\":{");
					record.add("     \"val\":\""+prefix+"\",");
					/****
					 * 重命名注释
					record.add("     \"audio\":\"data/audio/dialogues/"+i+postfix+"\"}");
					Copy(files[i].getAbsolutePath(),outputFolder+langName+"/"+i+postfix);
					   
					record.add("     \"audio\":\"data/audio/dialogues/"+filename+"\"}");
					Copy(files[i].getAbsolutePath(),outputFolder+langName+"/"+filename);
					 */
					record.add("     \"audio\":\"data/audio/dialogues/"+filename+"\"}");
					Copy(files[i].getAbsolutePath(),outputFolder+langName+"/"+filename);
					
					if(i != files.length-1)
						record.add(" },");
					else
						record.add(" }");	
				}
				record.add("]");
				try {
					FileUtils.writeLines(outFile, record);
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Finished Analysis for folder [ "+langName+" ]");
				
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
