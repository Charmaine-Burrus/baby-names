import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

/**
 * Write a description of BabyBirths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BabyBirths {
    String data = "C:\\Users\\Computer\\Documents\\Coding Practice\\Coursera - Java Programming Solving Problems with Software\\Week 4\\us_babynames_by_year\\";
    
    public void printNames() {
    FileResource fr = new FileResource();
    for (CSVRecord rec : fr.getCSVParser(false)) {
        int numBorn = Integer.parseInt(rec.get(2));
        if (numBorn <= 100) {
        System.out.println("Name " + rec.get(0) + 
                           " Gender " + rec.get(1) +
                           " Num Born " + rec.get(2));
        }
    }
}

public void totalBirths(FileResource fr) {
    int totalBirths = 0;
    int totalNames = 0;
    int maleBirths = 0;
    int maleNames = 0;
    int femaleBirths = 0;
    int femaleNames = 0;
    for (CSVRecord rec : fr.getCSVParser(false)) {
        int numBorn = Integer.parseInt(rec.get(2));
        totalBirths += numBorn;
        totalNames += 1;
        if (rec.get(1).equals("M")) {
            maleBirths += numBorn;
            maleNames += 1;
        }
        else {
            femaleBirths += numBorn;
            femaleNames += 1;
        }
    }
    System.out.println("Total Births = " + totalBirths);
    System.out.println("Female Births = " + femaleBirths);
    System.out.println("Male Births = " + maleBirths);
    System.out.println("Total Names = " + totalNames);
    System.out.println("Female Names = " + femaleNames);
    System.out.println("Male Names = " + maleNames);    
}

public int getRank(int year, String name, String gender) {
    int currentRank = 0;
    FileResource fr = new FileResource(data + "yob" + year + ".csv");
    CSVParser parser = fr.getCSVParser(false);
    for (CSVRecord row : parser) {
        if (row.get(1).equals(gender)) {
            currentRank += 1;
            if (row.get(0).equals(name)) {
                return currentRank;
            }
        }
    }
    return -1;
}

public String getName(int year, int rank, String gender) {
    int currentRank = 0;
    FileResource fr = new FileResource(data + "yob" + year + ".csv");
    CSVParser parser = fr.getCSVParser(false);
    for (CSVRecord row : parser) {
        if (row.get(1).equals(gender)) {
            currentRank += 1;
            if (currentRank == rank) {
                String name = row.get(0);
                return name;
            }
        }
    }
    return "NO NAME";
}

public void whatIsNameInYear(String name, int year, int newYear, String gender) {
    int rank = getRank(year, name, gender);
    String newName = getName(newYear, rank, gender);
    System.out.println(name + ", born in " + year + ", would be " + newName + " if they were born in " + newYear);
}

public int yearOFHighestRank(String name, String gender) {
    int rank = -1;
    int bestYear = -1;
    DirectoryResource dr = new DirectoryResource();
    for (File f : dr.selectedFiles()) {
        String fName = f.getName();
        String y = fName.substring(3,7);
        int year = Integer.parseInt(y);
        int currentRank = getRank(year, name, gender);
        if (currentRank != -1 && (rank == -1 || currentRank < rank)) {
            rank = currentRank;
            bestYear = year;
        }
    }
    //System.out.println(name + " was rank # " + rank + " in year " + bestYear);
    return bestYear;
    }

public double getAverageRank(String name, String gender) {
    double total = 0;
    int instances = 0;
    DirectoryResource dr = new DirectoryResource();
    for (File f : dr.selectedFiles()) {
        String fName = f.getName();
        String y = fName.substring(3,7);
        int year = Integer.parseInt(y);
        int currentRank = getRank(year, name, gender);
        if (currentRank != -1) {
            total += currentRank;
            instances++;
        }
    }
    if (total == 0) {
        return -1;
    }
    double average = total / instances;
    return average;
}

public int getTotalBirthsRankedHigher(int year, String name, String gender) {
    int totalBirthsHigher = 0;
    FileResource fr = new FileResource(data + "yob" + year + ".csv");
    CSVParser parser = fr.getCSVParser(false);
    for (CSVRecord row : parser) {
        if (row.get(1).equals(gender)) {
            if (row.get(0).equals(name)) {
                return totalBirthsHigher;
            }
            else {
                int currentBirths = Integer.parseInt(row.get(2));
                totalBirthsHigher += currentBirths;
            }
        }
    }
    return -1;
}

public void testTotalBirths() {
    FileResource fr = new FileResource();
    totalBirths(fr);
}

public void testgetRank() {
    System.out.println("2012 rank for Emma is " + getRank(2012, "Emma", "F"));
    System.out.println("2012 rank for Noah is " + getRank(2012, "Noah", "M"));
    System.out.println("2012 rank for George is " + getRank(2012, "George", "M"));
}

public void testgetName() {
    System.out.println("2012 name for #2F is " + getName(2012, 2, "F"));
    System.out.println("2012 name for #4M is " + getName(2012, 4, "M"));
    System.out.println("2012 name for #13M is " + getName(2012, 13, "M"));
}


}
