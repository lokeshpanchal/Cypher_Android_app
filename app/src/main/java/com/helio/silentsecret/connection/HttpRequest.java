package com.helio.silentsecret.connection;

import android.content.Context;

import com.helio.silentsecret.models.DoowappDTO;
import com.helio.silentsecret.models.DoowappTagDTO;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Maroof Ahmed Siddique on 10/4/2016.
 */
public class HttpRequest {
    String serviceUrl = "";

    String Baseurl = "http://api.doowapp.me/1.0/";


    // http://api.doowapp.me/1.0/doowapp?tagId=1&fullTrackInfo=true&apikey=573f314f4f979

    String APIkey = "573f314f4f979";
    String TAG = getClass().getSimpleName();
    private String json = null;
    Context mContext;

    public HttpRequest(Context context) {
        super();
        mContext = context;
    }


    public String makeconnection() {


        try {
            URL url = new URL(serviceUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return getStringFromInputStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "";
    }


    public List<DoowappTagDTO> gettagname(int page_number) {
        // http://api.doowapp.me/1.0/doowappTag/category/1.json?apikey=573f314f4f979

        serviceUrl = Baseurl + "doowappTag/category/" + page_number + ".json?apikey=" + APIkey;
        String data = makeconnection();
        return Parsetagname(data);
    }


    public List<DoowappDTO> getaudiobytag(String Tag_id) {
        // http://api.doowapp.me/1.0/doowapp?tagId=1&fullTrackInfo=true&apikey=573f314f4f979

        serviceUrl = Baseurl + "doowapp?tagId=" + Tag_id + "&fullTrackInfo=true&apikey=" + APIkey;
        String data = makeconnection();
        return ParstTagAuodio(data);
    }


    public List<DoowappDTO> getnewsdata(int page_number) {
        serviceUrl = Baseurl + "usage/popularQuotes.json?apikey=" + APIkey + "&page=" + page_number;
        String data = makeconnection();
        return Parsenews(data);
    }

    public List<DoowappDTO> getsearchdata(String searchtext,int panum) {
        String data = "";
        try {

          //  http://api.doowapp.me/1.0/track.json?doowappInfo=true&apikey=573f314f4f979&q=Nothing

            if (searchtext.contains(" "))
                searchtext = searchtext.replace(" ", "%20");
            serviceUrl = Baseurl + "track.json?doowappInfo=true&apikey=" + APIkey + "&q=" + searchtext +"&page="+panum;

            //old  serviceUrl = "http://test.api.doowapp.me/1.0/artist/search.json?&q="+ searchtext+ "&apikey=573f314f4f979";
            data = makeconnection();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ParseSearch(data);
    }


    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }


    private List<DoowappTagDTO> Parsetagname(String jsonResponse) {


        String status = "", id = "", name = "";
        int i;
        List<DoowappTagDTO> doowappTagDTOs = new ArrayList<DoowappTagDTO>();


        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");


                if (status.equals("ok")) {
                    JSONArray json_array = jsonObject.getJSONArray("doowappTags");

                    for (i = 0; i < json_array.length(); i++) {

                        JSONObject jsonObject1 = new JSONObject(json_array.getString(i));
                        id = jsonObject1.getString("id");
                        name = jsonObject1.getString("name");
                        doowappTagDTOs.add(new DoowappTagDTO(name, id));

                    }

                } else
                    doowappTagDTOs = null;

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doowappTagDTOs;
    }


    private List<DoowappDTO> ParstTagAuodio(String jsonResponse) {
        String status = "", lyrics = "", title = "", play_id = "", artist_name = "", image_url = "", pages = "";
        int i;
        List<DoowappDTO> newsDTOs = new ArrayList<DoowappDTO>();


        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                //  Iterator<String> iterator = object.keys();

                if (jsonObject.has("totalItems"))
                    pages = jsonObject.getString("totalItems");

                if (status.equals("ok"))
                {
                    JSONArray doowap_array = null;
                   // String objectlist1 = jsonObject.getString("doowapps");

                    try
                    {
                        doowap_array = jsonObject.getJSONArray("doowapps");
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                   /* if (jsonObject.getClass().isInstance(JSONArray.class))
                    {
                        doowap_array = jsonObject.getJSONArray("doowapps");
                    }*/


                    if (doowap_array != null && doowap_array.length() > 0)
                    {
                        for (i = 0; i < doowap_array.length(); i++)
                        {

                            JSONObject jsonObject1 = new JSONObject(doowap_array.getString(i));




                         /*   String keyobject = allobjects.getString(json_array.get(i));

                            JSONObject jsonObject1 = new JSONObject(keyobject);*/

                            play_id = jsonObject1.getString("id");

                            lyrics = jsonObject1.getString("lyrics");

                            String trackobj = jsonObject1.getString("track");

                            JSONObject trackobject = new JSONObject(trackobj);

                            title = trackobject.getString("title");

                            String Artistobjstring = trackobject.getString("artist");

                            JSONObject Artistobj = new JSONObject(Artistobjstring);

                            artist_name = Artistobj.getString("name");


                            if (trackobject.has("images")) {
                                String imagesobjstring = trackobject.getString("images");

                                JSONObject imageobg = new JSONObject(imagesobjstring);

                                image_url = imageobg.getString("200");
                            } else {
                                image_url = "";
                            }


                            newsDTOs.add(new DoowappDTO(play_id, title, lyrics, artist_name, pages, image_url));

                        }
                    }
                    else
                    {


                        String objectlist = jsonObject.getString("doowapps");


                        JSONObject allobjects = new JSONObject(objectlist);

                        Iterator<String> iterator = allobjects.keys();

                        int count = 0;

                        List<String> json_array = new ArrayList<>();

                        while (iterator.hasNext()) {
                            json_array.add(iterator.next());
                        }

                        for (i = 0; i < json_array.size(); i++)
                        {




                            String keyobject = allobjects.getString(json_array.get(i));

                            JSONObject jsonObject1 = new JSONObject(keyobject);

                            play_id = jsonObject1.getString("id");

                            lyrics = jsonObject1.getString("lyrics");

                            String trackobj = jsonObject1.getString("track");

                            JSONObject trackobject = new JSONObject(trackobj);

                            title = trackobject.getString("title");

                            String Artistobjstring = trackobject.getString("artist");

                            JSONObject Artistobj = new JSONObject(Artistobjstring);

                            artist_name = Artistobj.getString("name");


                            if (trackobject.has("images")) {
                                String imagesobjstring = trackobject.getString("images");

                                JSONObject imageobg = new JSONObject(imagesobjstring);

                                image_url = imageobg.getString("200");
                            } else {
                                image_url = "";
                            }


                            newsDTOs.add(new DoowappDTO(play_id, title, lyrics, artist_name, pages, image_url));

                        }
                    }

                } else
                    newsDTOs = null;

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsDTOs;

    }


    private List<DoowappDTO> Parsenews(String jsonResponse) {


        String status = "", lyrics = "", title = "", play_id = "", artist_name = "", image_url = "", pages = "";
        int i;
        List<DoowappDTO> newsDTOs = new ArrayList<DoowappDTO>();


        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");


                pages = jsonObject.getString("totalItems");
                if (status.equals("ok")) {
                    JSONArray json_array = jsonObject.getJSONArray("quotes");

                    if (json_array != null && json_array.length() > 0) {
                        for (i = 0; i < json_array.length(); i++) {

                            JSONObject jsonObject1 = new JSONObject(json_array.getString(i));


                            String idobject = jsonObject1.getString("id");


                            JSONObject idobj = new JSONObject(idobject);


                            play_id = idobj.getString("q");


                            String valuestring = jsonObject1.getString("value");


                            JSONObject valuobj = new JSONObject(valuestring);


                            title = valuobj.getString("trackName");

                            lyrics = valuobj.getString("lyrics");
                            artist_name = valuobj.getString("artistName");


                            String imagestring = valuobj.getString("images");

                            JSONObject imageonj = new JSONObject(imagestring);
                            image_url = imageonj.getString("200");


                            newsDTOs.add(new DoowappDTO(play_id, title, lyrics, artist_name, pages, image_url));

                        }
                    }

                } else
                    newsDTOs = null;

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsDTOs;
    }


    private List<DoowappDTO> ParseSearch(String jsonResponse) {


        String status = "", lyrics = "", title = "", play_id = "", artist_name = "", image_url = "", pages = "";
        int i;
        List<DoowappDTO> newsDTOs = new ArrayList<DoowappDTO>();


        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                //  Iterator<String> iterator = object.keys();


                pages = jsonObject.getString("totalItems");

                if (status.equals("ok")) {
                    String objectlist = jsonObject.getString("tracks");

                    JSONObject allobjects = new JSONObject(objectlist);

                    Iterator<String> iterator = allobjects.keys();

                    int count = 0;

                    List<String> json_array = new ArrayList<>();

                    while (iterator.hasNext()) {
                        json_array.add(iterator.next());
                    }

                    for (i = 0; i < json_array.size(); i++) {

                        // JSONObject jsonObject1 = new JSONObject(json_array.get(i));


                        String keyobject = allobjects.getString(json_array.get(i));

                        JSONObject jsonObject1 = new JSONObject(keyobject);




                        title = jsonObject1.getString("title");


                        // String Doowapps = jsonObject1.getString("doowapps");

                        JSONArray Doowappsarray = jsonObject1.getJSONArray("doowapps");


                        if (Doowappsarray != null && Doowappsarray.length() > 0) {
                            JSONObject innnerobject = new JSONObject(Doowappsarray.getString(0));

                            lyrics = innnerobject.getString("lyrics");
                            play_id = innnerobject.getString("id");

                        }


                        String Artistobjstring = jsonObject1.getString("artist");

                        JSONObject Artistobj = new JSONObject(Artistobjstring);

                        artist_name = Artistobj.getString("name");


                        if (jsonObject1.has("images")) {
                            String imagesobjstring = jsonObject1.getString("images");

                            JSONObject imageobg = new JSONObject(imagesobjstring);

                            image_url = imageobg.getString("200");
                        } else {
                            image_url = "";
                        }


                        newsDTOs.add(new DoowappDTO(play_id, title, lyrics, artist_name, pages, image_url));

                    }

                } else
                    newsDTOs = null;

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsDTOs;
    }

}