import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Play{
    public static void main(String[] args){
        String usrHome = System.getProperty("user.home");
        String fileName;
        fileName = usrHome+"/FutureSoft/JP/LiveList_test.data";

        File file = new File(fileName);

        if (file.exists()){

        }
        try {
            PrintWriter output = new PrintWriter(file);
            output.println("a");

            output.close();
        } catch ( FileNotFoundException e){
            System.out.print(e);
        }



    }
}
