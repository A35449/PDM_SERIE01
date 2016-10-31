package com.example.workstation.pdm_se01.Utils;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.workstation.pdm_se01.DAL.Weather.Bulk;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by workstation on 27/10/2016.
 */

public class Utils {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static List<Bulk> mapBulk(InputStream stream){
        ObjectMapper mapper = new ObjectMapper();
        List<Bulk> list = new ArrayList<>();

        try{
            Scanner s = new Scanner(stream).useDelimiter("\\A");
            String result = IOUtils.toString(stream, StandardCharsets.UTF_8);
            String[] lines = result.split(System.getProperty("line.separator"));
            Bulk bulk;

            for(int i = 0; i < lines.length ; i++) {
                String line = lines[i];
                bulk = mapper.readValue(line,Bulk.class);
                list.add(bulk);
            }

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            return list;
        }

    }
}
