package objConverter;

import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suvo on 26-Dec-15.
 */
public class CollideLoader {
    public static CollideData load(String str){
        List<CollideFace> faces=new ArrayList<>();
        List<Vector3f> vertexts=new ArrayList<>();
        List<Vector3f> normals=new ArrayList<>();
        try {
            FileReader fileReader=new FileReader("res/"+str+".obj");
            BufferedReader  reader= new BufferedReader(fileReader);
            String line;
            while((line=reader.readLine())!=null){
                if(line.startsWith("#")) continue;
                if(line.startsWith("v ")){
                    String s[]=line.split(" ");
                    float x=Float.parseFloat(s[1]);
                    float y=Float.parseFloat(s[2]);
                    float z=Float.parseFloat(s[3]);
                    Vector3f vector3f=new Vector3f(x,y,z);
                    vertexts.add(vector3f);
                }
                else if(line.startsWith("vn ")){
                    String s[]=line.split(" ");
                    float x=Float.parseFloat(s[1]);
                    float y=Float.parseFloat(s[2]);
                    float z=Float.parseFloat(s[3]);
                    Vector3f vector3f=new Vector3f(x,y,z);
                    normals.add(vector3f);
                }
                else if(line.startsWith("f ")){
                    String s[]=line.split(" ");
                    int v1=Integer.parseInt(s[1].split("//")[0]);
                    int v2=Integer.parseInt(s[2].split("//")[0]);
                    int v3=Integer.parseInt(s[3].split("//")[0]);
                    int v4=Integer.parseInt(s[4].split("//")[0]);
                    int n=Integer.parseInt(s[1].split("//")[1]);
                    List<Vector3f> fV=new ArrayList<>();
                    fV.add(vertexts.get(v1-1));
                    fV.add(vertexts.get(v2-1));
                    fV.add(vertexts.get(v3-1));
                    fV.add(vertexts.get(v4-1));
                    CollideFace collideFace=new CollideFace(fV,normals.get(n-1));
                    faces.add(collideFace);
                }
                else continue;


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CollideData(faces,vertexts);

    }
}
