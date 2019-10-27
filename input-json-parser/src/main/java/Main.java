import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.Json;
import javax.json.JsonWriter;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        JsonArrayBuilder arrbuild = Json.createArrayBuilder();
        File file = new File("atm_201909_ix_eng.txt");
        Scanner scanner = new Scanner(new FileInputStream("atm_201909_ix_eng.txt"), "UTF-8");

        if(scanner.hasNextLine()) {
            scanner.nextLine();
        }
        int k=0;
        int haromig=0;
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] splittedline;
            splittedline = line.split("\t");
            String date = splittedline[0];
            Boolean deposit = splittedline[1].equals("Y");

            String[] address = new String[3];
            address[0] = splittedline[2];
            address[1] = splittedline[3];
            address[2] = splittedline[4];

            Float[] coord = new Float[2];
            coord[0] = Float.parseFloat(splittedline[5].replace(',', '.'));
            coord[1] = Float.parseFloat(splittedline[6].replace(',', '.'));

            String day = splittedline[7];

            Integer[] count = new Integer[48];

            for(int j=0; j<48; ++j) {
                count[j] = Integer.parseInt(splittedline[j+8]);
            }

            JsonObjectBuilder obj = Json.createObjectBuilder()
                    .add("id", k)
                    .add("date", date)
                    .add("deposit", deposit)
                    .add("address", Json.createObjectBuilder()
                            .add("postalCode", address[0])
                            .add("city", address[1])
                            .add("streetAddress", address[2]))
                    .add("coord", Json.createObjectBuilder()
                            .add("x", coord[0])
                            .add("y", coord[1]))
                    .add("day", day.trim());
            JsonArrayBuilder arrcount = Json.createArrayBuilder();
            for(int i=0; i<48; ++i) {
                arrcount.add(count[i]);
            }
            obj.add("sections", arrcount);

            arrbuild.add(obj.build());
            ++haromig;
            if(haromig==3) {
                haromig = 0;
                ++k;
            }
        }
        JsonArray arr = arrbuild.build();

        File fileout = new File("data.json");

        FileOutputStream fileOutputStream = new FileOutputStream(fileout);
        if (!file.exists()) {
            file.createNewFile();
        }

        JsonWriter writer = Json.createWriter(fileOutputStream);
        writer.writeArray(arr);
        writer.close();
    }
}