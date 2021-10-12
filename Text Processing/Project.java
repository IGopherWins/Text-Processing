import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Project {
  static Scanner resetScanner(Path fileName){
    try{
      return new Scanner(fileName);
    }
    catch(java.io.IOException e){
      System.out.println(e);
      System.out.println("Input Output Exception");
      System.exit(2);
    }
    return null;
  }

  static void bubbleSort(String[] s){
    boolean sort = false;
    while(!sort){
      sort = true;
      for(int i = 0; i<s.length-1;i++){
        if(s[i].compareTo(s[i+1]) > 0){
          String temp = s[i];
          s[i] = s[i+1];
          s[i+1] = temp;
          sort = false;
        }
      }
    }
  }

  static boolean search(Path fileName){
    Scanner filescanner = resetScanner(fileName);
    int linecount = 0;
    while(filescanner.hasNextLine()){
      filescanner.nextLine();
      linecount++;
    }
    String [] lines = new String[linecount];
    filescanner = resetScanner(fileName);
    for(int i = 0; i <linecount; i++){
      lines[i] = filescanner.nextLine();
    }
    System.out.println("Please enter a word to be searched, to quit type in EINPUT: ");
    Scanner scanner = new Scanner(System.in);
    String userin = scanner.nextLine();
    if(userin.contentEquals("EINPUT")){
      return false;
    }else{
      boolean matchFound = false;
      for(int i = 0; i<linecount; i++){
        boolean lineMatchfound = false;
        int lineMatch = -1;
        String marker = new String();
        lineMatch = lines[i].toLowerCase().indexOf(userin.toLowerCase().trim());
        int previouslineMatch = 0;
        while(lineMatch != -1){
          for(int j = 0; j<lineMatch-previouslineMatch; j++){
            marker += " ";
          }
          marker += "^";
          lineMatchfound = true;
          matchFound = true;
          previouslineMatch = lineMatch + 1;
          lineMatch = lines[i].toLowerCase().indexOf(userin.toLowerCase().trim(),lineMatch+1);
        }
        if(lineMatchfound){
          System.out.println("Match on line: " + (i+1));
          System.out.println(lines[i]);
          System.out.println(marker);
        }
      }
      if(!matchFound){
        System.out.println("The word " + userin + " is not found in the text file.");
      }
      return true;
    }
  }

  public static void main(String[] args) {
    Path p = Paths.get(args[0]);
    Scanner fileIn = resetScanner(p);
    // Counting the number of words in a file
    int wordcount = 0;
    fileIn.useDelimiter("\\W+");
    while(fileIn.hasNext()){
      fileIn.next();
      wordcount++;
    }
    System.out.println("The total number of words in the file is: " + wordcount);
    // Reset Scanner
    fileIn = resetScanner(p);
    // Print the total number of different words in the file.
    String [] words = new String[wordcount];
    fileIn.useDelimiter("\\W+");
    for(int i = 0; i<words.length; i++){
      words[i] = fileIn.next();
    }

    String[] diffwords = new String[wordcount];
    int diffwordcount = 0;
    for(int i = 0; i<diffwords.length; i++){
      diffwords[i] = new String();
    }
    for(int i = 0; i<words.length; i++){
      boolean unique = true;
      for(int j = 0; j<diffwords.length; j++){
        if(words[i].contentEquals(diffwords[j])){
          unique = false;
        }
      }
      if(unique){
        diffwords[diffwordcount] = words[i];
        diffwordcount++;
      }
    }
    System.out.println("The total number of unique words in the file is: " + diffwordcount);
    // Print all words in ascending order (based on the ASCII code) without duplication.
    String [] uniquewords = new String[diffwordcount];
    for(int i = 0; i<uniquewords.length; i++){
      uniquewords[i] = diffwords[i];
    }

    bubbleSort(uniquewords);

    for(int i =0; i<uniquewords.length; i++){
      System.out.println(uniquewords[i]);
    }
    while(search(p));
  }
}