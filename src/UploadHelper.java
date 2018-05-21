import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class UploadHelper {

  private String path = "d://stuff//dl";  // jpg folder
  private String webpath = "";    // fodlername on blog.hu
  private int more = 3;   // place of more tag
  
  private String prefix = "<img src=\"http://juve.blog.hu/media/image/";
  private String postfix = "\" alt=\"\" />";
  private static BufferedWriter outputFile = null;
  
  public UploadHelper(String p, String wp, String m) {
    if (p != null)
      path = p;
    
    if (wp != null)
      webpath = wp;
    
    if (!"".equals(webpath) && (!webpath.endsWith("/") || !webpath.endsWith("\\")))
      webpath += "/";
    
    if (m != null)
      try {
        more = Integer.parseInt(m);
      } catch (Exception e) {}
  }
  
  private void process() {
    StringBuffer res = new StringBuffer();
    File[] files = null;
    int counter = 0;
    
    File folder = new File(path);
    if (folder != null && folder.isDirectory())
      files = folder.listFiles();
    else
      return;
    
    // collect file names
    for (File f : files) {
      if (f.getName().endsWith(".jpg"))
        res.append(prefix).append(webpath).append(f.getName()).append(postfix).append("\n");
      counter++;
      if (counter == more)
        res.append("<!--more-->").append("\n");
    }
    
    // open target file
    try {
      outputFile = new BufferedWriter(new FileWriter(new File(path + "//upload.txt")));
    } catch (IOException ie) {
      System.out.println("File létrehozása nem sikerült!");
    }

    // write target file
    try {
      outputFile.write(res.toString());
    } catch (IOException ioe) {
      System.out.println("Hiba a file írása közben!");
    }
    
    // close target file
    try {
      outputFile.close();
    } catch (IOException ioe) {
      System.out.println("Hiba a file bezárásánál!");
    }
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    
    UploadHelper up = null;
    if (args != null && args.length == 3)
      up = new UploadHelper(args[0], args[1], args[2]);
    else
      up = new UploadHelper(null, null, null);

    if (up != null)
      up.process();
  }
  
}
